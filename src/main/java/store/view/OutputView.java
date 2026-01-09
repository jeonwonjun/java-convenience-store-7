package store.view;

import java.util.List;
import store.model.consumer.Receipt;
import store.model.consumer.ReceiptItem;
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

    public void printReceipt(Receipt receipt) {
        System.out.println("\n==============W 편의점================");
        System.out.printf("%-15s\t%-5s\t%-10s%n", "상품명", "수량", "금액");

        // 구매 내역 출력
        for (ReceiptItem item : receipt.getPurchaseItems()) {
            System.out.printf("%-15s\t%-5d\t%,-10d%n",
                    item.getName(), item.getQuantity(), item.getQuantity());
        }

        // 증정 내역 출력
        if (!receipt.getBonusItems().isEmpty()) {
            System.out.println("=============증	정===============");
            for (ReceiptItem bonus : receipt.getBonusItems()) {
                System.out.printf("%-15s\t%-5d%n", bonus.getName(), bonus.getQuantity());
            }
        }

        System.out.println("====================================");

        // 최종 금액 정보 출력
        System.out.printf("%-15s\t%-5d\t%,-10d%n", "총구매액",
                receipt.getTotalQuantity(), receipt.getTotalAmount());
        System.out.printf("%-15s\t\t-%,-10d%n", "행사할인", receipt.getPromotionDiscount());
        System.out.printf("%-15s\t\t-%,-10d%n", "멤버십할인", receipt.getMembershipDiscount());
        System.out.printf("%-15s\t\t %,-10d%n", "내실돈", receipt.getFinalPayment());
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
