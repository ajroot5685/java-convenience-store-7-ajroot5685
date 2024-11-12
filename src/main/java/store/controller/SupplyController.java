package store.controller;

import store.service.SupplyService;

public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    public void save() {
        supplyService.supply();
    }
}
