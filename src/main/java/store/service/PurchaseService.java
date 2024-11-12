package store.service;

import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;
import static store.constant.ExceptionMessage.PROMOTION_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.function.BiFunction;
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

    public PurchaseService(ProductService productService, ProductModel productModel,
                           PromotionModel promotionModel, CartModel cartModel) {
        this.productService = productService;
        this.productModel = productModel;
        this.promotionModel = promotionModel;
        this.cartModel = cartModel;
    }

    public boolean isStockAvailable(List<PurchaseDto> purchaseDtos) {
        return purchaseDtos.stream()
                .allMatch(purchaseDto -> productModel.isStockAvailable(purchaseDto.name(), purchaseDto.quantity()));
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

    public int purchasePromotionProduct2(PurchaseDto purchaseDto) {
        Product product = productModel.findByName(purchaseDto.name())
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
        int min = Math.min(product.getPromotionQuantity(), purchaseDto.quantity());
        purchasePromotionProduct(purchaseDto.name(), product.getPrice(), min, 0);
        return purchaseDto.quantity() - min;
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

    public void purchaseProduct(String name, Integer price, Integer quantity, Integer freeCount) {
        if (freeCount == 0) {
            productModel.decreaseNormal(name, quantity);
            cartModel.addNormalQuantity(name, price, quantity);
            return;
        }
        productModel.decreasePromotion(name, quantity);
        cartModel.addPromotionQuantity(name, price, quantity, freeCount);
    }

    public void purchasePromotionProduct(String name, Integer price, Integer quantity, Integer freeCount) {
        productModel.decreasePromotion(name, quantity);
        cartModel.addPromotionQuantity(name, price, quantity, freeCount);
    }

    public String getStoredProductInfo() {
        StringBuilder sb = new StringBuilder();
        productService.appendProductInfo(sb);
        return sb.toString();
    }
}
