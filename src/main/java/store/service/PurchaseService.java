package store.service;

import java.util.List;
import store.builder.ReceiptBuilder;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.dto.PurchaseDto;
import store.entity.Product;
import store.model.CartModel;
import store.parse.PurchaseInputParser;

public class PurchaseService {

    private final ProductService productService;
    private final PromotionService promotionService;
    private final CartService cartService;
    private final PurchaseInputParser purchaseInputParser;
    private final CartModel cartModel;
    private final ReceiptBuilder receiptBuilder;

    public PurchaseService(ProductService productService, PromotionService promotionService, CartService cartService,
                           PurchaseInputParser purchaseInputParser, CartModel cartModel,
                           ReceiptBuilder receiptBuilder) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.cartService = cartService;
        this.purchaseInputParser = purchaseInputParser;
        this.cartModel = cartModel;
        this.receiptBuilder = receiptBuilder;
    }

    public void purchase(String purchaseInput) {
        List<PurchaseDto> purchaseDtos = purchaseInputParser.toPurchaseDtoList(purchaseInput);
        productService.validateStock(purchaseDtos);
        for (PurchaseDto purchaseDto : purchaseDtos) {
            Product product = productService.getByName(purchaseDto.name());
            cartService.add(purchaseDto, product.getPrice());
            productService.decreaseStock(purchaseDto);
        }
    }

    public String getCalculateResult() {
        List<CalculateProductDto> productInfo = cartService.getProductInfo();
        List<CalculatePromotionDto> promotionInfo = cartService.getPromotionInfo();
        CalculateResultDto resultInfo = cartService.getResultInfo();
        cartService.completePayed();
        return receiptBuilder.issue(productInfo, promotionInfo, resultInfo);
    }
}
