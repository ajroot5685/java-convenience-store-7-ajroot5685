package store.entity;

import store.dto.CalculateProductDto;

public class Item {

    private final String name;
    private final Integer price;
    private Integer quantity;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Item clone() {
        return new Item(this.name, this.price);
    }

    public void buy(Integer quantity) {
        this.quantity += quantity;
    }

    public Long calculateTotalPrice() {
        return (long) price * quantity;
    }

    public CalculateProductDto calculate() {
        return new CalculateProductDto(name, quantity, calculateTotalPrice());
    }
}
