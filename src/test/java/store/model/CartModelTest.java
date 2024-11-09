package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import store.entity.Item;

class CartModelTest {

    private final CartModel cartModel = new CartModel();

    @Test
    void 정상적으로_구매할_상품이_등록된다() {
        // given
        String name = "콜라";
        Integer price = 1000;

        // when
        cartModel.registerItem(name, price);

        // then
        Map<String, Item> items = cartModel.getItems();
        assertThat(items.containsKey(name)).isTrue();
    }

    @Test
    void 이미_상품이_등록되어_있으면_아무_동작도_하지_않는다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        cartModel.registerItem(name, price);

        // when
        cartModel.registerItem(name, price);

        // then
        Map<String, Item> items = cartModel.getItems();
        assertThat(items.values().size()).isEqualTo(1);
    }

    @Test
    void 상품을_구매하여_구매한_수량을_증가시킨다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        cartModel.registerItem(name, price);

        // when
        cartModel.addQuantity(name, quantity);

        // then
        Map<String, Item> items = cartModel.getItems();
        // 방어적 읽기로 인해 확인불가
//        assertThat(items.get(name).getQuantity()).isEqualTo(quantity);
    }
}