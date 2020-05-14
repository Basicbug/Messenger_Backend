package com.basicbug.messenger.repository;

import com.basicbug.messenger.model.message.TalkRoom;
import com.basicbug.messenger.pubsub.RedisSubscriber;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Repository
public class TalkRoomRepository {

    private static final String TALK_ROOM = "TALK_ROOM";

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, TalkRoom> opsHashTalkRoom;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashTalkRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public TalkRoom createTalkRoom(String name) {
        TalkRoom talkRoom = TalkRoom.create(name);
        opsHashTalkRoom.put(TALK_ROOM, talkRoom.getRoomId(), talkRoom);

        return talkRoom;
    }

    public TalkRoom findTalkRoomById(String roomId) {
        return opsHashTalkRoom.get(TALK_ROOM, roomId);
    }

    public void enterTalkRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

}
