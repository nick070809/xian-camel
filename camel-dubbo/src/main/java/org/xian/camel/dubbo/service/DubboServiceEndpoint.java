package org.xian.camel.dubbo.service;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xian.spring.base.BeanFactory;

import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:44
 */
public class DubboServiceEndpoint extends DefaultEndpoint {
    private static final Logger log = LoggerFactory.getLogger(DubboServiceEndpoint.class);

    private String uri;
    private DubboServiceComponent component;

    private String interfaceName;
    private Map<String, Object> parameters;
    private ApplicationConfig applicationConfig;

    public DubboServiceEndpoint(String endpointUri, DubboServiceComponent component, String interfaceName, Map<String, Object> parameters) {
        super();
        this.uri = endpointUri;
        this.component = component;

        this.interfaceName = interfaceName;
        this.parameters = parameters;
        this.applicationConfig = component.getApplicationConfig() ;

    }

    //作为服务发布者，不需要创建 Producer
    @Override
    public Producer createProducer() throws Exception {
        throw new IllegalArgumentException("NOT SUPPORTED ");
    }

    //作为dubbo服务发布者，不需要使用consumer处理数据
    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        //  return new DubboServiceConsumer(this, processor);
        return new DefaultConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false; // 根据需要返回 true 或 false
    }

    @Override
    public String getEndpointUri() {
        // 实现这个方法来返回端点的 URI 字符串
        return uri;
    }

    @Override
    public DubboServiceComponent getComponent() {
        return component;
    }

    @Override
    protected void doStart() throws Exception {
        log.info("DUBBO  export service [{}]", interfaceName);

        Class clz = Class.forName(interfaceName);

        ServiceConfig serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setInterface(clz);
        serviceConfig.setRef(BeanFactory.getBean(clz));
        serviceConfig.setVersion((String) parameters.get("version"));
        serviceConfig.setTimeout(Integer.valueOf((String) parameters.get("timeout")));
        serviceConfig.setGroup((String) parameters.get("group"));
        // 暴露及注册服务
        serviceConfig.export();
    }
}
