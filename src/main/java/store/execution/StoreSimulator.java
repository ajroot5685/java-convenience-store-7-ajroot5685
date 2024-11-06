package store.execution;

import store.injection.ObjectFactory;
import store.view.GreetView;

public class StoreSimulator {

    private final GreetView greetView;
    private final ObjectFactory objectFactory;

    public StoreSimulator() {
        this.greetView = new GreetView();
        this.objectFactory = new ObjectFactory();
    }

    public void run() {
        greetView.printGreetMessage();
    }
}
