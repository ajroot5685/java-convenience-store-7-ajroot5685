package store.service;

import java.util.List;
import store.dto.ProductDto;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.parse.Parser;

public class ProductService {

    private final String fileName;
    private final ConvenienceDataReader convenienceDataReader;
    private final Parser<ProductDto> productDtoParser;
    private final ProductModel productModel;

    public ProductService(String fileName, ConvenienceDataReader convenienceDataReader,
                          Parser<ProductDto> productDtoParser, ProductModel productModel) {
        this.fileName = fileName;
        this.convenienceDataReader = convenienceDataReader;
        this.productDtoParser = productDtoParser;
        this.productModel = productModel;
    }

    public void supply() {
        List<ProductDto> products = loadProducts();
        products.forEach(productModel::add);
    }

    private List<ProductDto> loadProducts() {
        List<String[]> table = convenienceDataReader.read(fileName);
        return productDtoParser.parse(table);
    }
}