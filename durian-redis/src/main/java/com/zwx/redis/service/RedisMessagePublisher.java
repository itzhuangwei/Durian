package com.zwx.redis.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis消息发布者
 *
 * @author zwx
 */
@Slf4j
@Service
public class RedisMessagePublisher {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, String message) {
        log.info("[Publish channel and message]:{}###{}", channel, message);
        redisTemplate.convertAndSend(channel, message);
    }
}
