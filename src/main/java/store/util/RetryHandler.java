package store.util;

import java.util.function.Supplier;
import store.view.OutputView;

public class RetryHandler {

    public static <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
