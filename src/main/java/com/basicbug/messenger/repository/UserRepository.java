package com.basicbug.messenger.repository;

import com.basicbug.messenger.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUid(String email);
}
