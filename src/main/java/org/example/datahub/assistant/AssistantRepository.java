package org.example.datahub.assistant;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    Assistant findByEmployeeIdAndAssistantName(String employeeId, String assistantName);
}
