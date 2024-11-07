package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;

class PromotionModelTest {

    private final PromotionModel promotionModel = new PromotionModel();

    @Test
    void Promotion_리스트로_정상적으로_초기화된다() {
        // given
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("프로모션1", 2, 1, LocalDate.parse("2024-11-07"),
                DateTimes.now().toLocalDate()));
        promotions.add(new Promotion("프로모션2", 3, 2, LocalDate.parse("2024-11-07"),
                DateTimes.now().toLocalDate()));

        // when
        promotionModel.init(promotions);

        // then
        Map<String, Promotion> storedPromotions = promotionModel.getPromotions();

        assertThat(storedPromotions.get("프로모션1")).isNotNull();
        assertThat(storedPromotions.get("프로모션2")).isNotNull();
    }

    @Test
    void 방어적_읽기로_인해_내부_데이터를_악의적으로_조작할_수_없다() {
        // given
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("프로모션1", 2, 1, LocalDate.parse("2024-11-07"),
                DateTimes.now().toLocalDate()));
        promotionModel.init(promotions);

        // when
        Map<String, Promotion> defensiveData = promotionModel.getPromotions();
        defensiveData.put("악의적 추가", new Promotion("", 2, 99, LocalDate.parse("2024-11-07"),
                LocalDate.parse("2099-11-07")));

        // then
        Map<String, Promotion> newDefensiveData = promotionModel.getPromotions();
        assertThat(newDefensiveData.get("악의적 추가")).isNull();
    }
}