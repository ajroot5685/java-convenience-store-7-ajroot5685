package store.view;

import static store.constant.ExceptionMessage.ERROR_PREFIX;

public class OutputView {

    public void print(String s) {
        System.out.println(s);
    }

    public static void printError(String s) {
        System.out.println(ERROR_PREFIX + s);
    }
}
