package store.parse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.constant.ExceptionMessage;
import store.dto.ProductDto;
import store.file.ConvenienceDataReader;

class ProductDtoParserTest {

    private final ProductDtoParser productDtoParser = new ProductDtoParser();

    @Test
    void 데이터를_정상적으로_파싱한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("products.md");

        // when
        List<ProductDto> productDtos = productDtoParser.parse(read);

        // then
        assertThat(productDtos.size()).isGreaterThan(0);
    }

    @Test
    void 데이터_형식이_달라_파싱에_실패한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("badFormatProducts.md");

        // when

        // then
        assertThatThrownBy(() -> productDtoParser.parse(read))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(ExceptionMessage.FILE_COLUMN_SIZE_WRONG);
    }
}