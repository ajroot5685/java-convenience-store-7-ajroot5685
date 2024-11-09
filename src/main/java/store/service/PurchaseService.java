package store.service;

import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;
import static store.constant.ExceptionMessage.PROMOTION_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.builder.ReceiptBuilder;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.dto.PurchaseDto;
import store.entity.Product;
import store.entity.Promotion;
import store.model.CartModel;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.PurchaseInputParser;

public class PurchaseService {

    private final ProductModel productModel;
    private final PromotionModel promotionModel;
    private final CartModel cartModel;
    private final PurchaseInputParser purchaseInputParser;
    private final ReceiptBuilder receiptBuilder;

    public PurchaseService(ProductModel productModel, PromotionModel promotionModel, CartModel cartModel,
                           PurchaseInputParser purchaseInputParser, ReceiptBuilder receiptBuilder) {
        this.productModel = productModel;
        this.promotionModel = promotionModel;
        this.cartModel = cartModel;
        this.purchaseInputParser = purchaseInputParser;
        this.receiptBuilder = receiptBuilder;
    }

    public void purchase(String purchaseInput) {
        List<PurchaseDto> purchaseDtos = purchaseInputParser.toPurchaseDtoList(purchaseInput);
        for (PurchaseDto purchaseDto : purchaseDtos) {
            process(purchaseDto);
        }
    }

    private void process(PurchaseDto purchaseDto) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        Integer promotionCount = applicablePromotionCount(product, purchaseDto);
        Integer normalCount = purchaseDto.quantity() - promotionCount;

        purchaseProduct(purchaseDto.name(), product.getPrice(), promotionCount, true);
        purchaseProduct(purchaseDto.name(), product.getPrice(), normalCount, false);
    }

    private Integer applicablePromotionCount(Product product, PurchaseDto purchaseDto) {
        if (isPromotionApplicable(product)) {
            Promotion promotion = promotionModel.findByName(product.getPromotion())
                    .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
            return promotion.applicableCount(purchaseDto.quantity());
        }
        return 0;
    }

    // 상품에 프로모션 정보 있는지 확인, 오늘 적용가능한지 확인
    private boolean isPromotionApplicable(Product product) {
        if (product.getPromotion() == null) {
            return false;
        }
        Promotion promotion = promotionModel.findByName(product.getPromotion())
                .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
        return promotion.checkDate(DateTimes.now().toLocalDate());
    }

    private void purchaseProduct(String name, Integer price, Integer quantity, boolean isPromotion) {
        productModel.decrease(name, quantity, isPromotion);
        cartModel.addQuantity(name, price, quantity, isPromotion);
    }

    public String getCalculateResult() {
        List<CalculateProductDto> productInfo = cartModel.calculate();
        List<CalculatePromotionDto> promotionInfo = cartModel.calculatePromotion();
        CalculateResultDto resultInfo = cartModel.result();
        cartModel.clear();
        return receiptBuilder.issue(productInfo, promotionInfo, resultInfo);
    }
}
