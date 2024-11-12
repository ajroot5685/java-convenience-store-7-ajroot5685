package store.service;

public class SupplyService {

    private final ProductService productService;
    private final PromotionService promotionService;

    public SupplyService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public void supply() {
        promotionService.supply();
        productService.supply();
    }
}
