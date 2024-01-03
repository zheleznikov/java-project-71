import hexlet.code.Differ;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    private final String pathToExpectedJson = "src/test/resources/testData/expected.json";

    private final String pathToJson1 = "src/test/resources/testData/file1.json";
    private final String pathToJson2 = "src/test/resources/testData/file2.json";

    @Test
    public void testJsonShouldCompareCorrectly() throws IOException {
        String expected = FileUtils.readFileToString(new File(pathToExpectedJson), StandardCharsets.UTF_8);
        String actual = Differ.generate(pathToJson1, pathToJson2);
        assertEquals(actual, expected, "json should match");
    }

}
