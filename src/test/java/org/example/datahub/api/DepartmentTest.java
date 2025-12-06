package org.example.datahub.api;


import org.example.datahub.auth.JwtUtil;
import org.example.datahub.department.DepartmentService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class DepartmentTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentTest.class);

    private static final String username = "departmentsTest";
    private static final String password = "password";
    private static final String email = "departmentsTest@test.com";
    private static String token;
    private static Long userId;

    private static Long deptId1;
    private static Long deptId2;
    private static Long deptId3;

    @BeforeAll
    void prepare() {
        userId = userService.createUser(username, password, email);
        token = jwtUtil.generateToken(userId);
        deptId1 = departmentService.createDepartment("Engineering");
        logger.info("Created department with ID: " + deptId1);
        deptId2 = departmentService.createDepartment("Marketing");
        logger.info("Created department with ID: " + deptId2);
        deptId3 = departmentService.createDepartment("Sales");
        logger.info("Created department with ID: " + deptId3);
    }

    @AfterAll
    void cleanUp() {
        departmentService.deleteDepartment(deptId1);
        departmentService.deleteDepartment(deptId2);
        departmentService.deleteDepartment(deptId3);
        userService.deleteUser(userId);
    }

    @Test
    @Order(1)
    public void testListDepartments() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/departments")
            .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andReturn();

        logger.info("Response: " + response.getResponse().getContentAsString());
    }
}
