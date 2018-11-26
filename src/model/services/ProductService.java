package model.services;

import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class ProductService {
    private PromotionService promotionService;

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }
}
