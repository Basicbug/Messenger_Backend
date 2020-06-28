package com.basicbug.messenger.api_server.repository.talk;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author JaewonChoi
 */
public interface TalkMessageRepository extends JpaRepository<TalkMessage, Long> {

    //FIXME Native Query 로 수정 필요!
    @Query(value = "select message from TalkMessage where message.mid = :mid and message.roomId = :roomId")
    TalkMessage findTalkMessageByIdAndRoomId(@Param("mid") String mid, @Param("roomId") String roomId);
}
