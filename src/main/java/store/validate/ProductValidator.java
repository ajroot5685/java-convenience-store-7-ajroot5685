package store.validate;

import store.dto.ProductDto;
import store.entity.Product;

public class ProductValidator {

    public static void validateBeforeInsert(ProductDto productDto, Product product) {
        validatePrice(productDto, product);
        validateFile(productDto, product);
    }

    private static void validatePrice(ProductDto productDto, Product product) {
        if (!productDto.price().equals(product.getPrice())) {
            throw new IllegalArgumentException("상품의 정상가격과 프로모션 가격은 같아야 합니다.");
        }
    }

    private static void validateFile(ProductDto productDto, Product product) {
        if (productDto.promotion() != null && product.getPromotion() != null) {
            throw new IllegalArgumentException("같은 상품의 프로모션 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.");
        }
        if (productDto.promotion() == null && product.getQuantity() != 0) {
            throw new IllegalArgumentException("같은 상품 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.");
        }
    }
}
