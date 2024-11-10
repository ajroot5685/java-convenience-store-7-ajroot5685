package store.validate;

import static store.constant.ExceptionMessage.INPUT_CANT_EMPTY;
import static store.constant.ExceptionMessage.INPUT_FORMAT_ERROR;
import static store.constant.ExceptionMessage.PRODUCT_END_WITH_RIGHT_SQUARE_BRACKET;
import static store.constant.ExceptionMessage.PRODUCT_NAME_KOREAN;
import static store.constant.ExceptionMessage.PRODUCT_SEPARATOR_HYPHEN;
import static store.constant.ExceptionMessage.PRODUCT_START_WITH_LEFT_SQUARE_BRACKET;
import static store.constant.ExceptionMessage.QUANTITY_POSITIVE_INTEGER;
import static store.constant.ExceptionMessage.WRONG_INPUT;

public class InputValidator {

    private final String SEPARATOR = ",";
    private final String HYPHEN = "-";
    private final String EMPTY = "";
    private final String EMPTY_REGEX = ".*\\s+.*";
    private final String KOREAN_REGEX = "^[가-힣]+$";
    private final String SQUARE_BRACKET_REGEX = "[\\[\\]]";
    private final String CHOOSE_REGEX = "^[YN]$";
    private final String LEFT_SQUARE_BRACKET = "[";
    private final String RIGHT_SQUARE_BRACKET = "]";
    private final int SINGLE_PRODUCT_FORMAT_SIZE = 2;

    public void validatePurchaseInput(String input) {
        validateBlank(input);
        String[] split = input.split(SEPARATOR);
        for (String s : split) {
            validateSinglePurchaseInput(s);
        }
    }

    private void validateBlank(String input) {
        if (input == null || input.isEmpty() || input.matches(EMPTY_REGEX)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + INPUT_CANT_EMPTY);
        }
    }

    private void validateSinglePurchaseInput(String input) {
        validateSquareBracket(input);
        String[] split = input.split(HYPHEN);
        validateFormatSize(split);
        validateProductName(removeSquareBracket(split[0]));
        validateQuantity(removeSquareBracket(split[1]));
    }

    private void validateSquareBracket(String input) {
        if (!input.startsWith(LEFT_SQUARE_BRACKET)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + PRODUCT_START_WITH_LEFT_SQUARE_BRACKET);
        }
        if (!input.endsWith(RIGHT_SQUARE_BRACKET)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + PRODUCT_END_WITH_RIGHT_SQUARE_BRACKET);
        }
    }

    private String removeSquareBracket(String input) {
        return input.replaceAll(SQUARE_BRACKET_REGEX, EMPTY);
    }

    private void validateFormatSize(String[] split) {
        if (split.length != SINGLE_PRODUCT_FORMAT_SIZE) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + PRODUCT_SEPARATOR_HYPHEN);
        }
    }

    private void validateProductName(String productName) {
        if (!productName.matches(KOREAN_REGEX)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + PRODUCT_NAME_KOREAN);
        }
    }

    private void validateQuantity(String quantity) {
        if (!isPositiveInteger(quantity)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + QUANTITY_POSITIVE_INTEGER);
        }
    }

    private boolean isPositiveInteger(String quantity) {
        try {
            int value = Integer.parseInt(quantity);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void validateChooseInput(String input) {
        if (!input.matches(CHOOSE_REGEX)) {
            throw new IllegalArgumentException(INPUT_FORMAT_ERROR + WRONG_INPUT);
        }
    }
}
