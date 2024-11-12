package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;
import store.injection.TestObjectFactory;
import store.model.PromotionModel;

class SupplyServiceTest {

    private SupplyService supplyService;

    private PromotionModel promotionModel;

    @BeforeEach
    void init() {
        TestObjectFactory testObjectFactory = new TestObjectFactory();

        promotionModel = testObjectFactory.promotionModel;
        supplyService = testObjectFactory.supplyService;
    }

    @Test
    void 프로모션과_상품_저장에_성공한다() {
        // given

        // when
        supplyService.supply();

        // then
        Map<String, Promotion> promotions = promotionModel.getPromotions();
        assertThat(promotions.get("탄산2+1")).isNotNull();
    }
}