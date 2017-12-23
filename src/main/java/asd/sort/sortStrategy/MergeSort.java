package asd.sort.sortStrategy;

import asd.sort.Element;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by andy on 8/3/2017.
 */
public class MergeSort implements Sort {
    private Element [] arr;
    private int chunksCounter;
    private static final Logger logger = LogManager.getLogger();


    public Element[] sort(Element[] inputArr) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        if (inputArr.length <= 1) {
            return inputArr;
        }
        //Split the array in half in two parts
        Element[] left = new Element[inputArr.length / 2];
        Element[] right = new Element[inputArr.length - left.length];

        System.arraycopy(inputArr, 0, left, 0, left.length);
        System.arraycopy(inputArr, left.length, right, 0, right.length);

        //Sort each half recursively
        sort(left);
        sort(right);
        //Merge both halves together, overwriting to original array
        merge(left, right, inputArr);
//        logger.debug(chunksCounter + "Merge sort fo chunk finished in " + stopwatch.stop().elapsed().getSeconds());
        return inputArr;
    }

    private void merge(Element[] left, Element[] right, Element[] list) {
        //Index Position in first array - starting with first element
        int iLeft = 0;
        //Index Position in second array - starting with first element
        int iRight = 0;
        //Index Position in merged array - starting with first position
        int iMerged = 0;

        //Compare elements at iFirst and iSecond,
        //and move smaller element at iMerged
        while (iLeft < left.length && iRight < right.length){
            if(left[iLeft].getValue() < right[iRight].getValue()){
                list[iMerged] = left[iLeft];
                iLeft++;
            }else {
                list[iMerged] = right[iRight];
                iRight++;
            }
            iMerged++;
        }
        System.arraycopy(left, iLeft, list, iMerged, left.length - iLeft);
        System.arraycopy(right, iRight, list, iMerged, right.length - iRight);
    }

}