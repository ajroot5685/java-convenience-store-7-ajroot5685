package store.view;

import static store.constant.Message.PURCHASE_GUIDE;

import camp.nextstep.edu.missionutils.Console;
import store.validate.InputValidator;

public class InputView {

    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String getInput() {
        String input = Console.readLine();
        System.out.println();
        return input;
    }

    public String getPurchaseInput() {
        String input = Console.readLine();
        inputValidator.validatePurchaseInput(input);
        System.out.println();
        return input;
    }

    public String getRetryInput() {
        String input = Console.readLine();
        inputValidator.validateChooseInput(input);
        System.out.println();
        return input;
    }

    public void printPurchaseGuide() {
        System.out.println(PURCHASE_GUIDE);
    }
}
