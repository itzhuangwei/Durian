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
}
