package store.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.model.product.Product;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;
import store.repository.ProductRepository;

public class FileScanner {
    private static final String DELIMITER = ",";

    public List<Product> loadProducts(String path, Promotions promotions) {
        List<String> lines = readFile(path);
        ProductRepository repository = new ProductRepository();

        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            String promotionName = parts[3];

            repository.addProductFromLine(name, price, quantity, promotionName, promotions);
        }

        return repository.findAll();
    }

    public List<Promotion> loadPromotions(String path) {
        List<String> lines = readFile(path);
        return lines.stream()
                .map(this::parseToPromotion)
                .collect(Collectors.toList());
    }

    private Promotion parseToPromotion(String line) {
        String[] parts = line.split(DELIMITER);
        return new Promotion(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                LocalDate.parse(parts[3]),
                LocalDate.parse(parts[4])
        );
    }

    private static List<String> readFile(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            List<String> fileBody = new ArrayList<>();
            scanner.next();
            while (scanner.hasNext()) {
                fileBody.add(scanner.next());
            }
            return fileBody;
        } catch (IOException e) {
            throw new IllegalStateException(ErrorMessage.INVALID_FILE_READ.getMessage());
        }
    }
}
