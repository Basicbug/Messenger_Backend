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

        String roomId = talkService.getRoomId((String) message.getHeaders().get("simpDestination"));
        String sessionId = (String) message.getHeaders().get("simpSessionId");

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.error(roomId + " " + sessionId + " subscribe");
        } else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
            log.error(roomId + " " + sessionId + " unsubscribe");
        } else if (StompCommand.CONNECT == accessor.getCommand()) {
            log.error(roomId + " " + sessionId + " connect");
        } else if (StompCommand.SEND == accessor.getCommand()) {
            log.error(roomId + " " + sessionId + " send");
        }

        return message;
    }
}
