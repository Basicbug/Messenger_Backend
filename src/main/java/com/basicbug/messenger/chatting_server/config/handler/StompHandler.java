package com.basicbug.messenger.chatting_server.config.handler;

import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import com.basicbug.messenger.api_server.service.talk.TalkService;
import com.basicbug.messenger.auth_server.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final TalkService talkService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info(accessor.getShortLogMessage(null));
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

            try {
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            } catch (Exception e) {
                log.error("StompHandler token is invalid!");
                //TODO 연결 실패 처리
            }

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = talkService.getRoomId(accessor.getDestination());
            String token = jwtTokenProvider.resolveToken(accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));

            log.info("Stomp SUBSCRIBE to ", roomId, token);

            if (!jwtTokenProvider.validateToken(token)) {
                log.info("Stomp subscribe request but invalid token");
                return null;
            }

            User user = userRepository.findByUid(jwtTokenProvider.getUserPk(token)).orElse(null);

            if (user == null) {
                log.info("user is null");
                return null;
            }

            talkService.participateToRoom(roomId, jwtTokenProvider.getUserPk(token));

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
