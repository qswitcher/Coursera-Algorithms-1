import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by jrussom on 2/12/16.
 */
public class Test {

    @org.junit.Test
    public void test() {
        Map<Integer, Integer> testNumToNum = new HashMap<Integer, Integer>();
        testNumToNum.put(6, 1);
        testNumToNum.put(8, 2);
        testNumToNum.put(9, 1);
        testNumToNum.put(10, 2);
        testNumToNum.put(20, 5);
        testNumToNum.put(40, 4);
        testNumToNum.put(50, 7);

        for (Integer key : testNumToNum.keySet()) {
            assertEquals(testNumToNum.get(key).intValue(), runTest("data/input" + key.toString() + ".txt"));
        }
    }

    private int runTest(String filename) {
        // read the N points from a file
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            segment.draw();
        }
        return collinear.segments().length;
    }
}
