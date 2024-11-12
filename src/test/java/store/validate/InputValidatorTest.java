package store.validate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ExceptionMessage.INPUT_FORMAT_ERROR;
import static store.constant.ExceptionMessage.WRONG_INPUT;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    private InputValidator inputValidator = new InputValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-10],[사이다-3]",
            "[한글-10]",
            "[가나다라마바사아자차카타파하-3]",
            "[치킨-20000],[피자-9999]",
            "[모니터-2147483647],[키보드-2147483647]",
            "[볼펜-5],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10],[연필-10]",
            "[책상-3333],[의자-123456]",
            "[수박-1],[배-999999]",
            "[한국어-2147483647],[한국어-2147483647],[한국어-2147483647],[한국어-2147483647],[한국어-2147483647]"
    })
    void 구매할_상품_입력_검증에_성공한다(String input) {
        // given

        // when

        // then
        assertThatCode(() -> inputValidator.validatePurchaseInput(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "\t",
            "\n",
            "\r",
            "왼쪽대괄호없음-10]",
            "[오른쪽대괄호없음-10",
            "[콜라-10,왼쪽대괄호없음-3]",
            "[오른쪽공백있음 -10]",
            "[콜라-10], [대괄호왼쪽에공백있음-3]",
            "[ 왼쪽공백있음-10]",
            "[숫자앞에공백있음- 3]",
            "[English-10]",
            "[123-10]",
            "[영어있음a-10]",
            "[자음만있음ㅁㅁㅁ-10]",
            "[숫자가음수--10]",
            "[숫자가제로-0]",
            "[인트범위를넘음-2147483648]",
            "[숫자가아님-abc]",
            "콜라-10],[Cider -3]",
            "[콜라-2147483648],[사이다-abc]",
            " [한글-10],[영어-3]",
    })
    void 대괄호_양식이_맞지_않아_검증에_실패한다(String input) {
        // given

        // when

        // then
        assertThatThrownBy(() -> inputValidator.validatePurchaseInput(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Y", "N"})
    void 사용자가_선택_질의에_대해_올바르게_입력한다(String input) {
        // given

        // when

        // then
        assertThatCode(() -> inputValidator.validateChooseInput(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"y", "n", ".", "", "\n", "\t", "NO"})
    void 사용자가_선택_질의에_대해_잘못된_값을_입력한다(String input) {
        // given

        // when

        // then
        assertThatThrownBy(() -> inputValidator.validateChooseInput(input))
                .isInstanceOf(IllegalArgumentException.class)
                .extracting(Throwable::getMessage)
                .isEqualTo(INPUT_FORMAT_ERROR + WRONG_INPUT);
    }
}