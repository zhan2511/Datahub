package org.example.datahub.teacher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Long createTeacher(
        String teacherName,
        String employeeId,
        String teacherEmail,
        Long deptId
    ) {
        return teacherRepository.save(
            new Teacher(
                employeeId,
                teacherName,
                teacherEmail,
                deptId
            )
        ).getId();
    }

    public List<Teacher> listTeachersByDeptId(
        Long deptId
    ) {
        return teacherRepository.findAllByDeptId(deptId);
    }
}
