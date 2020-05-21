package com.basicbug.messenger.service.user;

import com.basicbug.messenger.exception.EmailSigninFailedException;
import com.basicbug.messenger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        //TODO userPk needs to be renamed

        return userRepository.findById(Long.valueOf(userPk)).orElseThrow(EmailSigninFailedException::new);
    }
}
