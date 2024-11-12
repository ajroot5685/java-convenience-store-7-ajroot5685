package store.dto;

public record ProductDto(
        String name,
        Integer price,
        Integer quantity,
        String promotion
) {

}
