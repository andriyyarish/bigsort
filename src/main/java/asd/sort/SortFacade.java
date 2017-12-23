package asd.sort;

import asd.sort.sortStrategy.Sort;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

public class SortFacade {
   private static final Logger logger = LogManager.getLogger();
    private String fileName;

    private File inputFile;
    private Sort sortStrategy;
    int TEMP_ARRAY_LENGTH = 25_000;

    static int chunksCounter = 1;

    public SortFacade(String inputFileName, Sort sort) {
        this.fileName = inputFileName;
        inputFile = new File(inputFileName);
        this.sortStrategy = sort;
    }


    public void sortChunks() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int currentTempFileIndex = 0;

        long currentlineNumber = 1;
        int tempArrayIndexPointer = 0;
        Element [] tempArr = new Element[TEMP_ARRAY_LENGTH];
        Stopwatch stopwatch = Stopwatch.createStarted();
        while ((line = reader.readLine()) != null){
            System.out.println(currentlineNumber + "-> " + line);
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
                tempArrayIndexPointer++;
                currentlineNumber++;
            }
        }
        //TODO remove nulls from last array
        logger.debug("The Last Chunk prepared for sorting");
        Element[] noNulls = removeNulls(tempArr);
        tempArr = null;
        Element[] sorted =  sortWrapper(noNulls);
        TempFilesManager.dumpToTempFile(sorted);
        reader.close();
    }

    private Element[] removeNulls(Element[] arr){
        int notNullSize = 0;
        int indexIter = 0;
        boolean isNull = true;
        int i = arr.length-1;
        while (arr[i] == null)
            i--;
        return Arrays.copyOfRange(arr, 0, i+1);
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


    public void setTEMP_ARRAY_LENGTH(int TEMP_ARRAY_LENGTH) {
        this.TEMP_ARRAY_LENGTH = TEMP_ARRAY_LENGTH;
    }
}
