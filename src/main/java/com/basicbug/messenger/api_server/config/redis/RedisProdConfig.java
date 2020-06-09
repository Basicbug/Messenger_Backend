package com.basicbug.messenger.api_server.config.redis;

import io.lettuce.core.RedisURI;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author JaewonChoi
 */
@Profile("prod")
@Configuration
public class RedisProdConfig {

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("talkroom");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() throws URISyntaxException {
        RedisURI redisURI = RedisURI.create(new URI(System.getenv("REDIS_URL")));
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisURI.getHost());
        redisStandaloneConfiguration.setPort(redisURI.getPort());
        redisStandaloneConfiguration.setPassword(redisURI.getPassword());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory, ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }
}
