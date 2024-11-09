package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.dto.PurchaseDto;
import store.model.CartModel;

public class CartService {

    private final CartModel cartModel;

    public CartService(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    public void add(PurchaseDto purchaseDto, Integer price) {
        cartModel.registerItem(purchaseDto.name(), price);
        cartModel.addQuantity(purchaseDto.name(), purchaseDto.quantity());
    }

    public List<CalculateProductDto> getProductInfo() {
        return cartModel.calculate();
    }

    public List<CalculatePromotionDto> getPromotionInfo() {
        // TODO - 담긴 프로모션 상품 반환
        return new ArrayList<>();
    }

    public CalculateResultDto getResultInfo() {
        return cartModel.result();
    }

    public void completePayed() {
        cartModel.clear();
    }
}
