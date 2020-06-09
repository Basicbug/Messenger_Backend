package com.basicbug.messenger.chatting_server.controller.rabbitmq;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import com.basicbug.messenger.chatting_server.config.rabbitmq.RabbitMQDevConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@Component
public class RabbitMQListeners {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = RabbitMQDevConfig.QUEUE_NAME)
    public void onMessage(TalkMessage message) {
        log.info("onMessage " + message.toString());
        simpMessagingTemplate.convertAndSend("/sub/talk/room/" + message.getRoomId(), message);
    }
}
