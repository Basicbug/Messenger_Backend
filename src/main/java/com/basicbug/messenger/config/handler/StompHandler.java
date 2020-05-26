package com.basicbug.messenger.config.handler;

import com.basicbug.messenger.model.user.User;
import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import com.basicbug.messenger.repository.user.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            //TODO connection needs to be authenticated. also simpUser header needs to be added.
            log.info("connect request");
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = talkService.getRoomId(accessor.getDestination());
            log.info("stomp subscribe to roomId " + roomId);

            if (talkRoomRepository.findTalkRoomById(roomId) == null) {
                log.error("stomp subscribe to room that does not exists " + roomId);
                return null;
            }

            //TODO 명시된 사용자의 구독 목록에 roomId 추가.
            User user = userRepository.findByUid("qwebnm7788").orElse(null);

            if (user == null) {
                log.error("invalid user " + "uid here!!");
                return null;
            }

            user.participateToRoom(roomId);

        } else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
            String roomId = talkService.getRoomId(accessor.getDestination());
            log.info("stomp.unsubscribe roomId " + roomId);

            if (talkRoomRepository.findTalkRoomById(roomId) == null) {
                log.error("Unsubscribe to invalid roomId " + roomId);
                return null;
            }

            User user = userRepository.findByUid("qwebnm7788").orElse(null);
            if (user == null) {
                log.error("invalid user " + "uid here!!");
                return null;
            }

            user.leaveFromRoom(roomId);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("stomp disconnect");
        } else if (StompCommand.SEND == accessor.getCommand()) {
            log.info("stomp send");
        }

        return message;
    }
}
