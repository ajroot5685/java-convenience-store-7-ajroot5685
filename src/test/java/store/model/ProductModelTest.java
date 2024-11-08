package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.dto.ProductDto;
import store.entity.Product;

class ProductModelTest {

    private final ProductModel productModel = new ProductModel();

    @Test
    void 프로모션_상품이_정상적으로_저장된다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        String promotion = "탄산2+1";
        ProductDto productDto = new ProductDto(name, price, quantity, promotion);

        // when
        productModel.add(productDto);

        // then
        Product product = productModel.getProducts().get(name);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getPromotionQuantity()).isEqualTo(quantity);
        assertThat(product.getPromotion()).isEqualTo(promotion);
    }

    @Test
    void 일반_상품이_정상적으로_저장된다() {
        // given
        String name = "콜라";
        Integer price = 1000;
        Integer quantity = 10;
        String promotion = null;
        ProductDto productDto = new ProductDto(name, price, quantity, promotion);

        // when
        productModel.add(productDto);

        // then
        Product product = productModel.getProducts().get(name);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getQuantity()).isEqualTo(quantity);
        assertThat(product.getPromotion()).isNull();
    }

    @Test
    void 방어적_읽기로_인해_내부_데이터를_악의적으로_조작할_수_없다() {
        // given
        String name = "콜라";
        productModel.add(new ProductDto(name, 1000, 10, null));

        // when
        Integer badQuantity = 99999;
        Product defensiveProduct = productModel.getProducts().get(name);
        defensiveProduct.setQuantity(badQuantity);

        // then
        Product newDefensiveProduct = productModel.getProducts().get(name);
        assertThat(newDefensiveProduct.getQuantity()).isNotEqualTo(badQuantity);
    }
}