package hexlet.code.core;

import hexlet.code.status.ValueStatus;

import java.util.Map;

import static hexlet.code.core.SourceFileHandler.createJoinedMap;
import static hexlet.code.core.Utils.getFileAsByteArray;
import static hexlet.code.core.SourceFileHandler.handleByteArrAsMap;
import static hexlet.code.core.OutputMaker.makeOutputJson;
import static hexlet.code.core.OutputMaker.makeOutputPlain;
import static hexlet.code.core.OutputMaker.makeOutputStylish;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class Differ {


    public static String generate(String filePath1, String filePath2, String format) {
        byte[] file1 = getFileAsByteArray(filePath1);
        byte[] file2 = getFileAsByteArray(filePath2);

        Map<String, Object> map1 = handleByteArrAsMap(file1, getExtension(filePath1));
        Map<String, Object> map2 = handleByteArrAsMap(file2, getExtension(filePath2));

        Map<String, ValueStatus> joinedMap = createJoinedMap(map1, map2);

        return switch (format) {
            case "json" -> makeOutputJson(joinedMap);
            case "plain" -> makeOutputPlain(joinedMap);
            default -> makeOutputStylish(joinedMap);
        };

    }


    public static void main(String[] args) {
        String path1Json = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file1.json";
        String path2Json = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file2.json";

        String path1Yml = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file1.yml";
        String path2Yml = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file2.yml";


        String res = generate(path1Json, path2Json, "stylish");
        System.out.println(res);
    }
}
