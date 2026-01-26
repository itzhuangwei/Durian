package com.zwx.redis.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Redis消息发布者
 *
 * @author zwx
 */
@Service
public class RedisStreamPublisher {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, Map<String, Object> message) {
        redisTemplate.opsForStream().add(channel, message);
    }
}
