package challenges.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileLineReader {

    private FileLineReader() { // Intentionally hiding the constructor
    }

    public static List<String> getFileLines(String fileName) {
        try {
            URL fileUrl = FileLineReader.class.getResource("/" + fileName);
            Path filePath = Paths.get(Objects.requireNonNull(fileUrl).toURI());
            return Files.readAllLines(filePath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("There was an error reading the file: " + e);
        }
    }

}
