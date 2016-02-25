/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

/**
 * Created by jrussom on 2/14/16.
 */
public class HeapsortBottomUp {

    private Comparable[] a;

    private int count;

    public HeapsortBottomUp(Comparable[] a) {
        this.a = new Comparable[a.length + 1];
        System.arraycopy(a, 0, this.a, 1, a.length);

        int N = a.length;
        for (int k = N/2; k >= 1; k--) {
            sink(k, N);
        }

        System.out.println("Took " + count + " compares for length " + a.length + " array");
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            System.out.println(this.a[k/2] + " is less than " + this.a[k]);
            exch(k/2, k);
            k = k/2;
        }
    }

    private boolean less(int i, int j) {
        count++;
        return this.a[i].compareTo(this.a[j]) < 0;
    }

    private void exch(int left, int right) {
        Comparable temp = a[left];
        a[left] = a[right];
        a[right] = temp;

    }

    private void sink(int k, int N) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
