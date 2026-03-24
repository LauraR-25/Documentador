package generator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Main {

    /**
     * Uso:
     *   java generator.Main <fully.qualified.ClassName> [outputFile]
     */
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.err.println("Uso: java generator.Main <fully.qualified.ClassName> [outputFile]");
            System.exit(2);
        }

        String className = args[0];
        String out = args.length == 2 ? args[1] : (className.substring(className.lastIndexOf('.') + 1) + ".md");

        try {
            Class<?> clazz = Class.forName(className);
            DocGenerator gen = new DocGenerator();
            ClassDoc doc = gen.generateFor(clazz);
            String md = MarkdownRenderer.render(doc);

            Path outPath = Paths.get(out);
            Files.write(outPath, md.getBytes(StandardCharsets.UTF_8));

            System.out.println("OK -> " + outPath.toAbsolutePath());
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró la clase: " + className);
            System.exit(3);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

