package org.example.datahub.assistant;

import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| assistant_id | 整数 | 主键 (PK) | 唯一标识一个科研秘书 |
//| employee_id | 字符串 | 唯一 (UNIQUE)，必填 (NOT NULL) | 科研秘书工号 |
//| assistant_name | 字符串 | 必填 (NOT NULL) | 科研秘书姓名 |
//| assistant_email | 字符串 | 唯一 (UNIQUE) | 科研秘书邮箱地址 |

@Entity
@Table(name = "assistants")
public class Assistant extends BaseEntity {
    @Column(name = "employee_id", unique = true, nullable = false)
    String employeeId;

    @Column(name = "assistant_name", nullable = false)
    String assistantName;

    @Column(name = "assistant_email", unique = true)
    String assistantEmail;

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

    // =============== Setters ================
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }
    public void setAssistantEmail(String assistantEmail) {
        this.assistantEmail = assistantEmail;
    }

    // ================ Constructor ===============
    public Assistant() {
    }
    public Assistant(String employeeId, String assistantName, String assistantEmail) {
        this.employeeId = employeeId;
        this.assistantName = assistantName;
        this.assistantEmail = assistantEmail;
    }
}



