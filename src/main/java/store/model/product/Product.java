package store.model.product;

import java.time.LocalDate;
import java.util.List;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.util.ErrorMessage;
import store.util.FormatManager;

public class Product {
    private static final int NAME_IDX = 0;
    private static final int PRICE_IDX = 1;
    private static final int QUANTITY_IDX = 2;
    private static final int PROMOTION_IDX = 3;

    private final String name;
    private final int price;
    private int promotionQuantity;
    private int normalQuantity;
    private final Promotion promotion;

    public Product(String name, int price, int promotionQuantity, int normalQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.normalQuantity = normalQuantity;
        this.promotion = promotion;
    }

    public boolean isPromotionActive(LocalDate date) {
        return promotion != null && promotion.isAvailable(date);
    }

    public void reduceStock(int purchaseAmount) {
        int totalAmount = promotionQuantity + normalQuantity;
        if (!hasStock(purchaseAmount)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_QUANTITY.getMessage());
        }

        int fromPromotion = Math.min(promotionQuantity, totalAmount);
        promotionQuantity -= fromPromotion;

        int remaining = totalAmount - fromPromotion;
        normalQuantity -= remaining;
    }

    public boolean hasStock(int amount) {
        int totalQuantity = promotionQuantity + normalQuantity;
        return totalQuantity >= amount;
    }

    public void addNormalQuantity(int quantity) {
        this.normalQuantity += quantity;
    }

    public void addPromotionQuantity(int quantity) {
        this.promotionQuantity += quantity;
    }

    public String getStatus() {
        String promotionName = "없음";
        if (this.promotion != null) {
            promotionName = this.promotion.getName();
        }

        return String.format("- %s, %d원, 프로모션재고: %d(%s), 일반재고: %d",
                name, price, promotionQuantity, promotionName, normalQuantity);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
