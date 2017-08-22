package au.com.codeka.carrot;

import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.FileResourceLocator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

class BuildDocs {
  public static void main(String[] args) {
    String inputDir = args[0];
    String outputDir = args[1];

    CarrotEngine engine = new CarrotEngine(
        new Configuration.Builder()
            .setResourceLocator(new FileResourceLocator.Builder(inputDir))
            .build());
    processDirectory(engine, 0, inputDir, outputDir);
  }

  private static void processDirectory(CarrotEngine engine, int depth, String inputDir, String outputDir) {
    File ind = new File(inputDir);
    File[] children = ind.listFiles();
    if (children == null) {
      // Shouldn't happen.
      return;
    }

    File outd = new File(outputDir);
    if (!outd.mkdirs()) {
      System.out.println("Output directory already exists.");
    }

    for (File inf : children) {
      if (inf.isDirectory()) {
        if (inf.getName().startsWith("_")) {
          System.out.printf("Skipping %s\n", inf.getName());
          continue;
        }
        processDirectory(engine, depth + 1, inf.getAbsolutePath(), outputDir + File.separator + inf.getName());
      } else {
        processFile(engine, depth, inf.getAbsolutePath(), outputDir + File.separator + inf.getName());
      }
    }
  }

  private static void processFile(CarrotEngine engine, int depth, String inputFile, String outputFile) {
    if (inputFile.endsWith(".html")) {
      // process with carrot
      System.out.printf("Processing %s\n", inputFile);
      try {
        Map<String, Object> bindings = new HashMap<>();
        String basePath = "";
        for (int i = 0; i < depth; i++) {
          basePath += "../";
        }
        bindings.put("basePath", basePath);

        String contents = engine.process(inputFile, new MapBindings(bindings));
        Files.write(new File(outputFile).toPath(), contents.getBytes("UTF-8"));
      } catch (CarrotException | IOException e) {
        e.printStackTrace(System.err);
        System.exit(-1);
      }
    } else {
      // just copy it
      System.out.printf("Copying %s\n", inputFile);
      try {
        File outf = new File(outputFile);
        if (outf.exists()) {
          outf.delete();
        }
        Files.copy(new File(inputFile).toPath(), outf.toPath());
      } catch (IOException e) {
        e.printStackTrace(System.err);
        System.exit(-1);
      }
    }
  }
}
