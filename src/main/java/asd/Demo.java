package asd;

import asd.sort.KWaySort.MergeAndSort;
import asd.sort.SortFacade;
import asd.sort.TempFilesManager;
import asd.sort.sortingAlgortims.InsertionSort;
import asd.sort.sortingAlgortims.MergeSort;
import asd.sort.sortingAlgortims.Sort;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Demo {

    static final Logger logger = LogManager.getLogger();
    static final int LINES_TO_BE_GENERATED = 10_000_000;
    static final int TEMP_ARR_LENGTH = 1_000_000;


    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        InputFileGenerator.setLinesAmount(LINES_TO_BE_GENERATED);
        InputFileGenerator.generateInputFile();

        Sort sortAlgoritm = new MergeSort();
//        Sort sortAlgoritm = new InsertionSort();
        SortFacade sortFacade = new SortFacade(InputFileGenerator.getInputFileName(),sortAlgoritm);
        sortFacade.setTEMP_ARRAY_LENGTH(TEMP_ARR_LENGTH);

        sortFacade.sortChunks();

        List<String> tempFilesList = TempFilesManager.getTempFilesList();
        MergeAndSort mergeAndSort = new MergeAndSort(tempFilesList);
        mergeAndSort.marge();

        logger.info("Total time for creating input and sorting is -> " + stopwatch.stop().elapsed().getSeconds() );

    }

}
