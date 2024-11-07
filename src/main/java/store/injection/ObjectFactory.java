package store.injection;

import store.controller.SupplyController;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.PromotionModel;
import store.parse.Parser;
import store.parse.PromotionParser;
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
    private final Parser<Promotion> promotionParser = new PromotionParser();

    public SupplyController supplyController() {
        return new SupplyController(inputView, supplyService());
    }

    private SupplyService supplyService() {
        return new SupplyService(promotionService());
    }

    private PromotionService promotionService() {
        return new PromotionService(promotionFileName, convenienceDataReader, promotionParser, promotionModel());
    }

    private PromotionModel promotionModel() {
        return new PromotionModel();
    }
}
