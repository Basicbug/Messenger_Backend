package com.basicbug.messenger.repository.user;

import com.basicbug.messenger.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUid(String uid);

    Optional<User> findByUidAndProvider(String uid, String provider);
}
