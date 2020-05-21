package com.basicbug.messenger.config.handler;

import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import com.basicbug.messenger.service.talk.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final TalkService talkService;
    private final TalkRoomRepository talkRoomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.error("preSend with SUBSCRIBE");
            //TODO session ID 가 항상 고정 값인지, 매번 접속마다 변하는 값인지 확인 필요.
            String roomId = talkService.getRoomId((String) message.getHeaders().get("simpDestination"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");


        } else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {

        }

        log.error("StompHandler preSend");
        return message;
    }
}
