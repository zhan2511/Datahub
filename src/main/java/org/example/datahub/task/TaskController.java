package org.example.datahub.task;


import org.example.datahub.api.TasksApi;
import org.example.datahub.assistant.Assistant;
import org.example.datahub.assistant.AssistantService;
import org.example.datahub.auth.JwtFilter;
import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.department.Department;
import org.example.datahub.department.DepartmentService;
import org.example.datahub.file.ExcelMergerService;
import org.example.datahub.file.File;
import org.example.datahub.file.FileService;
import org.example.datahub.mail.MailService;
import org.example.datahub.model.*;
import org.example.datahub.submission.Submission;
import org.example.datahub.submission.SubmissionService;
import org.example.datahub.teacher.Teacher;
import org.example.datahub.teacher.TeacherService;
import org.example.datahub.user.User;
import org.example.datahub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController implements TasksApi {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final SubmissionService submissionService;
    private final DepartmentService departmentService;
    private final AssistantService assistantService;
    private final UserService userService;
    private final FileService fileService;
    private final MailService mailService;
    private final ExcelMergerService excelMergerService;

    private String tempDir;

    @Autowired
    public TaskController(
        TaskService taskService,
        TeacherService teacherService,
        SubmissionService submissionService,
        DepartmentService departmentService,
        AssistantService assistantService,
        UserService userService,
        FileService fileService,
        MailService mailService,
        ExcelMergerService excelMergerService,
        @Value("${app.temp.dir}") String tempDir
    ) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.submissionService = submissionService;
        this.departmentService = departmentService;
        this.assistantService = assistantService;
        this.userService = userService;
        this.fileService = fileService;
        this.mailService = mailService;
        this.excelMergerService = excelMergerService;
        this.tempDir = tempDir;
    }

    public void checkMailbox(Long assistantId, Long taskId) throws Exception {
        Task task = taskService.getTask(taskId);
        if (task.getStatus().equals("Finished")) {
            return;
        }

        Assistant assistant = assistantService.getAssistantById(assistantId);
        mailService.init(assistant.getAssistantEmail(), assistant.getEmailAppPassword());

        List<Map<String, Object>> emails = mailService.listEmails()
            .stream()
            .filter(email ->
                email.get("subject").equals(task.getTaskName()))
            .toList();
        List<Submission> submissions = submissionService.listSubmissionsByTaskId(taskId);
        List<Teacher> teachers = teacherService.listTeachersByDeptId(task.getDeptId());

        // process emails:  from in teachers, uid not in submissions, sentDate after submission's submittedAt
        for (Map<String, Object> email : emails) {
            String from = (String) email.get("from");
            Long uid = (Long) email.get("uid");
            LocalDateTime sentDate = (LocalDateTime) email.get("sentDate");

            // find teacher by email
            Optional<Teacher> teacherMatch = teachers.stream()
                .filter(teacher -> teacher.getTeacherEmail().equals(from))
                .findFirst();
            if (teacherMatch.isEmpty()) {
                continue;
            }
            Teacher teacher = teacherMatch.get();

            // find submission by teacherId and uid
            Optional<Submission> submissionMatch = submissions.stream()
                .filter(submission -> submission.getTeacherId().equals(teacher.getId()))
                .findFirst();
            if (submissionMatch.isPresent() && submissionMatch.get().getAttachmentEmailUid().equals(uid)) {
                continue;
            }else if (submissionMatch.isPresent()) {
                if (submissionMatch.get().getSubmittedAt().isAfter(sentDate)) {
                    continue;
                }
                submissionService.deleteSubmission(submissionMatch.get().getId());
            }

            // get email details
            Map<String, Object> emailDetails = mailService.getEmailByUid(uid);
            List<Map<String, Object>> attachmentList = (List<Map<String, Object>>) emailDetails.get("attachments");
            if (attachmentList.isEmpty()) {
                continue;
            }
            Map<String, Object> attachment = attachmentList.getFirst();
            Long attachmentFileId = fileService.saveFile(
                teacher.getId(),
                (InputStream) attachment.get("inputStream"),
                (String) attachment.get("filename"),
                (Long) attachment.get("fileSize"),
                (String) attachment.get("fileMineType")
            ).getId();

            // create submission
            submissionService.createSubmission(
                taskId,
                teacher.getId(),
                sentDate,
                uid,
                attachmentFileId,
                (String) emailDetails.get("bodyText")
            );
        }
    }


    @Override
    public ResponseEntity<TaskSubmissionExportResponseDTO> exportTaskSubmissions(Long taskId) {
        // check permission
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Task task = taskService.getTask(taskId);
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(task.getCreatorId())) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to export this task's submissions",
                HttpStatus.FORBIDDEN
            );
        }

        // check mailbox
        String message = "Export submissions successfully.\n";
        try {
            checkMailbox(currentUserId, taskId);
        } catch (Exception e) {
            message += "Failed to check mailbox for new submissions: " + e.getMessage() + "\n";
//            throw new ServiceException(
//                "MAILBOX_CHECK_FAILED",
//                "Failed to check mailbox for new submissions: " + e.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//            );
        }

        List<Submission> submissions = submissionService.listSubmissionsByTaskId(taskId);
        List<String> submissionFilePaths = submissions.stream()
            .map(submission -> {
                File file = fileService.getFileById(submission.getAttachmentFileId());
                return file.getFilePath();
            })
            .toList();
        String templateFilePath = fileService.getFileById(task.getTemplateFileId()).getFilePath();
        String exportFilePath =  tempDir + "/" + task.getTaskName() + ".xlsx";
        try {
            excelMergerService.mergeExcelFiles(
                templateFilePath,
                submissionFilePaths,
                exportFilePath
            );
        } catch (Exception e) {
            throw new ServiceException(
                "EXPORT_FAILED",
                "Failed to export task submissions: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        File exportFile = fileService.saveFile(currentUserId, exportFilePath);
        return ResponseEntity.ok(new TaskSubmissionExportResponseDTO()
            .success(true)
            .data(new TaskSubmissionExportResponseDataDTO()
                .fileId(exportFile.getId())
            )
            .message(message)
        );
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> remindUnsubmittedTeachers(Long taskId) {
        // check permission
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        Task task = taskService.getTask(taskId);
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(task.getCreatorId())) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You don't have permission to remind this task's unsubmitted teachers",
                HttpStatus.FORBIDDEN
            );
        }

        // check mailbox
        try {
            checkMailbox(currentUserId, taskId);
        } catch (Exception e) {
            throw new ServiceException(
                "MAILBOX_CHECK_FAILED",
                "Failed to check mailbox for new submissions: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        List<Submission> submissions = submissionService.listSubmissionsByTaskId(taskId);
        List<Teacher> teachers = teacherService.listTeachersByDeptId(task.getDeptId());
        List<String> unsubmittedTeacherEmails = teachers.stream()
            .filter(teacher -> submissions.stream()
                .noneMatch(submission -> submission.getTeacherId().equals(teacher.getId()))
            )
            .map(Teacher::getTeacherEmail)
            .toList();
        if (unsubmittedTeacherEmails.isEmpty()) {
            return ResponseEntity.ok(new SuccessResponseDTO()
                .success(true)
                .message("No unsubmitted teachers found")
            );
        }else {
            Assistant assistant = assistantService.getAssistantById(currentUserId);
            mailService.init(assistant.getAssistantEmail(), assistant.getEmailAppPassword());

            String subject = "Reminder: Please submit your assignment for task \"" + task.getTaskName() + "\"";
            String content =
                "Dear teachers, \n\n" +
                "This is a friendly reminder to submit your assignment for the task \"" + task.getTaskName() + "\". " +
                "The deadline for submission is " + task.getDeadline().atOffset(ZoneOffset.UTC) + ".\n\n" +
                "Please make sure to submit your assignment before the deadline.\n\n" +
                "Best regards,\n" +
                "DataHub Team";
            String templateFilePath = fileService.getFileById(task.getTemplateFileId()).getFilePath();

            for (String tos : unsubmittedTeacherEmails) {
                try {
                    mailService.sendEmail(tos, subject, content, templateFilePath);
                } catch (Exception e) {
                    throw new ServiceException(
                        "EMAIL_SEND_FAILED",
                        "Failed to send reminder email to " + tos + ": " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                    );
                }
            }

            return ResponseEntity.ok(new SuccessResponseDTO()
                .success(true)
                .message("Reminders sent to " + unsubmittedTeacherEmails.size() + " unsubmitted teachers")
            );
        }
    }

    @Override
    public ResponseEntity<TaskCreateResponseDTO> taskCreate(TaskMetadataDTO metadata, MultipartFile templateFile) {
        // check permission
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

        String message = "Task created successfully.\n";
        User user = userService.getUserById(currentUserId);
        Long assistantId = user.getAssistantId();
        if (assistantId != null && assistantService.getAssistantById(assistantId).getAssistantEmail() != null) {
            Assistant assistant = assistantService.getAssistantById(assistantId);
            mailService.init(assistant.getAssistantEmail(), assistant.getEmailAppPassword());
            String subject = "New task created: \"" + metadata.getTaskName() + "\"";
            String content =
                "Dear Teachers,\n\n" +
                "A new task \"" + metadata.getTaskName() + "\" has been created. "+
                "Please find the details below:\n\n" +
                "Task Name: " + metadata.getTaskName() + "\n" +
                "Description: " + metadata.getDescription() + "\n" +
                "Deadline: " + metadata.getDeadline() + "\n\n" +
                "Please make sure to submit your assignments before the deadline.\n\n" +
                "Attention:\n" +
                "\t1. Please make sure your submission follows the template provided.\n" +
                "\t2. Please send your submission to this email with the subject as the task name.\n\n" +
                "Best regards,\n" +
                "DataHub Team";
            List<Teacher> teachers = teacherService.listTeachersByDeptId(metadata.getDeptId());
            for (Teacher teacher : teachers) {
                try {
                    mailService.sendEmail(teacher.getTeacherEmail(), subject, content, uploadedFile.getFilePath());
                } catch (Exception e) {
                    throw new ServiceException(
                        "EMAIL_SEND_FAILED",
                        "Failed to send task creation email to " + teacher.getTeacherEmail() + ": " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                    );
                }
            }
        }else {
            message += "No assistant found for this task, no email sent.\n";
        }

        return ResponseEntity.ok(new TaskCreateResponseDTO()
            .success(true)
            .data(new TaskCreateResponseDataDTO()
                .taskId(taskId)
            )
            .message(message)
        );
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> taskDelete(Long taskId) {
        // check permission
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
        // TODO: delete submissions related
        return ResponseEntity.ok(new SuccessResponseDTO()
            .success(true)
            .message("Task deleted successfully")
        );
    }

    @Override
    public ResponseEntity<TaskDetailResponseDTO> taskDetail(Long taskId) {
        // check permission
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

        // check mailbox
        String message = "Task detail retrieved successfully.\n";
        try {
            checkMailbox(currentUserId, taskId);
        } catch (Exception e) {
            message += "Failed to check mailbox for new submissions: " + e.getMessage() + "\n";
//            throw new ServiceException(
//                "MAILBOX_CHECK_FAILED",
//                "Failed to check mailbox for new submissions: " + e.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//            );
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
            .message(message)
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
            .message("Task list retrieved successfully.")
        );
    }

    @Override
    public ResponseEntity<TaskTeacherListResponseDTO> taskTeacherList(
        Long taskId, Integer pageNum, Integer pageSize, String statusFilter
    ) {
        // check permission
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

        // check mailbox
        String message = "Task teacher list retrieved successfully.\n";
        try {
            checkMailbox(currentUserId, taskId);
        } catch (Exception e) {
            message += "Failed to check mailbox for new submissions: " + e.getMessage() + "\n";
//            throw new ServiceException(
//                "MAILBOX_CHECK_FAILED",
//                "Failed to check mailbox for new submissions: " + e.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//            );
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
            .message(message)
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

        Long assistantId = userService.getUserById(currentUserId).getAssistantId();
        if (assistantId != null && assistantService.getAssistantById(assistantId).getAssistantEmail() != null) {
            Assistant assistant = assistantService.getAssistantById(assistantId);
            mailService.init(assistant.getAssistantEmail(), assistant.getEmailAppPassword());
            String subject = "Task updated: \"" + metadata.getTaskName() + "\"";
            String content =
                "Dear Teachers,\n\n" +
                "The task \"" + metadata.getTaskName() + "\" has been updated. "+
                "Please find the updated details below:\n\n" +
                "Task Name: " + metadata.getTaskName() + "\n" +
                "Description: " + metadata.getDescription() + "\n" +
                "Deadline: " + metadata.getDeadline() + "\n\n" +
                "Please make sure to submit your assignments before the deadline.\n\n" +
                "Attention:\n" +
                "\t1. Please make sure your submission follows the updated template provided.\n" +
                "\t2. Please send your submission to this email with the subject as the task name.\n\n" +
                "Best regards,\n" +
                "DataHub Team";
            List<Teacher> teachers = teacherService.listTeachersByDeptId(metadata.getDeptId());
            for (Teacher teacher : teachers) {
                try {
                    mailService.sendEmail(teacher.getTeacherEmail(), subject, content, fileService.getFileById(uploadedFileId).getFilePath());
                } catch (Exception e) {
                    throw new ServiceException(
                        "EMAIL_SEND_FAILED",
                        "Failed to send task update email to " + teacher.getTeacherEmail() + ": " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                    );
                }
            }
        }

        return ResponseEntity.ok(new SuccessResponseDTO()
            .success(true)
            .message("Task updated successfully")
        );
    }


}
