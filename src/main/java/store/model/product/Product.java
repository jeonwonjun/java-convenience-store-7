package store.model.product;

import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.util.ErrorMessage;
import store.util.FormatManager;

public class Product {
    private static final int NAME_IDX = 0;
    private static final int PRICE_IDX = 1;
    private static final int QUANTITY_IDX = 2;
    private static final int PROMOTION_IDX = 3;

    private String name;
    private int price = 0;
    private int totalQuantity = 0;
    private int promotionQuantity = 0;
    private List<Promotion> promotions = List.of();

    public static Product from(String input, Promotions promotions) {
        List<String> productData = FormatManager.parseInput(input, ",");
        validateInputFormat(productData.get(PRICE_IDX));
        validateInputFormat(productData.get(QUANTITY_IDX));
        return new Product(productData.get(NAME_IDX), Integer.parseInt(productData.get(PRICE_IDX)),
                Integer.parseInt(productData.get(QUANTITY_IDX)),
                promotions.findByName(productData.get(PROMOTION_IDX)));
    }

    private Product(String name, int price, int quantity, List<Promotion> promotions) {
        this.name = name;
        this.price = price;
        QuantityDto quantityDto = updateQuantityByPromotion(promotions, quantity);
        this.totalQuantity = quantityDto.totalQuantity();
        this.promotionQuantity = quantityDto.promotionQuantity();
        if (isPromotions(promotions)) {
            this.promotions = promotions;
        }
    }

    private static void validateInputFormat(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }

    private QuantityDto updateQuantityByPromotion(List<Promotion> promotions, int quantity) {
        if (isPromotions(promotions)) {
            return new QuantityDto(totalQuantity + quantity, quantity);
        }

        return new QuantityDto(totalQuantity + quantity, 0);
    }

    private static boolean isPromotions(List<Promotion> promotions) {
        return !promotions.isEmpty();
    }

    public boolean isEnoughQuantity(int count) {
        return totalQuantity >= count;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
