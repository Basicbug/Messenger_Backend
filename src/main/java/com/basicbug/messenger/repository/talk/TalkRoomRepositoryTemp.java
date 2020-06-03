package com.basicbug.messenger.repository.talk;

import com.basicbug.messenger.model.message.TalkRoom;
import com.basicbug.messenger.model.user.User;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Repository
public class TalkRoomRepositoryTemp {

    private static final String TALK_ROOM = "TALK_ROOM";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, TalkRoom> hashOpsTalkRoom;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOpsUser;

    public TalkRoom createTalkRoom(String name) {
        TalkRoom talkRoom = TalkRoom.create(name);
        hashOpsTalkRoom.put(TALK_ROOM, talkRoom.getRoomId(), talkRoom);
        return talkRoom;
    }

    public TalkRoom findTalkRoomById(String roomId) {
        return hashOpsTalkRoom.get(TALK_ROOM, roomId);
    }

    public void joinRoom(User user, String roomId) {
        listOpsUser.leftPush(user.getUid(), roomId);
    }

    public void leaveRoom(User user, String roomId) {
        listOpsUser.remove(user.getUid(), 1, roomId);
    }

    /**
     * 유저가 참여하고 있는 채팅방 목록 반환
     * @param user
     * @return
     */
    public TalkRoom findAllTalkRoomWithUser(User user) {
        return null;
    }

}
