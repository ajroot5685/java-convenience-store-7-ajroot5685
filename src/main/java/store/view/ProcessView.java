package store.view;

import static store.constant.ExceptionMessage.WRONG_INPUT;

import camp.nextstep.edu.missionutils.Console;

public class ProcessView {

    private final String CHOOSE_REGEX = "^[YN]$";

    public String getChooseInput() {
        String input = Console.readLine();
        if (!input.matches(CHOOSE_REGEX)) {
            throw new IllegalArgumentException(WRONG_INPUT);
        }
        return input;
    }

    public void printFreeProduct(String name, Integer freeQuantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", name, freeQuantity);
    }

    public void printNotApplyPromotion(String name, Integer quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", name, quantity);
    }
}
