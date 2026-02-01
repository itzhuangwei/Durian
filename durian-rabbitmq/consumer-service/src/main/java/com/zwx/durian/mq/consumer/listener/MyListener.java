package com.zwx.durian.mq.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    private static final String QUEUE_NAME = "test-simple-queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void onMessage(String message) {
        System.out.println("接收到消息：" + message);
    }
}
