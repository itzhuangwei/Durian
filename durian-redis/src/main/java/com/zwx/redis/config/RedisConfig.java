package com.zwx.redis.config;

import com.zwx.redis.service.RedisMessageSubscriber;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 配置监听容器
 * <p>
 * 无需 @EnableRedisListeners 注解（此注解不存在）
 * 通过 RedisMessageListenerContainer 管理消息监听
 * 使用 MessageListener 接口处理消息接收
 * 配置 ChannelTopic 指定监听的频道
 * </p>
 *
 * @author zwx
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisChannelsConfig redisChannelsConfig;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅特定频道
//        container.addMessageListener(new RedisMessageSubscriber(),
//                new PatternTopic("test-channel"));

        // 监听多个频道或使用通配符
        //container.addMessageListener(new RedisMessageSubscriber(),
                //new PatternTopic("message:*"));  // 监听 message: 开头的所有频道
        redisChannelsConfig.getChannels().forEach(channel ->
                container.addMessageListener(new RedisMessageSubscriber(),
                        new PatternTopic(channel)));

        return container;
    }
}
