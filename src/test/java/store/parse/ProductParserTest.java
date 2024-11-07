package store.parse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.constant.ExceptionMessage;
import store.entity.Product;
import store.file.ConvenienceDataReader;

class ProductParserTest {

    private final ProductParser productParser = new ProductParser();

    @Test
    void 데이터를_정상적으로_파싱한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("products.md");

        // when
        List<Product> products = productParser.parse(read);

        // then
        assertThat(products.size()).isEqualTo(16);
    }

    @Test
    void 데이터_형식이_달라_파싱에_실패한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("badFormatProducts.md");

        // when

        // then
        assertThatThrownBy(() -> productParser.parse(read))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(ExceptionMessage.FILE_COLUMN_SIZE_WRONG);
    }
}