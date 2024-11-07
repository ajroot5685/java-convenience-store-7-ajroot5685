package store.service;

import java.util.List;
import store.entity.Product;
import store.file.ConvenienceDataReader;
import store.model.ProductModel;
import store.parse.Parser;

public class ProductService {

    private final String fileName;
    private final ConvenienceDataReader convenienceDataReader;
    private final Parser<Product> productParser;
    private final ProductModel productModel;

    public ProductService(String fileName, ConvenienceDataReader convenienceDataReader, Parser<Product> productParser,
                          ProductModel productModel) {
        this.fileName = fileName;
        this.convenienceDataReader = convenienceDataReader;
        this.productParser = productParser;
        this.productModel = productModel;
    }

    public void supply() {
        List<Product> products = loadProducts();
        productModel.init(products);
    }

    private List<Product> loadProducts() {
        List<String[]> table = convenienceDataReader.read(fileName);
        return productParser.parse(table);
    }
}