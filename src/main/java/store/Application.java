package store;

import store.execution.StoreSimulator;

public class Application {
    public static void main(String[] args) {
        StoreSimulator storeSimulator = new StoreSimulator();
        storeSimulator.run();
    }
}
