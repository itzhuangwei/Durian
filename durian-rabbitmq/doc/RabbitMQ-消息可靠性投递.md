# 消息可靠性投递

## 1. 概述

|      阶段       |      可能丢失消息的典型场景       | 丢失概率 |       业界最常用的解决方案       | 
|:-------------:|:----------------------:|:----:|:----------------------:|
| 生产者 -> broker | 网络抖动、Broker宕机、队列满、路由失败 | 中~高  |  同步发送+重试+确认机制+本地持久话兜底  |
|   broker内部    |    单点宕机、磁盘损坏、刷盘策略弱     | 低~中  | 持久话 + 多副本 + 同步刷盘/ISR机制 |
| broker -> 消费者 |   消费过程中崩溃、ACK丢失、网络中断   |  中   |  手动ACK+幂等+死信队列+消费进度快照  |

### 1.1 问题引入

![img.png](img/消息可靠性投递/img.png)

![img_1.png](img/消息可靠性投递/img_1.png)


### 1.2 解决方案

![img_2.png](img/消息可靠性投递/img_2.png)


## 2. 故障1解决: 生产者端消息确认机制

### 2.1 概念
![img_3.png](img/消息可靠性投递/img_3.png)

![img_4.png](img/消息可靠性投递/img_4.png)

1. 故障1:
    生产者发送消息,可能发给交换机时失败了,例如:交换机名称指定名称错误
        
        需要 : 交换机不管收没收到消息都必须给生产者端确认,返回ack=true或ack=false
        生产者通过确认回调函数来决定消息是否重新发送,还是发邮件给管理员通知一下
    生产者发送的消息的路由key指定错误,那么交换机找不到与之匹配的队列,消息发送失败.

        回退回调函数在队列没收到消息时执行,注意:队列接收到消息不执行.
![img_5.png](img/消息可靠性投递/img_5.png)


## 3. 代码逻辑展示

### 3.1 生产者端可靠性投递(最重要、最容易丢失)
```java
// 1. 开启发布者确认 + 开启mandatory（路由不可达返回）
ConnectionFactory factory = new ConnectionFactory();
factory.setPublisherConfirms(true);   // 同步/异步确认
factory.setPublisherReturns(true);

// 2. 异步确认 + 批量 + 缓存未确认消息
channel.confirmSelect();

// 非常推荐的写法：结合CorrelationData + 监听器
channel.addConfirmListener((ack, deliveryTag) -> {
    if (ack) {
        // 成功，移除本地缓存/数据库记录
        removeFromWaitConfirm(deliveryTag);
    } else {
        // 失败，重发或转人工
        reSendOrAlert(deliveryTag);
    }
}, (deliveryTag, multiple) -> { /* nack 处理 */ });

// 3. mandatory + ReturnListener 处理路由失败
channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
    // 路由不到队列的消息会回调这里
    log.error("消息路由失败 → 转DB/死信/告警");
    saveToDbOrDeadLetter(body);
});
```

**一句话总结生产端:**
- 金融/核心业务 -> RocketMQ事务消息 > RabbitMQ publisher confirms + mandatory > kafka acks = all + 幂等性
- 大数据/日志类 -> kafka acks = all + 幂等 + 失败转本地表/文件

### 3.2 Broker 端不丢失消息(持久话+高可用)
|    MQ    |                                   推荐配置(尽量不丢)                                    |      代价      |
|:--------:|:-------------------------------------------------------------------------------:|:------------:|
| RabbitMQ |     持久化 + 集群 + 镜像 (durable queue + persistent message + 镜像队列（ha-mode: all）)     |    性能下架明显    |
| RocketMQ | 持久化 + 集群 + 顺序消息 + 顺序消息组 + 顺序消息队列 (SYNC_MASTER + 同步刷盘（flushDiskType=SYNC_FLUSH）) | 吞吐量降低30%-50% |
|  Kafka   |    持久化 + 集群 + 幂等性 (min.insync.replicas=2 + replication.factor=3 + acks=all)     |  延迟略增,磁盘消耗大  |

**结论:** 想接近0丢失,必须要牺牲一部分性能,没有免费的午餐.

#### 3.2.1 消费者端可靠消费(不丢+不重)
核心原则:先消费成功,再ACK (至少一次投递 + 业务幂等)

```java
// 最佳实践（几乎所有MQ通用）
@RabbitListener(queues = "order.pay.queue")
public void onMessage(Message message, Channel channel) {
    // 消息的唯一标识,类似于数据的ID(主键)
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
        String body = new String(message.getBody());
        OrderPayEvent event = parse(body);

        // 核心：业务幂等（最重要！）
        if (alreadyProcessed(event.getOrderId())) {
            channel.basicAck(deliveryTag, false);
            return;
        }

        // 执行业务
        processOrderPay(event);

        // 成功才ACK : 手动确认,异步返回给服务器ack=true,服务器就会删除队列中指定ID的消息
        channel.basicAck(deliveryTag, false);
    } catch (Exception e) {
        // 失败策略：根据异常类型决定
        if (isRetryable(e)) {
            // deliveryTag : 队列中消息的ID
            // multiple: 是否批量
            // requeue: 是否重新入队
            channel.basicNack(deliveryTag, false, true);   // 重回队列
        } else {
            channel.basicReject(deliveryTag, false);       // 直接丢或转死信
            saveToErrorLogOrDb(message);
        }
    }
}
```
**幂等常用的几种方式:** 
1. 数据库字段: 添加唯一索引,保证数据不重复 (数据库唯一索引/防重表[最常用])
2. Redis setnx / 布隆过滤器 [高并发场景]
3. 业务字段天然幂等[如订单状态机]
4. 消息自带的唯一业务ID + 消费记录表

## 4. 总结: 不同场景最优的组合

|      场景      |   推荐MQ   |                 生产端方案                  |      Broker方案      |      消费端方案      |  能达到的可靠性   |
|:------------:|:--------:|:--------------------------------------:|:------------------:|:---------------:|:----------:|
| 金融、支付、订单核心链路 | RocketMQ |                  事务消息                  | SYNC_MASTER + 同步刷盘 | 手动ACK + 幂等 + 死信 |   接近0丢失    |
|  金融对账、强一致性   | RabbitMQ | Publisher Confirms + mandatory + 备份交换机 |     镜像队列 + 持久化     |   手动ACK + 幂等    |     极高     |
| 日志、监控、埋点、大数据 |  Kafka   |        acks=all + 幂等 + 失败本地持久化         | 多副本 + min.insync=2 |  手动commit + 幂等  | 高（可接受极少丢失） |
|  普通业务、活动、通知  |    任意    |              同步发送 + 3次重试               |       默认持久化        |      幂等即可       |     较高     |
