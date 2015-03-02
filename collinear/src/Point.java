import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null) {
                throw new NullPointerException("Null argument!");
            }

            Point p0 = Point.this;

            double p1Slope = p0.slopeTo(p1);
            double p2Slope = p0.slopeTo(p2);
            return Double.compare(p1Slope, p2Slope);
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        if (o == null) {
            throw new NullPointerException("Null argument!");
        }

        if (this.y == o.y) {
            if (this.x == o.x) {
                return 0;
            } else if (this.x > o.x) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.y > o.y) {
            return 1;
        } else {
            return -1;
        }
    }

    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        if (that.y == this.y) {
            return 0;
        }
        return ((double) (that.y - this.y))/((double) (that.x - this.x));
    }
}
