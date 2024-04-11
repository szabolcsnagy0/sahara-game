package tester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Scanner;

public class TestCase {
    public File input = null;
    public File expected = null;

    public boolean run(Process process, Writer output) throws IOException {
        BufferedWriter stdIn = process.outputWriter(); // Standard input of the subprocess
        try (FileReader fr = new FileReader(input)) {
            fr.transferTo(stdIn);
        }
        stdIn.close();

        int lineCount = 1;
        boolean passed = true; // Indicates that there were no differences

        Iterator<String> gotLines = process.inputReader().lines().iterator();
        try (Scanner scanner = new Scanner(expected)) {
            while (gotLines.hasNext()) {
                String expectedLine = null;
                try {
                    expectedLine = scanner.nextLine();
                } catch (Exception e) {
                }

                String got = gotLines.next();
                output.write(got + "\n");

                if (!got.equals(expectedLine)) {
                    System.err.printf("\nDifference on line %d:\n", lineCount);
                    System.err.printf("\tExpected: '%s', got: '%s'\n", expectedLine, got);
                    passed = false;
                }
                lineCount++;
            }
        }
        return passed;
    }
}
