import com.zwx.redis.DurianRedisApplication;
import com.zwx.redis.service.RedisMessagePublisher;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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
}
