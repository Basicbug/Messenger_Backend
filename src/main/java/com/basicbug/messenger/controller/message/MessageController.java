package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.redis.RedisPublisher;
import com.basicbug.messenger.service.talk.TalkRoomService;
import com.basicbug.messenger.service.talk.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final RedisPublisher redisPublisher;
    private final TalkRoomService talkRoomService;

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.debug("sendMessage in /talk/message " + message.getRoomId());
//        redisPublisher.publish(talkRoomService.createOrGetTopic(message.getRoomId()), message);
        redisPublisher.publish(new ChannelTopic("messageQueue"), message);
    }
}
