package store.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;
import store.entity.Item;

public class CartModel {

    private final Map<String, Item> items = new LinkedHashMap<>();

    public Map<String, Item> getItems() {
        Map<String, Item> defensiveMap = new LinkedHashMap<>();
        for (Entry<String, Item> entry : items.entrySet()) {
            defensiveMap.put(entry.getKey(), entry.getValue().clone());
        }
        return defensiveMap;
    }

    public void addQuantity(String name, Integer price, Integer quantity, Integer freeQuantity) {
        createItem(name, price);
        items.get(name).increaseTotalQuantity(quantity);
        items.get(name).increaseFreeQuantity(freeQuantity);
    }

    private void createItem(String name, Integer price) {
        if (!items.containsKey(name)) {
            items.put(name, new Item(name, price));
        }
    }

    public void clear() {
        items.clear();
    }

    public List<CalculateProductDto> calculate() {
        return items.values().stream()
                .map(Item::calculate)
                .toList();
    }

    public List<CalculatePromotionDto> calculatePromotion() {
        return items.values().stream()
                .filter(item -> item.getFreeQuantity() != 0)
                .map(Item::calculatePromotion)
                .toList();
    }

    public CalculateResultDto result() {
        return new CalculateResultDto(
                totalCount(),
                totalPrice(),
                promotionDiscount(),
                membershipDiscount(),
                payAmount()
        );
    }

    private Long totalCount() {
        return items.values().stream()
                .mapToLong(Item::getTotalQuantity)
                .sum();
    }

    private Long totalPrice() {
        return items.values().stream()
                .mapToLong(Item::calculateTotalPrice)
                .sum();
    }

    private Long promotionDiscount() {
        return items.values().stream()
                .filter(item -> item.getFreeQuantity() != 0)
                .mapToLong(item -> -item.calculateDiscountPrice())
                .sum();
    }

    private Long membershipDiscount() {
        return 0L;
    }

    private Long payAmount() {
        return items.values().stream()
                .mapToLong(Item::calculatePayAmount)
                .sum();
    }
}
