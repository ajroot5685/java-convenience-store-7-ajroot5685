package store.entity;

public class Product {

    private String name;
    private Integer price;
    private Integer quantity;
    private String promotion;

    public Product(String name, Integer price, Integer quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
}