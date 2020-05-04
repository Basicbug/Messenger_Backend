package com.basicbug.messenger.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.basicbug.messenger.model.user.User;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author JaewonChoi
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUid_ShouldReturnUser_WhenValid() {
        String uid = "qwebnm7788";
        String name = "jaewon";

        //given
        userRepository.save(User.builder()
            .uid(uid)
            .password("password")
            .name(name)
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

        //when
        Optional<User> user = userRepository.findByUid(uid);

        //then
        assertThat(user).isNotNull();
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUid()).isEqualTo(uid);
        assertThat(user.get().getName()).isEqualTo(name);
    }

    @Test
    public void findByUidAndProvider_ShouldReturnUser_WhenValid() {
        String uid = "qwebnm7788";
        String name = "jaewon";
        String provider = "naver";

        //given
        userRepository.save(User.builder()
            .uid(uid)
            .name(name)
            .provider(provider)
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

        //when
        Optional<User> user = userRepository.findByUidAndProvider(uid, provider);

        //then
        assertThat(user).isNotNull();
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUid()).isEqualTo(uid);
        assertThat(user.get().getProvider()).isEqualTo(provider);
    }
}