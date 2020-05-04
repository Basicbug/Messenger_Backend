package com.basicbug.messenger.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.basicbug.messenger.model.friends.Friends;
import java.util.List;
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
public class FriendsRepositoryTest {

    @Autowired
    private FriendsRepository friendsRepository;

    @Test
    public void findAllByUid_ShouldReturnFriendsList() {
        /*
        FIXME
        This test would be fail if data.sql is changed
        because "qwebnm7788" uid has some dummy data.

        Please change below code when data.sql is changed
        or remove dependency to data.sql
         */

        String uid = "qwebnm7788";

        //given
        friendsRepository.save(Friends.builder()
            .uid(uid)
            .frienduid("A")
            .build());

        friendsRepository.save(Friends.builder()
            .uid(uid)
            .frienduid("B")
            .build());

        //when
        Optional<List<Friends>> friends = friendsRepository.findAllByUid(uid);

        //then
        assertThat(friends).isNotNull();
        assertThat(friends.isPresent()).isTrue();
        assertThat(friends.get().size()).isEqualTo(3);
        assertThat(friends.get().get(0).getFrienduid()).isIn("A", "B", "bsgreentea");
    }
}