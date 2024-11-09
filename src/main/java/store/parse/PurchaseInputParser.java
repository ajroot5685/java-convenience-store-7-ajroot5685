package store.parse;

import java.util.ArrayList;
import java.util.List;
import store.dto.PurchaseDto;

public class PurchaseInputParser {

    private final String SEPARATOR = ",";
    private final String HYPHEN = "-";
    private final String EMPTY = "";
    private final String SQUARE_BRACKET_REGEX = "[\\[\\]]";

    public List<PurchaseDto> toPurchaseDtoList(String purchaseInput) {
        String[] products = purchaseInput.replaceAll(SQUARE_BRACKET_REGEX, EMPTY).split(SEPARATOR);
        List<PurchaseDto> purchaseDtos = new ArrayList<>();
        for (String product : products) {
            add(purchaseDtos, product);
        }
        return purchaseDtos;
    }

    private void add(List<PurchaseDto> purchaseDtos, String productInput) {
        String[] nameAndQuantity = productInput.split(HYPHEN);
        String name = nameAndQuantity[0];
        Integer quantity = parseQuantity(nameAndQuantity[1]);
        purchaseDtos.add(new PurchaseDto(name, quantity));
    }

    private Integer parseQuantity(String quantityString) {
        return Integer.parseInt(quantityString);
    }
}
