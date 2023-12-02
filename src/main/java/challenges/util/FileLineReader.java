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

    public List<String> getFileLines(String fileName) {
        try {
            URL fileUrl = getClass().getResource("/" + fileName);
            Path filePath = Paths.get(Objects.requireNonNull(fileUrl).toURI());
            return Files.readAllLines(filePath);
//            System.out.println("The amount of lines is: " + fileLines.size());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
