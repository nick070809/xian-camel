package org.xian.camel.cases.endpoint;

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:47
 */
public class MyCustomConsumer extends DefaultConsumer {
    public MyCustomConsumer(MyCustomEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }
}