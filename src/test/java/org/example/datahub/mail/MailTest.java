package org.example.datahub.mail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MailTest {

    private GreenMail greenMail;
    private MailService mailService;

    private final String testEmail = "test@gmail.com";

    private static final String attachmentPath = "src/test/resources/test-attachment.txt";

    @BeforeAll
    static void prepare() {
        try {
            java.nio.file.Files.writeString(
                java.nio.file.Path.of(attachmentPath),
                "attachment content"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create attachment file for testing.", e);
        }
    }

    @BeforeEach
    void setUp() {
        // Launch a local SMTP and IMAP server for testing
        greenMail = new GreenMail(ServerSetupTest.SMTP_IMAP);
        greenMail.start();

        mailService = new MailService();
        // GreenMail uses email as password by default
        mailService.init(testEmail, testEmail);
        mailService.setMailConfig("localhost", "localhost", "localhost", "3143", "3025");
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @AfterAll
    static void afterAll() {
        try {
            java.nio.file.Files.delete(java.nio.file.Path.of(attachmentPath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attachment file for testing.", e);
        }
    }

    @Test
    @Order(1)
    void sendEmailWithoutAttachment() throws Exception {
        String to = "receiver@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        mailService.sendEmail(to, subject, content, null);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage msg = receivedMessages[0];
        assertEquals(subject, msg.getSubject());
        assertEquals(content, msg.getContent().toString().trim());
        assertEquals(to, msg.getAllRecipients()[0].toString());
    }

    @Test
    @Order(2)
    void sendEmailWithAttachment() throws Exception {
        String to = "receiver@example.com";
        String subject = "Test Subject With Attachment";
        String content = "Test Content";

        mailService.sendEmail(to, subject, content, attachmentPath);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage msg = receivedMessages[0];

        assertInstanceOf(MimeMultipart.class, msg.getContent());
        jakarta.mail.internet.MimeMultipart multipart =
            (jakarta.mail.internet.MimeMultipart) msg.getContent();

        assertEquals(2, multipart.getCount());
    }

    @Test
    @Order(3)
    void listEmails() throws Exception {
        GreenMailUtil.sendTextEmailTest(
            testEmail,
            "sender1@example.com",
            "Subject A",
            "Body A"
        );
        GreenMailUtil.sendTextEmailTest(
            testEmail,
            "sender2@example.com",
            "Subject B",
            "Body B"
        );
        GreenMailUtil.sendTextEmailTest(
            testEmail,
            "sender3@example.com",
            "Subject C",
            "Body C"
        );

        List<Map<String, Object>> emails = mailService.listEmails(null, null);

        assertEquals(3, emails.size());

        assertEquals("Subject A", emails.get(0).get("subject"));
        assertEquals("Subject B", emails.get(1).get("subject"));
        assertEquals("Subject C", emails.get(2).get("subject"));
    }
}
