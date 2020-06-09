package com.basicbug.messenger.api_server.repository.friend;

import com.basicbug.messenger.api_server.model.friends.Friends;
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
