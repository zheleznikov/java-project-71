package hexlet.code;

import hexlet.code.status.ValueStatus;

import java.util.Map;

import static hexlet.code.core.Formatter.makeOutputDependsOnFormat;
import static hexlet.code.core.SourceFileHandler.createComparedMap;
import static hexlet.code.core.SourceFileHandler.handleByteArrAsMap;
import static hexlet.code.core.Utils.getFileAsByteArray;
import static hexlet.code.formatters.StylishFormatter.makeOutputStylish;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class Differ {


    public static String generate(String filePath1, String filePath2, String format) {
        return makeOutputDependsOnFormat(format, prepareMap(filePath1, filePath2));
    }

    public static String generate(String filePath1, String filePath2) {
        return makeOutputStylish(prepareMap(filePath1, filePath2));
    }

    private static Map<String, ValueStatus> prepareMap(String filePath1, String filePath2) {
        byte[] file1 = getFileAsByteArray(filePath1);
        byte[] file2 = getFileAsByteArray(filePath2);

        Map<String, Object> map1 = handleByteArrAsMap(file1, getExtension(filePath1));
        Map<String, Object> map2 = handleByteArrAsMap(file2, getExtension(filePath2));

        return createComparedMap(map1, map2);
    }

}
