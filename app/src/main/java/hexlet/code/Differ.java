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

public class Differ {

    public static String generate(String filePath1, String filePath2) {
        byte[] file1 = getFileAsByteArray(filePath1);
        byte[] file2 = getFileAsByteArray(filePath2);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map1 = getMapDependsOnJson(mapper, file1);
        Map<String, String> map2 = getMapDependsOnJson(mapper, file2);

        Map<String, String> target = createTargetMap(map1, map2);

        return getResultAsString(mapper, target);
    }

    private static Map<String, String> createTargetMap(Map<String, String> map1, Map<String, String> map2) {
        String noChangedValue = "  ";
        String updatedValueInJson1 = "- ";
        String updatedValueInJson2 = "+ ";

        Comparator<String> jsonKeysComparator = (o1Original, o2original) -> {
            String o1Cropped = new StringBuilder(o1Original).substring(2).toLowerCase();
            String o2Cropped = new StringBuilder(o2original).substring(2).toLowerCase();

            return o1Cropped.equals(o2Cropped)
                    ? o2original.toLowerCase().compareTo(o1Original.toLowerCase())
                    : o1Cropped.compareTo(o2Cropped);
        };

        Map<String, String> result = new TreeMap<>(jsonKeysComparator);

        for (Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            if (map2.containsKey(key)) {
                String valueInMap2 = String.valueOf(map2.get(key));

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

        for (Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            if (!result.containsKey(noChangedValue + key)) {
                result.put(updatedValueInJson2 + key, value);
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

    private static Map<String, String> getMapDependsOnJson(ObjectMapper mapper, byte[] arr) {
        try {
            return mapper.readValue(arr, Map.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResultAsString(ObjectMapper mapper, Map<String, String> target) {
        try {
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(target)
                    .replaceAll("\\\"", "")
                    .replaceAll(" :", ":");

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String path1 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\main\\resources\\file1.json";
        String path2 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\main\\resources\\file2.json";
        String res = generate(path1, path2);
        System.out.println(res);
    }
}
