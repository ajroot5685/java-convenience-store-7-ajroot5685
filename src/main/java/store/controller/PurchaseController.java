package store.controller;

import static store.constant.Message.PURCHASE_AGAIN_GUIDE;

import store.service.PurchaseService;
import store.util.RetryHandler;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final String AGAIN = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final PurchaseService purchaseService;

    public PurchaseController(InputView inputView, OutputView outputView, PurchaseService purchaseService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.purchaseService = purchaseService;
    }

    public void purchase() {
        while (true) {
            process();
            if (!again()) {
                break;
            }
        }
    }

    private void process() {
        inputView.printPurchaseGuide();
        String input = RetryHandler.retryUntilSuccess(inputView::getPurchaseInput);
        purchaseService.purchase(input);
        purchaseService.memberShip();
        outputView.print(purchaseService.getCalculateResult());
    }

    private boolean again() {
        outputView.print(PURCHASE_AGAIN_GUIDE);
        String input = RetryHandler.retryUntilSuccess(inputView::getRetryInput);
        if (input.equals(AGAIN)) {
            return true;
        }
        return false;
    }
}
