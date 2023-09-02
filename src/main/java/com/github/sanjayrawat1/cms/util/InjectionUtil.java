package com.github.sanjayrawat1.cms.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Sanjay Singh Rawat
 */
public class InjectionUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        InjectionUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanType) {
        return InjectionUtil.applicationContext.getBean(beanType);
    }

    public static <T> T getBean(String name, Class<T> beanType) {
        return InjectionUtil.applicationContext.getBean(name, beanType);
    }
}
