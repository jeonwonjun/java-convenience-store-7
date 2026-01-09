package store.service;

import store.dto.OrderRequest;
import store.model.consumer.OrderResult;
import store.model.consumer.OrderStatus;
import store.model.product.Product;
import store.model.product.Products;
import store.model.promotion.Promotion;

public class OrderService {
    private final Products products;

    public OrderService(Products products) {
        this.products = products;
    }

    // 주문 하나를 분석하여 최종 결제 내역(OrderRequest)을 반환
    public OrderResult calculateOrder(OrderRequest request) {
        Product product = products.findByName(request.getProductName());
        int quantity = request.getQuantity();

        return calculatePromotion(product, quantity);
    }

    public OrderResult calculatePromotion(Product product, int quantity) {
        Promotion promotion = product.getPromotion();

        if (promotion == null) {
            return new OrderResult(product, quantity, 0, quantity, OrderStatus.NORMAL);
        }
        int buyAmount = promotion.getBuy();
        int getAmount = promotion.getGet();
        int unit = buyAmount + getAmount;
        int promoStock = product.getPromotionQuantity();

        // 1. 추가 증정 가능 여부 확인
        if ((quantity % unit == buyAmount) && promoStock >= quantity + getAmount) {
            return new OrderResult(product, quantity, getAmount, 0, OrderStatus.BONUS_READY);
        }

        // 2. 프로모션 재고 부족 할 때
        if (quantity > promoStock) {
            // 프로모션 혜택을 받는 최대 묶음 수량 계산
            int maxUnits = promoStock / unit;
            int processedByPromo = maxUnits * unit;
            int fullPriceQuantity = quantity - processedByPromo;

            return new OrderResult(product, quantity, 0, fullPriceQuantity, OrderStatus.STOCK_SHORTAGE);
        }

        int processedByPromo = (quantity / unit) * unit;
        int fullPriceQuantity = quantity - processedByPromo;

        return new OrderResult(product, quantity, 0, fullPriceQuantity, OrderStatus.NORMAL);
    }
}
