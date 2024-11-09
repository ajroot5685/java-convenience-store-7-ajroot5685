package store.entity;

public class Product {

    private String name;
    private Integer price;
    private Integer promotionQuantity;
    private Integer quantity;
    private String promotion;

    public Product(String name, Integer price, Integer promotionQuantity, Integer quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getPromotionQuantity() {
        return promotionQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotionQuantity(Integer promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Product clone() {
        return new Product(this.name, this.price, this.promotionQuantity, this.quantity, this.promotion);
    }

    public static Product create(String name, Integer price) {
        return new Product(name, price, 0, 0, null);
    }

    public void decreaseQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void decreasePromotionQuantity(Integer quantity) {
        this.promotionQuantity -= quantity;
    }
}