package asd;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Random;

public class InputFileGenerator {
     private InputFileGenerationStrategy genStrategy;
     private double INPUT_SIZE_IN_GB;
     private int INPUT_SIZE_LINES;
     private int maxBufferSize = 10_000_000;
     private int bufferIterator = 0;
     private String INPUT_FILE_NAME;
     private final Logger logger = LogManager.getLogger();

    public InputFileGenerator() {
        Properties props = PropertyManager.getProps();
        INPUT_SIZE_IN_GB = Double.parseDouble(props.getProperty("INPUT_SIZE_IN_GB", "1"));
        INPUT_FILE_NAME = props.getProperty("INPUT_FILE_NAME", "input.txt");
        INPUT_SIZE_LINES = Integer.parseInt(props.getProperty("INPUT_SIZE_LINES", "0"));
        try {
            genStrategy = InputFileGenerationStrategy.valueOf(props.getProperty("InputFileGenerationStrategy"));
        }catch (IllegalArgumentException ia){
            logger.warn("Possible options are LINES_BASED, SIZE_BASED; ");
            throw ia;
        }

    }

    public void generateInputFile() throws IOException {
        File file = new File(INPUT_FILE_NAME);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        switch (genStrategy){
            case SIZE_BASED:
                generateNumbers_sizeBased_optimized(writer);
                break;
            case LINES_BASED:
                generateNumbers_lineBased(writer);
                break;
        }
    }

    private  void generateNumbers_lineBased(BufferedWriter writer) throws IOException {

        logger.info("File generation started. Number of lines with random int that going to be generated -> " + INPUT_SIZE_LINES);

        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i<INPUT_SIZE_LINES; i++){
            writeRandomAndFlush(writer);
        }
        stopwatch.stop();
        logger.info("Amount of lines that was generated: " + INPUT_SIZE_LINES + " takes in seconds -> " + stopwatch.elapsed().getSeconds());

        writer.flush();
        writer.close();
    }

    private void generateNumbers_sizeBased_optimized(BufferedWriter writer) throws IOException {
        INPUT_SIZE_LINES = sizeToLinesConverter(INPUT_SIZE_IN_GB);
        generateNumbers_lineBased(writer);
    }

    private int sizeToLinesConverter(double sizeGb){
        Double res = (sizeGb/0.2)*10000000;
        return res.intValue();
    }

    private void generateNumbers_sizeBased(File file, BufferedWriter writer) throws IOException {
        logger.info("File generation started. File size to be generated -> " + INPUT_SIZE_IN_GB);
        Stopwatch stopwatch = Stopwatch.createStarted();
        while (getFileLengthInGB(file) <= INPUT_SIZE_IN_GB){
                writeRandomAndFlush(writer);
        }
        writer.flush();
        writer.close();
        stopwatch.stop();
        logger.info("Input file succesfully generated using SIZE_BASED strategy in -> " + stopwatch.elapsed().getSeconds());
    }

    private double getFileLengthInGB(File file){
        if(file.exists())
            return file.length()/1024/1024/1024;
        else
            throw new RuntimeException("Input file does not exist");
    }

    private void writeRandomAndFlush(BufferedWriter writer) throws IOException {
        long random = new Random().nextLong();
        writer.write(String.valueOf(random));
        writer.newLine();
        bufferIterator++;
        if(bufferIterator == maxBufferSize) {
            writer.flush();
            bufferIterator = 0;
        }
    }

    public String getINPUT_FILE_NAME() {
        return INPUT_FILE_NAME;
    }

    public enum InputFileGenerationStrategy{
        LINES_BASED, SIZE_BASED;

    }
}
