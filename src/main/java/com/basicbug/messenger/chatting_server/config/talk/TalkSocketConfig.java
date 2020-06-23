package com.basicbug.messenger.chatting_server.config.talk;

import com.basicbug.messenger.chatting_server.config.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author JaewonChoi
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class TalkSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/stomp", "/stomp/websocket")
            .setAllowedOrigins("*")
            .withSockJS();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void customizeClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        //FIXME add authentication here!
//        messages
//            .nullDestMatcher().authenticated()
//            .nullDestMatcher().permitAll()
//            .simpSubscribeDestMatchers("/sub/talk/room/**").hasRole("USER")
//            .simpDestMatchers("/pub/talk/message/**").hasRole("USER")
//            .anyMessage().denyAll();
    }

}
