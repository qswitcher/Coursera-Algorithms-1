import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> lineSegments = new LinkedList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        int N = points.length;
        checkRepeats(points);

        for (int i = 0; i < points.length - 1; i++) {
            Point p = points[i];
            Point[] pointSorted = new Point[N - 1];
            if (i > 0) {
                System.arraycopy(points, 0, pointSorted, 0, i);
            }
            if (i < points.length - 1) {
                System.arraycopy(points, i + 1, pointSorted, i, N - 1 - i);
            }

            // sort
            Arrays.sort(pointSorted, new SlopeThenLexigraphic(p.slopeOrder()));

            double commonSlope = Double.NEGATIVE_INFINITY;
            int count = 0;
            for (int j = 0; j < pointSorted.length; j++) {
                Point s = pointSorted[j];
                double slope = p.slopeTo(s);

                boolean type1 = count >= 3 && slope != commonSlope;
                boolean type2 = count >= 2 && j == pointSorted.length - 1 && slope == commonSlope;

                if (type1 || type2) {
                    int start = j - count;
                    int end = type1 ? j - 1 : j;
                    if (isNotRepeat(points, pointSorted, i, start, end)) {
                        // need logic to see if p is on left or right side
                        if (p.compareTo(pointSorted[end]) > 0) {
                            lineSegments.add(new LineSegment(pointSorted[start], p));
                        } else if (p.compareTo(pointSorted[start]) < 0){
                            lineSegments.add(new LineSegment(p, pointSorted[end]));
                        } else {
                            lineSegments.add(new LineSegment(pointSorted[start], pointSorted[end]));
                        }
                    }
                    count = 1;
                } else if (slope == commonSlope) {
                    count++;
                } else {
                    count = 1;
                }
                commonSlope = slope;
            }
        }
    }

    private void checkRepeats(Point[] points) {
        Point[] sorted = new Point[points.length];
        System.arraycopy(points, 0, sorted, 0, points.length);
        Arrays.sort(sorted);

        for (int i = 0; i < points.length - 1; i++) {
            if (sorted[i].compareTo(sorted[i+1]) == 0) {
                throw new IllegalArgumentException("Repeated points!");
            }
        }
    }

    private boolean isNotRepeat(Point[] sorted, Point[] pointSorted, int currentIndex, int from, int to) {
        for (int i = 0; i < currentIndex; i++) {
            for (int j = from ; j <= to; j++) {
                if (sorted[i].compareTo(pointSorted[j]) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
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
}
