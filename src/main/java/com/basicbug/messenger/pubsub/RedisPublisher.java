package com.basicbug.messenger.pubsub;

import com.basicbug.messenger.model.message.TalkMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, TalkMessage message) {
        log.error("Redis publish with " + topic.getTopic() + " " + message.getMessage());
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
