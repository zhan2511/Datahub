package org.example.datahub.assistant;


import jakarta.validation.constraints.NotNull;
import org.example.datahub.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public Long verifyRole(String employeeId, String assistantName) {
        Long assistantId = assistantRepository.findByEmployeeIdAndAssistantName(employeeId, assistantName);
        if (assistantId == null) {
            throw new ServiceException(
                "ASSISTANT_NOT_FOUND",
                "Assistant not found",
                HttpStatus.NOT_FOUND
            );
        }
        return assistantId;
    }
}
