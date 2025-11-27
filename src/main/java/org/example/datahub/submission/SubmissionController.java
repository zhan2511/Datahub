package org.example.datahub.submission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmissionController {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionController(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }
}
