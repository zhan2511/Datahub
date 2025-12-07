package org.example.datahub.department;




import jakarta.persistence.*;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//| 属性名 | 类型 | 约束 | 描述 |
//| :--- | :---: | --- | --- |
//| dept_id | 整数 | 主键 (PK) | 唯一标识一个系 |
//| dept_name | 字符串 | 唯一 (UNIQUE), 必填 (NOT NULL) | 系名 |

@Entity
@Table(name = "departments")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE departments SET deleted_at = CURRENT_TIMESTAMP, dept_name = CONCAT(dept_name, '_deleted_', id) WHERE id = ?")
public class Department extends BaseEntity {
    @Column(name = "dept_name", nullable = false, unique = true)
    String deptName;

    // Getters
    public String getDeptName() {
        return deptName;
    }

    // Setters
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    // Constructors
    public Department() {
    }
    public Department(String deptName) {
        this.deptName = deptName;
    }

}




