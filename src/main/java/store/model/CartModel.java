package store.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
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

    public void registerItem(String name, Integer price) {
        if (!items.containsKey(name)) {
            items.put(name, new Item(name, price));
        }
    }

    public void addQuantity(String name, Integer quantity) {
        items.get(name).buy(quantity);
    }
}
