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

public class TestObjectFactory {

    // file
    private String productFileName = "products.md";
    private String promotionFileName = "promotions.md";
    private ConvenienceDataReader convenienceDataReader = new ConvenienceDataReader();

    // validator
    private InputValidator inputValidator = new InputValidator();

    // parser
    private Parser<ProductDto> productParser = new ProductDtoParser();
    private Parser<Promotion> promotionParser = new PromotionParser();
    private PurchaseInputParser purchaseInputParser = new PurchaseInputParser();

    // builder
    private ProductOutputBuilder productOutputBuilder = new ProductOutputBuilder();
    private ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    // view
    private InputView inputView = new InputView();
    private OutputView outputView = new OutputView();

    // model
    public ProductModel productModel = new ProductModel();
    public PromotionModel promotionModel = new PromotionModel();
    public CartModel cartModel = new CartModel();

    // service
    public ProductService productService = new ProductService(productFileName, convenienceDataReader,
            productParser, productOutputBuilder, productModel);
    public PromotionService promotionService = new PromotionService(promotionFileName, convenienceDataReader,
            promotionParser, promotionModel);
    public SupplyService supplyService = new SupplyService(productService, promotionService);
    public PurchaseService purchaseService = new PurchaseService(productModel, promotionModel, cartModel,
            purchaseInputParser, receiptBuilder);

    // controller
    public SupplyController supplyController = new SupplyController(outputView, supplyService);
    public PurchaseController purchaseController = new PurchaseController(inputView, outputView, inputValidator,
            purchaseService);
}
