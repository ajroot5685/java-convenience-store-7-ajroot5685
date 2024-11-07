package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.PromotionModel;
import store.parse.Parser;
import store.parse.PromotionParser;

class SupplyServiceTest {

    private SupplyService supplyService;

    private PromotionModel promotionModel;

    @BeforeEach
    void init() {
        String fileName = "promotions.md";
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        Parser<Promotion> promotionParser = new PromotionParser();
        promotionModel = new PromotionModel();
        PromotionService promotionService = new PromotionService(fileName, convenienceDataReader, promotionParser,
                promotionModel);

        supplyService = new SupplyService(promotionService);
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