package store.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.model.product.Product;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;

public class ProductRepository {
    private final Map<String, Product> products = new LinkedHashMap<>();

    public void addProductFromLine(String name, int price, int quantity, String promotionName, Promotions promotions) {
        Promotion promotion = promotions.findByName(promotionName);

        if (products.containsKey(name)) {
            updateExistingProduct(name, quantity, promotionName);
            return;
        }
        createNewProduct(name, price, quantity, promotion);
    }

    private void createNewProduct(String name, int price, int quantity, Promotion promotion) {
        int promotionQuantity = 0;
        int normalQuantity = 0;

        if (promotion != null) {
            promotionQuantity = quantity;
        }

        if (promotion == null) {
            normalQuantity = quantity;
        }

        products.put(name, new Product(name, price, promotionQuantity, normalQuantity, promotion));
    }

    private void updateExistingProduct(String name, int quantity, String promotionName) {
        Product existingProduct = products.get(name);
        if (promotionName.equals("null")) {
            existingProduct.addNormalQuantity(quantity);
            return;
        }
        existingProduct.addPromotionQuantity(quantity);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
