package com.zwx.demo.controller;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zwx.demo.config.ConfigListenerExample;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/nacos")
public class DockingInterfaceExampleController {

    @Resource
    private NacosConfigManager nacosConfigManager;

    /**
     * Get configuration information
     *
     * @param dataId dataId
     * @param group  group
     * @return config
     */
    @RequestMapping("/getConfig")
    public String getConfig(@RequestParam("dataId") String dataId,
                            @RequestParam(value = "group", required = false) String group) throws NacosException {
        if (StringUtils.hasLength(group)) {
            group = ConfigListenerExample.GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.getConfig(dataId, group, 5000);
    }

    /**
     * Publish configuration.
     *
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @return boolean
     */
    @RequestMapping("/publishConfig")
    public boolean publishConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam("content") String content) throws NacosException {
        if (StringUtils.hasLength(group)) {
            group = ConfigListenerExample.GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.publishConfig(dataId, group, content);
    }

    /**
     * Delete configuration.
     *
     * @param dataId dataId
     * @param group  group
     * @return boolean
     */
    @RequestMapping("/removeConfig")
    public boolean removeConfig(@RequestParam("dataId") String dataId,
                                @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.hasLength(group)) {
            group = ConfigListenerExample.GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.removeConfig(dataId, group);
    }

    /**
     * Add listener configuration information.
     *
     * @param dataId dataId
     * @param group  group
     */
    @RequestMapping("/listener")
    public String listenerConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.hasLength(group)) {
            group = ConfigListenerExample.GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("[Listen for configuration changes]:{}", configInfo);
            }
        });
        return "Add Lister successfully!";
    }
}
