package store.injection;

import store.view.InputView;
import store.view.OutputView;

public class ObjectFactory {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
}
