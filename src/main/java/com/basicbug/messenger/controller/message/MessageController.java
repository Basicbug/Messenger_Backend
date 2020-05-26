package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.pubsub.RedisPublisher;
import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import com.basicbug.messenger.service.talk.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final TalkService talkService;
    private final ChannelTopic channelTopic;

    @MessageMapping("/talk/message")
    public void sendMessage(TalkMessage message) {
        log.error("MessageController " + message.getRoomId() + " " + message.getMessage() + " " + message.getSenderUid());
        talkService.sendTalkMessage(message);
    }
}
