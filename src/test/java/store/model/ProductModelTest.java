package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.entity.Product;

class ProductModelTest {

    private final ProductModel productModel = new ProductModel();

    @Test
    void Product_리스트로_정상적으로_초기화된다() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("제품1", 1000, 5, "탄산2+1"));
        products.add(new Product("제품2", 3000, 6, "MD추천상품"));

        // when
        productModel.init(products);

        // then
        List<Product> storedProducts = productModel.getProducts();
        assertThat(storedProducts.size()).isEqualTo(2);
    }

    @Test
    void 방어적_읽기로_인해_내부_데이터를_악의적으로_조작할_수_없다() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("제품1", 1000, 5, "탄산2+1"));
        productModel.init(products);

        // when
        List<Product> defensiveData = productModel.getProducts();
        defensiveData.add(new Product("싼상품", 100, 999, "공짜 프로모션"));

        // then
        List<Product> newDefensiveData = productModel.getProducts();
        assertThat(newDefensiveData.size()).isEqualTo(1);
    }
}