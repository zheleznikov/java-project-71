package hexlet.code.formatters;

import hexlet.code.status.ValueStatus;

import java.util.Map;

import static hexlet.code.formatters.Separator.LINE_SEPARATOR;

public class StylishFormatter {

    private static final String UNCHANGED_PREFIX = "    ";
    private static final String ADDED_PREFIX = "  + ";
    private static final String DELETED_PREFIX = "  - ";


    public static String makeOutputStylish(Map<String, ValueStatus> sourceMap) {


        StringBuilder builder = new StringBuilder()
                .append("{")
                .append(LINE_SEPARATOR);

        sourceMap.forEach((key, value) -> {
            Object prevValue = value.getValue1();
            Object updatedValue = value.getValue2();

            StringBuilder line = switch (value.getStatus()) {
                case changed -> createLine(DELETED_PREFIX, key, prevValue)
                        .append(createLine(ADDED_PREFIX, key, updatedValue));

                case unchanged -> createLine(UNCHANGED_PREFIX, key, prevValue);

                case deleted -> createLine(DELETED_PREFIX, key, prevValue);

                case added -> createLine(ADDED_PREFIX, key, updatedValue);
            };

            builder.append(line);
        });

        return builder.append("}").toString();
    }

    private static StringBuilder createLine(String prefix, String key, Object value) {
        return new StringBuilder()
                .append(prefix).append(key)
                .append(": ")
                .append(value)
                .append(LINE_SEPARATOR);
    }
}
