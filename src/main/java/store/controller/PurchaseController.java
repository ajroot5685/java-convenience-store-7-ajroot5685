package store.controller;

import store.service.PurchaseService;
import store.validate.InputValidator;
import store.view.InputView;

public class PurchaseController {

    private final InputView inputView;
    private final InputValidator inputValidator;
    private final PurchaseService purchaseService;

    public PurchaseController(InputView inputView, InputValidator inputValidator, PurchaseService purchaseService) {
        this.inputView = inputView;
        this.inputValidator = inputValidator;
        this.purchaseService = purchaseService;
    }

    public void purchase() {
        inputView.printPurchaseGuide();
        String input = inputView.getInput();
        inputValidator.validatePurchaseInput(input);
        purchaseService.purchase(input);
    }
}
