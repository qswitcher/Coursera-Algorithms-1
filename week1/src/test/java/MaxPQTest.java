/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import org.junit.Test;

/**
 * Created by jrussom on 2/14/16.
 */
public class MaxPQTest {

    @Test
    public void test() {
        MaxPQ<Integer> pq = new MaxPQ<Integer>(new Integer[] {91, 86, 88, 75, 78, 84, 49, 32, 66, 59});
        pq.insert(23);
        pq.insert(13);
        pq.insert(64);
        System.out.println(pq.toString());
    }

    @Test
    public void test2() {
        String[] values = "87 86 46 74 77 37 39 14 51 41".split(" ");
        Integer[] a = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            a[i] = Integer.valueOf(values[i]);
        }

        MaxPQ<Integer> pq = new MaxPQ<Integer>(a);
        pq.delMax();
        pq.delMax();
        pq.delMax();

        System.out.println(pq.toString());
    }
}
