package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Product;
import store.injection.TestObjectFactory;
import store.model.ProductModel;

class ProductServiceTest {

    private ProductService productService;

    private ProductModel productModel;

    @BeforeEach
    void init() {
        TestObjectFactory testObjectFactory = new TestObjectFactory();

        productModel = testObjectFactory.productModel;
        productService = testObjectFactory.productService;
    }

    @Test
    void 상품_정보를_불러와_저장한다() {
        // given

        // when
        productService.supply();

        // then
        Map<String, Product> products = productModel.getProducts();
        assertThat(products.values().size()).isGreaterThan(0);
    }

    @Test
    void 저장된_상품_정보를_StringBuilder_에_추가한다() {
        // given
        productService.supply();
        StringBuilder sb = new StringBuilder();

        // when
        productService.appendProductInfo(sb);

        // then
        assertThat(!sb.isEmpty()).isTrue();
    }
}