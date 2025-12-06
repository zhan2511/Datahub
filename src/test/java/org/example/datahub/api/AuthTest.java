package org.example.datahub.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datahub.auth.JwtUtil;
import org.example.datahub.user.UserService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthTest.class);

    private static Long userId;
    private static final String username = "authTest";
    private static final String password = "password";
    private static final String email = "authTest@test.com";

    @BeforeAll
    void prepare() {
        userId = userService.createUser(username, password, email);
    }

    @AfterAll
    void cleanUp() {
        userService.deleteUser(userId);
    }

    @Test
    @Order(1)
    public void testUserLogin() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/auth/login")
            .contentType("application/json")
            .content(
            """
                {
                     "username": "authTest",
                     "password": "password"
                }
            """
            );

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.token").isNotEmpty())
            .andExpect(jsonPath("$.data.user.user_id").value(userId))
            .andExpect(jsonPath("$.data.user.username").value(username))
            .andExpect(jsonPath("$.data.user.role").value("Guest"))
            .andReturn();

        String responseContent = response.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(responseContent);
        String token = rootNode.path("data").path("token").asText();
        Long userId = rootNode.path("data").path("user").path("user_id").asLong();

        logger.info("Token: {}", token);
        logger.info("User ID: {}", userId);
        if (!userId.equals(jwtUtil.getCurrentUserId(token))) {
            throw new Exception("Token and User ID do not match.");
        }

        logger.info(responseContent);
    }
}
