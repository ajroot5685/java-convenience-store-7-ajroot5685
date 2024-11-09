package store.dto;

public record CalculateResultDto(
        Long totalCount,
        Long totalPrice,
        Long promotionDiscount,
        Long membershipDiscount,
        Long payAmount
) {
}
