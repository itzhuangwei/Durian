package com.zwx.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * Redis消息订阅者
 *
 * @author zwx
 */
@Slf4j
@Component
public class RedisMessageSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {

        log.info("RedisMessageSubscriber-1: " + message);
        log.info("RedisMessageSubscriber-2: " + new String(message.getBody()));

        //处理接收到的消息
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("[Receive channel and message]:{}-{}", channel, body);
    }

    // 注册监听器到特定频道
    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(this);
    }
}
