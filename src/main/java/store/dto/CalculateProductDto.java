package store.dto;

public record CalculateProductDto(
        String name,
        Integer quantity,
        Long totalPrice
) {
}
