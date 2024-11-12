package store.builder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.entity.Product;

class ProductOutputBuilderTest {

    private ProductOutputBuilder productOutputBuilder = new ProductOutputBuilder();

    @Test
    void 상품_리스트를_StringBuilder에_추가한다() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("콜라", 1000, 10, 10, "탄산2+1"));
        products.add(new Product("오렌지주스", 1800, 9, 0, "MD추천상품"));

        StringBuilder sb = new StringBuilder();

        // when
        productOutputBuilder.build(sb, products);

        // then
        assertThat(sb.toString()).isEqualTo("""
                - 콜라 1,000원 10개 탄산2+1
                - 콜라 1,000원 10개
                - 오렌지주스 1,800원 9개 MD추천상품
                - 오렌지주스 1,800원 재고 없음
                """);
    }
}