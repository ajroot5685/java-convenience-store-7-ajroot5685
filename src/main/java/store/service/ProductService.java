package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.builder.ProductOutputBuilder;
import store.constant.ExceptionMessage;
import store.dto.ProductDto;
import store.entity.Product;
import store.entity.Promotion;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.model.PromotionModel;
import store.parse.Parser;

public class ProductService {

    private final String fileName;
    private final ConvenienceDataReader convenienceDataReader;
    private final Parser<ProductDto> productDtoParser;
    private final ProductOutputBuilder productOutputBuilder;
    private final ProductModel productModel;
    private final PromotionModel promotionModel;

    public ProductService(String fileName, ConvenienceDataReader convenienceDataReader,
                          Parser<ProductDto> productDtoParser, ProductOutputBuilder productOutputBuilder,
                          ProductModel productModel, PromotionModel promotionModel) {
        this.fileName = fileName;
        this.convenienceDataReader = convenienceDataReader;
        this.productDtoParser = productDtoParser;
        this.productOutputBuilder = productOutputBuilder;
        this.productModel = productModel;
        this.promotionModel = promotionModel;
    }

    public void supply() {
        List<ProductDto> products = loadProducts();
        products.stream()
                .filter(this::validatePromotion)
                .forEach(productModel::add);
    }

    private boolean validatePromotion(ProductDto productDto) {
        if (productDto.promotion() == null) {
            return true;
        }
        Promotion promotion = promotionModel.findByName(productDto.promotion())
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.PROMOTION_NOT_FOUND));
        return promotion.checkDate(DateTimes.now().toLocalDate());
    }

    private List<ProductDto> loadProducts() {
        List<String[]> table = convenienceDataReader.read(fileName);
        return productDtoParser.parse(table);
    }

    public void appendProductInfo(StringBuilder sb) {
        List<Product> products = productModel.getProducts().values().stream()
                .toList();
        productOutputBuilder.build(sb, products);
    }
}