package store.entity;

import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;

public class Item {

    private final String name;
    private final Integer price;
    private Long totalQuantity;
    private Integer freeQuantity;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
        this.totalQuantity = 0L;
        this.freeQuantity = 0;
    }

    private Item(String name, Integer price, Long totalQuantity, Integer freeQuantity) {
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.freeQuantity = freeQuantity;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public Integer getFreeQuantity() {
        return freeQuantity;
    }

    public Item clone() {
        return new Item(this.name, this.price, this.totalQuantity, this.freeQuantity);
    }

    public void increaseTotalQuantity(Integer totalQuantity) {
        this.totalQuantity += totalQuantity;
    }

    public void increaseFreeQuantity(Integer freeQuantity) {
        this.freeQuantity += freeQuantity;
    }

    public Long calculateTotalPrice() {
        return price * totalQuantity;
    }

    public Long calculateDiscountPrice() {
        return (long) price * freeQuantity;
    }

    public Long calculatePayAmount() {
        return (long) price * (totalQuantity - freeQuantity);
    }

    public CalculateProductDto calculate() {
        return new CalculateProductDto(name, totalQuantity, calculateTotalPrice());
    }

    public CalculatePromotionDto calculatePromotion() {
        return new CalculatePromotionDto(name, freeQuantity);
    }
}
