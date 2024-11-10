package store.entity;

import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;

public class Item {

    private final String name;
    private final Integer price;
    private Integer promotionQuantity;
    private Integer quantity;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = 0;
        this.quantity = 0;
    }

    private Item(String name, Integer price, Integer promotionQuantity, Integer quantity) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.quantity = quantity;
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

    public Item clone() {
        return new Item(this.name, this.price, this.promotionQuantity, this.quantity);
    }

    public void increaseQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void increasePromotionQuantity(Integer quantity) {
        this.promotionQuantity += quantity;
    }

    public Long calculateTotalCount() {
        return (long) quantity + promotionQuantity;
    }

    public Long calculateTotalPrice() {
        return price * calculateTotalCount();
    }

    public Long calculateDiscountPrice() {
        return (long) price * promotionQuantity;
    }

    public Long calculatePayAmount() {
        return (long) price * quantity;
    }

    public CalculateProductDto calculate() {
        return new CalculateProductDto(name, calculateTotalCount(), calculateTotalPrice());
    }

    public CalculatePromotionDto calculatePromotion() {
        return new CalculatePromotionDto(name, promotionQuantity);
    }
}
