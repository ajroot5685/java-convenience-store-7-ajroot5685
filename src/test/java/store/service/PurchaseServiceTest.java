package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.builder.ProductOutputBuilder;
import store.builder.ReceiptBuilder;
import store.file.ConvenienceDataReader;
import store.model.CartModel;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.ProductDtoParser;
import store.parse.PromotionParser;
import store.parse.PurchaseInputParser;

class PurchaseServiceTest {

    private ProductService productService;
    private PurchaseService purchaseService;

    private ProductModel productModel;
    private CartModel cartModel;

    @BeforeEach
    void init() {
        productModel = new ProductModel();
        cartModel = new CartModel();
        productService = new ProductService("products.md", new ConvenienceDataReader(),
                new ProductDtoParser(), new ProductOutputBuilder(), productModel);
        PromotionService promotionService = new PromotionService("promotions.md", new ConvenienceDataReader(),
                new PromotionParser(), new PromotionModel());
        this.purchaseService = new PurchaseService(productService, promotionService, new CartService(cartModel),
                new PurchaseInputParser(),
                cartModel, new ReceiptBuilder());
    }

    @Test
    void 상품_구매에_성공한다() {
        // given
        productService.supply();
        String purchaseInput = "[콜라-10],[사이다-3]";

        // when
        purchaseService.purchase(purchaseInput);

        // then
        assertThat(productModel.getProducts().get("콜라").getQuantity()).isEqualTo(0);
        // 방어적 읽기로 인해 확인 불가
//        assertThat(cartModel.getItems().get("콜라").getQuantity()).isEqualTo(10);
    }

    @Test
    void 영수증_출력에_성공한다() {
        // given
        productService.supply();
        purchaseService.purchase("[콜라-10],[사이다-3]");

        // when

        // then
        assertThatCode(() -> purchaseService.getCalculateResult())
                .doesNotThrowAnyException();
    }
}