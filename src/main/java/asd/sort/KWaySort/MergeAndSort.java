package asd.sort.KWaySort;

import asd.sort.Element;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MergeAndSort {
    private List<String> tempSortedFileNames;
    private List<Source> sortedSorces;
    private String resultFileName = "finalResult.txt";
    private File resultFile;
    private BufferedWriter writer;
    private static final Logger logger = LogManager.getLogger();
    private Set<Element> bufferToRemoveDuplBeforeDump = new LinkedHashSet<>();
    private static final int MAX_TEMP_BUF_SIZE = 10000;

    public MergeAndSort(List<String> tempSortedFileNames) throws IOException {
        this.tempSortedFileNames = tempSortedFileNames;
        initSources();
        resultFile = new File(resultFileName);
        resultFile.createNewFile();
        writer = new BufferedWriter(new FileWriter(resultFile));
    }

    private void initSources() throws IOException {
        sortedSorces = new ArrayList<>();
        for(String s : tempSortedFileNames)
            sortedSorces.add(new Source(s));
    }

    public void marge() throws IOException {
        logger.info("Merge of temp files started .... ");
        Stopwatch stopwatch = Stopwatch.createStarted();

        while (sortedSorces.size()>0) {
            Collections.sort(sortedSorces);
            Source minHolder = sortedSorces.get(0);
            Element minElement = minHolder.getCurrentElement();
            dumpResult(minElement);
            boolean hasNext = minHolder.pickNextElement();
            if(!hasNext) {
                sortedSorces.remove(minHolder);
                logger.info("Temp source file merged -> " + minHolder.getFilePath());
            }
        }
        writeAndFlush(bufferToRemoveDuplBeforeDump);
        bufferToRemoveDuplBeforeDump = null;
        logger.info("Merge takes -> " + stopwatch.stop().elapsed().getSeconds());
    }

    private void dumpResult(Element element) throws IOException {
        if(bufferToRemoveDuplBeforeDump.size() < MAX_TEMP_BUF_SIZE) {
            bufferToRemoveDuplBeforeDump.add(element);
        } else {
            writeAndFlush(bufferToRemoveDuplBeforeDump);
            bufferToRemoveDuplBeforeDump = new LinkedHashSet<>();
            bufferToRemoveDuplBeforeDump.add(element);
        }
//        writer.write(element.toString());
//        writer.newLine();
//        writer.flush();
    }

    private void writeAndFlush(Set<Element> set) throws IOException {
        for (Element e: set){
            writer.write(e.toString());
            writer.newLine();
        }
        writer.flush();

    }
}
