package org.example.datahub.assistant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class AssistantService {
    private final AssistantRepository assistantRepository;

    @Autowired
    public AssistantService(
        AssistantRepository assistantRepository
    ) {
        this.assistantRepository = assistantRepository;
    }

    public Long createAssistant(
        String employeeId,
        String assistantName,
        String assistantEmail
    ) {
        return assistantRepository.save(
            new Assistant(employeeId, assistantName, assistantEmail)
        ).getId();
    }

}
