package org.example.datahub.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(
        String userName,
        String userEmail,
        String passwordHash
    ) {
        return userRepository.save(
            new User(
                userName,
                userEmail,
                passwordHash
            )
        ).getId();
    }


}
