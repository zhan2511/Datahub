package org.example.datahub.task;



import jakarta.persistence.*;
import org.example.datahub.common.BaseEntity;

import java.time.LocalDateTime;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| task_id | 整数 | 主键 (PK) | 唯一标识一个汇总任务 |
//| task_name | 字符串 | 必填 (NOT NULL) | 任务名称 |
//| description | 字符串 | 可空 (NULLABLE) | 任务描述 |
//| template_path | 字符串 | 必填 (NOT NULL) | Excel模板文件存储路径 |
//| dept_id | 整数 | 外键 (FK) | 指向 $Department$ 表 |
//| creator_id | 整数 | 外键 (FK) | 指向 $ResearchAssistant$ 表 |
//| create_time | 时间戳 | 必填 (NOT NULL) | 任务创建时间 |
//| deadline | 时间戳 | 必填 (NOT NULL) | 截止日期 |
//| status | 字符串 | 必填 (NOT NULL), CHECK IN（'已完成-Finished', '正在进行-Ongoing'） | 任务状态 |
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Column(name = "task_name", nullable = false)
    String taskName;

    @Column(name = "description", nullable = true)
    String description;

    @Column(name = "template_path", nullable = false)
    String templatePath;

    @Column(name = "dept_id", nullable = false)
    Long deptId;

    @Column(name = "creator_id", nullable = false)
    Long creatorId;

    @Column(name = "create_time", nullable = false)
    LocalDateTime createTime;

    @Column(name = "deadline", nullable = false)
    LocalDateTime deadline;

    @Column(name = "status", nullable = false)
    String status;

    // ================ Getters ================
    public String getTaskName() {
        return taskName;
    }
    public String getDescription() {
        return description;
    }
    public String getTemplatePath() {
        return templatePath;
    }
    public Long getDeptId() {
        return deptId;
    }
    public Long getCreatorId() {
        return creatorId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }
    public String getStatus() {
        return status;
    }

    // ================ Setters ================
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // ================ Constructors ================
    public Task() {
    }
    public Task(String taskName, String description, String templatePath, Long deptId, Long creatorId, LocalDateTime deadline, String status) {
        this.taskName = taskName;
        this.description = description;
        this.templatePath = templatePath;
        this.deptId = deptId;
        this.creatorId = creatorId;
        this.createTime = LocalDateTime.now();
        this.deadline = deadline;
        this.status = status;
    }
    // Note: Without description
    public Task(String taskName, String templatePath, Long deptId, Long creatorId, LocalDateTime deadline, String status) {
        this.taskName = taskName;
        this.templatePath = templatePath;
        this.deptId = deptId;
        this.creatorId = creatorId;
        this.createTime = LocalDateTime.now();
        this.deadline = deadline;
        this.status = status;
    }

}














