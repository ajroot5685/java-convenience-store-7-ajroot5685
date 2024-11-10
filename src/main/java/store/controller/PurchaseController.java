package store.controller;

import static store.constant.Message.PURCHASE_AGAIN_GUIDE;

import java.util.List;
import store.constant.Message;
import store.dto.PurchaseDto;
import store.service.PurchaseService;
import store.util.RetryHandler;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final String AGAIN = "Y";

    private final InputView inputView;
    private final OutputView outputView;
    private final PurchaseService purchaseService;

    public PurchaseController(InputView inputView, OutputView outputView, PurchaseService purchaseService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.purchaseService = purchaseService;
    }

    public void purchase() {
        while (true) {
            process();
            if (!again()) {
                break;
            }
        }
    }

    private boolean again() {
        outputView.print(PURCHASE_AGAIN_GUIDE);
        String input = RetryHandler.retryUntilSuccess(inputView::getRetryInput);
        if (input.equals(AGAIN)) {
            return true;
        }
        return false;
    }

    private void process() {
        showProductInfo();

        List<PurchaseDto> purchaseDtos = buildPurchaseDtoList();
        for (PurchaseDto purchaseDto : purchaseDtos) {
            purchaseProduct(purchaseDto);
        }
        // 멤버십 적용
        purchaseService.memberShip(inputView::getApplyMembership);
        // 영수증 출력
        outputView.print(purchaseService.getCalculateResult());
    }

    private void purchaseProduct(PurchaseDto purchaseDto) {
        // 구매 가능한지 확인

        // 프로모션 상품이 노출되어 있는지 확인
        boolean applicablePromotion = purchaseService.isApplicablePromotion(purchaseDto.name());
        int remainCount2 = purchaseDto.quantity();
        if (applicablePromotion) {
            // 노출되어 있으면 프로모션 상품 구매하고 남은 상품 개수 반환
            int remainCount = purchaseService.purchasePromotionProduct(purchaseDto);
            // 프로모션 추가로 적용 가능한지 확인하고 남은 상품 개수 반환
            remainCount2 = purchaseService.checkFreePromotion(new PurchaseDto(purchaseDto.name(), remainCount),
                    inputView::getApplyFreeInput);
            // 프로모션 적용안되어도 괜찮은지 확인
            if (remainCount2 > 0) {
                if (!purchaseService.checkCantApplyPromotion(new PurchaseDto(purchaseDto.name(), remainCount2),
                        inputView::getCantApplyPromotionInput)) {
                    return;
                }
            }
        }
        // 기타 일반 상품 구매 처리
        purchaseService.purchaseNormalProduct(new PurchaseDto(purchaseDto.name(), remainCount2));
    }

    private void showProductInfo() {
        outputView.print(Message.SHOW_STORED_PRODUCT);
        outputView.print(purchaseService.getStoredProductInfo());
    }

    private List<PurchaseDto> buildPurchaseDtoList() {
        inputView.printPurchaseGuide();
        return RetryHandler.retryUntilSuccess(inputView::getPurchaseInput);
    }
}
