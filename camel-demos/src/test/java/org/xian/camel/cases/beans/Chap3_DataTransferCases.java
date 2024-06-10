package org.xian.camel.cases.beans;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.junit.Test;
import org.xian.camel.cases.JavaCamelCase;
import org.xian.camel.dataformat.transfer.DataTransformer;


/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 13:37
 */
public class Chap3_DataTransferCases extends JavaCamelCase {


    @Test
    public void useBeanDataTransfer() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .log("Headers: ${headers}")
                        .log("Body: ${body}")
                        .bean(DataTransformer.class, "transform(${header.param1}, ${header.param2})")
                        //.to("log:xian-log?showAll=true")
                        .log("Body: ${body}")
                        .log("Headers: ${headers}");

            }
        });
        execute(generateOneMap());
    }

    @Test
    public void useExpressionDataTransfer() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .log("Body: ${body}")
                        .setBody(simple("${body.toUpperCase()}"))
                        .setHeader("Content", constant("New Value"))
                        .log("Body: ${body}")
                        .log("Header.Content: ${headers.Content}");
            }
        });
        execute();
    }


    @Test
    public void beanToJson() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .log("Body: ${body}")
                        .marshal().json(JsonLibrary.Jackson)
                        .log("Body: ${body}");
            }
        });
        execute(generateOneMap());
    }



    @Test
    public void beanToCSV() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .log("Body: ${body}")
                        .marshal().csv()
                        //.to("file:target/marshalledXml")
                        .to("file:/Users/sunkaixiang/IdeaProjects/nick070809/xian-base/testFiles/to/x.csv");
                        ;//.log("Body: ${body}");
            }
        });

     /* execute(new ArrayList<List>(){{
          add(generateMapList());
      }});*/

        execute(generateMapList());
    }



}
