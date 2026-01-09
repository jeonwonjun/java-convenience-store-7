package store.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.OrderRequest;
import store.util.ErrorMessage;

public class InputParser {
    private static final Pattern ITEM_PATTERN = Pattern.compile("\\[([가-힣a-zA-Z0-9]+)-([0-9]+)\\]");
    private static final int MIN_PURCHASE_AMOUNT = 1;

    public List<OrderRequest> parseOrder(String input) {
        validateEmpty(input);
        List<OrderRequest> orders = new ArrayList<>();

        String[] chunks = input.split(",");
        for (String chunk : chunks) {
            orders.add(parseSingleOrder(chunk.trim()));
        }
        return orders;
    }

    private OrderRequest parseSingleOrder(String chunk) {
        Matcher matcher = ITEM_PATTERN.matcher(chunk);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }

        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));

        validateQuantity(quantity);
        return new OrderRequest(name, quantity);
    }

    private void validateEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_BLANK.getMessage());
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_PURCHASE_AMOUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_QUANTITY_MIN.getMessage());
        }
    }
}
