package org.example.datahub.department;


import org.example.datahub.model.DepartmentListResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Long createDepartment(String deptName) {
        return departmentRepository.save(new Department(deptName)).getId();
    }

    public Department getDepartment(Long deptId) {
        return departmentRepository.findById(deptId).orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    public Long findDeptIdByName(String deptName) {
        Department department = departmentRepository.findByDeptName(deptName);
        return department != null ? department.getId() : null;
    }


    public List<Department> getDepartmentList() {
        return departmentRepository.findAll();
    }
}
