package org.xian.camel.cases.beans;

import cn.hutool.core.lang.UUID;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;
import org.xian.camel.cases.JavaCamelCase;
import org.xian.camel.cases.endpoint.MyCustomComponent;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 21:28
 */
public class Chap4_BeanCases extends JavaCamelCase {

    @Test
    public void useBeanProcesser() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
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
                        .log("Body : ${body}")
                ;
            }
        });
        execute();
    }


    @Test
    public void registerSimpleBean() throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("helloBean", new HelloBean());
        DefaultCamelContext defaultCamelContext = (DefaultCamelContext) camelContext;
        defaultCamelContext.setRegistry(registry);

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start").bean("helloBean").log("Body : ${body}")
                ;
            }
        });
        execute();
    }


    @Test
    public void releaseHttpService_9993() throws Exception {

        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jetty:http://localhost:9993/myapp/myservice").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody("<html><body>Book is good</body></html>");
                    }
                });

            }
        });

        System.out.println("Server started. Press any key to stop.");
        System.in.read();
        // 停止上下文
        camelContext.stop();
    }


    @Test
    public void releaseHttpService_9994() throws Exception {

        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jetty:http://localhost:9994/myapp/myservice").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody("hello world !");
                    }
                });

            }
        });

        System.out.println("Server started. Press any key to stop.");
        System.in.read();
        // 停止上下文
        camelContext.stop();
    }




    @Test
    public void invokeHttpService_9994() throws Exception {

        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // 配置HTTP端点
                from("direct:start")
                        .to("http://localhost:9994/myapp/myservice");

            }
        });

        String response =  executeAndResponse(String.class);
        System.out.println("Response from HTTP service: " + response);

        // 停止上下文
        camelContext.stop();
    }



    @Test
    public void customEndPoint() throws Exception {
        //注册自定义组件
        camelContext.addComponent("test", new MyCustomComponent());

        // 添加路由配置
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("test:someUrl")
                        .log("Body : ${body}");


            }
        });
        execute();
        // 停止上下文
        camelContext.stop();
    }

}
