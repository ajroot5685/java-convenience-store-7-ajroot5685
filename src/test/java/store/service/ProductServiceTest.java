package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.builder.ProductOutputBuilder;
import store.dto.PurchaseDto;
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
                new ProductOutputBuilder(), productModel);
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

    @Test
    void 구매_목록_리스트로_구매_가능한지_겅증에_성공한다() {
        // given
        productService.supply();
        List<PurchaseDto> purchaseDtos = List.of(
                new PurchaseDto("콜라", 10),
                new PurchaseDto("사이다", 3)
        );

        // when

        // then
        assertThatCode(() -> productService.validateStock(purchaseDtos))
                .doesNotThrowAnyException();
    }

    @Test
    void 상품_객체를_정상적으로_조회한다() {
        // given
        productService.supply();
        String presentProductName = "콜라";

        // when

        // then
        assertThatCode(() -> productService.getByName(presentProductName))
                .doesNotThrowAnyException();
    }

    @Test
    void 빈_상품_객체를_조회하여_에러가_발생한다() {
        // given
        productService.supply();
        String notPresentProductName = "존재하지않는상품";

        // when

        // then
        assertThatThrownBy(() -> productService.getByName(notPresentProductName))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(PRODUCT_NOT_FOUND);
    }
}