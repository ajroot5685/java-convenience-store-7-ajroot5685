package store.service;

import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;
import static store.constant.ExceptionMessage.PRODUCT_QUANTITY_INSUFFICIENT;
import static store.constant.ExceptionMessage.PROMOTION_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
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
import store.parse.PurchaseInputParser;
import store.view.ProcessView;

public class PurchaseService {

    private final ProcessView processView;
    private final ProductModel productModel;
    private final PromotionModel promotionModel;
    private final CartModel cartModel;
    private final PurchaseInputParser purchaseInputParser;
    private final ReceiptBuilder receiptBuilder;

    public PurchaseService(ProcessView processView, ProductModel productModel, PromotionModel promotionModel,
                           CartModel cartModel,
                           PurchaseInputParser purchaseInputParser, ReceiptBuilder receiptBuilder) {
        this.processView = processView;
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
        PromotionDto promotionDto = applicablePromotionCalculate(product, purchaseDto);
        purchaseProduct(purchaseDto.name(), product.getPrice(), promotionDto.applicableCount(),
                promotionDto.freeCount());

        Integer normalCount = purchaseDto.quantity() - promotionDto.applicableCount();

        if (product.getPromotion() != null) {
            PromotionDto additionalFreeCount = getAdditionalFreeCount(product,
                    purchaseDto.quantity() - promotionDto.applicableCount());
            normalCount -= (additionalFreeCount.applicableCount() - additionalFreeCount.freeCount());
            purchaseProduct(purchaseDto.name(), product.getPrice(), additionalFreeCount.applicableCount(),
                    additionalFreeCount.freeCount());
        }

        if (normalCount == 0) {
            return;
        }
        if (product.getQuantity() < normalCount) {
            throw new IllegalArgumentException(PRODUCT_QUANTITY_INSUFFICIENT);
        }
        if (!normalCount.equals(purchaseDto.quantity())) {
            processView.printNotApplyPromotion(product.getName(), normalCount);
            String chooseInput = processView.getChooseInput();
            if (chooseInput.equals("N")) {
                return;
            }
        }
        purchaseProduct(purchaseDto.name(), product.getPrice(), normalCount, 0);
    }

    private PromotionDto getAdditionalFreeCount(Product product, Integer quantity) {
        Promotion promotion = promotionModel.findByName(product.getPromotion())
                .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
        if (promotion.getBuy().equals(quantity)) {
            processView.printFreeProduct(product.getName(), promotion.getGet());
            String input = processView.getChooseInput();
            if (input.equals("Y")) {
                return new PromotionDto(quantity + promotion.getGet(), promotion.getGet());
            }
        }
        return new PromotionDto(0, 0);
    }

    private PromotionDto applicablePromotionCalculate(Product product, PurchaseDto purchaseDto) {
        if (isPromotionApplicable(product)) {
            Promotion promotion = promotionModel.findByName(product.getPromotion())
                    .orElseThrow(() -> new IllegalArgumentException(PROMOTION_NOT_FOUND));
            int min = Math.min(product.getPromotionQuantity(), purchaseDto.quantity());
            return promotion.applicablePromotion(min);
        }
        return new PromotionDto(0, 0);
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

    private void purchaseProduct(String name, Integer price, Integer quantity, Integer freeCount) {
        if (freeCount == 0) {
            productModel.decreaseNormal(name, quantity);
            cartModel.addNormalQuantity(name, price, quantity);
            return;
        }
        productModel.decreasePromotion(name, quantity);
        cartModel.addPromotionQuantity(name, price, quantity, freeCount);
    }

    public void memberShip() {
        processView.printMembershipApply();
        String input = processView.getChooseInput();
        if (input.equals("Y")) {
            cartModel.setMembership(true);
            return;
        }
        cartModel.setMembership(false);
    }

    public String getCalculateResult() {
        List<CalculateProductDto> productInfo = cartModel.calculate();
        List<CalculatePromotionDto> promotionInfo = cartModel.calculatePromotion();
        CalculateResultDto resultInfo = cartModel.result();
        cartModel.clear();
        return receiptBuilder.issue(productInfo, promotionInfo, resultInfo);
    }
}
