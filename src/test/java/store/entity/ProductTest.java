package store.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void 정상적으로_생성한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer promotionQuantity = 10;
        Integer quantity = 10;
        String promotion = "탄산2+1";

        // when

        // then
        Assertions.assertThatCode(() -> new Product(name, price, promotionQuantity, quantity, promotion))
                .doesNotThrowAnyException();
    }
}