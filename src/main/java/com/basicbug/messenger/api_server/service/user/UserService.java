package com.basicbug.messenger.api_server.service.user;

import com.basicbug.messenger.api_server.exception.EmailSigninFailedException;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {

        return userRepository.findByUid(uid).orElseThrow(EmailSigninFailedException::new);
    }

    public List<User> findUsersByUid(List<String> uids) {
        List<User> users = new ArrayList<>();
        for (String uid : uids) {
            User user = userRepository.findByUid(uid).orElse(null);
            // TODO uid 리스트 중 존재하지 않는 사용자가 존재하는 경우의 처리
            if (user != null) {
                users.add(user);
            }
        }

        return users;
    }

    public User findUserByUid(String uid) {
        return userRepository.findByUid(uid).orElse(null);
    }
}
