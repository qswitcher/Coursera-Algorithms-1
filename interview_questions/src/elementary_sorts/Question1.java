/*
 * Copyright HomeAway, Inc 2005-2015. All Rights Reserved.
 * No unauthorized use of this software.
 */
package elementary_sorts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * jrussom has not yet bothered to enter a useful javadoc comment.
 */
public class Question1 {

    public static int intersection(Point[] a, Point[] b) {
        List<Point> x = new ArrayList<Point>(a.length + b.length);
        Collections.addAll(x, a);
        Collections.addAll(x, b);

        Collections.sort(x, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getX() == o2.getX()) {
                    return o1.getY() - o2.getY();
                } else {
                    return o1.getX() - o2.getX();
                }
            }
        });

        // since the points in a and b are unique, there will only ever be at most 2
        // points that are the same side-by-side
        int sum = 0;
        for (int i = 0; i < x.size() - 1; i++) {
            if (x.get(i) == x.get(i+1)) {
                sum++;
            }
        }
        return sum;
    }

    private static class Point  {
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
