package model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.javax.annotation.PostConstruct;
import org.springframework.beans.factory.javax.annotation.PreDestroy;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductService {
    @Autowired
    private PromotionService promotionService;

    @Resource(name = "resourceService")
    private ResourceService resourceService;

    @PostConstruct
    public void setUp() {
        System.out.println("===PostConstruct===");
        System.out.println("Bean " + promotionService.getBeanName() + " was successfully injected");
    }

    @PreDestroy
    public void shutDown() {
        System.out.println("===PreDestroy===");
    }

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }


    public ResourceService getResourceService() {
        return resourceService;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
