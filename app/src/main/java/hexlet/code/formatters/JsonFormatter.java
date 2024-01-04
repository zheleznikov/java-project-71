package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.status.ValueStatus;

import java.util.Map;

public class JsonFormatter {

    public static String makeOutputJson(Map<String, ValueStatus> sourceMap) {
        try {
            return new ObjectMapper().writeValueAsString(sourceMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
