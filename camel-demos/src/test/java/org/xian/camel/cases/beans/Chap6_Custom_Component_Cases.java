package org.xian.camel.cases.beans;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.dubbo.config.ApplicationConfig;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xian.camel.dubbo.DubboRegisterConfig;


/**
 * @Author kaixiang.sun
 * @Date 2024/6/10 14:10
 */
public class Chap6_Custom_Component_Cases {


    @Test
    public void releaseDubboService() throws Exception {

        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("chap6/chap6_spring_context.xml");
        CamelContext camelContext = springContext.getBean(CamelContext.class);
        //注册自定义组件
        camelContext.addComponent("dubboService", (Component) springContext.getBean("dubboService"));
        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("dubboService:org.xian.camel.dubbo.demo.client.HelloService?version=1.0.0&group=DUBBO&timeout=5000")
                        .log("Body : ${body}");
            }
        });

        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("dubboService:org.xian.camel.dubbo.demo.client.BizService?version=1.0.0&group=DUBBO&timeout=5000")
                        .log("Body : ${body}");
            }
        });
        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("dubboService:org.xian.camel.dubbo.demo.client.HelloService?version=2.0.0&group=DUBBO&timeout=5000")
                        .log("Body : ${body}");
            }
        });
        System.in.read();
    }




    @Test
    public void invokeDubboService() throws Exception {

        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("chap6/chap6_spring_context.xml");
        CamelContext camelContext = springContext.getBean(CamelContext.class);
        //注册自定义组件
        camelContext.addComponent("dubbo", (Component) springContext.getBean("dubboClient"));
        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("dubbo:org.xian.camel.dubbo.demo.client.HelloService?method=sayHello&parameterTypes=java.lang.String&args=xian&group=DUBBO&version=1.0.0")
                        .log("Body : ${body}");


            }
        });
        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:start","xian");

        // 停止上下文
        // camelContext.stop();
    }

}
