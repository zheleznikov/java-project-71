package hexlet.code.core;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static byte[] getFileAsByteArray(String filePath) {
        try {
            return FileUtils.readFileToByteArray(new File(filePath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
