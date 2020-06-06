package com.basicbug.messenger.redis;

import com.basicbug.messenger.model.message.TalkMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, TalkMessage message) {
        log.error("Redis publish with " + topic.getTopic() + " " + message.getMessage());
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
