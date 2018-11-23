package org.springframework.beans.factory;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
