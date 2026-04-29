package com.practice.redis_learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RedisLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisLearningApplication.class, args);
	}

}
