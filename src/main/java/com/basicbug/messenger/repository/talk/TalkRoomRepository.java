package com.basicbug.messenger.repository.talk;

import com.basicbug.messenger.model.message.TalkRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface TalkRoomRepository extends JpaRepository<TalkRoom, Long> {

    Optional<TalkRoom> findByRoomId(String roomId);
}
