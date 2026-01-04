package store.view;

import java.util.List;
import store.model.product.Product;

public class OutputView {
    public void printProducts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        for (Product product : products) {
            printPromotionStock(product);
            printNormalStock(product);
        }
    }

    private void printPromotionStock(Product product) {
        if (product.getPromotion() != null) {
            String quantityLabel = formatQuantity(product.getPromotionQuantity());
            System.out.printf("- %s %,d원 %s %s\n", product.getName(), product.getPrice(), quantityLabel, product.getPromotion().getName());
        }
    }

    private void printNormalStock(Product product) {
        String quantityLabel = formatQuantity(product.getNormalQuantity());
        System.out.printf("- %s %,d원 %s\n", product.getName(), product.getPrice(), quantityLabel);
    }

    private String formatQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }
}
