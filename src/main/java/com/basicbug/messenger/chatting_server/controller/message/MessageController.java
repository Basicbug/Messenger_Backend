package com.basicbug.messenger.chatting_server.controller.message;

import com.basicbug.messenger.chatting_server.config.rabbitmq.RabbitMQDevConfig;
import com.basicbug.messenger.chatting_server.controller.rabbitmq.RabbitMQUtil;
import com.basicbug.messenger.api_server.model.message.TalkMessage;
import com.basicbug.messenger.api_server.service.talk.TalkRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author JaewonChoi
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final RabbitMQUtil rabbitMQUtil;
    private final RabbitTemplate rabbitTemplate;
    private final TalkRoomService talkRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.debug("sendMessage in /talk/message " + message.getRoomId());
        rabbitMQUtil.sendMessage(message);
    }

    @RabbitListener(queues = RabbitMQDevConfig.QUEUE_NAME)
    public void onMessage(TalkMessage message) {
        log.info("onMessage " + message.toString());
        simpMessagingTemplate.convertAndSend("/sub/talk/room/" + message.getRoomId(), message);
    }
}
