package com.basicbug.messenger.chatting_server.controller.rabbitmq;

import com.basicbug.messenger.chatting_server.config.rabbitmq.RabbitMQDevConfig;
import com.basicbug.messenger.api_server.model.message.TalkMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@Component
public class RabbitMQUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(TalkMessage message) {
        log.info("sendMessage");

        if (message != null) {
            rabbitTemplate.setRoutingKey(RabbitMQDevConfig.QUEUE_NAME);
            rabbitTemplate.convertAndSend(RabbitMQDevConfig.EXCHANGE_NAME, RabbitMQDevConfig.DEFAULT_ROUTING_KEY, message);
            log.info("RabbitMQUtil send message");
        }
    }
}
