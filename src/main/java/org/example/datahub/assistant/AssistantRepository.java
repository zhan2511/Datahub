package org.example.datahub.assistant;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    Long findByEmployeeIdAndAssistantName(String employeeId, String assistantName);
}
