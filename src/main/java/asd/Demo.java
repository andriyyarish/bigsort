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

    static final int TEMP_ARR_LENGTH = 30_000_000; // 20 ml +- 1gb, 40ml +-2gb out of memory


    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        InputFileGenerator inGen = new InputFileGenerator();
        inGen.generateInputFile();

        Sort sortAlgoritm = new MergeSort();
//        Sort sortAlgoritm = new InsertionSort();
        SortFacade sortFacade = new SortFacade(inGen.getINPUT_FILE_NAME(),sortAlgoritm);
        sortFacade.setTEMP_ARRAY_LENGTH(TEMP_ARR_LENGTH);

        sortFacade.sortChunks();

        List<String> tempFilesList = TempFilesManager.getTempFilesList();
        MergeAndSort mergeAndSort = new MergeAndSort(tempFilesList);
        mergeAndSort.marge();

        logger.info("Total time for creating input and sorting is -> " + stopwatch.stop().elapsed().getSeconds() );

    }

}
