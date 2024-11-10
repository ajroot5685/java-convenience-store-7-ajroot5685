package store.service;

import org.junit.jupiter.api.BeforeEach;
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

}