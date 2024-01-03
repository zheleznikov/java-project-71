package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws IOException {

        byte[] file1AsByteArray = FileUtils.readFileToByteArray(new File(filePath1));
        byte[] file2AsByteArray = FileUtils.readFileToByteArray(new File(filePath2));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map1 = mapper.readValue(file1AsByteArray, Map.class);
        Map<String, String> map2 = mapper.readValue(file2AsByteArray, Map.class);


        Map<String, String> result = new TreeMap<>((o1Original, o2original) -> {
            String o1Cropped = new StringBuilder(o1Original).substring(2).toLowerCase();
            String o2Cropped = new StringBuilder(o2original).substring(2).toLowerCase();

            return o1Cropped.equals(o2Cropped)
                    ? o2original.toLowerCase().compareTo(o1Original.toLowerCase())
                    : o1Cropped.compareTo(o2Cropped);
        });

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            if (map2.containsKey(key)) {
                String valueInMap2 = String.valueOf(map2.get(key));

                if (value.equals(valueInMap2)) {
                    result.put("  " + key, value);

                } else {
                    result.put("- " + key, value);
                    result.put("+ " + key, valueInMap2);
                }

            } else {
                result.put("- " + key, value);
            }
        }

        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            if (!result.containsKey("  " + key)) {
                result.put("+ " + key, value);
            }
        }

        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(result)
                .replaceAll("\\\"", "")
                .replaceAll(" :", ":");

    }
    public static void main(String[] args) throws IOException {
        String path1 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\main\\resources\\file1.json";
        String path2 = "C:\\Users\\fraud\\Documents\\git\\java-project-71\\app\\src\\main\\resources\\file2.json";
        String res = generate(path1, path2);
        System.out.println(res);
    }
}
