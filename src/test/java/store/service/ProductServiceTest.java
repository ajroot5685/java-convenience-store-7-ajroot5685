package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Product;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.parse.ProductParser;

class ProductServiceTest {

    private ProductService productService;

    private ProductModel productModel;

    @BeforeEach
    void init() {
        productModel = new ProductModel();

        productService = new ProductService("products.md", new ConvenienceDataReader(), new ProductParser(),
                productModel);
    }

    @Test
    void 제품_정보를_불러와_저장한다() {
        // given

        // when
        productService.supply();

        // then
        List<Product> products = productModel.getProducts();
        assertThat(products.size()).isEqualTo(16);
    }
}