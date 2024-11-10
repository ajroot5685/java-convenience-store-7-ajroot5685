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
    private boolean membership;

    public Map<String, Item> getItems() {
        Map<String, Item> defensiveMap = new LinkedHashMap<>();
        for (Entry<String, Item> entry : items.entrySet()) {
            defensiveMap.put(entry.getKey(), entry.getValue().clone());
        }
        return defensiveMap;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public void addNormalQuantity(String name, int price, long quantity) {
        createItem(name, price);
        items.get(name).increaseNormalQuantity(quantity);
    }

    public void addPromotionQuantity(String name, Integer price, Integer quantity, Integer freeQuantity) {
        createItem(name, price);
        items.get(name).increasePromotionQuantity(quantity);
        items.get(name).increaseFreeQuantity(freeQuantity);
    }

    private void createItem(String name, int price) {
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

    private long totalCount() {
        return items.values().stream()
                .mapToLong(Item::getTotalQuantity)
                .sum();
    }

    private long totalPrice() {
        return items.values().stream()
                .mapToLong(Item::calculateTotalPrice)
                .sum();
    }

    private long promotionDiscount() {
        return items.values().stream()
                .filter(item -> item.getFreeQuantity() != 0)
                .mapToLong(Item::calculateDiscountPrice)
                .sum();
    }

    private long membershipDiscount() {
        if (!membership) {
            return 0L;
        }
        long payAmountBeforeMembershipDiscount = items.values().stream()
                .mapToLong(Item::calculateNotApplyPromotionAmount)
                .sum();
        long discount = payAmountBeforeMembershipDiscount * 3 / 10;
        return Math.min(8000, discount);
    }

    private long payAmount() {
        long payAmountBeforeMembershipDiscount = items.values().stream()
                .mapToLong(Item::calculatePayAmount)
                .sum();
        return payAmountBeforeMembershipDiscount - membershipDiscount();
    }
}
