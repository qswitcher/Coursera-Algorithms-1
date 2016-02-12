/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import org.junit.Test;

/**
 * Created by jrussom on 2/9/16.
 */
public class MergeSortTest {

    @Test
    public void mergeSort() {
       TopDownMergeSort.sort(new Integer[] {96, 38, 34, 64, 61, 86, 19, 66, 50, 31, 27, 10 });
    }

    @Test
    public void bottomUp() {
        BottomUpMergeSort.sort(new Integer[] {88, 26, 65, 25, 90, 15, 50, 96, 94, 86});
    }
}
