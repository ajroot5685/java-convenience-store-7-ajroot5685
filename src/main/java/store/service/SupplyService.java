package store.service;

public class SupplyService {

    private final PromotionService promotionService;

    public SupplyService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void supply() {
        promotionService.supply();
    }
}
