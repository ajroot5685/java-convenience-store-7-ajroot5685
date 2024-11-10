package store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;

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
        Integer badQuantity = 99999;
        clone.increasePromotionQuantity(badQuantity);

        // then
        assertThat(item.getQuantity()).isEqualTo(0);
    }

    @Test
    void 최종_계산_결과를_위한_계산_메서드가_정상적으로_작동한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 5;
        Integer promotionQuantity = 10;
        Item item = new Item("콜라", 1000);

        // when
        item.increaseQuantity(quantity);
        item.increasePromotionQuantity(promotionQuantity);

        // then
        Long expectTotalCount = (long) quantity + promotionQuantity;
        Long expectTotalPrice = ((long) quantity + promotionQuantity) * item.getPrice();
        Long expectDiscountPrice = (long) promotionQuantity * item.getPrice();
        Long expectPayAmount = (long) quantity * item.getPrice();
        CalculateProductDto expectCalculateProductDto =
                new CalculateProductDto(name, expectTotalCount, expectTotalPrice);
        CalculatePromotionDto expectCalculatePromotionDto =
                new CalculatePromotionDto(name, promotionQuantity);

        assertThat(item.calculateTotalCount()).isEqualTo(expectTotalCount);
        assertThat(item.calculateTotalPrice()).isEqualTo(expectTotalPrice);
        assertThat(item.calculateDiscountPrice()).isEqualTo(expectDiscountPrice);
        assertThat(item.calculatePayAmount()).isEqualTo(expectPayAmount);

        CalculateProductDto calculateProductDto = item.calculate();
        assertThat(calculateProductDto.name()).isEqualTo(expectCalculateProductDto.name());
        assertThat(calculateProductDto.quantity()).isEqualTo(expectCalculateProductDto.quantity());
        assertThat(calculateProductDto.totalPrice()).isEqualTo(expectCalculateProductDto.totalPrice());

        CalculatePromotionDto calculatePromotionDto = item.calculatePromotion();
        assertThat(calculatePromotionDto).isEqualTo(expectCalculatePromotionDto);
    }
}