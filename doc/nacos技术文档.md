# 1. 版本区别

- 2023.0.1.3 及以上版本 **spring.cloud.nacos.config.shared-configs**、**extension-configs** 
以及默认加载的 **application.name** 配置已经废弃，以上版本请统一使用 **spring.config.import** 方式导入 Nacos 配置
- **bootstrap.yml** 以及 **bootstrap.properties** 已不推荐使用，
请使用 **application.yml** 或者 **application.properties**

# 2. 配置文件取值方式

应用会从 Nacos Server 中获取相应的配置，并添加在 Spring Environment 的 PropertySources 中。假设我们通过 Nacos 作为配置中心保存应用服务的部分配置，有以下几种方式实现：
1. ValueAnnotationExample: 通过 @Value 注解获取配置
2. BeanAutoRefreshConfigExample：通过将配置信息配置为 bean，支持配置变自动刷新；
3. ConfigListenerExample：监听配置信息；
4. DockingInterfaceExample：对接 Nacos 接口，通过接口完成对配置信息增删改查；