package store.service;

import java.util.List;
import store.dto.PurchaseDto;
import store.entity.Product;
import store.model.CartModel;
import store.parse.PurchaseInputParser;

public class PurchaseService {

    private final ProductService productService;
    private final PromotionService promotionService;
    private final PurchaseInputParser purchaseInputParser;
    private final CartModel cartModel;

    public PurchaseService(ProductService productService, PromotionService promotionService,
                           PurchaseInputParser purchaseInputParser, CartModel cartModel) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.purchaseInputParser = purchaseInputParser;
        this.cartModel = cartModel;
    }

    public void purchase(String purchaseInput) {
        List<PurchaseDto> purchaseDtos = purchaseInputParser.toPurchaseDtoList(purchaseInput);
        productService.validateStock(purchaseDtos);
        for (PurchaseDto purchaseDto : purchaseDtos) {
            Product product = productService.getByName(purchaseDto.name());
            cartModel.registerItem(product.getName(), product.getPrice());
            cartModel.addQuantity(purchaseDto.name(), purchaseDto.quantity());
            productService.decreaseStock(purchaseDto);
        }
    }
}
