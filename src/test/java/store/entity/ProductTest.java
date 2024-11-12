package store.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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
        assertThatCode(() -> new Product(name, price, promotionQuantity, quantity, promotion))
                .doesNotThrowAnyException();
    }

    @Test
    void clone_을_사용하여_방어적_읽기를_수행한다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Product product = Product.create(name, price);

        // when
        Product clone = product.clone();
        Integer badQuantity = 99999;
        clone.setQuantity(badQuantity);

        // then
        assertThat(product.getQuantity()).isNotEqualTo(badQuantity);
    }

    @Test
    void create를_사용하면_필드들이_초기화된다() {
        // given
        String name = "콜라";
        Integer price = 1000;

        // when
        Product product = Product.create(name, price);

        // then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getPromotionQuantity()).isEqualTo(0);
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(product.getPromotion()).isNull();
    }

    @Test
    void 수량을_감소시킨다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Product product = Product.create(name, price);
        product.setQuantity(10);

        Integer buyQuantity = 2;

        // when
        product.decreaseQuantity(buyQuantity);

        // then
        assertThat(product.getQuantity()).isEqualTo(8);
    }

    @Test
    void 프로모션_수량을_감소시킨다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Product product = Product.create(name, price);
        product.setPromotionQuantity(10);

        Integer buyQuantity = 2;

        // when
        product.decreasePromotionQuantity(buyQuantity);

        // then
        assertThat(product.getPromotionQuantity()).isEqualTo(8);
    }
}