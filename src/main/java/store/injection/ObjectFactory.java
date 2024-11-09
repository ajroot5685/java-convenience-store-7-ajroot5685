package store.injection;

import store.builder.ProductOutputBuilder;
import store.controller.PurchaseController;
import store.controller.SupplyController;
import store.dto.ProductDto;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.CartModel;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.Parser;
import store.parse.ProductDtoParser;
import store.parse.PromotionParser;
import store.parse.PurchaseInputParser;
import store.service.ProductService;
import store.service.PromotionService;
import store.service.PurchaseService;
import store.service.SupplyService;
import store.validate.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class ObjectFactory {

    // file
    private final String productFileName = "products.md";
    private final String promotionFileName = "promotions.md";
    private final ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();

    // validator
    private final InputValidator inputValidator = new InputValidator();

    // parser
    private final Parser<ProductDto> productParser = new ProductDtoParser();
    private final Parser<Promotion> promotionParser = new PromotionParser();
    private final PurchaseInputParser purchaseInputParser = new PurchaseInputParser();

    // builder
    private final ProductOutputBuilder productOutputBuilder = new ProductOutputBuilder();

    // view
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    // model
    private final ProductModel productModel = new ProductModel();
    private final PromotionModel promotionModel = new PromotionModel();
    private final CartModel cartModel = new CartModel();

    // service
    private final ProductService productService = new ProductService(productFileName, convenienceDataReader,
            productParser, productOutputBuilder, productModel);
    private final PromotionService promotionService = new PromotionService(promotionFileName, convenienceDataReader,
            promotionParser, promotionModel);
    private final SupplyService supplyService = new SupplyService(productService, promotionService);
    private final PurchaseService purchaseService = new PurchaseService(productService, promotionService,
            purchaseInputParser, cartModel);

    // controller
    public final SupplyController supplyController = new SupplyController(outputView, supplyService);
    public final PurchaseController purchaseController = new PurchaseController(inputView, inputValidator,
            purchaseService);
}
