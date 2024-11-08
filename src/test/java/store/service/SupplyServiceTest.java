package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.builder.ProductOutputBuilder;
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
                new ProductOutputBuilder(), new ProductModel());

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

    @Test
    void 상품_정보_반환에_성공한다() {
        // given
        supplyService.supply();

        // when
        String storedProductInfo = supplyService.getStoredProductInfo();

        // then
        assertThat(storedProductInfo).isEqualTo(
                """
                        - 콜라 1,000원 10개 탄산2+1
                        - 콜라 1,000원 10개
                        - 사이다 1,000원 8개 탄산2+1
                        - 사이다 1,000원 7개
                        - 오렌지주스 1,800원 9개 MD추천상품
                        - 오렌지주스 1,800원 재고 없음
                        - 탄산수 1,200원 5개 탄산2+1
                        - 탄산수 1,200원 재고 없음
                        - 물 500원 10개
                        - 비타민워터 1,500원 6개
                        - 감자칩 1,500원 5개 반짝할인
                        - 감자칩 1,500원 5개
                        - 초코바 1,200원 5개 MD추천상품
                        - 초코바 1,200원 5개
                        - 에너지바 2,000원 5개
                        - 정식도시락 6,400원 8개
                        - 컵라면 1,700원 1개 MD추천상품
                        - 컵라면 1,700원 10개
                        """
        );
    }
}