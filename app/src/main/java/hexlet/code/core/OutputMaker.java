package hexlet.code.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.status.ValueStatus;

import java.util.Map;

public class OutputMaker {

    private static final String LINE_SEPARATOR = "\r\n";

    public static String makeOutputJson(Map<String, ValueStatus> sourceMap) {
        try {
            return new ObjectMapper().writeValueAsString(sourceMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String makeOutputStylish(Map<String, ValueStatus> sourceMap) {
        String unchangedPrefix = "    ";
        String addedPrefix = "  + ";
        String deletedPrefix = "  - ";

        StringBuilder builder = new StringBuilder()
                .append("{")
                .append(LINE_SEPARATOR);

        sourceMap.forEach((key, value) -> {
            Object prevValue = value.getValue1();
            Object updatedValue = value.getValue2();

            StringBuilder line = switch (value.getStatus()) {
                case changed -> createLine(deletedPrefix, key, prevValue)
                        .append(createLine(addedPrefix, key, updatedValue));

                case unchanged -> createLine(unchangedPrefix, key, prevValue);

                case deleted -> createLine(deletedPrefix, key, prevValue);

                case added -> createLine(addedPrefix, key, updatedValue);
            };

            builder.append(line);
        });

        return builder
                .append("}")
                .toString();
    }

    private static StringBuilder createLine(String prefix, String key, Object value) {
        return new StringBuilder()
                .append(prefix).append(key)
                .append(": ")
                .append(value)
                .append(LINE_SEPARATOR);
    }

    public static String makeOutputPlain(Map<String, ValueStatus> target) {
        return "";
    }

}
