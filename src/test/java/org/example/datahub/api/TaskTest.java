package org.example.datahub.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datahub.assistant.AssistantService;
import org.example.datahub.auth.JwtUtil;
import org.example.datahub.department.DepartmentService;
import org.example.datahub.file.ExcelService;
import org.example.datahub.file.FileService;
import org.example.datahub.submission.SubmissionService;
import org.example.datahub.task.TaskService;
import org.example.datahub.teacher.TeacherService;
import org.example.datahub.user.UserService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// Attention: this test does not include the test associated with email,
//            because we cannot use a real email server in the test environment.
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TaskTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskTest.class);

    private static final String assistantUsername = "testAssistant";
    private static final String assistantPassword = "password";
    private static final String assistantEmail = "testAssistant@test.com";
    private static Long assistantUserId;
    private static String assistantToken;

    private static final String adminUsername = "testAdmin";
    private static final String adminPassword = "password";
    private static final String adminEmail = "testAdmin@test.com";
    private static Long adminUserId;
    private static String adminToken;

    private static final String username = "test";
    private static final String password = "password";
    private static final String email = "test@test.com";
    private static Long userId;
    private static String token;

    private static Long deptId;
    private static Long assistantId;
    private static Long teacherId1;
    private static Long teacherId2;
    private static Long teacherId3;
    private static Long teacherId4;

    private final String templateFilePath = "src/test/resources/template.xlsx";
    private MockMultipartFile templateFile;
    private final String submissionFilePath = "src/test/resources/submission.xlsx";
    private MockMultipartFile metadata;

    @BeforeAll
    void prepare() {
        assistantId = assistantService.createAssistant("E123", "Test Assistant", null, null);

        assistantUserId = userService.createUser(assistantUsername, assistantPassword, assistantEmail);
        assistantToken = jwtUtil.generateToken(assistantUserId);
        userService.setUserRole(assistantUserId, "Assistant", assistantId);

        adminUserId = userService.createUser(adminUsername, adminPassword, adminEmail);
        adminToken = jwtUtil.generateToken(adminUserId);
        userService.setUserRole(adminUserId, "Administrator", null);

        userId = userService.createUser(username, password, email);
        token = jwtUtil.generateToken(userId);

        deptId = departmentService.createDepartment("testDepartment");

        teacherId1 = teacherService.createTeacher("zhang", "123", "zhang@test.com", deptId);
        teacherId2 = teacherService.createTeacher("li", "456", "li@test.com", deptId);
        teacherId3 = teacherService.createTeacher("wang", "789", "wang@test.com", deptId);
        teacherId4 = teacherService.createTeacher("zhou", "012", "zhou@test.com", deptId);

        try {
            String[] header = {"姓名", "学号", "成绩"};
            excelService.createExcelFile(templateFilePath, List.of(new String[][]{header}));
            templateFile = new MockMultipartFile(
                "template_file",
                "template.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                Files.readAllBytes(Paths.get(templateFilePath))
            );
            excelService.createExcelFile(submissionFilePath, List.of(
                header,
                new String[]{"张三", "123", "90"},
                new String[]{"李四", "456", "85"}
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Excel file", e);
        }
        String metadataJson = """
            {
                "task_name": "2023年第一学期课程教学情况汇总表",
                "description": "请填写本学期课程教学情况汇总表",
                "deadline": "2023-12-31T23:59:59Z",
                "dept_id": %d
            }
            """.formatted(deptId);
        metadata = new MockMultipartFile("metadata", "", "application/json", metadataJson.getBytes());
    }

    @AfterAll
    void cleanUp() {
        teacherService.deleteTeacher(teacherId1);
        teacherService.deleteTeacher(teacherId2);
        teacherService.deleteTeacher(teacherId3);
        teacherService.deleteTeacher(teacherId4);
        departmentService.deleteDepartment(deptId);
        userService.deleteUser(userId);
        userService.deleteUser(assistantUserId);
        userService.deleteUser(adminUserId);
        assistantService.deleteAssistant(assistantId);

        try {
            Files.deleteIfExists(Paths.get(templateFilePath));
            Files.deleteIfExists(Paths.get(submissionFilePath));
        } catch (Exception e) {
            logger.error("Failed to delete Excel file", e);
        }
    }

    @Test
    @Order(1)
    public void testCreateTaskByAssistant() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/tasks")
            .file(metadata)
            .file(templateFile)
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("multipart/form-data");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.task_id").isNumber())
            .andReturn();

        Long taskId = objectMapper.readTree(response.getResponse().getContentAsString()).path("data").path("task_id").asLong();

        logger.info("Created Task ID: {}", taskId);
    }

    @Test
    @Order(2)
    public void testCreateTaskByAdmin() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/tasks")
            .file(metadata)
            .file(templateFile)
            .header("Authorization", "Bearer " + adminToken)
            .contentType("multipart/form-data");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.task_id").isNumber())
            .andReturn();

        Long taskId = objectMapper.readTree(response.getResponse().getContentAsString()).path("data").path("task_id").asLong();

        logger.info("Created Task ID: {}", taskId);
    }

    @Test
    @Order(3)
    public void testCreateTaskByUserForbidden() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/tasks")
            .file(metadata)
            .file(templateFile)
            .header("Authorization", "Bearer " + token)
            .contentType("multipart/form-data");

        mockMvc
            .perform(request)
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error.code").value("PERMISSION_DENIED"));
    }

    @Test
    @Order(4)
    public void testGetTaskList() throws Exception {
        Long deptId2 = departmentService.createDepartment("testDepartment2");
        taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", 1L, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        taskService.createTask("2023年第二学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", 2L, deptId2, assistantUserId, LocalDateTime.now().plusDays(2), "Finished");
        taskService.createTask("2023年第三学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", 3L, deptId2, assistantUserId, LocalDateTime.now().plusDays(4), "Ongoing");
        taskService.createTask("2023年第四学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", 4L, deptId2, assistantUserId, LocalDateTime.now().plusDays(3), "Ongoing");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/tasks")
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("application/json")
            .param("page_num", "0")
            .param("page_size", "5")
            .param("status", "Ongoing")
            .param("dept_ids", String.valueOf(deptId2))
            .param("order_by", "deadline")
            .param("order_direction", "asc");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tasks").isArray())
            .andExpect(jsonPath("$.data.page.total_pages").value(1))
            .andExpect(jsonPath("$.data.page.page_num").value(0))
            .andExpect(jsonPath("$.data.page.page_size").value(5))
            .andReturn();

        logger.info("Task List Response: {}", response.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    public void testGetTaskDetail() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/tasks/" + taskId)
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("application/json");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.task_id").value(taskId))
            .andExpect(jsonPath("$.data.template_file_id").value(templateFileId))
            .andReturn();

        logger.info("Task Detail Response: {}", response.getResponse().getContentAsString());
    }

    @Test
    @Order(6)
    public void testUpdateTaskWithoutTemplateFile() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/tasks/" + taskId)
            .file(metadata)
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("multipart/form-data");

        mockMvc
            .perform(request)
            .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void testUpdateTaskWithTemplateFile() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/tasks/" + taskId)
            .file(metadata)
            .file(templateFile)
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("multipart/form-data");

        mockMvc
            .perform(request)
            .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    public void testDeleteTaskByAssistant() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/tasks/" + taskId)
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("application/json");

        mockMvc
            .perform(request)
            .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    public void testDeleteTaskByAdmin() throws Exception {
        Long templateFileId = fileService.saveFile(adminUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/tasks/" + taskId)
            .header("Authorization", "Bearer " + adminToken)
            .contentType("application/json");

        mockMvc
            .perform(request)
            .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    public void testDeleteTaskByUserForbidden() throws Exception {
        Long templateFileId = fileService.saveFile(userId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/tasks/" + taskId)
            .header("Authorization", "Bearer " + token)
            .contentType("application/json");

        mockMvc
            .perform(request)
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error.code").value("PERMISSION_DENIED"));
    }

    @Test
    @Order(11)
    public void testGetTeacherListByTask() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        submissionService.createSubmission(taskId, teacherId1, LocalDateTime.now().plusDays(1), null, null, null);
        submissionService.createSubmission(taskId, teacherId2, LocalDateTime.now().plusDays(2), null, null, null);
        submissionService.createSubmission(taskId, teacherId3, LocalDateTime.now().plusDays(3), null, null, null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/tasks/" + taskId + "/teachers")
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("application/json");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.teachers").isArray())
            .andExpect(jsonPath("$.data.page.total_pages").value(1))
            .andExpect(jsonPath("$.data.page.page_num").value(0))
            .andExpect(jsonPath("$.data.page.page_size").value(10))
            .andReturn();

        logger.info("Teacher List by Task Response: {}", response.getResponse().getContentAsString());
    }

    @Test
    @Order(12)
    public void testExportTaskSubmissions() throws Exception {
        Long templateFileId = fileService.saveFile(assistantUserId, this.templateFilePath).getId();
        Long submissionFileId = fileService.saveFile(userId, this.submissionFilePath).getId();
        Long taskId = taskService.createTask("2023年第一学期课程教学情况汇总表", "请填写本学期课程教学情况汇总表", templateFileId, deptId, assistantUserId, LocalDateTime.now().plusDays(1), "Ongoing");
        submissionService.createSubmission(taskId, teacherId1, LocalDateTime.now().plusDays(1), null, submissionFileId, null);
        submissionService.createSubmission(taskId, teacherId2, LocalDateTime.now().plusDays(2), null, submissionFileId, null);
        submissionService.createSubmission(taskId, teacherId3, LocalDateTime.now().plusDays(3), null, submissionFileId, null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/tasks/" + taskId + "/submissions/export")
            .header("Authorization", "Bearer " + assistantToken)
            .contentType("application/json");

        MvcResult response = mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.file_id").isNumber())
            .andReturn();

        logger.info("Task Submissions Export Response: {}", response.getResponse().getContentAsString());
    }
}
