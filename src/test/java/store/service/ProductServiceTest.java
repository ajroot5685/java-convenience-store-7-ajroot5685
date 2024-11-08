package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Product;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.parse.ProductDtoParser;

class ProductServiceTest {

    private ProductService productService;

    private ProductModel productModel;

    @BeforeEach
    void init() {
        productModel = new ProductModel();

        productService = new ProductService("products.md", new ConvenienceDataReader(), new ProductDtoParser(),
                productModel);
    }

    @Test
    void 제품_정보를_불러와_저장한다() {
        // given

        // when
        productService.supply();

        // then
        Map<String, Product> products = productModel.getProducts();
        assertThat(products.values().size()).isGreaterThan(0);
    }
}