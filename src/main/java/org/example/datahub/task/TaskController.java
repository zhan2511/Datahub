package org.example.datahub.task;


import org.example.datahub.api.TasksApi;
import org.example.datahub.department.Department;
import org.example.datahub.department.DepartmentService;
import org.example.datahub.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.List;

@RestController
public class TaskController implements TasksApi {
    private final TaskService taskService;
    private final DepartmentService departmentService;

    @Autowired
    public TaskController (
            TaskService taskService,
            DepartmentService departmentService
    ) {
        this.taskService = taskService;
        this.departmentService = departmentService;
    }


    @Override
    public ResponseEntity<Resource> exportTaskSubmissions(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> remindUnsubmittedTeachers(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<TaskCreateResponseDTO> taskCreate(TaskCreateRequestDTO taskCreateRequestDTO) {
        Long taskId = taskService.createTask(
                taskCreateRequestDTO.getTaskName(),
                taskCreateRequestDTO.getDescription(),
                taskCreateRequestDTO.getTemplatePath(),
                taskCreateRequestDTO.getDeptId(),
                1L, // TODO: replace with current user id
                taskCreateRequestDTO.getDeadline().toLocalDateTime(),
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
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new SuccessResponseDTO()
               .success(true)
               .message("Task deleted successfully")
        );
    }

    @Override
    public ResponseEntity<TaskDetailResponseDTO> taskDetail(Long taskId) {
        Task task = taskService.getTask(taskId);
        Department department = departmentService.getDepartment(task.getDeptId());
        return ResponseEntity.ok(new TaskDetailResponseDTO()
               .success(true)
               .data(new TaskItemDTO()
                       .taskId(task.getId())
                       .taskName(task.getTaskName())
                       .description(task.getDescription())
                       .templatePath(task.getTemplatePath())
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
        Page<Task> taskPage = taskService.listTasks(
                pageNum,
                pageSize,
                1L, // TODO: replace with current user id
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
                       .templatePath(task.getTemplatePath())
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

    // TODO: implement this method
    @Override
    public ResponseEntity<TaskTeacherListResponseDTO> taskTeacherList(Long taskId, Integer pageNum, Integer pageSize, String status) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> taskUpdate(Long taskId, TaskCreateRequestDTO taskCreateRequestDTO) {
        taskService.updateTask(
                taskId,
                taskCreateRequestDTO.getTaskName(),
                taskCreateRequestDTO.getDescription(),
                taskCreateRequestDTO.getTemplatePath(),
                taskCreateRequestDTO.getDeptId(),
                taskCreateRequestDTO.getDeadline().toLocalDateTime(),
                "Ongoing"
        );
        return ResponseEntity.ok(new SuccessResponseDTO()
               .success(true)
               .message("Task updated successfully")
        );
    }


}
