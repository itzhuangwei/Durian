package com.zwx.durian.mq.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    private static final String QUEUE_NAME = "test-simple-queue";

    private static final String WORK_QUEUE_NAME = "test.work.queue";

    /**
     * 监听队列 - 简单消息队列模式
     *
     * @param message
     */
    @RabbitListener(queues = QUEUE_NAME)
    public void onMessage(String message) {
        System.out.println("接收到消息：" + message);
    }

    /**
     * 监听队列 - 工作消息队列模式
     *
     * @param message
     */
    @RabbitListener(queues = WORK_QUEUE_NAME)
    public void onWorkMessage1(String message) {
        System.out.println("onWorkMessage1 - 接收到消息：" + message);
    }

    @RabbitListener(queues = WORK_QUEUE_NAME)
    public void onWorkMessage2(String message) {
        System.out.println("onWorkMessage2 - 接收到消息：" + message);
    }

    @RabbitListener(queues = "test.queue.fanout01")
    public void handleFanout01(String message) {
        System.out.println("test.queue.fanout01 - 接收到消息：" + message);
    }

    @RabbitListener(queues = "test.queue.fanout02")
    public void handleFanout02(String message) {
        System.out.println("test.queue.fanout02 - 接收到消息：" + message);
    }

    @RabbitListener(queues = "test.queue.direct")
    public void handleDirect(String message) {
        System.out.println("test.queue.direct - 接收到消息：" + message);
    }
}
