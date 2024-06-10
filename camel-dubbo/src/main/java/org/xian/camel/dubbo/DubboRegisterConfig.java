package org.xian.camel.dubbo;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
 * @Author kaixiang.sun
 * @Date 2024/6/10 22:22
 */


public class DubboRegisterConfig {
    private static final Logger log = LoggerFactory.getLogger(DubboRegisterConfig.class);


    @Value("${applicationName}")
    private String applicationName = "xian-camel-service";

    @Value("${dubbo.registerAddr}")
    private String registerAddr = "nacos://192.168.31.100:10048";

    @Value("${dubbo.port}")
    private String port = "20880";

    @PostConstruct
    public void init(){
        log.info("DubboRegisterConfig init .");
    }

    ApplicationConfig applicationConfig ;

    public ApplicationConfig applicationConfig() {
        if(applicationConfig != null){
            return applicationConfig;
        }
        log.info("ApplicationConfig init .");
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(registerAddr);

        // 应用配置
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        applicationConfig.setRegistry(registry); // 也可以设置多个注册中心
        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(Integer.valueOf(port));
        protocol.setThreads(200);
        return applicationConfig;
    }
}
