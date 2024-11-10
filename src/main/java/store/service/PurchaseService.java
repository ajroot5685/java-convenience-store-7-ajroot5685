package store.service;

import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;
import static store.constant.ExceptionMessage.PROMOTION_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import store.builder.ReceiptBuilder;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;
import store.entity.Product;
import store.entity.Promotion;
import store.model.CartModel;
import store.model.ProductModel;
import store.model.PromotionModel;

public class PurchaseService {

    private final ProductService productService;
    private final ProductModel productModel;
    private final PromotionModel promotionModel;
    private final CartModel cartModel;
    private final ReceiptBuilder receiptBuilder;

    public PurchaseService(ProductService productService, ProductModel productModel,
                           PromotionModel promotionModel, CartModel cartModel, ReceiptBuilder receiptBuilder) {
        this.productService = productService;
        this.productModel = productModel;
        this.promotionModel = promotionModel;
        this.cartModel = cartModel;
        this.receiptBuilder = receiptBuilder;
    }

    public boolean isApplicablePromotion(String productName) {
        Product product = productModel.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        if (product.getPromotion() == null) {
            return false;
        }
        Promotion promotion = promotionModel.findByName(product.getPromotion())
                .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
        return promotion.checkDate(DateTimes.now().toLocalDate());
    }

    public int purchasePromotionProduct(PurchaseDto purchaseDto) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        Promotion promotion = promotionModel.findByName(product.getPromotion())
                .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
        int min = Math.min(product.getPromotionQuantity(), purchaseDto.quantity());
        PromotionDto promotionDto = promotion.applicablePromotion(min);
        purchaseProduct(purchaseDto.name(), product.getPrice(), promotionDto.applicableCount(),
                promotionDto.freeCount());
        return purchaseDto.quantity() - promotionDto.applicableCount();
    }

    public int checkFreePromotion(PurchaseDto purchaseDto,
                                  BiFunction<String, Integer, String> inputFunction) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        Promotion promotion = promotionModel.findByName(product.getPromotion())
                .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
        // 추가 적용
        if (product.getPromotionQuantity() >= promotion.getPromotionUnit()
                && promotion.getBuy().equals(purchaseDto.quantity())) {
            String input = inputFunction.apply(product.getName(), promotion.getGet());
            if (input.equals("Y")) {
                purchaseProduct(purchaseDto.name(), product.getPrice(),
                        purchaseDto.quantity() + promotion.getGet(), promotion.getGet());
                return 0;
            }
        }
        return purchaseDto.quantity();
    }

    public boolean checkCantApplyPromotion(PurchaseDto purchaseDto,
                                           BiFunction<String, Integer, String> inputFunction) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        String input = inputFunction.apply(product.getName(), purchaseDto.quantity());
        return input.equals("Y");
    }

    public void purchaseNormalProduct(PurchaseDto purchaseDto) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        purchaseProduct(purchaseDto.name(), product.getPrice(), purchaseDto.quantity(), 0);
    }

    public void memberShip(Supplier<String> inputSupplier) {
        String input = inputSupplier.get();
        if (input.equals("Y")) {
            cartModel.setMembership(true);
            return;
        }
        cartModel.setMembership(false);
    }

    public void purchaseProduct(String name, Integer price, Integer quantity, Integer freeCount) {
        if (freeCount == 0) {
            productModel.decreaseNormal(name, quantity);
            cartModel.addNormalQuantity(name, price, quantity);
            return;
        }
        productModel.decreasePromotion(name, quantity);
        cartModel.addPromotionQuantity(name, price, quantity, freeCount);
    }

    public String getCalculateResult() {
        List<CalculateProductDto> productInfo = cartModel.calculate();
        List<CalculatePromotionDto> promotionInfo = cartModel.calculatePromotion();
        CalculateResultDto resultInfo = cartModel.result();
        cartModel.clear();
        return receiptBuilder.issue(productInfo, promotionInfo, resultInfo);
    }

    public String getStoredProductInfo() {
        StringBuilder sb = new StringBuilder();
        productService.appendProductInfo(sb);
        return sb.toString();
    }
}
