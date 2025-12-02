package org.example.datahub.task;


import org.example.datahub.api.TasksApi;
import org.example.datahub.auth.JwtFilter;
import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.department.Department;
import org.example.datahub.department.DepartmentService;
import org.example.datahub.file.File;
import org.example.datahub.file.FileService;
import org.example.datahub.model.*;
import org.example.datahub.submission.Submission;
import org.example.datahub.submission.SubmissionService;
import org.example.datahub.teacher.Teacher;
import org.example.datahub.teacher.TeacherService;
import org.example.datahub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TaskController implements TasksApi {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final SubmissionService submissionService;
    private final DepartmentService departmentService;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public TaskController(
        TaskService taskService,
        TeacherService teacherService,
        SubmissionService submissionService,
        DepartmentService departmentService,
        UserService userService,
        FileService fileService
    ) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.submissionService = submissionService;
        this.departmentService = departmentService;
        this.userService = userService;
        this.fileService = fileService;
    }



    @Override
    // TODO: implement exportTaskSubmissions
    public ResponseEntity<Resource> exportTaskSubmissions(Long taskId) {
        return null;
    }

    @Override
    // TODO: implement remindUnsubmittedTeachers
    public ResponseEntity<SuccessResponseDTO> remindUnsubmittedTeachers(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<TaskCreateResponseDTO> taskCreate(TaskMetadataDTO metadata, MultipartFile templateFile) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        if (!"Administrator".equals(currentUserRole) && !"Assistant".equals(currentUserRole)) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to create a task",
                HttpStatus.FORBIDDEN
            );
        }
        File uploadedFile = fileService.saveFile(currentUserId, templateFile);
        Long taskId = taskService.createTask(
            metadata.getTaskName(),
            metadata.getDescription(),
            uploadedFile.getId(),
            metadata.getDeptId(),
            currentUserId,
            metadata.getDeadline().toLocalDateTime(),
            "Ongoing"
        );
        return ResponseEntity.ok(new TaskCreateResponseDTO()
            .success(true)
            .data(new TaskCreateResponseDataDTO()
                .taskId(taskId)
            )
        );
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> taskDelete(Long taskId) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Long taskOwner = taskService.getTask(taskId).getCreatorId();
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(taskOwner)) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to delete this task",
                HttpStatus.FORBIDDEN
            );
        }
        fileService.deleteFile(taskService.getTask(taskId).getTemplateFileId());
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new SuccessResponseDTO()
            .success(true)
            .message("Task deleted successfully")
        );
    }

    @Override
    public ResponseEntity<TaskDetailResponseDTO> taskDetail(Long taskId) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Task task = taskService.getTask(taskId);
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(task.getCreatorId())) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to view this task",
                HttpStatus.FORBIDDEN
            );
        }
        Department department = departmentService.getDepartment(task.getDeptId());
        return ResponseEntity.ok(new TaskDetailResponseDTO()
            .success(true)
            .data(new TaskItemDTO()
                .taskId(task.getId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .templateFileId(task.getTemplateFileId())
                .deadline(task.getDeadline().atOffset(ZoneOffset.UTC))
                .status(task.getStatus())
                .createTime(task.getCreateTime().atOffset(ZoneOffset.UTC))
                .department(new DepartmentItemDTO()
                    .deptId(department.getId())
                    .deptName(department.getDeptName())
                )
            )
        );
    }


    @Override
    public ResponseEntity<TaskListResponseDTO> taskList(Integer pageNum, Integer pageSize, String status, List<Long> deptIds, String orderBy, String orderDirection) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        Page<Task> taskPage = taskService.listTasks(
            pageNum,
            pageSize,
            currentUserId,
            deptIds,
            status,
            orderBy,
            orderDirection
        );
        // it's an awful way to convert Task to TaskItemDTO, but it works for now
        List<TaskItemDTO> taskItemDTOList = taskPage.stream()
            .map(task -> new TaskItemDTO()
                .taskId(task.getId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .templateFileId(task.getTemplateFileId())
                .deadline(task.getDeadline().atOffset(ZoneOffset.UTC))
                .status(task.getStatus())
                .createTime(task.getCreateTime().atOffset(ZoneOffset.UTC))
                .department(new DepartmentItemDTO()
                    .deptId(task.getDeptId())
                    .deptName(departmentService.getDepartment(task.getDeptId()).getDeptName())
                )
            )
            .toList();
        return ResponseEntity.ok(new TaskListResponseDTO()
            .success(true)
            .data(new TaskListResponseDataDTO()
                .tasks(taskItemDTOList)
                .page(new PageInfoDTO()
                    .pageNum(taskPage.getNumber())
                    .pageSize(taskPage.getSize())
                    .totalPages(taskPage.getTotalPages())
                )
            )
        );
    }

    @Override
    public ResponseEntity<TaskTeacherListResponseDTO> taskTeacherList(
        Long taskId, Integer pageNum, Integer pageSize, String statusFilter) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Task task = taskService.getTask(taskId);
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(task.getCreatorId())) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to view this task's teachers",
                HttpStatus.FORBIDDEN
            );
        }
        List<Teacher> teachers = teacherService.listTeachersByDeptId(task.getDeptId());
        List<Submission> submissions = submissionService.listSubmissionsByTaskId(taskId);

        List<TaskTeacherItemDTO> filteredList = teachers.stream()
            .map(teacher -> {
                Submission submission = submissions.stream()
                    .filter(s -> s.getTeacherId().equals(teacher.getId()))
                    .findFirst()
                    .orElse(null);

                String status = (submission == null) ? "Pending" : "Submitted";
                if (statusFilter != null && !status.equalsIgnoreCase(statusFilter)) {
                    return null; // 过滤掉不匹配状态
                }

                return new TaskTeacherItemDTO()
                    .teacherId(teacher.getId())
                    .teacherName(teacher.getTeacherName())
                    .teacherEmail(teacher.getTeacherEmail())
                    .submission(new SubmissionStatusItemDTO()
                        .status(status)
                        .submittedAt(submission == null ? null : submission.getSubmittedAt().atOffset(ZoneOffset.UTC))
                        .submissionId(submission == null ? null : submission.getId())
                    );
            })
            .filter(Objects::nonNull)
            .toList();

        int pageNumValue = (pageNum != null && pageNum >= 0) ? pageNum : 0;
        int pageSizeValue = (pageSize != null && pageSize >= 1) ? pageSize : 10;
        int total = filteredList.size();
        int fromIndex = Math.min((pageNumValue) * pageSizeValue, total);
        int toIndex = Math.min(fromIndex + pageSizeValue, total);
        List<TaskTeacherItemDTO> pageList = filteredList.subList(fromIndex, toIndex);

        PageInfoDTO pageInfo = new PageInfoDTO()
            .pageNum(pageNumValue)
            .pageSize(pageSizeValue)
            .totalPages((int) Math.ceil((double) total / pageSizeValue));

        return ResponseEntity.ok(new TaskTeacherListResponseDTO()
            .success(true)
            .data(new TaskTeacherListResponseDataDTO()
                .taskId(taskId)
                .teachers(pageList)
                .page(pageInfo)
            )
        );
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> taskUpdate(Long taskId, TaskMetadataDTO metadata, MultipartFile templateFile) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Task task = taskService.getTask(taskId);
        Long taskOwner = task.getCreatorId();
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(taskOwner)) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to update this task",
                HttpStatus.FORBIDDEN
            );
        }
        Long uploadedFileId = task.getTemplateFileId();
        if (templateFile != null && !templateFile.isEmpty()) {
            File uploadedFile = fileService.saveFile(currentUserId, templateFile);
            fileService.deleteFile(uploadedFileId);
            uploadedFileId = uploadedFile.getId();
        }
        taskService.updateTask(
            taskId,
            metadata.getTaskName(),
            metadata.getDescription(),
            uploadedFileId,
            metadata.getDeptId(),
            metadata.getDeadline().toLocalDateTime(),
            "Ongoing"
        );
        return ResponseEntity.ok(new SuccessResponseDTO()
            .success(true)
            .message("Task updated successfully")
        );
    }


}
