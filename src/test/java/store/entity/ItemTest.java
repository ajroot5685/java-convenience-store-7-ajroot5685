package store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void Item_을_생성하면_quantity가_0으로_초기화된다() {
        // given
        String name = "콜라";
        Integer price = 1000;

        // when
        Item item = new Item(name, price);

        // then
        assertThat(item.getQuantity()).isEqualTo(0);
    }

    @Test
    void clone_을_사용하여_방어적_읽기를_수행한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Item item = new Item(name, price);

        // when
        Item clone = item.clone();
        Integer badQuantity = -99999;
        clone.buy(badQuantity);

        // then
        assertThat(item.getQuantity()).isEqualTo(0);
    }

    @Test
    void 일반상품을_구매하면_그만큼_Item의_개수가_증가한다() {
        // given
        Item item = new Item("콜라", 1000);
        Integer quantity = 2;

        // when
        item.buy(quantity);

        // then
        assertThat(item.getQuantity()).isEqualTo(quantity);
    }
}