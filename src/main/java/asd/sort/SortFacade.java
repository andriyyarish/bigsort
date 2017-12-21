package asd.sort;

import asd.sort.sortingAlgortims.InsertionSort;
import asd.sort.sortingAlgortims.Sort;
import com.google.common.base.Stopwatch;

import java.io.*;
import java.util.*;

public class SortFacade {
    Set<Long> set;
    private String fileName;
    private int chungSize = 100;
    private int chunkCounter;
    private List<File> filesList;
    private File inputFile;
    private int writerFlushFrequancy = 100000;
    private Set<Long> temporarySetStorage;

    private Sort sortAlgoritm;
    int TEMP_ARRAY_LENGTH = 25_000;

    public SortFacade(String inputFileName, Sort sort) {
        this.filesList = new ArrayList<>();
        this.fileName = inputFileName;
        inputFile = new File(inputFileName);
        this.sortAlgoritm = sort;
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
                System.out.println("Chunk prepared for sorting");
                Element[] sorted = sortAlgoritm.sort(tempArr);
                TempFilesManager.dumpToTempFile(sorted);
                tempArr = new Element[TEMP_ARRAY_LENGTH];
                tempArrayIndexPointer = 0;
                tempArr[tempArrayIndexPointer] = new Element(Long.valueOf(line), currentlineNumber);
            }
        }
        reader.close();
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
