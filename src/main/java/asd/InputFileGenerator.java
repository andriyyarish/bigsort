package asd;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class InputFileGenerator {
    static int LINES_AMOUNT = 1_000_000;
    static final String INPUT_FILE_NAME = "input.txt";
    static final Logger logger = LogManager.getLogger();

    public InputFileGenerator() {
    }

    public static void generateInputFile() throws IOException {
        File file = new File(INPUT_FILE_NAME);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int linesAmount = LINES_AMOUNT;
        logger.info("File generation started. Number of lines with random int that going to be generated -> " + linesAmount);
        int tenThousand = 0;

        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i<linesAmount; i++){
            long random = new Random().nextLong();
            writer.write(String.valueOf(random));
            writer.newLine();
            tenThousand++;
            if(tenThousand == 10000) {
                writer.flush();
//                System.out.println("Ten thousand done in -> " + timeElapsed);
                tenThousand = 0;
            }
        }
        stopwatch.stop();
        logger.info("Amount of lines that was generated: " + linesAmount + " takes in seconds -> " + stopwatch.elapsed().getSeconds());

        writer.close();
    }

    public static int getLinesAmount() {
        return LINES_AMOUNT;
    }

    public static String getInputFileName() {
        return INPUT_FILE_NAME;
    }

    public static void setLinesAmount(int linesAmount) {
        LINES_AMOUNT = linesAmount;
    }
}
