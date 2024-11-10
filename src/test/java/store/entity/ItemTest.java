package store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;

class ItemTest {

    @Test
    void Item_을_생성하면_정보성_필드들이_0으로_초기화된다() {
        // given
        String name = "콜라";
        Integer price = 1000;

        // when
        Item item = new Item(name, price);

        // then
        assertThat(item.getTotalQuantity()).isEqualTo(0);
        assertThat(item.getFreeQuantity()).isEqualTo(0);
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
        clone.increaseFreeQuantity(badQuantity);

        // then
        assertThat(item.getFreeQuantity()).isEqualTo(0);
    }

    @Test
    void 최종_계산_결과를_위한_단일_항목_계산_메서드가_정상적으로_작동한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer totalQuantity = 10;
        Integer freeQuantity = 3;
        Item item = new Item("콜라", price);

        // when
        item.increaseTotalQuantity(totalQuantity);
        item.increaseFreeQuantity(freeQuantity);

        // then
        Long expectTotalCount = (long) totalQuantity;
        Long expectTotalPrice = (long) totalQuantity * item.getPrice();
        Long expectDiscountPrice = (long) freeQuantity * item.getPrice();
        Long expectPayAmount = ((long) totalQuantity - freeQuantity) * item.getPrice();
        CalculateProductDto expectCalculateProductDto =
                new CalculateProductDto(name, expectTotalCount, expectTotalPrice);
        CalculatePromotionDto expectCalculatePromotionDto =
                new CalculatePromotionDto(name, freeQuantity);

        assertThat(item.getTotalQuantity()).isEqualTo(expectTotalCount);
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