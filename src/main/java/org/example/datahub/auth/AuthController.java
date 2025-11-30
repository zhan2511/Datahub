package org.example.datahub.auth;


import org.example.datahub.api.AuthApi;
import org.example.datahub.model.*;
import org.example.datahub.user.User;
import org.example.datahub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @Override
    public ResponseEntity<UserLoginResponseDTO> userLogin(UserLoginRequestDTO userLoginRequestDTO) {
        String username = userLoginRequestDTO.getUsername();
        String password = userLoginRequestDTO.getPassword();
        User user = userService.getUserByUsernameAndPassword(username, password);
        String token = jwtUtil.generateToken(user.getId());
        return ResponseEntity.ok(new UserLoginResponseDTO()
           .success(true)
           .data(new UserLoginResponseDataDTO()
               .token(token)
               .user(new UserLoginResponseDataUserDTO()
                   .userId(user.getId())
                   .username(user.getUsername())
                   .role(user.getRole())
                )
            )
        );
    }
}
