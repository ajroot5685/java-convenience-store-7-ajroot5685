package store.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.constant.ExceptionMessage;

class ConvenienceDataReaderTest {

    private final ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();

    @Test
    void 테스트_파일을_정상적으로_읽어온다() {
        // given
        String fileName = "promotions.md";

        // when
        List<String[]> read = convenienceDataReader.read(fileName);

        // then
        assertThat(read.get(0)[0]).isEqualTo("탄산2+1");
        assertThat(read.get(1)[3]).isEqualTo("2024-01-01");
        assertThat(read.get(2)[0]).isEqualTo("반짝할인");
        assertThat(read.get(2)[1]).isEqualTo("1");
    }

    @Test
    void 존재하지_않는_파일_이름을_설정하여_에러가_발생한다() {
        // given
        String fileName = "notExist.md";

        // when

        // then
        assertThatThrownBy(() -> convenienceDataReader.read(fileName))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(ExceptionMessage.FILE_PARSING_ERROR);
    }
}