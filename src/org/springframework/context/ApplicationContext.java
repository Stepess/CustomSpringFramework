package org.springframework.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcess;

public class ApplicationContext {
    private BeanFactory beanFactory = new BeanFactory();

    public ApplicationContext(String basePackage) {
        System.out.println("******Context is under construction******");

        beanFactory.instantiate(basePackage);
        beanFactory.populateProperties();
        beanFactory.addPostProcessor(new InitDestroyAnnotationBeanPostProcess());
        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();


        SignatureWriter.printSignature();

        beanFactory.initializeBeans();
    }

    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    public void close() {
        beanFactory.close();
    }
}
