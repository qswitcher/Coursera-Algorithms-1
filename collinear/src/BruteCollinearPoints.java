import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segmentList = new LinkedList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Can't be null!");
        }

        int N = points.length;
        Point[] sorted = new Point[N];
        System.arraycopy(points, 0, sorted, 0, N);
        Arrays.sort(sorted);

        for (int i = 0; i < N - 1; i++) {
            if (sorted[i].compareTo(sorted[i+1]) == 0) {
                throw new IllegalArgumentException("Repeated points!");
            }
        }

        for (int i = 0; i < N; i++) {
            Point p = points[i];

            for (int j = 0; j < N; j++) {
                if (i == j) {
                    continue;
                }
                Point q = points[j];

                if (p.compareTo(q) <= 0) {
                    for (int k = 0; k < N; k++) {
                        if (j == k || i == k) {
                            continue;
                        }
                        Point r = points[k];

                        if (p.slopeTo(q) == p.slopeTo(r) && q.compareTo(r) <= 0) {
                            for (int l = 0; l < N; l++) {
                                Point s = points[l];
                                if (k == l || j == l || i == l) {
                                    continue;
                                }


                                if (p.slopeTo(q) == p.slopeTo(s) && r.compareTo(s) <= 0) {
                                    segmentList.add(new LineSegment(p, s));
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public int numberOfSegments() {
        return segmentList.size();
    }

    public LineSegment[] segments() {
        return segmentList.toArray(new LineSegment[segmentList.size()]);
    }
}
