package asd.sort.sortStrategy;

import asd.sort.Element;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InsertionSort implements Sort {
    private static final Logger logger = LogManager.getLogger();
    static int counter = 1;
    public static long[] sort(long [] inputArr){
        logger.debug("Started insertion sort on array with length -> " + inputArr.length);
        Stopwatch stopwatch = Stopwatch.createStarted();
        long temp;
        for(int i = 0; i< inputArr.length; i++){
            for(int j = i; j > 0; j--){
                if(inputArr[j] < inputArr[j-1]) {
                    temp = inputArr[j];
                    inputArr[j] = inputArr[j-1];
                    inputArr[j-1] = temp;
                }
            }
        }
        logger.trace(counter + ".Sorting takes(sec) -> " + stopwatch.stop().elapsed().getSeconds());
        counter++;
        return inputArr;

    }

    public Element[] sort(Element[] inputArr){
        logger.trace("Started insertion sort on array with length -> " + inputArr.length);
        Stopwatch stopwatch = Stopwatch.createStarted();
        Element temp;
        for(int i = 0; i< inputArr.length; i++){
            for(int j = i; j > 0; j--){
                if(inputArr[j].getValue() < inputArr[j-1].getValue()) {
                    temp = inputArr[j];
                    inputArr[j] = inputArr[j-1];
                    inputArr[j-1] = temp;
                }
            }
        }
        logger.debug(counter + ".Sorting takes(sec) -> " + stopwatch.stop().elapsed().getSeconds());
        counter++;
        return inputArr;
    }
}
