package org.example.datahub.assistant;

import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| assistant_id | 整数 | 主键 (PK) | 唯一标识一个科研秘书 |
//| employee_id | 字符串 | 唯一 (UNIQUE)，必填 (NOT NULL) | 科研秘书工号 |
//| assistant_name | 字符串 | 必填 (NOT NULL) | 科研秘书姓名 |
//| assistant_email | 字符串 | 唯一 (UNIQUE) | 科研秘书邮箱地址 |
//| email_app_password | 字符串 | 可空 (NULLABLE) | 用于发送邮件的应用专用密码 |

@Entity
@Table(name = "assistants")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE assistants SET deleted_at = CURRENT_TIMESTAMP, assistant_email = CONCAT(assistant_email, '_deleted_', id) WHERE id = ?")
public class Assistant extends BaseEntity {
    @Column(name = "employee_id", unique = true, nullable = false)
    String employeeId;

    @Column(name = "assistant_name", nullable = false)
    String assistantName;

    @Column(name = "assistant_email", unique = true)
    String assistantEmail;

    @Column(name = "email_app_password")
    String emailAppPassword;

    // ================ Getters ================
    public String getEmployeeId() {
        return employeeId;
    }
    public String getAssistantName() {
        return assistantName;
    }
    public String getAssistantEmail() {
        return assistantEmail;
    }
    public String getEmailAppPassword() { return emailAppPassword; }

    // =============== Setters ================
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setAssistantName(String assistantName) { this.assistantName = assistantName; }
    public void setAssistantEmail(String assistantEmail) { this.assistantEmail = assistantEmail; }
    public void setEmailAppPassword(String emailAppPassword) { this.emailAppPassword = emailAppPassword; }

    // ================ Constructor ===============
    public Assistant() {
    }
    public Assistant(String employeeId, String assistantName, String assistantEmail, String emailAppPassword) {
        this.employeeId = employeeId;
        this.assistantName = assistantName;
        this.assistantEmail = assistantEmail;
        this.emailAppPassword = emailAppPassword;
    }
}



