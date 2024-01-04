package hexlet.code.formatters;

import hexlet.code.status.ValueStatus;

import java.util.List;
import java.util.Map;

import static hexlet.code.formatters.Separator.LINE_SEPARATOR;

public class PlainFormatter {

    private static final String COMPLEX_VALUE = "[complex value]";


    public static String makeOutputPlain(Map<String, ValueStatus> sourceMap) {
        StringBuilder builder = new StringBuilder();

        sourceMap.forEach((key, value) -> {
            String prevValue = handleValues(value.getValue1());
            String updatedValue = handleValues(value.getValue2());

            StringBuilder line = switch (value.getStatus()) {
                case changed -> createUpdatedLine(key, prevValue, updatedValue);
                case added -> createAddedLine(key, updatedValue);
                case deleted -> createDeletedLine(key);
                case unchanged -> new StringBuilder();
            };

            builder.append(line);
        });

        return builder.toString().trim();
    }

    private static StringBuilder createDeletedLine(String key) {
        return createLine(key)
                .append(" was removed")
                .append(LINE_SEPARATOR);
    }

    private static StringBuilder createAddedLine(String key, String updatedValue) {
        return createLine(key)
                .append(" was added with value: ")
                .append(updatedValue)
                .append(LINE_SEPARATOR);
    }

    private static StringBuilder createUpdatedLine(String key, String prevValue, String updatedValue) {
        return createLine(key)
                .append(" was updated. ")
                .append("From ")
                .append(prevValue)
                .append(" to ")
                .append(updatedValue)
                .append(LINE_SEPARATOR);
    }

    private static StringBuilder createLine(String key) {
        return new StringBuilder().append("Property ").append(addBraces(key));
    }

    private static String handleValues(Object value) {

        if (value instanceof List || value instanceof Map) {
            return COMPLEX_VALUE;
        }

        if (value instanceof String) {
            return addBraces(value.toString());
        }

        return value == null ? null : value.toString();
    }

    private static String addBraces(String value) {
        return "'" + value + "'";
    }

}
