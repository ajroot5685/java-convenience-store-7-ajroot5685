package store.controller;

import store.constant.Message;
import store.service.SupplyService;
import store.view.InputView;
import store.view.OutputView;

public class SupplyController {

    private final InputView inputView;
    private final OutputView outputView;
    private final SupplyService supplyService;

    public SupplyController(InputView inputView, OutputView outputView, SupplyService supplyService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.supplyService = supplyService;
    }

    public void save() {
        supplyService.supply();
        outputView.print(Message.SHOW_STORED_PRODUCT);
        outputView.print(supplyService.getStoredProductInfo());
    }
}
