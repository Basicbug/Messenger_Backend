package com.basicbug.messenger.repository;

import com.basicbug.messenger.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
