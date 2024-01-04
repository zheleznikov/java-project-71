package hexlet.code;

import hexlet.code.core.Differ;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "0.0.1",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {


    @CommandLine.Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @CommandLine.Parameters(index = "0", description = "path to first file")
    private String filePath1;

    @CommandLine.Parameters(index = "1", description = "path to second file")
    private String filePath2;

    @Override
    public Integer call() {
        String result = Differ.generate(filePath1, filePath2, format);
        System.out.println(result);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
