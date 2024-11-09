package store.view;

import static store.constant.Message.PURCHASE_GUIDE;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getInput() {
        return Console.readLine();
    }

    public void printPurchaseGuide() {
        System.out.println(PURCHASE_GUIDE);
    }
}
