package com.hframe.hamster.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring上下文加载类
 * Created by zhangquanhong on 2016/9/27.
 */
public class HamsterContextInitializer {
    private static final Logger logger = LoggerFactory.getLogger(HamsterContextInitializer.class);

    private static ClassPathXmlApplicationContext context;

    static {
        logger.info("#########################################################################");
        logger.info("loading hamster context ..");
        logger.info("#########################################################################");
        context = new ClassPathXmlApplicationContext("spring/spring-config.xml"){
            @Override
            protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
                super.customizeBeanFactory(beanFactory);
                //TODO 启动报错，临时先注掉
//                beanFactory.setAllowBeanDefinitionOverriding(false);
            }
        };
        logger.info("#########################################################################");
        logger.info("load hamster context ok ..");
        logger.info("#########################################################################");
    }

    public static HamsterController getHamsterController() {
        return context.getBean(HamsterController.class);
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static<T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static void autowire(Object bean) {
        context.getAutowireCapableBeanFactory().autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
    }
}
