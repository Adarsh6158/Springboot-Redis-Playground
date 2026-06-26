package com.practice.redis_learning.Config;

import com.practice.redis_learning.PubSub.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * =====================================
 *      REDIS PUB/SUB CONFIGURATION
 * =====================================
 *
 * Configures Redis as a message broker for publish/subscribe pattern.
 *
 * USE CASE:
 * - Real-time notifications
 * - Event broadcasting
 * - Microservice communication
 *
 * FLOW:
 * - Publisher sends message to a "channel" (topic)
 * - All subscribers listening to that channel receive it instantly
 *
 * BENEFIT:
 * - No need for external brokers (Kafka/RabbitMQ) for simple pub/sub
 */

@Configuration
public class RedisPubSubConfig {

    public static final String ORDER_CHANNEL = "order-events";
    public static final String NOTIFICATION_CHANNEL = "notifications";

    @Bean
    public ChannelTopic orderTopic() {
        return new ChannelTopic(ORDER_CHANNEL);
    }

    @Bean
    public ChannelTopic notificationTopic() {
        return new ChannelTopic(NOTIFICATION_CHANNEL);
    }

    @Bean
    public MessageListenerAdapter orderMessageListener(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onOrderEvent");
    }

    @Bean
    public MessageListenerAdapter notificationMessageListener(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onNotification");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter orderMessageListener,
            MessageListenerAdapter notificationMessageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(orderMessageListener, orderTopic());
        container.addMessageListener(notificationMessageListener, notificationTopic());

        return container;
    }
}
