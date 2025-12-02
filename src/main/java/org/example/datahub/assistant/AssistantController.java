package org.example.datahub.assistant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AssistantController {
    private final AssistantService assistantService;

    @Autowired
    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

}
