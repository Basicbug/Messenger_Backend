package com.basicbug.messenger.api_server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.basicbug.messenger.api_server.model.friends.Friends;
import com.basicbug.messenger.api_server.repository.friend.FriendsRepository;
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

    private static final String TEST_UID = "qwebnm7788";

    @Test
    public void findAllByUid_ShouldReturnFriendsList() {
        /*
        FIXME
        This test would be fail if data.sql is changed
        because "qwebnm7788" uid has some dummy data.

        Please change below code when data.sql is changed
        or remove dependency to data.sql
         */

        //given
        friendsRepository.save(Friends.builder()
            .uid(TEST_UID)
            .frienduid("A")
            .build());

        friendsRepository.save(Friends.builder()
            .uid(TEST_UID)
            .frienduid("B")
            .build());

        //when
        Optional<List<Friends>> friends = friendsRepository.findAllByUid(TEST_UID);

        //then
        assertThat(friends).isNotNull();
        assertThat(friends.isPresent()).isTrue();
        assertThat(friends.get().size()).isEqualTo(3);
        assertThat(friends.get().get(0).getFrienduid()).isIn("A", "B", "bsgreentea");
    }

    @Test
    public void findUidAndFriendUid_ShouldReturnNonNull_WhenExists() {

        //given
        friendsRepository.save(Friends.builder()
            .uid(TEST_UID)
            .frienduid("A")
            .build());

        //when
        Optional<Friends> friends = friendsRepository.findByUidAndFrienduid(TEST_UID, "A");

        //then
        assertThat(friends).isNotNull();
        assertThat(friends.isPresent()).isTrue();
    }

    @Test
    public void findUidAndFriendUid_ShouldReturnNull_WhenNotExists() {

        //given
        friendsRepository.save(Friends.builder()
            .uid(TEST_UID)
            .frienduid("A")
            .build());

        //when
        Optional<Friends> friends = friendsRepository.findByUidAndFrienduid(TEST_UID, "B");

        //then
        assertThat(friends).isNotNull();
        assertThat(friends.isPresent()).isFalse();
    }
}