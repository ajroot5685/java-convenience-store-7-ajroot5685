package store.parse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.PurchaseDto;

class InputParserTest {

    private InputParser inputParser = new InputParser();

    @Test
    void 입력을_PurchaseDto_리스트로_변환한다() {
        // given
        String purchaseInput = "[콜라-10],[사이다-3]";

        // when
        List<PurchaseDto> purchaseDtos = inputParser.toPurchaseDtoList(purchaseInput);

        // then
        assertThat(purchaseDtos.get(0).name()).isEqualTo("콜라");
        assertThat(purchaseDtos.get(0).quantity()).isEqualTo(10);
        assertThat(purchaseDtos.get(1).name()).isEqualTo("사이다");
        assertThat(purchaseDtos.get(1).quantity()).isEqualTo(3);
    }
}