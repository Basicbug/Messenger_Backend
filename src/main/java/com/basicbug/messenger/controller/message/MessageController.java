package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.config.rabbitmq.RabbitMQDevConfig;
import com.basicbug.messenger.controller.rabbitmq.RabbitMQUtil;
import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.redis.RedisPublisher;
import com.basicbug.messenger.service.talk.TalkRoomService;
import com.basicbug.messenger.service.talk.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.debug("sendMessage in /talk/message " + message.getRoomId());
        rabbitMQUtil.sendMessage(message);
    }
}
