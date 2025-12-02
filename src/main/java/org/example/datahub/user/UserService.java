package org.example.datahub.user;


import jakarta.validation.constraints.NotNull;
import org.example.datahub.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createUser(
        String userName,
        String password,
        String userEmail
    ) {
        String passwordHash = passwordEncoder.encode(password);
        User user = new User(userName, passwordHash, userEmail);
        if (userRepository.findByUsername(userName)!= null) {
            throw new ServiceException("USER_NAME_ALREADY_EXISTS", "User name already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findByUserEmail(userEmail)!= null) {
            throw new ServiceException("USER_EMAIL_ALREADY_EXISTS", "User email already exists", HttpStatus.BAD_REQUEST);
        }
        return userRepository.save(user).getId();
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            return user;
        }else {
            throw new ServiceException("INVALID_CREDENTIALS", "Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }


    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ServiceException("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public void setUserRole(
        Long userId,
        String role,
        Long assistantId
    ) {
        User user = getUserById(userId);
        user.setRole(role);
        user.setAssistantId(assistantId);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
