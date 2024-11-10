package store.view;

import static store.constant.Message.PURCHASE_GUIDE;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getInput() {
        String input = Console.readLine();
        System.out.println();
        return input;
    }

    public void printPurchaseGuide() {
        System.out.println(PURCHASE_GUIDE);
    }
}
