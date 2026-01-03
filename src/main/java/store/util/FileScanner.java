package store.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileScanner {
    private FileScanner() {
    }

    public static List<String> readFile(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            List<String> fileBody = new ArrayList<>();
            while (scanner.hasNext()) {
                fileBody.add(scanner.next());
            }
            return fileBody;
        } catch (IOException e) {
            throw new IllegalStateException(ErrorMessage.INVALID_FILE_READ.getMessage());
        }
    }
}
