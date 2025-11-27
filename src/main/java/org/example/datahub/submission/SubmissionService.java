package org.example.datahub.submission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Long createSubmission(
        Long taskId,
        Long teacherId,
        LocalDateTime submittedAt,
        String attachmentPath,
        String attachmentDescription
    ) {
        return submissionRepository.save(
            new Submission(
                taskId,
                teacherId,
                submittedAt,
                attachmentPath,
                attachmentDescription
            )
        ).getId();
    }
}
