package org.xian.camel.cases.beans;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;
import org.xian.camel.cases.JavaCamelCase;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/8 15:34
 */
public class Chap5_ErrorHanderCases extends JavaCamelCase {
    @Test
    public void beanEx() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("helloBean", new HelloBean());
        DefaultCamelContext defaultCamelContext = (DefaultCamelContext) camelContext;
        defaultCamelContext.setRegistry(  registry);

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                /*errorHandler(defaultErrorHandler()
                        .maximumRedeliveries(2)
                        .redeliveryDelay(1000)
                        .retryAttemptedLogLevel(LoggingLevel.WARN));*/


                from("direct:start").bean("helloBean","ex(${body})").log("Body : ${body}")
                ;
            }
        });
        execute();
    }


}
