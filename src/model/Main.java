package model;

import model.postprocessors.CustomBeanPostProcessor;
import model.services.ProductService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcess;

public class Main    {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.addPostProcessor(new CustomBeanPostProcessor());
        beanFactory.addPostProcessor(new InitDestroyAnnotationBeanPostProcess());
        beanFactory.instantiate("model.services");
        beanFactory.populateProperties();
        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.initializeBeans();
        beanFactory.close();


        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);



        System.out.println(productService.getPromotionService());
        System.out.println("Bean name = " + productService.getPromotionService().getBeanName());

        System.out.println(productService.getResourceService());
        System.out.println("BeanFactory = " + productService.getResourceService().getBeanFactory());
    }
}
