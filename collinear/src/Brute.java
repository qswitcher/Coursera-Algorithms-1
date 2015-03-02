
public class Brute {

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

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    for (int l = 0; l < N; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        if (i == j || j == k
                                || k == l || i == k
                                || j == l || i == l) {
                            continue;
                        }
                        if (p.slopeTo(q) == p.slopeTo(r)
                                && p.slopeTo(q) == p.slopeTo(s)
                                && p.compareTo(q) <= 0
                                && q.compareTo(r) <= 0
                                && r.compareTo(s) <= 0) {
                            StdOut.printf("%s -> %s -> %s -> %s\n", p, q, r, s);
                            p.drawTo(s);
                        }
                    }
                }
            }
        }
    }
}
