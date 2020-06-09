package com.basicbug.messenger.api_server.repository.talk;

import com.basicbug.messenger.api_server.model.message.TalkRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface TalkRoomRepository extends JpaRepository<TalkRoom, Long> {

    Optional<TalkRoom> findByRoomId(String roomId);
}
