package store.entity;

import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;

public class Item {

    private final String name;
    private final int price;
    private long normalQuantity;
    private long promotionQuantity;
    private long freeQuantity;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    private Item(String name, int price, long normalQuantity, long promotionQuantity, long freeQuantity) {
        this.name = name;
        this.price = price;
        this.normalQuantity = normalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.freeQuantity = freeQuantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public long getNormalQuantity() {
        return normalQuantity;
    }

    public long getPromotionQuantity() {
        return promotionQuantity;
    }

    public long getFreeQuantity() {
        return freeQuantity;
    }

    public long getTotalQuantity() {
        return normalQuantity + promotionQuantity;
    }

    public Item clone() {
        return new Item(name, price, normalQuantity, promotionQuantity, freeQuantity);
    }

    public void increaseNormalQuantity(long normalQuantity) {
        this.normalQuantity += normalQuantity;
    }

    public void increasePromotionQuantity(long promotionQuantity) {
        this.promotionQuantity += promotionQuantity;
    }

    public void increaseFreeQuantity(Integer freeQuantity) {
        this.freeQuantity += freeQuantity;
    }

    public long calculateTotalPrice() {
        return price * getTotalQuantity();
    }

    public long calculateDiscountPrice() {
        return price * freeQuantity;
    }

    public long calculatePayAmount() {
        return price * (getTotalQuantity() - freeQuantity);
    }

    public long calculateNotApplyPromotionAmount() {
        return price * normalQuantity;
    }

    public CalculateProductDto calculate() {
        return new CalculateProductDto(name, getTotalQuantity(), calculateTotalPrice());
    }

    public CalculatePromotionDto calculatePromotion() {
        return new CalculatePromotionDto(name, freeQuantity);
    }
}
