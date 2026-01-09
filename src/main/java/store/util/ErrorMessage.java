package store.util;

public enum ErrorMessage {
    INVALID_FILE_READ("파일을 읽어올 수 없습니다."),
    INVALID_PRODUCT_QUANTITY_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_PRODUCT_EXITS("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_PRODUCT_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT_FORMAT("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_QUANTITY_MIN("수량은 1개 이상이어야 합니다."),
    INPUT_BLANK("입력값이 비어 있습니다.");


    private final String message;
    private static final String ERROR_FORMAT = "[ERROR]";

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_FORMAT + " " + message;
    }
}
