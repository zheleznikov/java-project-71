package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {

    private static final String JSON = "json";
    private static final String YAML = "yaml";
    private static final String YML = "yml";

    public static Map<String, Object> createMap(byte[] arr, String extension) {

        try {
            return switch (extension) {
                case JSON -> new ObjectMapper().readValue(arr, Map.class);
                case YAML, YML -> new YAMLMapper().readValue(arr, Map.class);
                default -> throw new RuntimeException(extension.toUpperCase() + "is not supported");
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
