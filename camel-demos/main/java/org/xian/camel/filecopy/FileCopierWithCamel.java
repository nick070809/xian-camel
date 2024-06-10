package org.xian.camel.filecopy;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 11:27
 */
public class FileCopierWithCamel {
    public static void main(String args[]) throws Exception {
        // create CamelContext
        CamelContext context = new DefaultCamelContext();

        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:/Users/sunkaixiang/IdeaProjects/nick070809/xian-base/testFiles/src").to("file:/Users/sunkaixiang/IdeaProjects/nick070809/xian-base/testFiles/to");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
    }
}
