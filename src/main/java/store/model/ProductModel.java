package store.model;

import static store.constant.ExceptionMessage.PRODUCT_NOT_FOUND;
import static store.constant.ExceptionMessage.PRODUCT_QUANTITY_INSUFFICIENT;
import static store.validate.ProductValidator.validateBeforeInsert;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import store.dto.ProductDto;
import store.entity.Product;

public class ProductModel {

    private final Map<String, Product> products = new LinkedHashMap<>();

    public Map<String, Product> getProducts() {
        Map<String, Product> defensiveMap = new LinkedHashMap<>();
        for (Entry<String, Product> entry : products.entrySet()) {
            defensiveMap.put(entry.getKey(), entry.getValue().clone());
        }
        return defensiveMap;
    }

    public void add(ProductDto productDto) {
        String name = productDto.name();
        if (!products.containsKey(name)) {
            products.put(name, Product.create(name, productDto.price()));
        }

        Product product = products.get(name);
        validateBeforeInsert(productDto, product);
        update(product, productDto);
    }

    private void update(Product product, ProductDto productDto) {
        if (productDto.promotion() == null) {
            product.setQuantity(productDto.quantity());
            return;
        }
        product.setPromotion(productDto.promotion());
        product.setPromotionQuantity(productDto.quantity());
    }

    public void validateAvailability(String name, Integer quantity) {
        if (!products.containsKey(name)) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }
        if (products.get(name).getQuantity() < quantity) {
            throw new IllegalArgumentException(PRODUCT_QUANTITY_INSUFFICIENT);
        }
    }

    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(products.get(name));
    }

    public void decrease(String name, Integer quantity) {
        products.get(name).decreaseQuantity(quantity);
    }
}