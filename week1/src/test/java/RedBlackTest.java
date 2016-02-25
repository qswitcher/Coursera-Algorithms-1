/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.junit.Test;

/**
 * Created by jrussom on 2/23/16.
 */
public class RedBlackTest {

    @Test
    public void test() {
        RedBlackBST<String, String> bst = new RedBlackBST<String, String>();
        String[] values = "90 57 93 27 76 91 99 20 56 60 83 13 33".split(" ");
        for (String value : values) {
            bst.put(value, value);
        }
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<>();
        for (String key : bst.redKeys()) {
            keys.add(key);
        }
        keys.sort(Comparator.comparing(Function.identity()));

        for (String key : keys) {
            sb.append(key).append(" ");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test2() {
        RedBlackBST<String, String> bst = new RedBlackBST<String, String>();
        String[] values = "62 40 89 24 55 73 95 17 70 78 48 96 51".split(" ");
        for (String value : values) {
            bst.put(value, value);
        }

        StringBuilder sb = new StringBuilder();
        for (String key : bst.levelOrder()) {
            sb.append(key).append(" ");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void ascending() {
        RedBlackBST<Integer, Integer> bst = new RedBlackBST<Integer, Integer>();
        Random random = new Random();
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            values.add(random.nextInt());
        }
        values.sort(Comparator.comparing(Function.identity()));
        for (int i : values) {
            bst.put(i, i);
        }
        System.out.println(bst.getRightRotations());

    }
}
