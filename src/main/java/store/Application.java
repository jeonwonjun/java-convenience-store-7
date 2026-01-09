package store;

import java.util.List;
import store.controller.StoreController;
import store.model.File;
import store.model.product.Product;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        storeController.run();
    }
}
