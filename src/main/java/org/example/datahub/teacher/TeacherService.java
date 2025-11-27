package org.example.datahub.teacher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
