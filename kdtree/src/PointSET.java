import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p: set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("Null argument");
        List<Point2D> points = new ArrayList<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        Point2D minP = null;
        double minR = Double.MAX_VALUE;
        for (Point2D j: set) {
            if (minP == null || p.distanceSquaredTo(j) < minR) {
                minP = j;
                minR = p.distanceSquaredTo(j);
            }
        }
        return minP;
    }

    public static void main(String[] args) {

    }
}
