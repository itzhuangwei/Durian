package com.zwx.redis.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Redis消息消费者
 *
 * @author zwx
 */
@Service
public class RedisStreamConsumer {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void consumeMessage(String streamKey, String groupName, String consumerName) {
        // 创建消费者组
        createGroupIfNotExists(streamKey, groupName);

        // 读取消息
        List<MapRecord<String, Object, Object>> records =
                redisTemplate.opsForStream().read(
                        Consumer.from(groupName, consumerName),
                        StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1)),
                        StreamOffset.create(streamKey, ReadOffset.lastConsumed())
                );

        // 处理消息并确认
        if (records != null) {
            for (MapRecord<String, Object, Object> record : records) {
                processMessage(record);
                redisTemplate.opsForStream().acknowledge(streamKey, groupName, record.getId());
            }
        }
    }

    private void processMessage(MapRecord<String, Object, Object> record) {
        // 提取消息内容
        Map<Object, Object> message = record.getValue();

        // 处理具体业务逻辑
        // 例如：保存到数据库、触发业务事件等

        System.out.println("Processing message: " + message);
    }

    public boolean createGroupIfNotExists(String streamKey, String groupName) {
        List<StreamInfo.XInfoGroup> groups = redisTemplate.opsForStream().groups(streamKey).toList();
        boolean exists = groups.stream()
                .anyMatch(group -> group.groupName().equals(groupName));

        if (!exists) {
            redisTemplate.opsForStream().createGroup(streamKey, groupName);
            return true;
        }
        return false;
    }

}
