import com.zwx.durian.mq.producer.ProducerApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ProducerApplication.class})
public class ProducerTest {

    private static final String QUEUE_NAME = "test-simple-queue";
    private static final String EXCHANGE_NAME = null;
    private static final String WORK_QUEUE_NAME = "test.work.queue";

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 简单消息队列模式
     */
    @Test
    public void testSendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, "hello world, rabbitmq-4");
    }

    /**
     * 工作队列模式
     */
    @Test
    public void testSendWorkMessage() {
        for (int i = 1; i <= 10; i++) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, WORK_QUEUE_NAME, "hello world, rabbitmq - " + i);
        }
    }

    private static final String FANOUT_EXCHANGE_NAME = "test.exchange.fanout";

    @Test
    public void testSendFanoutMessage() {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "", "hello world, rabbitmq - fanout");
    }

    // 定向交换机模式
    private static final String DIRECT_EXCHANGE_NAME = "test.exchange.direct";
    private static final String DIRECT_ROUTING_KEY = "text.routing.key.good";

    @Test
    public void testSendDirectMessage() {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME, DIRECT_ROUTING_KEY, "hello world, rabbitmq - direct");
    }

    // 主题交换机模式 topic

    private static final String TOPIC_EXCHANGE_NAME = "test.exchange.topic";

    @Test
    public void testSendTopicMessage() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "order.info", "hello world, rabbitmq - topic - order info");
    }

    @Test
    public void testSendTopicMessage2() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "order.info.error", "hello world, rabbitmq - topic - order info error");
    }
}
