package org.xian.camel.cases.beans;

import cn.hutool.core.lang.UUID;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xian.camel.cases.SpringCamelCase;
import org.xian.camel.util.RouteDefinitionReader;


/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 13:37
 */
public class Chap3_SpringCamelCases extends SpringCamelCase {
    static Logger logger = LoggerFactory.getLogger(Chap3_SpringCamelCases.class);


    @Test
    public void camel1() throws Exception {

        createCamelContext("chap3-1.xml");

        System.out.println("route size :" +camelContext.getRoutes().size());

        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:beanToJson", UUID.fastUUID().toString());

    }

    @Test
    public void camelroutes() throws Exception {

        createCamelContext();

        RouteDefinitionReader.loadRouteDefinitionFromResourcePath(camelContext, "chap3");

        logger.info("Context route size :" +camelContext.getRoutes().size());

        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:beanToJson", UUID.fastUUID().toString());
        template.sendBody("direct:useBeanDataTransfer", UUID.fastUUID().toString());

    }


}
