package store.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.entity.Promotion;

public class PromotionModel {

    private final Map<String, Promotion> promotions = new HashMap<>();

    public Map<String, Promotion> getPromotions() {
        return new HashMap<>(promotions);
    }

    public void init(List<Promotion> promotions) {
        this.promotions.clear();
        promotions.forEach(promotion -> this.promotions.put(promotion.getName(), promotion));
    }

    public Optional<Promotion> findByName(String name) {
        return Optional.ofNullable(promotions.get(name));
    }
}
