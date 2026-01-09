package store.model.consumer;

import java.util.ArrayList;
import java.util.List;
import store.model.promotion.Promotion;

public class Receipt {
    private final List<ReceiptItem> purchaseItems; // 구매 내역
    private final List<ReceiptItem> bonusItems; // 증정 내역

    private int totalAmount; // 총구매액
    private int totalQuantity; // 총구매수량
    private int promotionDiscount; // 행사할인
    private int membershipDiscount; // 멤버십할인
    private int finalPayment; // 내실돈

    public Receipt(List<OrderResult> results, boolean applyMembership) {
        this.purchaseItems = new ArrayList<>();
        this.bonusItems = new ArrayList<>();
        calculate(results, applyMembership);
    }

    private void calculate(List<OrderResult> results, boolean applyMembership) {
        int nonPromotionAmount = 0;

        for (OrderResult result : results) {
            // 1. 기본 정보 추출
            int itemTotalQuantity = result.getOrderedQuantity();
            int itemPrice = result.getProduct().getPrice();

            // 2. 총구매액 누적
            this.totalAmount += (itemTotalQuantity * itemPrice);
            this.totalQuantity += itemTotalQuantity;
            this.purchaseItems.add(new ReceiptItem(result.getProduct().getName(), itemTotalQuantity, itemPrice));

            // 3. 증정 내역 및 행사할인 계산
            int bonusQuantity = calculateBonusQuantity(result);
            if (bonusQuantity > 0) {
                this.bonusItems.add(new ReceiptItem(result.getProduct().getName(), bonusQuantity, bonusQuantity * itemPrice));
                this.promotionDiscount += (bonusQuantity * itemPrice);
            }

            // 4. 멤버십 할인 대상 금액(프로모션 미적용분) 계산
            nonPromotionAmount += calculateNonPromotionAmount(result) * itemPrice;
        }

        // 5. 멤버십 할인 적용
        if (applyMembership) {
            Membership membership = new Membership();
            this.membershipDiscount = membership.calculateDiscount(nonPromotionAmount);
        }

        this.finalPayment = this.totalAmount - this.promotionDiscount - this.membershipDiscount;
    }

    private int calculateBonusQuantity(OrderResult result) {
        if (result.getStatus() == OrderStatus.BONUS_READY) {
            return result.getBonusQuantity();
        }

        Promotion promotion = result.getProduct().getPromotion();
        if (promotion == null) return 0;

        int unit = promotion.getBuy() + promotion.getGet();

        int promoAppliedQty = result.getOrderedQuantity() - result.getFullPriceQuantity();
        return (promoAppliedQty / unit) * promotion.getGet();
    }

    private int calculateNonPromotionAmount(OrderResult result) {
        if (result.getStatus() == OrderStatus.STOCK_SHORTAGE || result.getStatus() == OrderStatus.NORMAL) {
            return result.getFullPriceQuantity();
        }

        return 0;
    }

    public List<ReceiptItem> getPurchaseItems() {
        return purchaseItems;
    }

    public List<ReceiptItem> getBonusItems() {
        return bonusItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalPayment() {
        return finalPayment;
    }
}
