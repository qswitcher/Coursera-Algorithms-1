/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import java.util.Arrays;
import java.util.stream.Collectors;

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


    @Test
    public void partition() {
        Integer[] a = Arrays.asList("26 17 36 47 84 22 77 40 27 13 86 32".split(" ")).stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList())
            .toArray(new Integer[12]);

        partition(a, 0, 11);
        StringBuilder sb = new StringBuilder();
        for (int value : a) {
            sb.append(value).append(" ");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void waypartition() {
        Integer[] a = Arrays.asList("49 32 94 49 80 73 49 49 30 63".split(" ")).stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList())
            .toArray(new Integer[10]);

        threeway(a, 0, 9);
        StringBuilder sb = new StringBuilder();
        for (int value : a) {
            sb.append(value).append(" ");
        }
        System.out.println(sb.toString());
    }

    private static int partition(Comparable[] a, int lo, int hi) {  // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi+1;            // left and right scan indices
        Comparable v = a[lo];            // partitioning item
        while (true) {  // Scan right, scan left, check for scan complete, and exchange.
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void threeway(Comparable[] a, int lo, int hi)
    {  // See page 289 for public sort() that calls this method.
        if (hi <= lo) return;
        int lt = lo, i = lo+1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt)
        {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else              i++;
        }  // Now a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
    }


    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int left, int right) {
        Comparable temp = a[left];
        a[left] = a[right];
        a[right] = temp;

    }
}
