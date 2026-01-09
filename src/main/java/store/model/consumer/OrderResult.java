package store.model.consumer;

import store.model.product.Product;

public class OrderResult {

    private final Product product;
    private int orderedQuantity; // 사용자가 처음 입력한 수량
    private int bonusQuantity; // 추가로 받을 수 있는 증정 수량
    private int fullPriceQuantity; // 프로모션 혜택을 못 받고 정가로 내야 하는 수량
    private OrderStatus status; // 현재 주문의 상태

    public OrderResult(Product product, int orderedQuantity, int bonusQuantity, int fullPriceQuantity,
                       OrderStatus status) {
        this.product = product;
        this.orderedQuantity = orderedQuantity;
        this.bonusQuantity = bonusQuantity;
        this.fullPriceQuantity = fullPriceQuantity;
        this.status = status;
    }

    public boolean isBonusReady() {
        return status == OrderStatus.BONUS_READY;
    }

    public boolean isStockShortage() {
        return status == OrderStatus.STOCK_SHORTAGE;
    }

    // 증정품 수령에 Y라고 대답했을 때
    public void acceptBonus() {
        this.orderedQuantity += this.bonusQuantity;
        this.bonusQuantity = 0;
        this.fullPriceQuantity = 0;
        this.status = OrderStatus.NORMAL;
    }

    // 증정품 수령에 N라고 대답했을 때
    public void ignoreBonus() {
        this.bonusQuantity = 0;
        this.status = OrderStatus.NORMAL;
    }

    // 정가 결제 안내에 Y라고 답한 경우
    public void acceptFullPricePay() {
        this.status = OrderStatus.NORMAL;
    }

    // 정가 결제 안내에 N라고 답한 경우
    public void rejectFullPricePay() {
        this.orderedQuantity -= this.fullPriceQuantity;
        this.fullPriceQuantity = 0;
        this.status = OrderStatus.NORMAL;
    }

    public Product getProduct() {
        return product;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getFullPriceQuantity() {
        return fullPriceQuantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

}
