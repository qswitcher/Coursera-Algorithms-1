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

        private Node(boolean dim, Point2D p, Node left, Node right) {
            this.dim = dim;
            this.p = p;
            this.left = left;
            this.right = right;
        }

        public static Node node(Point2D p, Node parent, Node left, Node right) {
            return new Node(!parent.dim, p, left, right);
        }
        public static Node xNode(Point2D p, Node left, Node right) {
            return new Node(X_DIM, p, left, right);
        }

        public boolean isXdim() {
            return dim == X_DIM;
        }

        public boolean isYdim() {
            return dim == Y_DIM;
        }

        public Point2D getP() {
            return p;
        }

        public int insert(Point2D o) {
            if (isLeft(o)) {
                if (left == null) {
                    left = Node.node(o, this, null, null);
                    return 1;
                } else {
                    return left.insert(o);
                }
            } else {
                if (right == null) {
                    if (o.x() == p.x()) {
                        return 0;
                    }
                    right = Node.node(o, this, null, null);
                    return 1;
                } else {
                    return right.insert(o);
                }
            }
        }

        public boolean contains(Point2D o) {
            if (this.p.equals(o)) {
                return true;
            } else if (isLeft(o) && left != null) {
                return left.contains(o);
            } else if (right != null) {
                return right.contains(o);
            }
            return false;
        }

        private boolean isLeft(Point2D o) {
            return isXdim() && o.x() < p.x()
                    || isYdim() && o.y() < p.y();
        }

        public void draw() {
            if (isXdim()) {
                StdDraw.line(p.x(), 0, p.x(), 1.0);
            } else {
                StdDraw.line(0, p.y(), 1.0, p.y());
            }
            StdDraw.point(p.x(), p.y());
            if (left != null) {
                left.draw();
            }
            if (right != null) {
                right.draw();
            }
        }

        public void range(Set<Point2D> acc, RectHV rect) {
            // add point
            if (rect.contains(p)) {
                acc.add(p);
            }

            RectHV[] halfRecs = halfRects();
            RectHV minorRect = halfRecs[0];
            RectHV majorRect = halfRecs[1];

            // prune
            if (left != null && rect.intersects(minorRect)) {
                left.range(acc, rect);
            }
            if (right != null && rect.intersects(majorRect)) {
                right.range(acc, rect);
            }
        }

        private RectHV[] halfRects() {
            if (isXdim()) {
                return new RectHV[] {
                        new RectHV(0,     0, p.x(), 1.0),
                        new RectHV(p.x(), 0, 1.0,   1.0)};
            } else {
                return new RectHV[] {
                        new RectHV(0, 0,     1.0, p.y()),
                        new RectHV(0, p.y(), 1.0, 1.0)};
            }
        }

        public Point2D nearest(Point2D closest, Point2D queryP) {
            Point2D closestSoFar = closest;
            if (closestSoFar == null
                    || p.distanceSquaredTo(queryP)
                        < closestSoFar.distanceSquaredTo(queryP)) {
                closestSoFar = p;
            }

            RectHV[] halfRecs = halfRects();
            RectHV minorRect = halfRecs[0];
            RectHV majorRect = halfRecs[1];

            if (left != null && minorRect.distanceSquaredTo(queryP)
                    < closestSoFar.distanceSquaredTo(queryP)) {
                closestSoFar = left.nearest(closestSoFar, queryP);
            }
            if (right != null && majorRect.distanceSquaredTo(queryP)
                    < closestSoFar.distanceSquaredTo(queryP)) {
                closestSoFar = right.nearest(closestSoFar, queryP);
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
        if (root == null) {
            root = Node.xNode(p, null, null);
            size++;
        } else {
            size += root.insert(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        return root != null && root.contains(p);
    }

    public void draw() {
        if (root != null) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("Null argument");
        Set<Point2D> points = new HashSet<Point2D>();
        if (root != null) {
            root.range(points, rect);
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("Null argument");
        if (root != null) {
            return root.nearest(null, p);
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
