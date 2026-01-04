package store.model.promotion;

import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;

public class Promotions {

    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion findByName(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(promotionName)) {
                return promotion;
            }
        }

        return null;
    }
}
