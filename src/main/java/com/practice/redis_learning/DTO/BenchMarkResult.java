package com.practice.redis_learning.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenchMarkResult {
    private String scenario;
    private String description;
    private long withoutRedisMs;
    private long withRedisMs;
    private double speedupFactor;
    private String verdict;
    private Map<String,Object> details;
}
