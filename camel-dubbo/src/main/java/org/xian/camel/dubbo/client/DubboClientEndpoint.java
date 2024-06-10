package org.xian.camel.dubbo.client;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xian.camel.dubbo.service.DubboServiceEndpoint;

import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:44
 */
public class DubboClientEndpoint extends DefaultEndpoint {
    private static final Logger log = LoggerFactory.getLogger(DubboServiceEndpoint.class);


    private String uri;
    private DubboClientComponent component;
    private String interfaceName;
    private Map<String, Object> parameters;

    ApplicationConfig applicationConfig;

    GenericService genericService;

    public DubboClientEndpoint(String endpointUri, DubboClientComponent component, String interfaceName, Map<String, Object> parameters) {
        super();
        this.uri = endpointUri;
        this.component = component;
        this.interfaceName = interfaceName;
        this.parameters = parameters;
        this.applicationConfig = component.getApplicationConfig();
    }

    @Override
    public Producer createProducer() throws Exception {

        log.info("DubboClientEndpoint createProducer");
        return new DubboClientProducer(this, genericService);
    }

    //作为服务调用者，不需要 Consumer消息
    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new IllegalArgumentException("NOT SUPPORTED ");
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
    public DubboClientComponent getComponent() {
        return component;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    protected void doStart() throws Exception {
        log.info("DUBBO  export service [{}]", interfaceName);
        // 可以添加缓存

        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig);
        reference.setInterface(interfaceName); // 服务接口名
        reference.setGeneric(true); // 开启泛化调用

        reference.setGroup((String) parameters.get("group"));
        reference.setVersion((String) parameters.get("version"));
        // 引用服务
        genericService = reference.get();
    }
}
