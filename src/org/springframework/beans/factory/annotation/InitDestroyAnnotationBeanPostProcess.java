package org.springframework.beans.factory.annotation;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.javax.annotation.PostConstruct;
import org.springframework.beans.factory.javax.annotation.PreDestroy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InitDestroyAnnotationBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
