package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class Differ {

    public static String generate(String filePath1, String filePath2, String format) {
            byte[] file1 = getFileAsByteArray(filePath1);
            byte[] file2 = getFileAsByteArray(filePath2);

            Map<String, Object> map1 = Parser.createMap(file1, getExtension(filePath1));
            Map<String, Object> map2 = Parser.createMap(file2, getExtension(filePath2));

            Map<String, Object> target = createTargetMap(map1, map2);

            return switch (format) {
                case "json" -> createJson(target);
                case "plain" -> "";
                default -> crateStylish(target);
            };


    }

    private static Map<String, Object> createTargetMap(Map<String, Object> map1, Map<String, Object> map2) {
        String noChangedValue = "  ";
        String updatedValueInJson1 = "- ";
        String updatedValueInJson2 = "+ ";

        Comparator<String> jsonKeyComparator = (original1, original2) -> {
            String cropped1 = new StringBuilder(original1).substring(2).toLowerCase();
            String cropped2 = new StringBuilder(original2).substring(2).toLowerCase();

            return cropped1.equals(cropped2)
                    ? original2.toLowerCase().compareTo(original1.toLowerCase())
                    : cropped1.compareTo(cropped2);
        };

        Map<String, Object> result = new TreeMap<>(jsonKeyComparator);

        for (Entry<String, Object> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (map2.containsKey(key)) {
                Object valueInMap2 = map2.get(key);

                if (value.equals(valueInMap2)) {
                    result.put(noChangedValue + key, value);

                } else {
                    result.put(updatedValueInJson1 + key, value);
                    result.put(updatedValueInJson2 + key, valueInMap2);
                }

            } else {
                result.put(updatedValueInJson1 + key, value);
            }
        }

        for (Entry<String, Object> entry : map2.entrySet()) {
            String key = entry.getKey();

            if (!result.containsKey(noChangedValue + key)) {
                result.put(updatedValueInJson2 + key, entry.getValue());
            }
        }

        return result;
    }

    private static byte[] getFileAsByteArray(String filePath) {
        try {
            return FileUtils.readFileToByteArray(new File(filePath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    private static String createJson(Map<String, Object> target) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String crateStylish(Map<String, Object> target) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(target)
                    .replaceAll("\\\"", "")
                    .replaceAll(" :", ":")
                    .replaceAll(",", "");

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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


        String res = generate(path1Yml, path2Yml, "stylish");
        System.out.println(res);
    }
}
