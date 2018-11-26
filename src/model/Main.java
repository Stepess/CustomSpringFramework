package model;

import model.postprocessors.CustomBeanPostProcessor;
import model.services.ProductService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcess;
import org.springframework.context.ApplicationContext;

public class Main    {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("model");
        applicationContext.close();



        ProductService productService = (ProductService) applicationContext.getBean("productService");
        System.out.println(productService);



        System.out.println(productService.getPromotionService());
        System.out.println("Bean name = " + productService.getPromotionService().getBeanName());

        System.out.println(productService.getResourceService());
        System.out.println("BeanFactory = " + productService.getResourceService().getBeanFactory());
    }
}
