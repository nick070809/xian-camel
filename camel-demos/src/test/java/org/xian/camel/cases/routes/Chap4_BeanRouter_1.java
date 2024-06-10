package org.xian.camel.cases.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.xian.camel.cases.beans.HelloBean;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 21:39
 */
public class Chap4_BeanRouter_1 extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String name = exchange.getIn().getBody(String.class);
                        HelloBean hello = new HelloBean();
                        String answer = hello.hello(name);
                        exchange.getOut().setBody(answer);
                    }
                })
                .log("Body : ${body}");
    }
}
