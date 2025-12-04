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
        String assistantEmail,
        String emailAppPassword
    ) {
        return assistantRepository.save(
            new Assistant(employeeId, assistantName, assistantEmail, emailAppPassword)
        ).getId();
    }

    public Long getAssistantByEmployeeIdAndAssistantName(String employeeId, String assistantName) {
        Long assistantId = assistantRepository.findByEmployeeIdAndAssistantName(employeeId, assistantName).getId();
        if (assistantId == null) {
            throw new ServiceException(
                "ASSISTANT_NOT_FOUND",
                "Assistant not found",
                HttpStatus.NOT_FOUND
            );
        }
        return assistantId;
    }

    public void deleteAssistant(Long assistantId) {
        if (!assistantRepository.existsById(assistantId)) {
            throw new ServiceException(
                "ASSISTANT_NOT_FOUND",
                "Assistant not found",
                HttpStatus.NOT_FOUND
            );
        }
        assistantRepository.deleteById(assistantId);
    }

    public Assistant getAssistantById(Long assistantId) {
        return assistantRepository.findById(assistantId).orElseThrow(() ->
            new ServiceException(
                "ASSISTANT_NOT_FOUND",
                "Assistant not found",
                HttpStatus.NOT_FOUND
            )
        );
    }

    public void setEmail(Long assistantId, String assistantEmail, String emailAppPassword) {
        Assistant assistant = assistantRepository.findById(assistantId).orElseThrow(() ->
            new ServiceException(
                "ASSISTANT_NOT_FOUND",
                "Assistant not found",
                HttpStatus.NOT_FOUND
            )
        );
        assistant.setAssistantEmail(assistantEmail);
        assistant.setEmailAppPassword(emailAppPassword);
        assistantRepository.save(assistant);
    }
}
