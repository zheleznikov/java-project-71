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
        try {
            byte[] file1 = getFileAsByteArray(filePath1);
            byte[] file2 = getFileAsByteArray(filePath2);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map1 = getMapDependsOnJson(mapper, file1);
            Map<String, Object> map2 = getMapDependsOnJson(mapper, file2);

            Map<String, Object> target = createTargetMap(map1, map2);
            return mapper.writeValueAsString(target);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

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

    private static Map<String, Object> getMapDependsOnJson(ObjectMapper mapper, byte[] arr) {
        try {
            return mapper.readValue(arr, Map.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        String path1 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file1.json";
        String path2 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\test\\resources\\"
                + "testData\\file2.json";
        String res = generate(path1, path2);
        System.out.println(res);
    }
}
