/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

/**
 * Created by jrussom on 2/10/16.
 */
public class BottomUpMergeSort {

    private static Comparable[] aux;

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                   a[k] = aux[j++];
            else if (j > hi)                    a[k] = aux[i++];
            else if (less(aux[j], aux[i]))      a[k] = aux[j++];
            else                                a[k] = aux[i++];
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static int CALL_COUNT  = 0;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
                StringBuilder sb = new StringBuilder("Call ").append(++CALL_COUNT).append(": ");
                for (Comparable value : a) {
                    sb.append(value).append(" ");
                }
                System.out.println(sb.toString());
            }
        }
    }
}
