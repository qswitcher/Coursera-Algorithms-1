import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class BoardTest {

    private int[][] blocks = new int[][]
            {{8, 1, 3},
             {4, 0, 2},
             {7, 6, 5}};
    private Board board = new Board(blocks);


    @Test
    public void dimension() {
        assertEquals(board.dimension(), 3);
    }

    @Test
    public void hamming() {
        assertEquals(board.hamming(), 5);
    }

    @Test
    public void manhattan() {
        assertEquals(board.manhattan(), 10);
    }

    @Test
    public void isGoal() {
        assertTrue(
                new Board(new int[][] {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 0}
                }).isGoal());

        assertFalse(
                new Board(new int[][] {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 0, 8}
                }).isGoal());
    }

    @Test
    public void neighborsMiddle() {
        List<Board> neighbors = new ArrayList<Board>();
        for (Board b : board.neighbors()) {
            neighbors.add(b);
        }

        assertEquals(neighbors.size(), 4);
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {0, 4, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 0, 3},
                {4, 1, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 6, 2},
                {7, 0, 5}
        })));
    }

    @Test
    public void neighborsLeft() {
        board = new Board(new int[][] {
                {8, 1, 3},
                {0, 4, 2},
                {7, 6, 5}
        });

        List<Board> neighbors = new ArrayList<Board>();
        for (Board b : board.neighbors()) {
            neighbors.add(b);
        }

        assertEquals(neighbors.size(), 3);
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {0, 1, 3},
                {8, 4, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {7, 4, 2},
                {0, 6, 5}
        })));
    }

    @Test
    public void neighborsRight() {
        board = new Board(new int[][] {
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5}
        });

        List<Board> neighbors = new ArrayList<Board>();
        for (Board b : board.neighbors()) {
            neighbors.add(b);
        }

        assertEquals(neighbors.size(), 3);
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 0},
                {4, 2, 3},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 2, 5},
                {7, 6, 0}
        })));
    }

    @Test
    public void neighborsTop() {
        board = new Board(new int[][] {
                {8, 0, 3},
                {4, 1, 2},
                {7, 6, 5}
        });

        List<Board> neighbors = new ArrayList<Board>();
        for (Board b : board.neighbors()) {
            neighbors.add(b);
        }

        assertEquals(neighbors.size(), 3);
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 3, 0},
                {4, 1, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {0, 8, 3},
                {4, 1, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        })));
    }

    @Test
    public void neighborsBottom() {
        board = new Board(new int[][] {
                {8, 1, 3},
                {4, 6, 2},
                {7, 0, 5}
        });

        List<Board> neighbors = new ArrayList<Board>();
        for (Board b : board.neighbors()) {
            neighbors.add(b);
        }

        assertEquals(neighbors.size(), 3);
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 6, 2},
                {0, 7, 5}
        })));
        assertThat(neighbors, hasItem(new Board(new int[][] {
                {8, 1, 3},
                {4, 6, 2},
                {7, 5, 0}
        })));
    }

    @Test
    public void twin1() {
        assertEquals(new Board(new int[][]
                {
                        {8, 1, 3},
                        {4, 0, 2},
                        {6, 7, 5}}),
                board.twin());
    }

    @Test
    public void twin2() {
        board = new Board(new int[][] {
                {8, 1, 3},
                {4, 6, 2},
                {7, 0, 5}
        });

        assertEquals(new Board(new int[][]
                        {
                                {1, 8, 3},
                                {4, 6, 2},
                                {7, 0, 5}}),
                board.twin());
    }

    @Test
    public void toStringTest() {
        assertEquals("3\n" +
                " 8  1  3\n" +
                " 4  0  2\n" +
                " 7  6  5\n", board.toString());
    }
}
