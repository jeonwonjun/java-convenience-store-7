package store.model.product;

import java.util.List;
import store.util.ErrorMessage;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public Product findByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_EXITS.getMessage()));
    }

    public List<Product> findAll() {
        return List.copyOf(products);
    }
}
