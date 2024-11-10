package store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {"2024-11-10", "2024-01-01", "2024-12-31"})
    void 현재_프로모션_기간에_속한다(String now) {
        // given
        LocalDate date = LocalDate.parse(now);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));

        // when
        boolean isPromotion = promotion.checkDate(date);

        // then
        assertThat(isPromotion).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023-11-10", "2023-12-31", "2025-01-01"})
    void 현재_프로모션_기간에_속하지_않는다(String now) {
        // given
        LocalDate date = LocalDate.parse(now);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));

        // when
        boolean isPromotion = promotion.checkDate(date);

        // then
        assertThat(isPromotion).isFalse();
    }

    @Test
    void 적용_가능한_프로모션_상품_개수를_반환한다() {
        // given
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));
        Integer quantity = 10;

        // when
        Integer applicableCount = promotion.applicableCount(quantity);

        // then
        assertThat(applicableCount).isEqualTo(9);
    }
}