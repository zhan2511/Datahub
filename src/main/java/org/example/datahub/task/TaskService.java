package org.example.datahub.task;



import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.model.DepartmentItemDTO;
import org.example.datahub.model.TaskItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public TaskService(
            TaskRepository taskRepository,
            ObjectMapper objectMapper
    ) {
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    public Long createTask(
        String taskName,
        String description,
        String templatePath,
        Long deptId,
        Long creatorId,
        LocalDateTime deadline,
        String status
    ) {
        return taskRepository.save(
            new Task(
                taskName,
                description,
                templatePath,
                deptId,
                creatorId,
                deadline,
                status
            )
        ).getId();
    }


    public void updateTask(
        Long taskId,
        String taskName,
        String description,
        String templatePath,
        Long deptId,
        LocalDateTime deadline,
        String status
    ) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setTemplatePath(templatePath);
        task.setDeptId(deptId);
        task.setDeadline(deadline);
        task.setStatus(status);
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new ServiceException(
            "TASK_NOT_FOUND",
            "Task not found",
            org.springframework.http.HttpStatus.NOT_FOUND
        ));
    }

    public Page<Task> listTasks(
        Integer page_num, // optional, default 0
        Integer page_size, // optional, default 10
        Long creatorId,
        List<Long> deptIds, // optional
        String status, // optional
        String sort_by, // optional, default "id"
        String order // optional, default "asc"
    ) {
        int pageNum = (page_num != null && page_num >= 0) ? page_num : 0;
        int pageSize = (page_size != null && page_size >= 1) ? page_size : 10;
        String orderBy = (sort_by != null && (sort_by.equals("deadline") || sort_by.equals("task_name") || sort_by.equals("create_time"))) ? sort_by : "id";
        String orderDirection = (order != null && (order.equals("asc") || order.equals("desc"))) ? order : "asc";
        Pageable pageable = PageRequest.of(pageNum, pageSize, orderDirection.equals("desc")? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending());
        if (status == null && (deptIds == null || deptIds.isEmpty())) {
            return taskRepository.findAllByCreatorId(creatorId, pageable);
        } else if (status != null && (deptIds == null || deptIds.isEmpty())) {
            return taskRepository.findAllByCreatorIdAndStatus(creatorId, status, pageable);
        } else if (status == null) {
            return taskRepository.findAllByCreatorIdAndDeptIdIn(creatorId, deptIds, pageable);
        } else {
            return taskRepository.findAllByCreatorIdAndStatusAndDeptIdIn(creatorId, status, deptIds, pageable);
        }
    }
}
