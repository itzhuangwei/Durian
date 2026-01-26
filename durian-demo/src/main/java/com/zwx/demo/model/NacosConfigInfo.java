package com.zwx.demo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
@Component
@Data
public class NacosConfigInfo {

    /**
     * Nacos server address.
     */
    private String serverAddr;

    /**
     * Data Id prefix.
     */
    private String prefix;

    /**
     * Nacos group.
     */
    private String group;

    /**
     * Nacos namespace.
     */
    private String namespace;
}
