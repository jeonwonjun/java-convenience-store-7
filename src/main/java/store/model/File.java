package store.model;

import java.util.List;

public enum File {
    PRODUCTS("./src/main/resources/products.md", ),
    PROMOTIONS("./src/main/resources/promotions.md", );

    private final String path;
    File(String path) {
        this.path = path;
    }

    public static List<File> getAllFile() {
        return List.of(File.values());
    }

    public String getPath() {
        return path;
    }
}
