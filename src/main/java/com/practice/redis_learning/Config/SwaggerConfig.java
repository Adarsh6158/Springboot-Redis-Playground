package com.practice.redis_learning.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI redisDemoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Redis API")
                        .description("""
                                A practical project covering all major Redis use cases using Spring Boot.

                                **Scenarios covered:**
                                1. **Caching** : @Cacheable / @CachePut / @CacheEvict with Redis
                                2. **Data Structures** : STRING, HASH, LIST, SET, SORTED SET
                                3. **Rate Limiting** : Atomic counters with auto-expiry
                                4. **Session Management** : @RedisHash as primary store
                                5. **Pub/Sub Messaging** : Publish/Subscribe event broadcasting
                                6. **Benchmarks** : Before vs After Redis performance comparison
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Redis Demo")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local dev")
                ));
    }
}
