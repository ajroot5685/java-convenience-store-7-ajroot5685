package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.dto.CalculateProductDto;
import store.dto.CalculateResultDto;
import store.dto.PurchaseDto;
import store.model.CartModel;

class CartServiceTest {

    private CartService cartService;
    private CartModel cartModel;

    @BeforeEach
    void init() {
        cartModel = new CartModel();
        cartService = new CartService(cartModel);
    }

    @Test
    void 구매_상품을_등록한다() {
        // given
        PurchaseDto purchaseDto = new PurchaseDto("콜라", 2);
        Integer price = 1000;

        // when
        cartService.add(purchaseDto, price);

        // then
        assertThat(cartModel.getItems().get("콜라").getPrice()).isEqualTo(1000);
        // 방어적 읽기로 인해 검증 불가
//        assertThat(cartModel.getItems().get("콜라").getQuantity()).isEqualTo(2);
    }

    @Test
    void 장바구니_상품_목록을_조회한다() {
        // given
        String name = "콜라";
        Integer quantity = 2;
        Integer price = 1000;
        PurchaseDto purchaseDto = new PurchaseDto(name, quantity);
        cartService.add(purchaseDto, price);

        // when
        List<CalculateProductDto> productInfo = cartService.getProductInfo();

        // then
        assertThat(productInfo.get(0).name()).isEqualTo(name);
        assertThat(productInfo.get(0).quantity()).isEqualTo(quantity);
        assertThat(productInfo.get(0).totalPrice()).isEqualTo((long) quantity * price);
    }

    @Test
    void 장바구니_결제_정보를_조회한다() {
        // given
        String name = "콜라";
        Integer quantity = 2;
        Integer price = 1000;
        PurchaseDto purchaseDto = new PurchaseDto(name, quantity);
        cartService.add(purchaseDto, price);

        // when
        CalculateResultDto resultInfo = cartService.getResultInfo();

        // then
        assertThat(resultInfo.totalCount()).isEqualTo((long) quantity);
        assertThat(resultInfo.totalPrice()).isEqualTo((long) quantity * price);
        assertThat(resultInfo.payAmount()).isEqualTo((long) quantity * price);
    }
}