package hexlet.code.core;

import hexlet.code.status.ValueStatus;

import java.util.Map;

import static hexlet.code.formatters.JsonFormatter.makeOutputJson;
import static hexlet.code.formatters.PlainFormatter.makeOutputPlain;
import static hexlet.code.formatters.StylishFormatter.makeOutputStylish;

public class Formatter {

    public static String makeOutputDependsOnFormat(String format, Map<String, ValueStatus> joinedMap) {
        return switch (format) {
            case "json" -> makeOutputJson(joinedMap);
            case "plain" -> makeOutputPlain(joinedMap);
            default -> makeOutputStylish(joinedMap);
        };
    }


}
