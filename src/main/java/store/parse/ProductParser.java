package store.parse;

import java.util.List;
import store.entity.Product;

public class ProductParser implements Parser<Product> {

    private final int COLUMN_COUNT = 4;

    @Override
    public List<Product> parse(List<String[]> table) {
        validateSize(table, COLUMN_COUNT);
        return table.stream()
                .map(this::toProduct)
                .toList();
    }

    private Product toProduct(String[] values) {
        String name = values[0];
        Integer buy = Integer.parseInt(values[1]);
        Integer get = Integer.parseInt(values[2]);
        String promotion = values[3];
        return new Product(name, buy, get, promotion);
    }
}