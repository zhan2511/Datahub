package org.example.datahub.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByDeptId(Long deptId);

    Page<Teacher> findByDeptId(Long deptId, Pageable pageable);
}
