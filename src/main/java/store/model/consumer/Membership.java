package store.model.consumer;

public class Membership {
    private static final double DISCOUNT_RATE = 0.3;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;

    public int calculateDiscount(int nonPromotionAmount) {
        int discount = (int) (nonPromotionAmount * DISCOUNT_RATE);

        if (discount > MAX_DISCOUNT_AMOUNT) {
            return MAX_DISCOUNT_AMOUNT;
        }

        return discount;
    }
}
