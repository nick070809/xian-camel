package org.xian.camel.cases.beans;

import cn.hutool.core.lang.UUID;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;
import org.xian.camel.cases.SpringCamelCase;
import org.xian.camel.cases.routes.Chap4_BeanRouter_2;
import org.xian.camel.util.RouteDefinitionReader;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 21:28
 */
public class Chap4_SpringBeanCases extends SpringCamelCase {

    // how to use   <routeBuilder ref="bean_route_1"/>
    @Test
    public void useBeanProcesser() throws Exception {


        createCamelContext("chap4-1.xml");

        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:start", UUID.fastUUID().toString());

    }

    // route 和bean 混合使用 参考  route4-1.xml
    @Test
    public void routeBean_1() throws Exception {
        createCamelContext();

        addBean("helloBean", new HelloBean());

        RouteDefinitionReader.loadRouteDefinitionFromResourcePath(camelContext, "chap4");

        logger.info("Context route size :" + camelContext.getRoutes().size());

        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:useHellobean", UUID.fastUUID().toString());

    }
    //  use beanRef
    @Test
    public void routeBean_2() throws Exception {
        createCamelContext();

        addBean("helloBean", new HelloBean());

        camelContext.addRoutes(new Chap4_BeanRouter_2());

        logger.info("Context route size :" + camelContext.getRoutes().size());

        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:hello", UUID.fastUUID().toString());

    }


    @Test
    public void registerSpringBean() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("helloBean", new HelloBean());
        DefaultCamelContext defaultCamelContext = (DefaultCamelContext) camelContext;
        defaultCamelContext.setRegistry(  registry);

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start").bean("helloBean").log("Body : ${body}")
                ;
            }
        });
        execute();
    }
}
