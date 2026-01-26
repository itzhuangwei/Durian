package com.zwx.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 使用配置类集中管理Redis消息监听
 */
@Component
@ConfigurationProperties(prefix = "redis.message")
@Data
public class RedisChannelsConfig {

    private List<String> channels = List.of("default-channel");

}
