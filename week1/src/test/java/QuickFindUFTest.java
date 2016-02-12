/*
 * Copyright HomeAway, Inc 2005-2015. All Rights Reserved.
 * No unauthorized use of this software.
 */

import org.junit.Test;

/**
 * jrussom has not yet bothered to enter a useful javadoc comment.
 */
public class QuickFindUFTest {

    @Test
    public void test() {
        QuickFindUF qf = new QuickFindUF(10);
        String ops = "4-7 6-9 8-7 6-4 0-4 5-0";
        for (String union : ops.split(" ")) {
            String[] args = union.split("-");
            qf.union(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        }
        System.out.println(qf.toString());
    }
}
