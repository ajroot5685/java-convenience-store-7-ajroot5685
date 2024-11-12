package store.builder;

import java.util.List;
import store.entity.Product;

public class ProductOutputBuilder {

    public void build(StringBuilder sb, List<Product> products) {
        products.forEach(product -> appendByCase(sb, product));
    }

    private void appendByCase(StringBuilder sb, Product product) {
        if (product.getPromotion() != null) {
            appendPromotionProduct(sb, product);
        }
        appendNormalProduct(sb, product);
    }

    private void appendPromotionProduct(StringBuilder sb, Product product) {
        sb.append(getOutputName(product.getName()));
        sb.append(getOutputPrice(product.getPrice()));
        sb.append(getOutputQuantity(product.getPromotionQuantity()));
        sb.append(getOutputPromotion(product.getPromotion()));
        sb.append("\n");
    }

    private void appendNormalProduct(StringBuilder sb, Product product) {
        sb.append(getOutputName(product.getName()));
        sb.append(getOutputPrice(product.getPrice()));
        sb.append(getOutputQuantity(product.getQuantity()));
        sb.append("\n");
    }

    private String getOutputName(String name) {
        return "- " + name;
    }

    private String getOutputPrice(Integer price) {
        return String.format(" %,d", price) + "원";
    }

    private String getOutputQuantity(Integer quantity) {
        if (quantity == 0) {
            return " 재고 없음";
        }
        return " " + quantity + "개";
    }

    private String getOutputPromotion(String promotion) {
        return " " + promotion;
    }
}
