package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.ProductDtoParser;
import store.parse.PromotionParser;

class SupplyServiceTest {

    private SupplyService supplyService;

    private PromotionModel promotionModel;

    @BeforeEach
    void init() {
        ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
        promotionModel = new PromotionModel();

        PromotionService promotionService = new PromotionService("promotions.md", convenienceDataReader,
                new PromotionParser(),
                promotionModel);
        ProductService productService = new ProductService("products.md", convenienceDataReader, new ProductDtoParser(),
                new ProductModel());

        supplyService = new SupplyService(productService, promotionService);
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