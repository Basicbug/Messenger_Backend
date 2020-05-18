package com.basicbug.messenger.pubsub;

import com.basicbug.messenger.model.message.TalkMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messageTemplate;

    public void sendMessage(String publishMessage) {
        try {
            TalkMessage talkMessage = objectMapper.readValue(publishMessage, TalkMessage.class);
            messageTemplate.convertAndSend("/sub/talk/room/" + talkMessage.getRoomId(), talkMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
