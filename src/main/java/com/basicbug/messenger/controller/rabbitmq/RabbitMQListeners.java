package com.basicbug.messenger.controller.rabbitmq;

import com.basicbug.messenger.config.rabbitmq.RabbitMQDevConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@Component
public class RabbitMQListeners {

    @RabbitListener(queues = RabbitMQDevConfig.QUEUE_NAME)
    public void onMessage(Message message) {
        log.info("onMessage " + message.toString());
        
    }
}
