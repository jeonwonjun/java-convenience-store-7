package store.model.consumer;

public enum OrderStatus {
    NORMAL, // 프로모션 없음 또는 완벽 적용
    BONUS_READY, // 증정품을 더 가져올 수 있는 상태
    STOCK_SHORTAGE, // 프로모션 재고가 모자라 정가 결제 발생
    COMPLETED; // 처리가 완료된 상태
}
