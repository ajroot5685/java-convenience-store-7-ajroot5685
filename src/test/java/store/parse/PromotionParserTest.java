package store.parse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.constant.ExceptionMessage;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;

class PromotionParserTest {

    private final PromotionParser promotionParser = new PromotionParser();

    @Test
    void 데이터를_정상적으로_파싱한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("promotions.md");

        // when
        List<Promotion> promotions = promotionParser.parse(read);

        // then
        assertThat(promotions.get(0).getName()).isEqualTo("탄산2+1");
        assertThat(promotions.get(1).getName()).isEqualTo("MD추천상품");
    }

    @Test
    void 데이터_형식이_달라_파싱에_실패한다() {
        // given
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        List<String[]> read = convenienceDataReader.read("badFormatPromotions.md");

        // when

        // then
        assertThatThrownBy(() -> promotionParser.parse(read))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(ExceptionMessage.FILE_COLUMN_SIZE_WRONG);
    }

}