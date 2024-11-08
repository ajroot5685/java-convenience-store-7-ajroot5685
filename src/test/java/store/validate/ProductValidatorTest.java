package store.validate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.Randoms;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.dto.ProductDto;
import store.entity.Product;

class ProductValidatorTest {

    @Test
    void 상품_정보_추가_전_검증에_성공한다() {
        // give
        ProductDto productDto = new ProductDto("콜라", 1000, 10, null);
        Product product = new Product("콜라", 1000, 0, 0, null);

        // when

        // then
        assertThatCode(() -> ProductValidator.validateBeforeInsert(productDto, product))
                .doesNotThrowAnyException();
    }

    @Test
    void 같은_상품이지만_가격이_달라_검증에_실패한다() {
        // give
        Integer newPrice = 1000;
        Integer oldPrice = 2000;
        ProductDto productDto = new ProductDto("콜라", newPrice, 10, null);
        Product product = new Product("콜라", oldPrice, 0, 0, null);

        // when

        // then
        assertThatThrownBy(() -> ProductValidator.validateBeforeInsert(productDto, product))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("상품의 정상가격과 프로모션 가격은 같아야 합니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "'프로모션', '프로모션'",
            "'다른프로모션', '프로모션'"
    })
    void 같은_상품의_프로모션_정보가_여러개가_들어온_경우_에러를_던진다(String newPromotion, String oldPromotion) {
        // give
        ProductDto productDto = new ProductDto("콜라", 1000, 10, newPromotion);
        Product product = new Product("콜라", 1000, 0, 0, oldPromotion);

        // when

        // then
        assertThatThrownBy(() -> ProductValidator.validateBeforeInsert(productDto, product))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("같은 상품의 프로모션 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.");
    }

    @Test
    void 같은_상품_정보가_여러개가_들어온_경우_에러를_던진다() {
        // give
        Integer newQuantity = Randoms.pickNumberInRange(1, 100);
        Integer oldQuantity = Randoms.pickNumberInRange(1, 100);
        ProductDto productDto = new ProductDto("콜라", 1000, newQuantity, null);
        Product product = new Product("콜라", 1000, 0, oldQuantity, null);

        // when

        // then
        assertThatThrownBy(() -> ProductValidator.validateBeforeInsert(productDto, product))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo("같은 상품 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.");
    }
}