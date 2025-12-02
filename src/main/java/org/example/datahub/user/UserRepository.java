package org.example.datahub.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPasswordHash(String username, String password);

    User findByUsername(String userName);

    User findByUserEmail(String userEmail);
}
