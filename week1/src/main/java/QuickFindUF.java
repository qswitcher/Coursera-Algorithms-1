/*
 * Copyright HomeAway, Inc 2005-2015. All Rights Reserved.
 * No unauthorized use of this software.
 */

import java.util.Arrays;

/**
 * jrussom has not yet bothered to enter a useful javadoc comment.
 */
public class QuickFindUF {

    private int[] id;

    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id[0]);
        for (int i = 1 ; i < id.length; i++ ) {
            sb.append(" ").append(id[i]);
        }
        return sb.toString();
    }
}
