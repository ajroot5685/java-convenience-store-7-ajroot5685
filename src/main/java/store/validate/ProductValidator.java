package store.validate;

import static store.constant.ExceptionMessage.FILE_NORMAL_PRODUCT_DUPLICATE;
import static store.constant.ExceptionMessage.FILE_PRICE_ERROR;
import static store.constant.ExceptionMessage.FILE_PROMOTION_PRODUCT_DUPLICATE;

import store.dto.ProductDto;
import store.entity.Product;

public class ProductValidator {

    public static void validateBeforeInsert(ProductDto productDto, Product product) {
        validatePrice(productDto, product);
        validateFile(productDto, product);
    }

    private static void validatePrice(ProductDto productDto, Product product) {
        if (!productDto.price().equals(product.getPrice())) {
            throw new IllegalArgumentException(FILE_PRICE_ERROR);
        }
    }

    private static void validateFile(ProductDto productDto, Product product) {
        if (productDto.promotion() != null && product.getPromotion() != null) {
            throw new IllegalArgumentException(FILE_PROMOTION_PRODUCT_DUPLICATE);
        }
        if (productDto.promotion() == null && product.getQuantity() != 0) {
            throw new IllegalArgumentException(FILE_NORMAL_PRODUCT_DUPLICATE);
        }
    }
}
