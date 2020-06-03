package com.basicbug.messenger.redis;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.service.talk.TalkRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final TalkRoomService talkRoomService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageStr = redisTemplate.getStringSerializer().deserialize(message.getBody());
            TalkMessage publishedMessage = objectMapper.readValue(messageStr, TalkMessage.class);

            log.debug("onMessage to " + publishedMessage.getRoomId() + " from " + publishedMessage.getSenderUid());
            messagingTemplate.convertAndSend(talkRoomService.getTopic(publishedMessage.getRoomId()).getTopic(), publishedMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
