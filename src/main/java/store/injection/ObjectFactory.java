package store.injection;

import store.controller.SupplyController;
import store.dto.ProductDto;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.Parser;
import store.parse.ProductDtoParser;
import store.parse.PromotionParser;
import store.service.ProductService;
import store.service.PromotionService;
import store.service.SupplyService;
import store.view.InputView;
import store.view.OutputView;

public class ObjectFactory {

    private final String productFileName = "products.md";
    private final String promotionFileName = "promotions.md";

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();
    private final Parser<ProductDto> productParser = new ProductDtoParser();
    private final Parser<Promotion> promotionParser = new PromotionParser();

    public SupplyController supplyController() {
        return new SupplyController(inputView, supplyService());
    }

    private SupplyService supplyService() {
        return new SupplyService(productService(), promotionService());
    }

    private ProductService productService() {
        return new ProductService(productFileName, convenienceDataReader, productParser, productModel());
    }

    private PromotionService promotionService() {
        return new PromotionService(promotionFileName, convenienceDataReader, promotionParser, promotionModel());
    }

    private ProductModel productModel() {
        return new ProductModel();
    }

    private PromotionModel promotionModel() {
        return new PromotionModel();
    }
}
