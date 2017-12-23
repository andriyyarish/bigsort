package asd;

import asd.sort.KWaySort.MergeAndSort;
import asd.sort.SortFacade;
import asd.sort.TempFilesManager;
import asd.sort.sortStrategy.InsertionSort;
import asd.sort.sortStrategy.MergeSort;
import asd.sort.sortStrategy.Sort;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;

public class Demo {

    static final Logger logger = LogManager.getLogger();
    static final int LINES_TO_BE_GENERATED = 3000;
    static final int TEMP_ARR_LENGTH = 2000;


    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        InputFileGenerator.setLinesAmount(LINES_TO_BE_GENERATED);
        InputFileGenerator.generateInputFile();

//        Sort sortAlgoritm = new MergeSort();
        Sort sortAlgoritm = new InsertionSort();
        SortFacade sortFacade = new SortFacade(InputFileGenerator.getInputFileName(),sortAlgoritm);
        sortFacade.setTEMP_ARRAY_LENGTH(TEMP_ARR_LENGTH);

        sortFacade.sortChunks();

        List<String> tempFilesList = TempFilesManager.getTempFilesList();
        MergeAndSort mergeAndSort = new MergeAndSort(tempFilesList);
        mergeAndSort.marge();

        logger.info("Total time for creating input and sorting is -> " + stopwatch.stop().elapsed().getSeconds() );

    }

}
