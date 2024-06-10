package org.xian.camel.cases;


import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 15:33
 */
public class JavaCamelCase extends AbstractCamelCase{

    @Before
    public void createCamelContext() throws Exception {
        camelContext = new DefaultCamelContext();
        template = camelContext.createProducerTemplate();
        camelContext.start();
    }
}
