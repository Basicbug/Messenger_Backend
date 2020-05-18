package com.basicbug.messenger.repository;

import com.basicbug.messenger.model.message.TalkRoom;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Repository
public class TalkRoomRepository {

    private static final String TALK_ROOM = "TALK_ROOM";
    private static final String PARTICIPANT = "PARTICIPANT";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, TalkRoom> hashOpsTalkRoom;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsParticipants;

    public TalkRoom createTalkRoom(String name) {
        TalkRoom talkRoom = TalkRoom.create(name);
        hashOpsTalkRoom.put(TALK_ROOM, talkRoom.getRoomId(), talkRoom);
        return talkRoom;
    }

    public TalkRoom findTalkRoomById(String roomId) {
        return hashOpsTalkRoom.get(TALK_ROOM, roomId);
    }

    /**
     * sessionID 를 가진 사용자가 roomID 를 가진 채팅방에 참여
     * @param sessionId 사용자 sessionID
     * @param roomId 대상 채팅방의 roomID
     */
    public void participateTalkRoom(String sessionId, String roomId) {
        hashOpsParticipants.put(PARTICIPANT, sessionId, roomId);
    }

    public void quitTalkRoom(String sessionId, String roomId) {

    }

}
