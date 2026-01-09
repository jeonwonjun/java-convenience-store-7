package store.util;

import java.util.List;

public class FormatManager {

    public static List<String> parseInput(String input, String delimeter) {
        return List.of(input.split(delimeter, -1));
    }
}
