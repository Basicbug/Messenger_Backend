package com.basicbug.messenger.chatting_server.config.handler;

import com.basicbug.messenger.auth_server.config.security.JwtTokenProvider;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.talk.TalkRoomRepository;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import com.basicbug.messenger.api_server.service.talk.TalkService;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("Stomp preSend");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            if (accessor.getFirstNativeHeader("Authorization") == null) {
                log.info("Stomp connect request but not Authorization header");
                return null;
            }

            String token = jwtTokenProvider.resolveToken(accessor.getFirstNativeHeader("Authorization"));
            log.info("Stomp connect request with " + token);

            if (!jwtTokenProvider.validateToken(token)) {
                log.info("Stomp connect request but invalid token");
                return null;
            }

            //TODO security context 에 user 를 추가..?
            accessor.setUser(jwtTokenProvider.getAuthentication(token));
            log.info("Stomp connect success " + accessor.getUser().getName());
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = talkService.getRoomId(accessor.getDestination());

//            if (talkRoomRepositoryTemp.findTalkRoomById(roomId) == null) {
//                log.error("stomp subscribe to room that does not exists " + roomId);
//                return null;
//            }

            //TODO 명시된 사용자의 구독 목록에 roomId 추가.
            User user = userRepository.findByUid("qwebnm7788").orElse(null);

            if (user == null) {
                log.error("invalid user " + "uid here!!");
                return null;
            }

//            user.participateToRoom(roomId);

        } else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
            String roomId = talkService.getRoomId(accessor.getDestination());
            log.info("stomp.unsubscribe roomId " + roomId);

//            if (talkRoomRepositoryTemp.findTalkRoomById(roomId) == null) {
//                log.error("Unsubscribe to invalid roomId " + roomId);
//                return null;
//            }

            User user = userRepository.findByUid("qwebnm7788").orElse(null);
            if (user == null) {
                log.error("invalid user " + "uid here!!");
                return null;
            }

//            user.leaveFromRoom(roomId);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("stomp disconnect");
        } else if (StompCommand.SEND == accessor.getCommand()) {
            log.info("stomp send");
        }

        return message;
    }
}
