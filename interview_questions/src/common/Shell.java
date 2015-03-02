/*
 * Copyright HomeAway, Inc 2005-2015. All Rights Reserved.
 * No unauthorized use of this software.
 */
package common;

/**
 * jrussom has not yet bothered to enter a useful javadoc comment.
 */
public class Shell {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N/3) {
            h = 3*h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h/3;
        }
    }

    private static boolean less(Comparable o1, Comparable o2) {
        return o1.compareTo(o2) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
