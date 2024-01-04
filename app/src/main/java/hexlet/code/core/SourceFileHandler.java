package hexlet.code.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.status.ValueStatus;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static hexlet.code.status.AcceptableStatusValue.added;
import static hexlet.code.status.AcceptableStatusValue.changed;
import static hexlet.code.status.AcceptableStatusValue.deleted;
import static hexlet.code.status.AcceptableStatusValue.unchanged;

public class SourceFileHandler {

    private static final String JSON = "json";
    private static final String YAML = "yaml";
    private static final String YML = "yml";

    public static Map<String, Object> handleByteArrAsMap(byte[] arr, String extension) {

        try {
            return switch (extension) {
                case JSON -> new ObjectMapper().readValue(arr, Map.class);
                case YAML, YML -> new YAMLMapper().readValue(arr, Map.class);
                default -> throw new RuntimeException(extension.toUpperCase() + " is not supported");
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, ValueStatus> createJoinedMap(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, ValueStatus> targetMap = new TreeMap<>(String::compareTo);

        map1.forEach((key1, value1) -> {

            if (map2.containsKey(key1)) {
                Object valueInMap2 = map2.get(key1);

                if (value1 != null && value1.equals(valueInMap2)) {
                    targetMap.put(key1, new ValueStatus(unchanged, value1, null));

                } else {
                    targetMap.put(key1, new ValueStatus(changed, value1, valueInMap2));
                }

            } else {
                targetMap.put(key1, new ValueStatus(deleted, value1, null));
            }
        });

        map2.forEach((key2, value2) -> {
            if (!targetMap.containsKey(key2)) {
                targetMap.put(key2, new ValueStatus(added, null, value2));
            }
        });

        return targetMap;
    }
}
