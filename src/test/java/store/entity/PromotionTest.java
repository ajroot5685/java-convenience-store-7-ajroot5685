package store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void 정상적으로_생성한다() {
        // given
        String name = "이름";
        Integer buy = 2;
        Integer get = 1;
        LocalDate startDate = LocalDate.parse("2024-11-07");
        LocalDate endDate = DateTimes.now().toLocalDate();

        // when
        Promotion promotion = new Promotion(name, buy, get, startDate, endDate);

        // then
        assertThat(promotion.getName()).isEqualTo(name);
    }
}