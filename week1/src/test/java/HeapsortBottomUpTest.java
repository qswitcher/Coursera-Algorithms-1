/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import org.junit.Test;

/**
 * Created by jrussom on 2/14/16.
 */
public class HeapsortBottomUpTest {

    @Test
    public void testReverse() {
        Integer[] a = new Integer[100000];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        new HeapsortBottomUp(a);
    }

    @Test
    public void test() {
        Integer[] a = new Integer[100000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        new HeapsortBottomUp(a);
    }
}
