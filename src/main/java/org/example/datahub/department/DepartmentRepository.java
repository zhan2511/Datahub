package org.example.datahub.department;


import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDeptName(String deptName);
}
