package org.xian.camel.cases.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 21:39
 */
public class Chap4_BeanRouter_2 extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:hello").beanRef("helloBean", "hello(${body})").log("Body : ${body}");
    }
}
