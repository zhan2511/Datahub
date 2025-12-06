package org.example.datahub.api;


import org.example.datahub.auth.JwtUtil;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    private static final String username = "test";
    private static final String password = "password";
    private static final String email = "test@test.com";
    private static Long userId;
    private static String token;

    @BeforeAll
    public void prepare() {
        userId = userService.createUser(username, password, email);
        token = jwtUtil.generateToken(userId);
    }

    @AfterAll
    public void cleanUp() {
        userService.deleteUser(userId);
    }

    @Test
    @Order(1)
    public void testUserSignUp() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users/signup")
            .contentType("application/json")
            .content("""
                {
                    "username": "test2",
                    "password": "password",
                    "user_email": "test2@test.com",
                    "role": "Guest"
                }
                """);

        MvcResult result = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.user_id").isNumber())
            .andReturn();
        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void testUserDetail() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/users/" + userId)
            .header("Authorization", "Bearer " + token)
            .contentType("application/json");

        MvcResult result = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.user.user_id").value(userId))
            .andExpect(jsonPath("$.data.user.username").value(username))
            .andExpect(jsonPath("$.data.user.user_email").value(email))
            .andExpect(jsonPath("$.data.user.role").value("Guest"))
            .andExpect(jsonPath("$.data.user.assistant_id").isEmpty())
            .andReturn();

        logger.info(result.getResponse().getContentAsString());
    }
}