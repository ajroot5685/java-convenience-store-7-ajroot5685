package store.service;

import java.util.List;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.PromotionModel;
import store.parse.Parser;

public class PromotionService {

    private final String fileName;
    private final ConvenienceDataReader convenienceDataReader;
    private final Parser<Promotion> promotionParser;
    private final PromotionModel promotionModel;

    public PromotionService(String fileName, ConvenienceDataReader convenienceDataReader,
                            Parser<Promotion> promotionParser, PromotionModel promotionModel) {
        this.fileName = fileName;
        this.convenienceDataReader = convenienceDataReader;
        this.promotionParser = promotionParser;
        this.promotionModel = promotionModel;
    }

    public void supply() {
        List<Promotion> promotions = loadPromotions();
        promotionModel.init(promotions);
    }

    private List<Promotion> loadPromotions() {
        List<String[]> table = convenienceDataReader.read(fileName);
        return promotionParser.parse(table);
    }
}
