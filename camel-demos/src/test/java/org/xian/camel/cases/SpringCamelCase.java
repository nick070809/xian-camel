package org.xian.camel.cases;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 15:33
 */
public class SpringCamelCase extends AbstractCamelCase {

    public ClassPathXmlApplicationContext springContext;

    public void createCamelContext() throws Exception {
        createCamelContext("camel_context.xml");
    }


    public void createCamelContext(String configFile) throws Exception {
        springContext = new ClassPathXmlApplicationContext(configFile);
        // 获取Camel上下文
        camelContext = springContext.getBean(CamelContext.class);
        // 启动Camel
        camelContext.start();

    }

    public void addBean(String beanName, Object bean)  {
        // 使用 registerSingleton 方法向容器中注册单例bean
        springContext.getBeanFactory().registerSingleton(beanName, bean);
    }


    public Object getBean(String beanName) {
        return springContext.getBean(beanName);

    }

}
