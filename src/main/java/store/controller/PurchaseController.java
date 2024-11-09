package store.controller;

import static store.constant.Message.PURCHASE_AGAIN_GUIDE;

import store.service.PurchaseService;
import store.validate.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final String AGAIN = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final InputValidator inputValidator;
    private final PurchaseService purchaseService;

    public PurchaseController(InputView inputView, OutputView outputView, InputValidator inputValidator,
                              PurchaseService purchaseService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputValidator = inputValidator;
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
        String input = inputView.getInput();
        inputValidator.validatePurchaseInput(input);
        purchaseService.purchase(input);
        outputView.print(purchaseService.getCalculateResult());
    }

    private boolean again() {
        outputView.print(PURCHASE_AGAIN_GUIDE);
        String input = inputView.getInput();
        inputValidator.validateChooseInput(input);
        if (input.equals(AGAIN)) {
            return true;
        }
        return false;
    }
}
