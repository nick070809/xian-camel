package org.xian.camel.cases.endpoint;


import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:46
 */
public class MyCustomComponent extends DefaultComponent {
    private static final Logger log = LoggerFactory.getLogger(MyCustomComponent.class);

    //该方法用于创建自定义组件的端点。在该方法中，可以根据需要设置端点的属性和配置。
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return new MyCustomEndpoint(uri, this);
    }
    //在自定义组件类中，可以重写其他方法来实现自定义组件的特定行为。
    @Override
    protected void doStop() throws Exception{
        log.error("MyCustomComponent stoping ... ");
    }
}
