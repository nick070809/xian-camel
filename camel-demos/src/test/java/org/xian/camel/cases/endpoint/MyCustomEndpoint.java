package org.xian.camel.cases.endpoint;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:44
 */
public class MyCustomEndpoint extends DefaultEndpoint {
    private String uri;
    private MyCustomComponent component;

    public MyCustomEndpoint(String endpointUri, MyCustomComponent component) {
        super();
        this.uri = endpointUri;
        this.component = component;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new MyCustomProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new MyCustomConsumer(this, processor);
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
}
