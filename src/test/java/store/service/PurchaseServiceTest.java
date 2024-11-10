package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.injection.TestObjectFactory;
import store.model.CartModel;
import store.model.ProductModel;

class PurchaseServiceTest {

    private ProductService productService;
    private PromotionService promotionService;
    private PurchaseService purchaseService;

    private ProductModel productModel;
    private CartModel cartModel;

    @BeforeEach
    void init() {
        TestObjectFactory testObjectFactory = new TestObjectFactory();

        productModel = testObjectFactory.productModel;
        cartModel = testObjectFactory.cartModel;
        productService = testObjectFactory.productService;
        promotionService = testObjectFactory.promotionService;
        purchaseService = testObjectFactory.purchaseService;
    }

    @Test
    void 상품_구매에_성공한다() {
        // given
        productService.supply();
        promotionService.supply();
        String purchaseInput = "[콜라-10]";

        // when
        purchaseService.purchase(purchaseInput);

        // then
        assertThat(productModel.getProducts().get("콜라").getQuantity()).isEqualTo(9);
        assertThat(productModel.getProducts().get("콜라").getPromotionQuantity()).isEqualTo(1);
        assertThat(cartModel.getItems().get("콜라").getQuantity()).isEqualTo(1);
        assertThat(cartModel.getItems().get("콜라").getPromotionQuantity()).isEqualTo(9);
    }

    @Test
    void 영수증_출력에_성공한다() {
        // given
        productService.supply();
        promotionService.supply();
        purchaseService.purchase("[콜라-10],[사이다-3]");

        // when

        // then
        assertThatCode(() -> purchaseService.getCalculateResult())
                .doesNotThrowAnyException();
    }
}