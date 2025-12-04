package org.example.datahub.submission;


import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| submission_id | 整数 | 主键 (PK) | 唯一标识一个提交 |
//| task_id | 整数 | 外键 (FK) | 指向 $CollectionTask$ 表 |
//| teacher_id | 整数 | 外键 (FK) | 指向 $Teacher$ 表 |
//| submitted_at | 时间戳 | 可空 (NULLABLE) | 提交时间 |
//| attachment_email_uid | 整数 | 可空 (NULLABLE) | 教师提交的附件在邮箱中的唯一标识 |
//| attachment_file_id     | 整数 | 外键 (FK), 可空 (NULLABLE) | 指向 $File$ 表，存储教师提交的附件信息 |
//| attachment_description | 字符串 | 可空 (NULLABLE) | 教师回复的附件描述 |
@Entity
@Table(name = "submissions")
@SQLDelete(sql = "UPDATE submissions SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Submission extends BaseEntity {
    @Column(name = "task_id", nullable = false)
    Long taskId;

    @Column(name = "teacher_id", nullable = false)
    Long teacherId;

    @Column(name = "submitted_at", nullable = true)
    LocalDateTime submittedAt;

    @Column(name = "attachment_email_uid", nullable = true)
    Long attachmentEmailUid;

    @Column(name = "attachment_file_id", nullable = true)
    Long attachmentFileId;

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
    public Long getAttachmentEmailUid() { return attachmentEmailUid; }
    public Long getAttachmentFileId() { return attachmentFileId; }
    public String getAttachmentDescription() { return attachmentDescription; }

    // ================ Setters ================
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public void setAttachmentEmailUid(Long attachmentEmailUid) { this.attachmentEmailUid = attachmentEmailUid; }
    public void setAttachmentFileId(Long attachmentFileId) { this.attachmentFileId = attachmentFileId; }
    public void setAttachmentDescription(String attachmentDescription) { this.attachmentDescription = attachmentDescription; }

    // ================ Constructors ================
    public Submission() {
    }
    public Submission(Long taskId, Long teacherId, LocalDateTime submittedAt, Long attachmentEmailUid, Long attachmentFileId, String attachmentDescription) {
        this.taskId = taskId;
        this.teacherId = teacherId;
        this.submittedAt = submittedAt;
        this.attachmentEmailUid = attachmentEmailUid;
        this.attachmentFileId = attachmentFileId;
        this.attachmentDescription = attachmentDescription;
    }
    public Submission(Long taskId, Long teacherId) {
        this.taskId = taskId;
        this.teacherId = teacherId;
        this.submittedAt = null;
        this.attachmentEmailUid = null;
        this.attachmentFileId = null;
        this.attachmentDescription = null;
    }

}








