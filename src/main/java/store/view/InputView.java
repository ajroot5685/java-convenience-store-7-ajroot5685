package store.view;

import static store.constant.ExceptionMessage.WRONG_INPUT;
import static store.constant.Message.FREE_PRODUCT;
import static store.constant.Message.MEMBERSHIP_APPLY;
import static store.constant.Message.NOT_APPLY_PROMOTION;
import static store.constant.Message.PURCHASE_GUIDE;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.dto.PurchaseDto;
import store.parse.InputParser;
import store.util.RetryHandler;
import store.validate.InputValidator;

public class InputView {

    private final String CHOOSE_REGEX = "^[YN]$";

    private final InputValidator inputValidator;
    private final InputParser inputParser;

    public InputView(InputValidator inputValidator, InputParser inputParser) {
        this.inputValidator = inputValidator;
        this.inputParser = inputParser;
    }

    public String getInput() {
        String input = Console.readLine();
        System.out.println();
        return input;
    }

    public List<PurchaseDto> getPurchaseInput() {
        String input = Console.readLine();
        inputValidator.validatePurchaseInput(input);
        System.out.println();
        return inputParser.toPurchaseDtoList(input);
    }

    public String getRetryInput() {
        String input = Console.readLine();
        inputValidator.validateChooseInput(input);
        System.out.println();
        return input;
    }

    public String getApplyFreeInput(String name, int freeQuantity) {
        System.out.printf(FREE_PRODUCT, name, freeQuantity);
        String input = RetryHandler.retryUntilSuccess(this::getChooseInput);
        System.out.println();
        return input;
    }

    public String getCantApplyPromotionInput(String name, int remainQuantity) {
        System.out.printf(NOT_APPLY_PROMOTION, name, remainQuantity);
        String input = RetryHandler.retryUntilSuccess(this::getChooseInput);
        System.out.println();
        return input;
    }

    public String getApplyMembership() {
        System.out.println(MEMBERSHIP_APPLY);
        return RetryHandler.retryUntilSuccess(this::getChooseInput);
    }

    private String getChooseInput() {
        String input = Console.readLine();
        if (!input.matches(CHOOSE_REGEX)) {
            throw new IllegalArgumentException(WRONG_INPUT);
        }
        return input;
    }

    public void printPurchaseGuide() {
        System.out.println(PURCHASE_GUIDE);
    }
}
