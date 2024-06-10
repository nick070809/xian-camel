package org.xian.spring.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BeanFactory.applicationContext == null) {
            BeanFactory.applicationContext = applicationContext;
        }
    }


    public static <T> T getBean(Class<T> clazz) {
        try {

            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            logger.error("getBean error ", e);
        }
        return null;
    }

    public static Object getBean(String id) {
        try {
            return applicationContext.getBean(id);
        } catch (Exception e) {
            logger.error("getBean error ", e);
        }
        return null;
    }


}
