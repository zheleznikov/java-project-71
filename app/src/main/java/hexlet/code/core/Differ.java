package hexlet.code.core;

import hexlet.code.status.ValueStatus;

import java.util.Map;

import static hexlet.code.core.Formatter.makeOutputDependsOnFormat;
import static hexlet.code.core.SourceFileHandler.createComparedMap;
import static hexlet.code.core.SourceFileHandler.handleByteArrAsMap;
import static hexlet.code.core.Utils.getFileAsByteArray;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class Differ {


    public static String generate(String filePath1, String filePath2, String format) {
        byte[] file1 = getFileAsByteArray(filePath1);
        byte[] file2 = getFileAsByteArray(filePath2);

        Map<String, Object> map1 = handleByteArrAsMap(file1, getExtension(filePath1));
        Map<String, Object> map2 = handleByteArrAsMap(file2, getExtension(filePath2));

        Map<String, ValueStatus> comparedMap = createComparedMap(map1, map2);

        return makeOutputDependsOnFormat(format, comparedMap);

    }

}
