package org.example.datahub.user;


import org.example.datahub.api.UsersApi;
import org.example.datahub.assistant.Assistant;
import org.example.datahub.assistant.AssistantService;
import org.example.datahub.auth.JwtFilter;
import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.mail.MailService;
import org.example.datahub.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController implements UsersApi {
    private final UserService userService;
    private final AssistantService assistantService;
    private final MailService mailService;

    @Autowired
    public UserController(
        UserService userService,
        AssistantService assistantService,
        MailService mailService
    ) {
        this.userService = userService;
        this.assistantService = assistantService;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<UserDetailResponseDTO> userDetail(Long userId) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(userId)) {
            throw new ServiceException("PERMISSION_DENIED", "You do not have permission to access this user's details.", HttpStatus.FORBIDDEN);
        }
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(new UserDetailResponseDTO()
            .success(true)
            .data(new UserDetailResponseDataDTO()
                .user(new UserDetailResponseDataUserDTO()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .userEmail(user.getUserEmail())
                    .role(user.getRole())
                    .assistantId(user.getAssistantId())
                )
            )
            .message("User details retrieved successfully.")
        );
    }

    @Override
    public ResponseEntity<UserSignUpResponseDTO> userSignUp(UserSignUpRequestDTO userSignUpRequestDTO) {
        Long userId = userService.createUser(
            userSignUpRequestDTO.getUsername(),
            userSignUpRequestDTO.getPassword(),
            userSignUpRequestDTO.getUserEmail()
        );
        return ResponseEntity.ok(new UserSignUpResponseDTO()
            .success(true)
            .data(new UserSignUpResponseDataDTO()
                .userId(userId)
            )
            .message("User created successfully.")
        );
    }

    @Override
    public ResponseEntity<UserVerifyRoleResponseDTO> userVerifyRole(Long userId, UserVerifyRoleRequestDTO userVerifyRoleRequestDTO) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(userId)) {
            throw new ServiceException("PERMISSION_DENIED", "You do not have permission to verify this user's role.", HttpStatus.FORBIDDEN);
        }
        Long assistantId = assistantService.getAssistantByEmployeeIdAndAssistantName(userVerifyRoleRequestDTO.getEmployeeId(), userVerifyRoleRequestDTO.getAssistantName());
        if (assistantId == null) {
            throw new ServiceException("ASSISTANT_NOT_FOUND", "Assistant not found", HttpStatus.NOT_FOUND);
        }
        assistantService.setEmailAppPassword(assistantId, userVerifyRoleRequestDTO.getEmailAppPassword());

        // check mail server connection
        String message = "User verify role successfully.";
        Assistant assistant = assistantService.getAssistantById(assistantId);
        try {
            mailService.init(assistant.getAssistantEmail(), assistant.getEmailAppPassword());
            mailService.checkConnection();
        } catch (Exception e) {
            message = "Error connecting to mail server.";
//            throw new ServiceException("MAIL_SERVER_ERROR", "Error connecting to mail server", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userService.setUserRole(userId, "Assistant", assistantId);
        return ResponseEntity.ok(new UserVerifyRoleResponseDTO()
            .success(true)
            .data(new UserVerifyRoleResponseDataDTO()
                .assistantId(assistantId)
            )
            .message(message)
        );
    }


}
