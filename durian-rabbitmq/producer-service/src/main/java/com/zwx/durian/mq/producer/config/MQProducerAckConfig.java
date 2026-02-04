package com.zwx.durian.mq.producer.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类: 注册确认回调函数 和 注册回退回调函数
 */
@Configuration
public class MQProducerAckConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 确认回调函数: 不管交换机收没收到,都会执行,根据ack的值true/false,可以知道消息是否成功发送
     *
     * @param correlationData correlation data for the callback. 关联数据:类似于电子邮件的附件
     * @param ack true for ack, false for nack
     * @param cause An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }

    /**
     * 回退回调函数: 交换机收不到消息(消息发送到交换机失败),会执行此方法,根据returnedMessage的值可以知道消息失败的原因
     *
     * @param returnedMessage the returned message and metadata.
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

    }
}
