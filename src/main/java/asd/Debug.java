package asd;

import asd.sort.KWaySort.MergeAndSort;
import asd.sort.SortFacade;
import asd.sort.TempFilesManager;
import asd.sort.sortingAlgortims.InsertionSort;
import asd.sort.sortingAlgortims.MergeSort;
import asd.sort.sortingAlgortims.Sort;
import com.google.common.base.Stopwatch;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Debug {
    static int LINES_AMOUNT = 50_000_000;

    public static void main(String[] args) throws IOException {
        String fileName = "input.txt";
        generateInputFile(fileName);
        Sort sortAlgoritm = new MergeSort();
        SortFacade sortFacade = new SortFacade(fileName,sortAlgoritm);
        sortFacade.setTEMP_ARRAY_LENGTH(5000000);
        sortFacade.sortChunks();
        System.out.println("Tree set created. Dumping to the temp file");

        List<String> tempFilesList = TempFilesManager.getTempFilesList();
        MergeAndSort mergeAndSort = new MergeAndSort(tempFilesList);
        mergeAndSort.marge();

    }

    public static void generateInputFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int linesAmount = LINES_AMOUNT;
        System.out.println("File generation started. Number of lines with random int that going to be generated -> " + linesAmount);
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
        System.out.println("Amount of lines that was generated: " + linesAmount + "tooks in seconds -> " + stopwatch.elapsed().getSeconds());

        writer.close();
    }

    private static void dumpToFile(String fileName, Set<Long> result) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int chunkCounter = 0;
        int chunkSize = 10000;
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();

        for (Long l: result){
            writer.write(l.toString());
            chunkCounter++;
            if(chunkCounter == chunkSize){
                writer.flush();
                chunkCounter = 0;
//                System.out.println("Ten thousand dumped");
            }
        }
        stopwatch.stop();
        System.out.println("Data dumped to the temp file in seconds -> " + stopwatch.elapsed().getSeconds());
    }

}
