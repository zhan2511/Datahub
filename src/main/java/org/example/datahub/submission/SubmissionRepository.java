package org.example.datahub.submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByTaskId(Long taskId);

    Submission findByTaskIdAndTeacherId(Long taskId, Long teacherId);
}