package org.example.datahub.teacher;


import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| teacher_id | 整数 | 主键 (PK) | 唯一标识一个教师 |
//| employee_id | 字符串 | 唯一 (UNIQUE)，必填 (NOT NULL) | 教师工号 |
//| teacher_name | 字符串 | 必填 (NOT NULL) | 教师姓名 |
//| teacher_email | 字符串 | 唯一 (UNIQUE) | 教师邮箱地址 |
//| dept_id | 整数 | 外键 (FK) | 指向 $Department$ 表 |
@Entity
@Table(name = "teachers")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE teachers " +
    "SET deleted_at = CURRENT_TIMESTAMP, " +
    "employee_id = CONCAT(employee_id, '_deleted_', id), " +
    "teacher_email = CONCAT(teacher_email, '_deleted_', id) " +
    "WHERE id = ?")
public class Teacher extends BaseEntity {
    @Column(name = "employee_id", unique = true, nullable = false)
    String employeeId;

    @Column(name = "teacher_name", nullable = false)
    String teacherName;

    @Column(name = "teacher_email", unique = true)
    String teacherEmail;

    @Column(name = "dept_id")
    Long deptId;

    // ================ Getters ================
    public String getEmployeeId() {
        return employeeId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public Long getDeptId() {
        return deptId;
    }

    // ================ Setters ================
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    // ================ Constructors ================
    public Teacher() {
    }

    public Teacher(String employeeId, String teacherName, String teacherEmail, Long deptId) {
        this.employeeId = employeeId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.deptId = deptId;
    }

    public Teacher(String employeeId, String teacherName) {
        this.employeeId = employeeId;
        this.teacherName = teacherName;
        this.teacherEmail = null;
        this.deptId = null;
    }
}








