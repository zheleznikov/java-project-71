import hexlet.code.Differ;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DifferTest {

    private static final String PATH_TO_EXPECTED_JSON = "src/test/resources/testData/expected.json";
    private static final String PATH_TO_EXPECTED_STYLISH = "src/test/resources/testData/expected-stylish.txt";
    private static final String PATH_TO_EXPECTED_PLAIN = "src/test/resources/testData/expected.json";

    private static final String PATH_TO_JSON_1 = "src/test/resources/testData/file1.json";
    private static final String PATH_TO_JSON_2 = "src/test/resources/testData/file2.json";


    private static final String PATH_TO_YML_1 = "src/test/resources/testData/file1.json";
    private static final String PATH_TO_YML_2 = "src/test/resources/testData/file2.json";

    @ParameterizedTest
    @MethodSource("provideTestCondition")
    public void testFilesShouldCompareCorrectly(String pathToFile1, String pathToFile2,
                                                String pathToResult, String format) throws IOException {
        String expected = FileUtils.readFileToString(new File(pathToResult), StandardCharsets.UTF_8);
        String actual = Differ.generate(pathToFile1, pathToFile2, format);

        assertEquals(expected, actual, "result should match");
    }

    private static Stream<Arguments> provideTestCondition() {
        return Stream.of(
                Arguments.of(PATH_TO_JSON_1, PATH_TO_JSON_2, PATH_TO_EXPECTED_STYLISH, "stylish"),
//                Arguments.of(pathToJson1, pathToJson2, pathToExpectedPlain, "plain"),
                Arguments.of(PATH_TO_JSON_1, PATH_TO_JSON_2, PATH_TO_EXPECTED_JSON, "json"),
//
                Arguments.of(PATH_TO_YML_1, PATH_TO_YML_2, PATH_TO_EXPECTED_STYLISH, "stylish"),
////                Arguments.of(pathToYml1, pathToYml2, pathToExpectedPlain, "plain"),
                Arguments.of(PATH_TO_YML_1, PATH_TO_YML_2, PATH_TO_EXPECTED_JSON, "json")

        );
    }
}
