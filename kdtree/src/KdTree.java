import java.util.HashSet;
import java.util.Set;

public class KdTree {

    private static class Node {
        private static boolean X_DIM = true;
        private static boolean Y_DIM = false;

        private boolean dim = X_DIM;
        private Point2D p;
        private Node left;
        private Node right;

        private Node(boolean dim, Point2D p) {
            this.dim = dim;
            this.p = p;
        }

        public static Node node(Point2D p, Node parent) {
            return new Node(!parent.dim, p);
        }
        public static Node xNode(Point2D p) {
            return new Node(X_DIM, p);
        }

        public boolean isXdim() {
            return dim == X_DIM;
        }

        public boolean isYdim() {
            return dim == Y_DIM;
        }

        public void insert(Point2D o) {
            if (isLeft(o)) {
                if (left == null) {
                    left = Node.node(o, this);
                } else {
                    left.insert(o);
                }
            } else {
                if (right == null) {
                    right = Node.node(o, this);
                } else {
                    right.insert(o);
                }
            }
        }

        public boolean contains(Point2D o) {
            if (this.p.equals(o)) {
                return true;
            } else if (isLeft(o)) {
                return left != null && left.contains(o);
            } else {
                return right != null && right.contains(o);
            }
        }

        private boolean isLeft(Point2D o) {
            return (isXdim() && o.x() < p.x())
                    || (isYdim() && o.y() < p.y());
        }

        private void draw(double xMin, double xMax, double yMin, double yMax) {
            StdDraw.setPenRadius(0.002);
            if (isXdim()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), yMin, p.x(), yMax);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(xMin, p.y(), xMax, p.y());
            }
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(p.x(), p.y());
            if (left != null) {
                if (isXdim()) {
                    left.draw(xMin, p.x(), yMin, yMax);
                } else {
                    left.draw(xMin, xMax, yMin, p.y());
                }
            }
            if (right != null) {
                if (isXdim()) {
                    right.draw(p.x(), xMax, yMin, yMax);
                } else {
                    right.draw(xMin, xMax, p.y(), yMax);
                }
            }
        }

        public void range(Set<Point2D> acc, RectHV rect,
                double xMin, double xMax, double yMin, double yMax) {
            // add point
            if (rect.contains(p)) {
                acc.add(p);
            }

            RectHV[] halfRecs = halfRects(xMin, xMax, yMin, yMax);
            RectHV minorRect = halfRecs[0];
            RectHV majorRect = halfRecs[1];

            // prune
            if (left != null && rect.intersects(minorRect)) {
                left.range(acc, rect,
                        minorRect.xmin(), minorRect.xmax(),
                        minorRect.ymin(), minorRect.ymax());
            }
            if (right != null && rect.intersects(majorRect)) {
                right.range(acc, rect,
                        majorRect.xmin(), majorRect.xmax(),
                        majorRect.ymin(), majorRect.ymax());
            }
        }

        private RectHV[] halfRects(double xMin, double xMax,
                double yMin, double yMax) {
            if (p.x() < xMin || p.x() > xMax
                    || p.y() < yMin || p.y() > yMax) {
                throw new IllegalArgumentException("Bad data");
            }
            if (isXdim()) {
                return new RectHV[] {
                        new RectHV(xMin,  yMin, p.x(), yMax),
                        new RectHV(p.x(), yMin, xMax,  yMax)};
            } else {
                return new RectHV[] {
                        new RectHV(xMin, yMin,  xMax, p.y()),
                        new RectHV(xMin, p.y(), xMax, yMax)};
            }
        }

        public Point2D nearest(Point2D closest, Point2D queryP,
                double xMin, double xMax, double yMin, double yMax) {
            Point2D closestSoFar = closest;
            if (closestSoFar == null
                    || p.distanceSquaredTo(queryP)
                        < closestSoFar.distanceSquaredTo(queryP)) {
                closestSoFar = p;
            }

            RectHV[] halfRecs = halfRects(xMin, xMax, yMin, yMax);
            RectHV minorRect = halfRecs[0];
            RectHV majorRect = halfRecs[1];

            if (minorRect.contains(queryP)) {
                closestSoFar = exploreLeft(minorRect, closestSoFar, queryP);
                closestSoFar = exploreRight(majorRect, closestSoFar, queryP);
            } else {
                closestSoFar = exploreRight(majorRect, closestSoFar, queryP);
                closestSoFar = exploreLeft(minorRect, closestSoFar, queryP);
            }
            return closestSoFar;
        }

        private Point2D exploreLeft(RectHV minorRect,
                Point2D closestSoFar, Point2D queryP) {
            if (left != null && minorRect.distanceSquaredTo(queryP)
                    < closestSoFar.distanceSquaredTo(queryP)) {
                return left.nearest(closestSoFar, queryP,
                        minorRect.xmin(), minorRect.xmax(),
                        minorRect.ymin(), minorRect.ymax());
            }
            return closestSoFar;
        }

        private Point2D exploreRight(RectHV majorRect,
                Point2D closestSoFar, Point2D queryP) {
            if (right != null && majorRect.distanceSquaredTo(queryP)
                    < closestSoFar.distanceSquaredTo(queryP)) {
                return right.nearest(closestSoFar, queryP,
                        majorRect.xmin(), majorRect.xmax(),
                        majorRect.ymin(), majorRect.ymax());
            }
            return closestSoFar;
        }
    }

    private Node root;
    private int size = 0;

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        if (contains(p)) return;

        if (root == null) {
            root = Node.xNode(p);
        } else {
            root.insert(p);
        }
        size++;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        return root != null && root.contains(p);
    }

    public void draw() {
        if (root != null) {
            root.draw(0, 1.0, 0.0, 1.0);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("Null argument");
        Set<Point2D> points = new HashSet<Point2D>();
        if (root != null) {
            root.range(points, rect, 0, 1.0, 0, 1.0);
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        if (root != null) {
            return root.nearest(null, p, 0, 1.0, 0, 1.0);
        }
        return null;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.draw();
        StdDraw.show(0);

    }
}
