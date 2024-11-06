package store.execution;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StoreSimulatorTest {

    private final StoreSimulator storeSimulator = new StoreSimulator();

    @Test
    public void 애플리케이션_실행에_성공한다() {
        // given

        // when

        // then
        Assertions.assertThatCode(storeSimulator::run)
                .doesNotThrowAnyException();
    }
}