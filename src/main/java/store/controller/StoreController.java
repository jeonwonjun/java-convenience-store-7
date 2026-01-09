package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRequest;
import store.model.File;
import store.model.consumer.OrderResult;
import store.model.consumer.OrderStatus;
import store.model.consumer.Receipt;
import store.model.product.Product;
import store.model.product.Products;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.repository.ProductRepository;
import store.service.OrderService;
import store.util.FileScanner;
import store.view.InputHandler;
import store.view.InputParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputParser inputParser;
    private Products products;
    private Promotions promotions;
    private OrderService orderService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.inputParser = new InputParser();
    }

    public void run() {
        setup();
        do {
            processPurchase();
        } while (confirmContinue());
    }

    private void setup() {
        FileScanner fileScanner = new FileScanner();

        List<Promotion> promotionList = fileScanner.loadPromotions(File.PROMOTIONS.getPath());
        this.promotions = new Promotions(promotionList);

        List<Product> productList = fileScanner.loadProducts(File.PRODUCTS.getPath(), promotions);
        this.products = new Products(productList);
        this.products = new Products(products.findAll());
        this.orderService = new OrderService(products);
    }

    private void processPurchase() {
        InputHandler.retry(() -> {
            outputView.printProducts(products.findAll());
            List<OrderRequest> requests = inputParser.parseOrder(inputView.readOrderItems());

            List<OrderResult> results = handleOrders(requests);
            completeTransaction(results);
            return null;
        });
    }

    private List<OrderResult> handleOrders(List<OrderRequest> requests) {
        List<OrderResult> results = new ArrayList<>();
        for (OrderRequest request : requests) {
            OrderResult result = orderService.calculateOrder(request);
            interactWithUser(result);
            results.add(result);
        }
        return results;
    }

    private void interactWithUser(OrderResult result) {
        if (result.isBonusReady()) {
            handleBonusReady(result);
        }
        if (result.isStockShortage()) {
            handleStockShortage(result);
        }
    }

    private void handleBonusReady(OrderResult result) {
        String answer = InputHandler.retry(() ->
                inputView.readPromotionBenefitAcceptance(result.getProduct().getName(), result.getBonusQuantity())
        );
        if (answer.equalsIgnoreCase("Y")) {
            result.acceptBonus();
            return;
        }
        result.ignoreBonus();
    }

    private void handleStockShortage(OrderResult result) {
        String answer = InputHandler.retry(() ->
                inputView.readFullPriceAcceptance(result.getProduct().getName(), result.getFullPriceQuantity())
        );
        if (answer.equalsIgnoreCase("N")) {
            result.rejectFullPricePay();
            return;
        }
        result.acceptFullPricePay();
    }

    private void completeTransaction(List<OrderResult> results) {
        boolean useMembership = askMembership();

        Receipt receipt = new Receipt(results, useMembership);

        applyInventory(results);

        outputView.printReceipt(receipt);
    }

    private boolean askMembership() {
        String answer = InputHandler.retry(inputView::readMembershipAcceptance);
        return answer.equalsIgnoreCase("Y");
    }

    private void applyInventory(List<OrderResult> results) {
        for (OrderResult result : results) {
            result.getProduct().reduceStock(result.getOrderedQuantity());
        }
    }

    private boolean confirmContinue() {
        String answer = InputHandler.retry(inputView::readContinueOrder);
        return answer.equalsIgnoreCase("Y");
    }
}
