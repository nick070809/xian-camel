package org.xian.camel.cases.beans;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 21:28
 */
public class HelloBean {

    public String hello(String name) {
        return "Hello-" + name;
    }

    public String ex(String name) {
        throw new RuntimeException("ex");
    }
}
