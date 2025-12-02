package org.example.datahub.department;


import org.example.datahub.api.DepartmentsApi;
import org.example.datahub.model.DepartmentItemDTO;
import org.example.datahub.model.DepartmentListResponseDTO;
import org.example.datahub.model.DepartmentListResponseDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController implements DepartmentsApi {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public ResponseEntity<DepartmentListResponseDTO> departmentList() {
        List<Department> departments = departmentService.getDepartmentList();
        return ResponseEntity.ok(new DepartmentListResponseDTO()
            .success(true)
            .data(new DepartmentListResponseDataDTO()
                .departments(departments.stream()
                    .map(Department -> new DepartmentItemDTO()
                        .deptId(Department.getId())
                        .deptName(Department.getDeptName()))
                    .toList()
                )
            )
        );

    }
}
