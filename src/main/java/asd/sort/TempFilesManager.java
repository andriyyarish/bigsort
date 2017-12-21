package asd.sort;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TempFilesManager {
    private static List<String> tempFilesList = new ArrayList<>();
    private static final String FILE_NAME_PATTERN = "temp/SortedChunk_";
    private static int tempFilesCounter = 1;
    private static Logger logger = LogManager.getLogger();

    public static String dumpToTempFile(Element[] sortedArr) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String fileName = FILE_NAME_PATTERN+tempFilesCounter+".txt";
        tempFilesList.add(fileName);
        tempFilesCounter++;
        File file = new File(fileName);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i<sortedArr.length; i++){
            writer.write(sortedArr[i].toString());
            writer.newLine();
        }
        writer.flush();
        writer.close();
        logger.trace(fileName + " -> Temp file with name created within (sec) -> " + stopwatch.stop().elapsed().getSeconds());
        return fileName;
    }

    public static List<String> getTempFilesList() {
        return tempFilesList;
    }
}
