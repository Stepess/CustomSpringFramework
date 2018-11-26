package model;

import model.postprocessors.CustomBeanPostProcessor;
import model.services.ProductService;
import org.springframework.beans.factory.BeanFactory;

public class Main    {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.addPostProcessor(new CustomBeanPostProcessor());
        beanFactory.instantiate("model.services");
        beanFactory.populateProperties();
        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.initializeBeans();


        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);



        System.out.println(productService.getPromotionService());
        System.out.println("Bean name = " + productService.getPromotionService().getBeanName());

        System.out.println(productService.getResourceService());
        System.out.println("BeanFactory = " + productService.getResourceService().getBeanFactory());
    }
}
