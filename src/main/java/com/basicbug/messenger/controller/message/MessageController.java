package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.pubsub.RedisPublisher;
import com.basicbug.messenger.repository.TalkRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final RedisPublisher redisPublisher;
    private final TalkRoomRepository talkRoomRepository;

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.error("here " + message.getRoomId() + " " + message.getMessage() + " " + message.getSenderUid());
        redisPublisher.publish(talkRoomRepository.getTopic(message.getRoomId()), message);
    }
}
