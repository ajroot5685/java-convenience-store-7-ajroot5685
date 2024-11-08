package store.parse;

import java.util.List;
import store.dto.ProductDto;

public class ProductDtoParser implements Parser<ProductDto> {

    private final int COLUMN_COUNT = 4;

    @Override
    public List<ProductDto> parse(List<String[]> table) {
        validateSize(table, COLUMN_COUNT);
        return table.stream()
                .map(this::toProductDto)
                .toList();
    }

    private ProductDto toProductDto(String[] values) {
        String name = values[0];
        Integer price = Integer.parseInt(values[1]);
        Integer quantity = Integer.parseInt(values[2]);
        String promotion = getPromotionData(values[3]);
        return new ProductDto(name, price, quantity, promotion);
    }

    private String getPromotionData(String promotionValue) {
        if (promotionValue.equals("null")) {
            return null;
        }
        return promotionValue;
    }
}