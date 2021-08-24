package com.lordsl.unit.test.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeanFetcher implements ApplicationContextAware {
    private static ApplicationContext applicationContext;//声明一个静态变量保存

    public static <T> T getBeanOfClass(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    public static <T> List<T> getBeansOfClass(Class<T> cla) {
        return new ArrayList<>(applicationContext.getBeansOfType(cla).values());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanFetcher.applicationContext = applicationContext;
    }
}
