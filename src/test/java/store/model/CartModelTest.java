package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.entity.Item;

class CartModelTest {

    private final CartModel cartModel = new CartModel();

    @Test
    void 일반_상품을_구매하여_구매한_수량을_증가시킨다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        boolean isPromotion = false;

        // when
        cartModel.addQuantity(name, price, quantity, isPromotion);

        // then
        Map<String, Item> items = cartModel.getItems();
        assertThat(items.get(name).getQuantity()).isEqualTo(quantity);
    }

    @Test
    void 프로모션_상품을_구매하여_구매한_수량을_증가시킨다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        boolean isPromotion = true;

        // when
        cartModel.addQuantity(name, price, quantity, isPromotion);

        // then
        Map<String, Item> items = cartModel.getItems();
        assertThat(items.get(name).getPromotionQuantity()).isEqualTo(quantity);
    }

    @Test
    void 영수증_출력을_완료하여_장바구니를_비운다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        boolean isPromotion = true;
        cartModel.addQuantity(name, price, quantity, isPromotion);

        // when
        cartModel.clear();

        // then
        assertThat(cartModel.getItems().size()).isEqualTo(0);
    }

    @Test
    void 영수증_정보를_조회한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        Integer promotionQuantity = 5;
        cartModel.addQuantity(name, price, quantity, false);
        cartModel.addQuantity(name, price, promotionQuantity, true);

        // when
        List<CalculateProductDto> productDtos = cartModel.calculate();
        List<CalculatePromotionDto> promotionDtos = cartModel.calculatePromotion();
        CalculateResultDto result = cartModel.result();

        // then
        Long expectTotalCount = (long) quantity + promotionQuantity;
        Long expectTotalPrice = ((long) quantity + promotionQuantity) * price;
        Long expectDiscountPrice = (long) promotionQuantity * price;
        Long expectPayAmount = (long) quantity * price;

        assertThat(productDtos.get(0).name()).isEqualTo(name);
        assertThat(productDtos.get(0).quantity()).isEqualTo(expectTotalCount);
        assertThat(productDtos.get(0).totalPrice()).isEqualTo(expectTotalPrice);

        assertThat(promotionDtos.get(0).name()).isEqualTo(name);
        assertThat(promotionDtos.get(0).quantity()).isEqualTo(promotionQuantity);

        assertThat(result.totalCount()).isEqualTo(expectTotalCount);
        assertThat(result.totalPrice()).isEqualTo(expectTotalPrice);
        assertThat(result.promotionDiscount()).isEqualTo(-expectDiscountPrice);
        // TODO - 멤버쉽 할인 적용
        assertThat(result.membershipDiscount()).isEqualTo(0);
        assertThat(result.payAmount()).isEqualTo(expectPayAmount);
    }
}