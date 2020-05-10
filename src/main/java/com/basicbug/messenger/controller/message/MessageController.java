package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkMessage;
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

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.error("here " + message.getRoomId() + " " + message.getMessage() + " " + message.getSenderUid());
        messagingTemplate.convertAndSend("/sub/talk/room" + message.getRoomId(), "Hello world!");
    }
}
