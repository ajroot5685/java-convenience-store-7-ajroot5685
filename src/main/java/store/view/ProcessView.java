package store.view;

import static store.constant.ExceptionMessage.WRONG_INPUT;
import static store.constant.Message.FREE_PRODUCT;
import static store.constant.Message.MEMBERSHIP_APPLY;
import static store.constant.Message.NOT_APPLY_PROMOTION;

import camp.nextstep.edu.missionutils.Console;

public class ProcessView {

    private final String CHOOSE_REGEX = "^[YN]$";

    public String getChooseInput() {
        String input = Console.readLine();
        if (!input.matches(CHOOSE_REGEX)) {
            throw new IllegalArgumentException(WRONG_INPUT);
        }
        System.out.println();
        return input;
    }

    public void printFreeProduct(String name, Integer freeQuantity) {
        System.out.printf(FREE_PRODUCT, name, freeQuantity);
    }

    public void printNotApplyPromotion(String name, Integer quantity) {
        System.out.printf(NOT_APPLY_PROMOTION, name, quantity);
    }

    public void printMembershipApply() {
        System.out.println(MEMBERSHIP_APPLY);
    }
}
