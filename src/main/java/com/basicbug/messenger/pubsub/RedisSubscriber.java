package com.basicbug.messenger.pubsub;

import com.basicbug.messenger.model.message.TalkMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishedMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            TalkMessage talkMessage = objectMapper.readValue(publishedMessage, TalkMessage.class);
            messageTemplate.convertAndSend("/sub/talk/room/" + talkMessage.getRoomId(), talkMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
