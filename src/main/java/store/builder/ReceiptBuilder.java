package store.builder;

import java.util.List;
import store.dto.CalculateProductDto;
import store.dto.CalculatePromotionDto;
import store.dto.CalculateResultDto;

public class ReceiptBuilder {

    private final String HEADER_FORMAT = "%-10s\t\t%-5s\t%5s\n";
    private final String PRODUCT_FORMAT = "%-10s\t\t%-5d\t%-,5d\n";
    private final String PROMOTION_FORMAT = "%-10s\t\t%-5d\n";
    private final String TOTAL_COUNT_FORMAT = "%-10s\t\t%-5d\t%-,5d\n";
    private final String DISCOUNT_RESULT_FORMAT = "%-10s\t\t     \t-%-,5d\n";
    private final String PAY_AMOUNT_FORMAT = "%-10s\t\t\t     %-,5d\n";

    public String issue(List<CalculateProductDto> calculateProductDto,
                        List<CalculatePromotionDto> calculatePromotionDto,
                        CalculateResultDto calculateResultDto) {
        StringBuilder sb = new StringBuilder();
        header(sb);
        productInfo(sb, calculateProductDto);
        promotionInfo(sb, calculatePromotionDto);
        calculateResult(sb, calculateResultDto);
        return sb.toString();
    }

    private void header(StringBuilder sb) {
        sb.append("==============W 편의점================\n");
        sb.append(String.format(HEADER_FORMAT, "상품명", "수량", "금액"));
    }

    private void productInfo(StringBuilder sb, List<CalculateProductDto> calculateProductDto) {
        for (CalculateProductDto calculateProduct : calculateProductDto) {
            sb.append(String.format(PRODUCT_FORMAT, calculateProduct.name(), calculateProduct.quantity(),
                    calculateProduct.totalPrice()));
        }
    }

    private void promotionInfo(StringBuilder sb, List<CalculatePromotionDto> calculatePromotionDto) {
        sb.append("=============증\t\t정===============\n");
        for (CalculatePromotionDto calculatePromotion : calculatePromotionDto) {
            sb.append(String.format(PROMOTION_FORMAT, calculatePromotion.name(), calculatePromotion.quantity()));
        }
    }

    private void calculateResult(StringBuilder sb, CalculateResultDto calculateResultDto) {
        sb.append("====================================\n");
        sb.append(String.format(TOTAL_COUNT_FORMAT, "총구매액", calculateResultDto.totalCount(),
                calculateResultDto.totalPrice()));
        sb.append(String.format(DISCOUNT_RESULT_FORMAT, "행사할인", calculateResultDto.promotionDiscount()));
        sb.append(String.format(DISCOUNT_RESULT_FORMAT, "멤버십할인", calculateResultDto.membershipDiscount()));
        sb.append(String.format(PAY_AMOUNT_FORMAT, "내실돈", calculateResultDto.payAmount()));
    }
}
