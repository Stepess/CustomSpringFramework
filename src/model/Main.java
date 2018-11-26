package model;

import model.services.ProductService;
import org.springframework.beans.factory.BeanFactory;

public class Main    {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("model.services");

        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);

        beanFactory.populateProperties();

        System.out.println(productService.getPromotionService());
    }
}
