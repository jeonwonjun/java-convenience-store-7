package store.model;

import java.util.Arrays;
import store.util.ErrorMessage;

public enum Select {
    YES("Y"),
    NO("N");

    private final String description;

    Select(String description) {
        this.description = description;
    }

    public static Select findSelect(String description) {
        return Arrays.stream(Select.values())
                .filter(o -> o.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage()));
    }
}
