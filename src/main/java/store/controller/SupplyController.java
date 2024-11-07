package store.controller;

import store.service.SupplyService;
import store.view.InputView;

public class SupplyController {

    private final InputView inputView;
    private final SupplyService supplyService;

    public SupplyController(InputView inputView, SupplyService supplyService) {
        this.inputView = inputView;
        this.supplyService = supplyService;
    }

    public void save() {
        supplyService.supply();
    }
}
