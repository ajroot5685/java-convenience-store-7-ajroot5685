package store.entity;

import java.time.LocalDate;
import store.dto.PromotionDto;

public class Promotion {

    private String name;
    private Integer buy;
    private Integer get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }

    public boolean checkDate(LocalDate now) {
        return (now.isEqual(startDate) || now.isAfter(startDate)) &&
                (now.isEqual(endDate) || now.isBefore(endDate));
    }

    public PromotionDto applicablePromotion(Integer quantity) {
        int unit = buy + get;
        int applicableCount = quantity - quantity % unit;
        int freeCount = (quantity / unit) * get;
        return new PromotionDto(applicableCount, freeCount);
    }
}
