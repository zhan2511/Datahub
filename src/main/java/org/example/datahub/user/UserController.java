package org.example.datahub.user;


import org.example.datahub.api.UsersApi;
import org.example.datahub.assistant.AssistantService;
import org.example.datahub.auth.JwtFilter;
import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;
    private final AssistantService assistantService;

    @Autowired
    public UserController(UserService userService, AssistantService assistantService) {
        this.userService = userService;
        this.assistantService = assistantService;
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
        );
    }

    @Override
    public ResponseEntity<UserSignUpResponseDTO> userSignUp(UserSignUpRequestDTO userSignUpRequestDTO) {
        Long userId = userService.createUser(
            userSignUpRequestDTO.getUsername(),
            userSignUpRequestDTO.getUserEmail(),
            userSignUpRequestDTO.getPassword()
        );
        return ResponseEntity.ok(new UserSignUpResponseDTO()
            .success(true)
            .data(new UserSignUpResponseDataDTO()
                .userId(userId)
            )
        );
    }

    @Override
    public ResponseEntity<UserVerifyRoleResponseDTO> userVerifyRole(Long userId, UserVerifyRoleRequestDTO userVerifyRoleRequestDTO) {
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        if (!"Administrator".equals(currentUserRole) && !currentUserId.equals(userId)) {
            throw new ServiceException("PERMISSION_DENIED", "You do not have permission to verify this user's role.", HttpStatus.FORBIDDEN);
        }
        Long assistantId = assistantService.verifyRole(userVerifyRoleRequestDTO.getEmployeeId(), userVerifyRoleRequestDTO.getAssistantName());
        return ResponseEntity.ok(new UserVerifyRoleResponseDTO()
            .success(true)
            .data(new UserVerifyRoleResponseDataDTO()
                .assistantId(assistantId)
            )
        );
    }


}
