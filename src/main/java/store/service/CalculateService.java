package store.service;

import java.util.List;
import java.util.function.Supplier;
import store.builder.ReceiptBuilder;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.model.CartModel;

public class CalculateService {

    private final ReceiptBuilder receiptBuilder;
    private final CartModel cartModel;

    public CalculateService(ReceiptBuilder receiptBuilder, CartModel cartModel) {
        this.receiptBuilder = receiptBuilder;
        this.cartModel = cartModel;
    }

    public void memberShip(Supplier<String> inputSupplier) {
        String input = inputSupplier.get();
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
