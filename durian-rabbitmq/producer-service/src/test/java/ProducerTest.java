import com.zwx.durian.mq.producer.ProducerApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ProducerApplication.class})
public class ProducerTest {

    private static final String QUEUE_NAME = "test-simple-queue";
    private static final String EXCHANGE_NAME = null;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_NAME, "hello world, rabbitmq-4");
    }
}
