package org.xian.camel.dubbo.client;


import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.dubbo.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xian.camel.dubbo.DubboRegisterConfig;

import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:46
 */
public class DubboClientComponent extends DefaultComponent {
    private static final Logger log = LoggerFactory.getLogger(DubboClientComponent.class);

    @Autowired
    DubboRegisterConfig dubboRegisterConfig ;

    ApplicationConfig applicationConfig ;

    //该方法用于创建自定义组件的端点。在该方法中，可以根据需要设置端点的属性和配置。
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

        return new DubboClientEndpoint(uri, this,   remaining,  parameters);
    }



    public ApplicationConfig getApplicationConfig() {
        if(applicationConfig == null){
            applicationConfig = dubboRegisterConfig.applicationConfig();
        }
        return applicationConfig;
    }

    //在自定义组件类中，可以重写其他方法来实现自定义组件的特定行为。

    @Override
    protected void validateParameters(String uri, Map<String, Object> parameters, String optionPrefix) {

    }

    public void setDubboRegisterConfig(DubboRegisterConfig dubboRegisterConfig) {
        this.dubboRegisterConfig = dubboRegisterConfig;
    }
}
