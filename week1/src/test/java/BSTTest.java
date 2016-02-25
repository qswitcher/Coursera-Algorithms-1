/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import org.junit.Test;

/**
 * Created by jrussom on 2/15/16.
 */
public class BSTTest {

    @Test
    public void test() {
        // 99 73 50
        BST<String, String> bst = new BST<String, String>();
        String[] values = "31 17 37 28 34 57 24 30 36 52 99 70".split(" ");
        for (String value : values) {
            bst.put(value, value);
        }
        bst.delete("30");
        bst.delete("99");
        bst.delete("37");
        StringBuilder sb = new StringBuilder();
        for (String key : bst.levelOrder()) {
            sb.append(key).append(" ");
        }
        System.out.println(sb.toString());
    }

}
