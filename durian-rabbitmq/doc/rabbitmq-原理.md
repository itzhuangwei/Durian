## 一、架构模型

生产者 -》 交换机(Exchange) -》绑定规则(Binding) -》 队列(Queue) -》 消费者

RabbitMQ服务器(Broker)内部本质上就是一个**消息路由** + **可靠存储** + **投递引擎**

| 概念       | 作用                          | 类比（快递场景）   | Java/Spring中常见对应                                         |
|----------|-----------------------------|------------|----------------------------------------------------------|
| Producer | 产生消息                        | 寄快递的人      | RabbitTemplate.convertAndSend()                          |
| Exchange | 接收消息 + 根据规则路由               | 邮局的分拣中心    | 声明 @Bean Exchange                                        |
| Binding  | Exchange 与 Queue 的关联 + 路由规则 | 邮局到小区的投递路线 | BindingBuilder.bind(queue).to(exchange).with(routingKey) |
| Queue    | 存储消息，等待消费                   | 收件人的邮箱     | @RabbitListener(queues = ""..."")                        |
| Consumer | 从Queue拉取/接收消息并处理            | 收件人        | @RabbitListener 或 MessageListener                        |

## 二、交换机（Exchange）类型与路由原理（最核心的部分）
RabbitMQ 的路由灵活性几乎全部来自于 **Exchange 类型** + **Routing Key** + **Binding Key** 的组合。

| 交换机类型   | 中文名   | 路由规则                             | Routing Key 匹配方式           | 典型使用场景                       | 吞吐量/复杂度 | 生产中使用频率    |
|---------|-------|----------------------------------|----------------------------|------------------------------|---------|------------|
| Direct  | 直连交换机 | Routing Key 完全相等 才路由             | 精确匹配                       | 点对点、任务分发、日志按级别路由             | ★★★★☆   | ★★★★★（最高）  |               
| Fanout  | 扇出交换机 | 忽略 Routing Key                   | 广播到所有绑定队列,无（不看key）         | 广播通知、全局配置刷新、实时日志收集           | ★★★★★   | ★★★★☆      |                                            
| Topic   | 主题交换机 | Routing Key 与 Binding Key 的 模式匹配 | 通配符 *（一个词）、#（0~N个词）        | 按业务模块/事件类型路由（如 order.*.paid） | ★★★★☆   | ★★★★★（最灵活） |
| Headers | 头交换机  | 根据消息的 headers 属性 匹配（key-value）   | 多字段匹配（支持 x-match: all/any） | 复杂条件路由（如带有标签的消息）             | ★★☆☆☆   | ★★☆☆☆（较少用） |

**快速记忆口诀:**
- **Direct(直连)**: Routing Key 必须**一模一样**,像一个快递单号精准投递, 如：点对点，任务分发，日志按级别路由
- **Fanout(扇出)**: 不管**三七二十一**都投递, 如: 广播，全局配置刷新，实时日志收集
- **Topic(主题)**: 支持**通配符**( * 一个词,# 0~N 个词), 像标签/路径智能分发,按业务模块/事件类型路由（如 order.*.paid）
- **Headers(头部)**: 靠**消息头属性**判断,像带了多个标签复杂刷选,复杂条件路由（如带有标签的消息）

### 2.1 最常用的 Topic 交换机通配符规则举例（面试/生产高频）：
- *.error : 匹配所有 error 结尾的路由, 如：匹配 order.error, user.error, 但不匹配 order.warn.error
- order.# : 匹配所有 order 开头的路由, 如：匹配 order.create, order.update, order.delete, order.pay.refound, 但不匹配 user.create
- chain.*.beijing: 匹配所有 chain 开头，且中间有 .beijing 结尾的路由, 如：匹配 chain.create.beijing, chain.update.beijing, chain.delete.beijing, 但不匹配 chain.create.shanghai, chain.update.shanghai, chain.delete.shanghai

## 三、消息在RabbitMQ内部的流转过程（生产常用视角）
1. 生产者通过 Channel 发布消息(带 Routing Key + 自定义headers/properties)
2. Broker收到消息后 -> 交给对应的 Exchange
3. Exchange 会根据 自身类型 + Binding 表 + Routing Key 决定消息投递到哪些Queue(可以0 ~ N个)
4. 消息进入 Queue (此时开始持久化/镜像逻辑)
5. Consumer 通过 Channel 拉取(basic.get)或订阅(basic.consume) 拿到消息
6. Consumer 处理成功 -> ack (手动/自动)
7. 未 ack 消息 -> 根据prefetchCount决定是否重新投递.  重新进入 Exchange -> 重新进入 Queue -> 重复第 5 步


## 四、队列（Queue）的几种重要类型（2025-2026 生产推荐）

| 队列类型            | 持久化方式      | 高可用机制         | 适用场景               | 2025-2026 推荐度 |
|-----------------|------------|---------------|--------------------|---------------|
| Classic Queue   | 可持久化       | Mirrored（老镜像） | 兼容老系统、简单场景         | ★★☆☆☆（逐渐淘汰）   |
| Quorum Queue    | Raft 协议强一致 | 多数派确认         | 金融、订单、核心业务（强烈推荐）   | ★★★★★         |
| Stream          | 追加写日志结构    | 可复制、可重放       | 事件溯源、日志、指标、Kafka替代 | ★★★★☆（高速场景）   |
| Transient Queue | 内存         | 无             | 临时、低价值消息           | ★★☆☆☆         |


**2025-2026 生产建议：**

新项目 优先 **Quorum Queue**（高可用 + 强一致）

需要超高吞吐 + 可回溯 → Stream（4.0+ 优化后非常强）

## 五、可靠性三大保障机制（Java开发最常被问）
1. 生产端确认(Publisher Confirms)
   - 单条: channel.confirmSelect() + waitForConfirms()
   - 批量: waitForConfirmsOrDie() 或异步回调

2. 消息持久化
   - Exchange durable = true
   - Queue durable = true
   - Message deliveryMode = 2 (持久化)

3. 消费者手动ACK + 死信队列
   - channel.basicQos(prefetchCount) 限流
   - 业务失败 -> nack + requeue = false -> 进入死信队列(DLX)

## 六、总结一句话概括RabbitMQ原理
RabbitMQ 本质上是一个基于AMQP协议的、支持多种路由策略的、可靠的、进程间异步通信中间件, 它通过Exchange 的灵活路由 + 
Queue 的可靠性存储 + 镜像/Quorum 的高可用机制,实现了解耦 、削峰、最终一致性等分布式系统常见的诉求.