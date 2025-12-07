package org.example.datahub.user;


import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| user_id | 整数 | 主键 (PK) | 唯一标识一个用户 |
//| username | 字符串 | 唯一 (UNIQUE) | 用于登录，不能重复 |
//| password_hash | 字符串 | 必填 (NOT NULL) | 存储密码的加密值 |
//| user_email | 字符串 | 唯一 (UNIQUE) | 用于找回密码或通知，不能重复 |
//| role | 字符串 | 必填 (NOT NULL), CHECK IN（'管理员'（Administrator）, '科研秘书'（Assistant）, '游客'（Guest）） | 身份/角色 |
//| assistant_id | 整数 | 外键 (FK), 唯一 (UNIQUE), 可空 (NULLABLE) | 指向 $Assistant$ 表。UNIQUE 约束保证一个科研秘书身份只能被一个用户账户绑定。 |
@Entity
@Table(name = "users")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users " +
    "SET deleted_at = CURRENT_TIMESTAMP, " +
    "username = CONCAT(username, '_deleted_', id), " +
    "user_email = CONCAT(user_email, '_deleted_', id), " +
    "role = 'Guest', " +
    "assistant_id = NULL " +
    "WHERE id = ?")
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @Column(name = "user_email", unique = true, nullable = false)
    String userEmail;

    @Column(name = "role", nullable = false)
    String role = "Guest";

    @Column(name = "assistant_id", unique = true, nullable = true)
    Long assistantId;

    // ================ Getters ================
    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getRole() {
        return role;
    }
    public Long getAssistantId() {
        return assistantId;
    }

    // ================ Setters ================
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setAssistantId(Long assistantId) {
        this.assistantId = assistantId;
    }

    // ================ Constructors ================
    public User() {
    }
    public User(String username, String passwordHash, String userEmail, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userEmail = userEmail;
        this.role = role;
        this.assistantId = null;
    }
    public User(String username, String passwordHash, String userEmail) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userEmail = userEmail;
        this.role = "Guest"; // set it again to avoid some exceptions
        this.assistantId = null;
    }
    public User(String username, String passwordHash, String userEmail, String role, Long assistantId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userEmail = userEmail;
        this.role = role;
        this.assistantId = assistantId;
    }


}




