package org.example.datahub.api;


import org.example.datahub.auth.JwtUtil;
import org.example.datahub.file.FileService;
import org.example.datahub.user.UserService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FileTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(FileTest.class);

    private static final String filePath = "src/test/resources/test-file.txt";
    private static Long fileId;

    private static Long userId;
    private static String token;

    @BeforeAll
    public void prepare() {
        userId = userService.createUser("test", "test", "test@test.com");
        token = jwtUtil.generateToken(userId);

        try {
            Files.writeString(Path.of(filePath), "This is a test file for file upload.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test file.", e);
        }
        fileId = fileService.saveFile(userId, filePath).getId();
    }

    @AfterAll
    public void cleanUp() {
        fileService.deleteFile(fileId);
        userService.deleteUser(userId);
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (Exception e) {
            logger.error("Failed to delete test file.", e);
        }
    }

    @Test
    public void testGetFile() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/files/" + fileId)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andReturn();
        
        byte[] contentAsByteArray = response.getResponse().getContentAsByteArray();
        String content = new String(contentAsByteArray);
        logger.info("File Content: {}", content);
    }

}
