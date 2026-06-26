package com.practice.redis_learning.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * =====================================
 *        REDIS CONFIGURATION
 * =====================================
 *
 * This class configures:
 * 1) RedisTemplate
 *      - for direct Redis operations (SET, GET, HASH, LIST, ZSET, etc.)
 *
 * 2) StringRedisTemplate
 *      - convenience template for string key-value operations
 *
 * 3) CacheManager
 *      - for @Cacheable / @CachePut / @CacheEvict annotations
 *
 * WHY CUSTOM CONFIG?
 * - Default JdkSerializationRedisSerializer produces unreadable binary in Redis.
 * - We use Jackson2JsonRedisSerializer so data stored in Redis is human-readable JSON.
 * - We configure per-cache TTL (e.g., products cache = 10 min, users cache = 5 min).
 */
@Configuration
public class RedisConfig implements CachingConfigurer {

    /**
     * RedisTemplate<String, Object> – the main workhorse for all Redis operations.
     * Keys are serialized as Strings; Values are serialized as JSON.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // JSON serializer for values
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        GenericJackson2JsonRedisSerializer jsonSerializer =
                new GenericJackson2JsonRedisSerializer(mapper);

        // String serializer for keys
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * CacheManager with per-cache TTL configuration.
     * This powers @Cacheable, @CachePut, @CacheEvict annotations.
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // Default cache config: 10 minutes TTL
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        // Per-cache TTL overrides
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("products",
                defaultConfig.entryTtl(Duration.ofMinutes(10)));
        cacheConfigurations.put("product-by-id",
                defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigurations.put("product-categories",
                defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("users",
                defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("user-by-id",
                defaultConfig.entryTtl(Duration.ofMinutes(10)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
