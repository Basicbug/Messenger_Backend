package com.basicbug.messenger.repository.friend;

import com.basicbug.messenger.model.friends.Friends;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    Optional<List<Friends>> findAllByUid(String uid);

    Optional<Friends> findByUidAndFrienduid(String uid, String frienduid);
}