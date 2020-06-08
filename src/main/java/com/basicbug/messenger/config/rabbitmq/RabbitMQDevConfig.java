package com.basicbug.messenger.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author JaewonChoi
 */

@Profile("dev")
@Configuration
public class RabbitMQDevConfig {

    public static final String EXCHANGE_NAME = "message.topic";
    public static final String QUEUE_NAME = "message.room";
    public static final String DEFAULT_ROUTING_KEY = "message.default";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEFAULT_ROUTING_KEY);
    }
}
