package store.model;

import java.util.ArrayList;
import java.util.List;
import store.entity.Product;

public class ProductModel {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void init(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
    }
}