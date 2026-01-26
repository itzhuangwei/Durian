# 一、redis stream

引入redis  stream:
    
## 1.1 首先理解它的出现背景?

Redis Stream 是 **Redis 5.0** 版本新增加的**数据结构**。

Redis Stream 主要用于**消息队列**（MQ，Message Queue），
Redis 本身是有一个 Redis 发布订阅 (pub/sub) 来实现消息队列的功能，
但它有个缺点就是**消息无法持久化**，如果出现网络断开、Redis 宕机等，消息就会被丢弃。

简单来说发布订阅 (pub/sub) 可以分发消息，但无法记录历史消息。

而 Redis Stream **提供了消息的持久化和主备复制功能**，可以让任何客户端访问任何时刻的数据，
并且能记住每一个客户端的访问位置，还能保证消息不丢失。

Redis Stream 的结构如下所示，它有一个消息链表，将所有加入的消息都串起来，每个消息都有一个唯一的 ID 和对应的内容:

![img.png](img/img.png)