import com.zwx.redis.DurianRedisApplication;
import com.zwx.redis.service.RedisMessagePublisher;
import com.zwx.redis.service.RedisStreamConsumer;
import com.zwx.redis.service.RedisStreamPublisher;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Redis模块测试类
 * <p>
 * <h1>集成测试要点:</h1>
 * 连接池配置：验证 Redis 连接池参数设置
 * 序列化器：确认 JSON 序列化器工作正常
 * 消息监听：测试消息发布订阅机制
 * 异常处理：验证 Redis 连接异常处理机制
 * </p>
 * <p>
 * <h1>性能测试要点:</h1>
 * 并发访问测试
 * 大量数据存取性能验证
 * 消息队列吞吐量测试
 * </p>
 *
 * @author zwx
 */
@SpringBootTest(classes = DurianRedisApplication.class)
public class RedisModuleTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedisMessagePublisher redisMessagePublisher;
    @Autowired
    private RedisStreamPublisher streamPublisher;
    @Autowired
    private RedisStreamConsumer streamConsumer;

    @Test
    void testRedisConnection() {
        redisTemplate.opsForValue().set("test-key", "test-value");
        String value = (String) redisTemplate.opsForValue().get("test-key");
        assertEquals("test-value", value);
    }


    @Test
    void testRedisPubSub() {
        // 发布消息
        redisMessagePublisher.publish("test-channel", "Hello Redis");
        //redisTemplate.convertAndSend("test-channel", "Hello Redis");

        try {
            Thread.sleep(1000); // 等待消息处理
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // 验证消息发送功能正常
        // 可通过订阅者日志验证接收情况
    }

    /**
     * 测试发送消息到 Redis 流
     */
    @Test
    void testSendMessageToStream() {
        Map<String, Object> message = new HashMap<>();
        message.put("id", "1");
        message.put("content", "test message");

        streamPublisher.publish("test-stream", message);

        // 验证消息已添加到流
        Long size = redisTemplate.opsForStream().size("test-stream");
        assertThat(size).isGreaterThan(0);
    }

    /**
     * 消费者组创建测试
     */
    @Test
    void testCreateConsumerGroup() {
        testSendMessageToStream();

        String streamKey = "test-stream";
        String groupName = "test-group";
        String consumerName = "test-consumer";


        // 验证消费者组已创建
        streamConsumer.consumeMessage(streamKey, groupName, consumerName);
    }

    public List<MapRecord<String, Object, Object>> readFromExistingGroup(
            String streamKey, String groupName, String consumerName) {

        // 直接尝试读取，让 Redis 处理组存在的情况
        return redisTemplate.opsForStream().read(
                Consumer.from(groupName, consumerName),
                StreamReadOptions.empty().count(1).block(Duration.ofMillis(100)),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed())
        );
    }


}
