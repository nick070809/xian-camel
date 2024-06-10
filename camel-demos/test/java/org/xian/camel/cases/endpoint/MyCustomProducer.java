package org.xian.camel.cases.endpoint;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:47
 */
public class MyCustomProducer extends DefaultProducer {
    private MyCustomEndpoint endpoint;

    public MyCustomProducer(MyCustomEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        // 使用endpoint的URL或其他属性来处理消息
        String message = endpoint.getEndpointUri() + " - " + exchange.getIn().getBody();
        // 将处理结果设置到消息中
        exchange.getIn().setBody(message);
    }
}
