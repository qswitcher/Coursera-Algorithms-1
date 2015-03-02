import java.util.Arrays;
import java.util.Comparator;

public class Fast {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] pointSorted = Arrays.copyOf(points, points.length);
            // move p to the end
            pointSorted[i] = pointSorted[N - 1];
            pointSorted[N - 1] = p;

            // sort
            Arrays.sort(pointSorted, 0, N - 1,
                    new SlopeThenLexigraphic(p.SLOPE_ORDER));

            double commonSlope = Double.NEGATIVE_INFINITY;
            int count = 0;
            for (int j = 0; j < N; j++) {
                Point s = pointSorted[j];
                double slope = p.slopeTo(s);
                if (slope == commonSlope) {
                    count++;
                } else if (count >= 3 && slope != commonSlope) {
                    print(pointSorted, j - count, j);
                    count = 1;
                    commonSlope = slope;
                } else {
                    count = 1;
                    commonSlope = slope;
                }
            }
        }
    }

    private static class SlopeThenLexigraphic implements Comparator<Point> {

        private Comparator<Point> slopeComparator;

        public SlopeThenLexigraphic(Comparator<Point> slopeComparator) {
            this.slopeComparator = slopeComparator;
        }

        @Override
        public int compare(Point o1, Point o2) {
            int value = this.slopeComparator.compare(o1, o2);
            if (value == 0) {
                return o1.compareTo(o2);
            }
            return value;
        }
    }

    private static void print(Point[] points, int from, int to) {
        Point p = points[points.length - 1];
        if (inOrder(p, points, from, to)) {
            StringBuilder sb = new StringBuilder();
            sb.append(p).append(" -> ");
            for (int i = from; i < to - 1; i++) {
                sb.append(points[i]).append(" -> ");
            }
            sb.append(points[to - 1]);
            StdOut.printf("%s\n", sb.toString());
            p.drawTo(points[to - 1]);
        }
    }

    private static boolean inOrder(Point first, Point[] points, int from, int to) {
        for (int i = from; i < to - 1; i++) {
            if (points[i].compareTo(points[i + 1]) > 0) {
                return false;
            }
        }
        return first.compareTo(points[from]) < 0;
    }
}
