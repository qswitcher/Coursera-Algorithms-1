import org.junit.Test;

import static org.junit.Assert.*;

public class KdTreeTest {

    @Test
    public void sizeTest() {
        KdTree tree = new KdTree();

        assertEquals(0, tree.size());

        tree.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));

        assertEquals(1, tree.size());

        for (int i = 2; i < 100000; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            tree.insert(new Point2D(x, y));
            assertEquals(i, tree.size());

            // don't insert duplicate
            tree.insert(new Point2D(x, y));
            assertEquals(i, tree.size());
        }
    }

    @Test
    public void containsTest2() {
        KdTree tree = new KdTree();

        for (int i = 0; i < 100000; i++) {
            Point2D point = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            assertFalse(tree.contains(point));

            tree.insert(point);

            assertTrue(tree.contains(point));
        }
        assertEquals(tree.size(), 100000);

        tree = new KdTree();
        for (int i = 0; i < 100000; i++) {
            Point2D point = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            assertFalse(tree.contains(point));

            assertEquals(tree.size(), i);

            tree.insert(point);

            assertEquals(tree.size(), i + 1);
            tree.insert(point);

            assertEquals(tree.size(), i + 1);


            assertTrue(tree.contains(point));
        }
        assertEquals(tree.size(), 100000);

        Point2D corner = new Point2D(1, 1);
        assertFalse(tree.contains(corner));
        tree.insert(corner);
        tree.insert(corner);
        assertEquals(tree.size(), 100001);
        assertTrue(tree.contains(corner));

        corner = new Point2D(0, 1);
        assertFalse(tree.contains(corner));
        tree.insert(corner);
        tree.insert(corner);
        assertEquals(tree.size(), 100002);
        assertTrue(tree.contains(corner));

        corner = new Point2D(1, 0);
        assertFalse(tree.contains(corner));
        tree.insert(corner);
        tree.insert(corner);
        assertEquals(tree.size(), 100003);
        assertTrue(tree.contains(corner));

        corner = new Point2D(0, 0);
        assertFalse(tree.contains(corner));
        tree.insert(corner);
        tree.insert(corner);
        assertEquals(tree.size(), 100004);
        assertTrue(tree.contains(corner));
    }

    @Test
    public void nonDistinct() {
        Point2D[] p = new Point2D[] {
                new Point2D(0.1, 0.2),
                new Point2D(0.4, 0.5),
                new Point2D(0.9, 0.1),
                new Point2D(0.1, 0.2),
                new Point2D(0.3, 0.7),
                new Point2D(0.7, 0.3),
                new Point2D(0.9, 0.1)};

        int[] sizeExpectedAfter = new int[] {
                1,
                2,
                3,
                3,
                4,
                5,
                5};
        boolean[] containsExpectedBefore = new boolean[] {
                false,
                false,
                false,
                true,
                false,
                false,
                true
        };

        KdTree kdTree = new KdTree();
        for (int i = 0; i < p.length; i++) {
            assertEquals(containsExpectedBefore[i], kdTree.contains(p[i]));
            kdTree.insert(p[i]);
            assertTrue(kdTree.contains(p[i]));
            assertEquals(sizeExpectedAfter[i], kdTree.size());
        }
    }

    @Test
    public void containsTest() {
        KdTree tree = new KdTree();

        assertEquals(0, tree.size());

        tree.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));

        assertEquals(1, tree.size());

        for (int i = 2; i < 10000; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();

            assertFalse(tree.contains(new Point2D(x, y)));

            tree.insert(new Point2D(x, y));

            assertTrue(tree.contains(new Point2D(x, y)));

            assertFalse(tree.contains(new Point2D(StdRandom.uniform(), StdRandom.uniform())));
        }
    }

    @Test
    public void insertLeftTest() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));

        assertEquals(1, tree.size());
        assertTrue(tree.contains(new Point2D(0.5, 0.5)));

        tree.insert(new Point2D(0.25, 0.5));
        assertEquals(2, tree.size());
        assertTrue(tree.contains(new Point2D(0.25, 0.5)));

        tree.insert(new Point2D(0.25, 0.5));
        assertEquals(2, tree.size());

        tree.insert(new Point2D(0.25, 0.75));
        assertEquals(3, tree.size());
        assertTrue(tree.contains(new Point2D(0.25, 0.75)));

        assertFalse(tree.contains(new Point2D(0.75, 0.5)));
        tree.insert(new Point2D(0.75, 0.5));
        assertEquals(4, tree.size());
        assertTrue(tree.contains(new Point2D(0.75, 0.5)));
    }
}
