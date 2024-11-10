package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;
import store.injection.TestObjectFactory;
import store.model.PromotionModel;

class PromotionServiceTest {

    private PromotionService promotionService;

    private PromotionModel promotionModel;

    @BeforeEach
    void init() {
        TestObjectFactory testObjectFactory = new TestObjectFactory();

        promotionModel = testObjectFactory.promotionModel;
        promotionService = testObjectFactory.promotionService;
    }

    @Test
    void 프로모션_정보를_불러와_저장한다() {
        // given

        // when
        promotionService.supply();

        // then
        Map<String, Promotion> promotions = promotionModel.getPromotions();
        assertThat(promotions.get("MD추천상품")).isNotNull();
    }
}