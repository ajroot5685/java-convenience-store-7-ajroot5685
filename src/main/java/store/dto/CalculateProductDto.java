package store.dto;

public record CalculateProductDto(
        String name,
        Long quantity,
        Long totalPrice
) {
}
