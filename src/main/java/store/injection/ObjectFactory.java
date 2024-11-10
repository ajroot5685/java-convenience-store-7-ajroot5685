package store.injection;

import store.builder.ProductOutputBuilder;
import store.builder.ReceiptBuilder;
import store.controller.PurchaseController;
import store.controller.SupplyController;
import store.dto.ProductDto;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.CartModel;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.InputParser;
import store.parse.Parser;
import store.parse.ProductDtoParser;
import store.parse.PromotionParser;
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
    private final InputParser inputParser = new InputParser();

    // builder
    private final ProductOutputBuilder productOutputBuilder = new ProductOutputBuilder();
    private final ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    // view
    private final InputView inputView = new InputView(inputValidator, inputParser);
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
    private final PurchaseService purchaseService = new PurchaseService(productService, productModel,
            promotionModel, cartModel, receiptBuilder);

    // controller
    public final SupplyController supplyController = new SupplyController(supplyService);
    public final PurchaseController purchaseController = new PurchaseController(inputView, outputView, purchaseService);
}
