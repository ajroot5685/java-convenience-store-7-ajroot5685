package store.parse;

import java.time.LocalDate;
import java.util.List;
import store.entity.Promotion;

public class PromotionParser implements Parser<Promotion> {

    private final int COLUMN_COUNT = 5;

    @Override
    public List<Promotion> parse(List<String[]> table) {
        validateSize(table, COLUMN_COUNT);
        return table.stream()
                .map(this::toPromotion)
                .toList();
    }

    private Promotion toPromotion(String[] values) {
        String name = values[0];
        Integer buy = Integer.parseInt(values[1]);
        Integer get = Integer.parseInt(values[2]);
        LocalDate startDate = LocalDate.parse(values[3]);
        LocalDate endDate = LocalDate.parse(values[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
