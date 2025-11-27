package org.example.datahub.user;


import org.example.datahub.api.UsersApi;
import org.example.datahub.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDetailResponseDTO> userDetail(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserSignInResponseDTO> userSignIn(UserSignInRequestDTO userSignInRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> userSignOut() {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> userSignUp(UserSignUpRequestDTO userSignUpRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponseDTO> userVerifyRole(Long userId, UserVerifyRoleRequestDTO userVerifyRoleRequestDTO) {
        return null;
    }
}
