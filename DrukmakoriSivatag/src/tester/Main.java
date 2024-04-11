package tester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "bin", "proto.Proto");
    static File testDir, outDir;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("The correct way to call this command is: ");
            System.err.printf("\ttester.Main <tests-directory> [output-directory]\n");
            System.exit(1);
        }

        testDir = new File(args[0]);
        if (!testDir.isDirectory()) {
            System.err.print("Could not find tests directory: ");
            System.err.println(testDir.getAbsolutePath());
            System.exit(1);
        }

        // Outdir is also specified
        outDir = 2 <= args.length ? new File(args[1]) : testDir;
        if (!outDir.isDirectory()) {
            System.err.print("Could not find output directory: ");
            System.err.println(outDir.getAbsolutePath());
            System.exit(1);
        }

        File[] testFiles = testDir.listFiles((f) -> {
            String name = f.getName();
            return name.endsWith("_in.txt") || name.endsWith("_exp.txt");
        });
        if (testFiles.length == 0) {
            System.err.print("There are no tests found in the directory: ");
            System.err.println(testDir.getAbsolutePath());
            System.exit(1);
        }

        HashMap<String, TestCase> testCases = new HashMap<>();
        for (File file : testFiles) {
            String testName = file.getName().split("(_in|_exp)\\.txt")[0];

            TestCase testCase = testCases.get(testName);
            if (testCase == null) {
                testCase = new TestCase();
                testCases.put(testName, testCase);
            }

            if (file.getName().endsWith("_in.txt"))
                testCase.input = file;
            else
                testCase.expected = file;
        }

        // Check if all test cases have an input and expected file
        testCases.forEach((testName, testCase) -> {
            if (testCase.input == null) {
                System.err.print("No input file found for test: ");
                System.err.println(testName);
                System.exit(1);
            }

            if (testCase.expected == null) {
                System.err.print("No expected file found for test: ");
                System.err.println(testName);
                System.exit(1);
            }
        });

        for (Map.Entry<String, TestCase> entry : testCases.entrySet()) {
            String testName = entry.getKey();
            TestCase testCase = entry.getValue();

            System.out.printf("Running %s: ", testName);
            File outFile = Paths.get(outDir.getAbsolutePath(), testName + "_out.txt").toFile();
            try (FileWriter fw = new FileWriter(outFile)) {
                Process process = processBuilder.start();

                boolean passed = testCase.run(process, fw);
                System.out.println(passed ? "PASSED" : "FAILED");

                process.destroy();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
