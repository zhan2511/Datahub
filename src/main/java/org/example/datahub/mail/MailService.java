package org.example.datahub.mail;

import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.UIDFolder;
import org.example.datahub.common.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class MailService {

    private String email;
    private String password;
    private String mailProvider;
    private String imapServer;
    private String smtpServer;
    private String imapPort = "993";
    private String smtpPort = "587";

    public MailService() {}

    public void init(String email, String password) {
        this.email = email;
        this.password = password;
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new ServiceException("INVALID_EMAIL_ADDRESS", "Invalid email address", HttpStatus.BAD_REQUEST);
        }
        this.mailProvider = email.split("@")[1].split("\\.")[0];
        this.imapServer = "imap." + mailProvider + ".com";
        this.smtpServer = "smtp." + mailProvider + ".com";
    }

    // Note: this method is only used for testing with custom mail servers.
    public void setMailConfig(String mailProvider, String imapServer, String smtpServer, String imapPort, String smtpPort) {
        this.mailProvider = mailProvider;
        this.imapServer = imapServer;
        this.smtpServer = smtpServer;
        this.imapPort = imapPort;
        this.smtpPort = smtpPort;
    }

    public void checkConnection() {
        try {
            getImapStore().close();
            getSmtpSession().getTransport("smtp").connect();
            getSmtpSession().getTransport("smtp").close();
        } catch (Exception e) {
            throw new ServiceException("MAIL_SERVER_CONNECTION_ERROR", "Error connecting to mail server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Session getSmtpSession() {
        Properties props = new Properties();
        if (smtpPort.equals("587")) {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
        }
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public Store getImapStore() throws Exception {
        Properties props = new Properties();
        if (imapPort.equals("993")) {
            props.put("mail.imap.ssl.enable", "true");
        }
        props.put("mail.imap.host", imapServer);
        props.put("mail.imap.port", imapPort);

        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");
        store.connect(email, password);
        return store;
    }

    public void sendEmail(String to, String subject, String content, String attachmentPath) throws Exception {
        Session session = getSmtpSession();

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject, "UTF-8");

        if (attachmentPath == null || attachmentPath.isEmpty()) {
            msg.setText(content, "UTF-8");
            Transport.send(msg);
            return;
        }

        Multipart multipart = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(content, "UTF-8");
        multipart.addBodyPart(textPart);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(attachmentPath);
        multipart.addBodyPart(attachmentPart);

        msg.setContent(multipart);
        Transport.send(msg);
    }

    /**
     * List all emails in the INBOX.
     *
     * @return List of maps: {uid, subject, from, sentDate}
     * @throws Exception If an error occurs during retrieval.
     */
    public List<Map<String, Object>> listEmails() throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        Store store = getImapStore();
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        UIDFolder uidFolder = (UIDFolder) inbox;

        for (Message msg : inbox.getMessages()) {
            Map<String, Object> item = new HashMap<>();
            item.put("uid", uidFolder.getUID(msg));
            item.put("subject", msg.getSubject());
            item.put("from", Arrays.toString(msg.getFrom()));
            item.put("sentDate", msg.getSentDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime());
            result.add(item);
        }

        inbox.close(false);
        store.close();

        return result;
    }

    /**
     * Retrieve email details by UID, including attachments as InputStreams.
     *
     * @param uid The UID of the email to retrieve.
     * @return map: {from, sentDate, bodyText, attachments}
     *         attachments: [{fileName, fileSize, fileMineType, inputStream}]
     * @throws Exception If an error occurs during retrieval.
     */
    public Map<String, Object> getEmailByUid(long uid) throws Exception {
        Map<String, Object> result = new HashMap<>();

        Store store = getImapStore();
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        UIDFolder uidFolder = (UIDFolder) inbox;
        Message msg = uidFolder.getMessageByUID(uid);

        if (msg == null) {
            throw new RuntimeException("Mail UID not found");
        }

        result.put("from", Arrays.toString(msg.getFrom()));
        result.put("sentDate", msg.getSentDate());

        List<Map<String, Object>> attachments = new ArrayList<>();
        if (msg.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) msg.getContent();

            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart part = mp.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    Map<String, Object> att = new HashMap<>();
                    att.put("fileName", part.getFileName());
                    att.put("fileSize", part.getSize());
                    att.put("fileMineType", part.getContentType());

                    DataHandler handler = part.getDataHandler();
                    InputStream is = handler.getInputStream();
                    att.put("inputStream", is);

                    attachments.add(att);
                }else if (part.isMimeType("text/plain")) {
                    result.put("bodyText", part.getContent().toString());
                }
            }
        }

        result.put("attachments", attachments);

        inbox.close(false);
        store.close();

        return result;
    }
}
