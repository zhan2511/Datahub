package org.example.datahub.submission;


import jakarta.persistence.*;
import org.example.datahub.common.BaseEntity;

import java.time.LocalDateTime;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| submission_id | 整数 | 主键 (PK) | 唯一标识一个提交 |
//| task_id | 整数 | 外键 (FK) | 指向 $CollectionTask$ 表 |
//| teacher_id | 整数 | 外键 (FK) | 指向 $Teacher$ 表 |
//| submitted_at | 时间戳 | 可空 (NULLABLE) | 提交时间 |
//| attachment_path | 字符串 | 可空 (NULLABLE) | 教师回复的附件存储路径 |
//| attachment_description | 字符串 | 可空 (NULLABLE) | 教师回复的附件描述 |
@Entity
@Table(name = "submissions")
public class Submission extends BaseEntity {
    @Column(name = "task_id", nullable = false)
    Long taskId;

    @Column(name = "teacher_id", nullable = false)
    Long teacherId;

    @Column(name = "submitted_at", nullable = true)
    LocalDateTime submittedAt;

    @Column(name = "attachment_path", nullable = true)
    String attachmentPath;

    @Column(name = "attachment_description", nullable = true)
    String attachmentDescription;


    // ================ Getters ================
    public Long getTaskId() {
        return taskId;
    }
    public Long getTeacherId() {
        return teacherId;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public String getAttachmentPath() {
        return attachmentPath;
    }
    public String getAttachmentDescription() {
        return attachmentDescription;
    }

    // ================ Setters ================
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
    public void setAttachmentDescription(String attachmentDescription) {
        this.attachmentDescription = attachmentDescription;
    }

    // ================ Constructors ================
    public Submission() {
    }
    public Submission(Long taskId, Long teacherId, LocalDateTime submittedAt, String attachmentPath, String attachmentDescription) {
        this.taskId = taskId;
        this.teacherId = teacherId;
        this.submittedAt = submittedAt;
        this.attachmentPath = attachmentPath;
        this.attachmentDescription = attachmentDescription;
    }
    public Submission(Long taskId, Long teacherId) {
        this.taskId = taskId;
        this.teacherId = teacherId;
        this.submittedAt = null;
        this.attachmentPath = null;
        this.attachmentDescription = null;
    }

}








