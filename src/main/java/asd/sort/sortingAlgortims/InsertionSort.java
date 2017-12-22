package asd.sort.sortingAlgortims;

import asd.sort.Element;
import com.google.common.base.Stopwatch;

public class InsertionSort implements Sort {
    static int counter = 1;
    public static long[] sort(long [] inputArr){
//        System.out.println("Started insertion sort on array with length -> " + inputArr.length);
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
//        System.out.println(counter + ".Sorting takes(sec) -> " + stopwatch.stop().elapsed().getSeconds());
        counter++;
        return inputArr;

    }

    public Element[] sort(Element[] inputArr){
//        System.out.println("Started insertion sort on array with length -> " + inputArr.length);
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
//        System.out.println(counter + ".Sorting takes(sec) -> " + stopwatch.stop().elapsed().getSeconds());
        counter++;
        return inputArr;
    }
}
