package store.service;

public class PurchaseService {

    private final ProductService productService;
    private final PromotionService promotionService;

    public PurchaseService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

}
