package model.services;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class ResourceService implements BeanFactoryAware {
    BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory factory) {
        beanFactory = factory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
