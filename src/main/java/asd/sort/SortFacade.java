package asd.sort;

import asd.sort.sortingAlgortims.Sort;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

public class SortFacade {
   private static final Logger logger = LogManager.getLogger();
    private String fileName;
    private int chungSize = 100;

    private File inputFile;
    private Sort sortStrategy;
    int TEMP_ARRAY_LENGTH = 25_000;

    static int chunksCounter;

    public SortFacade(String inputFileName, Sort sort) {
        this.fileName = inputFileName;
        inputFile = new File(inputFileName);
        this.sortStrategy = sort;
    }


    public void sortChunks() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int currentTempFileIndex = 0;

        long currentlineNumber = 0;
        int tempArrayIndexPointer = 0;
        Element [] tempArr = new Element[TEMP_ARRAY_LENGTH];
        Stopwatch stopwatch = Stopwatch.createStarted();
        while ((line = reader.readLine()) != null){
            if(tempArrayIndexPointer < TEMP_ARRAY_LENGTH) {
                Element element = new Element(Long.valueOf(line), currentlineNumber);
                tempArr[tempArrayIndexPointer] = element;
                tempArrayIndexPointer++;
                currentlineNumber++;
            }else {
                logger.debug("Chunk prepared for sorting");
                Element[] sorted = sortWrapper(tempArr);
                TempFilesManager.dumpToTempFile(sorted);
                tempArr = new Element[TEMP_ARRAY_LENGTH];
                tempArrayIndexPointer = 0;
                tempArr[tempArrayIndexPointer] = new Element(Long.valueOf(line), currentlineNumber);
            }
        }
        //TODO dump what was left
        reader.close();
    }

    private Element[] sortWrapper(Element[] inputArr){
        logger.debug(chunksCounter + ". Sort of chunk started");
        chunksCounter++;
        Stopwatch stopwatch = Stopwatch.createStarted();

        Element[] result = sortStrategy.sort(inputArr);

        logger.info("Chunk sorting started using algoritm: " + sortStrategy.getClass().getName() + "And takes -> " + stopwatch.stop().elapsed().getSeconds());
        return result;
    }

    private Double getInputFileSize(){
        double size = 0;
        if(inputFile.exists())
            size = inputFile.length()/(1024*1024);
        System.out.println("Input file size (MB) -> " + size);
        return size;
    }

    public int getChungSize() {
        return chungSize;
    }

    public void setTEMP_ARRAY_LENGTH(int TEMP_ARRAY_LENGTH) {
        this.TEMP_ARRAY_LENGTH = TEMP_ARRAY_LENGTH;
    }
}
