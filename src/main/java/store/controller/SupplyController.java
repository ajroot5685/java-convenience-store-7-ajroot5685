package store.controller;

import store.constant.Message;
import store.service.SupplyService;
import store.view.OutputView;

public class SupplyController {

    private final OutputView outputView;
    private final SupplyService supplyService;

    public SupplyController(OutputView outputView, SupplyService supplyService) {
        this.outputView = outputView;
        this.supplyService = supplyService;
    }

    public void save() {
        supplyService.supply();
        outputView.print(Message.SHOW_STORED_PRODUCT);
        outputView.print(supplyService.getStoredProductInfo());
    }
}
