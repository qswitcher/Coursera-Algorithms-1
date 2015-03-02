import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private char[] blocks;
    private int N;

    private Board(int N, char[] flatBlocks) {
        this.blocks = Arrays.copyOf(flatBlocks, N*N);
        this.N = N;
    }

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException("Null argument!");
        }
        if (blocks.length < 2) {
            throw new IllegalArgumentException("Invalid length!");
        }
        if (blocks.length != blocks[0].length) {
            throw new IllegalArgumentException("No square!");
        }

        this.N = blocks.length;
        this.blocks = new char[N*N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i * N + j] = (char) blocks[i][j];
            }
        }
    }

    public int dimension() {
        return N;
    }

    private int goalNumber(int i, int j) {
        if (i == dimension() - 1 && j == dimension() - 1) {
            return 0;
        }
        return dimension()*i + j + 1;
    }

    private char value(int i, int j) {
        return value(N, blocks, i, j);
    }

    private static char value(int N, char[] blocks, int i, int j) {
        return blocks[i*N + j];
    }

    public int hamming() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (value(i, j) > 0 &&  value(i, j) != goalNumber(i, j)) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int adjusted = value(i, j) - 1;
                if (adjusted < 0) {
                    continue;
                }
                int iGoal = adjusted / dimension();
                int jGoal = adjusted % dimension();
                sum += Math.abs(i - iGoal) + Math.abs(j - jGoal);
            }
        }
        return sum;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        // copy the board
        char[] newBlocks = Arrays.copyOf(this.blocks, this.blocks.length);

        // assume N > 1
        int row = blankPosition(this.N, newBlocks)[0];

        // get next row (will be row without blank)
        int nextRow = (row + 1) % dimension();

        // swap first 2 elements
        swap(this.N, newBlocks, nextRow, 0, nextRow, 1);

        return new Board(this.N, newBlocks);
    }

    private static void swap(int N, char[] blocks, int i1, int j1, int i2, int j2) {
        char swap = blocks[i1*N + j1];
        blocks[i1*N + j1] = blocks[i2*N + j2];
        blocks[i2*N + j2] = swap;
    }

    private static int[] blankPosition(int N, char[] blocks) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (value(N, blocks, i, j) == 0) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object y) {
        if (y == null
                || this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (this.dimension() != other.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.value(i, j) != other.value(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int[] temp = blankPosition(this.N, this.blocks);
        int iBlank = temp[0];
        int jBlank = temp[1];

        List<Board> neighbors = new ArrayList<Board>();
        if (iBlank > 0) {
            // add 'above'
            char[] newBlocks = Arrays.copyOf(this.blocks, this.blocks.length);
            swap(this.N, newBlocks, iBlank, jBlank, iBlank - 1, jBlank);
            neighbors.add(new Board(this.N, newBlocks));
        }
        if (iBlank < dimension() - 1) {
            // add 'below'
            char[] newBlocks = Arrays.copyOf(this.blocks, this.blocks.length);
            swap(this.N, newBlocks, iBlank, jBlank, iBlank + 1, jBlank);
            neighbors.add(new Board(this.N, newBlocks));
        }
        if (jBlank > 0) {
            // add 'left'
            char[] newBlocks = Arrays.copyOf(this.blocks, this.blocks.length);
            swap(this.N, newBlocks, iBlank, jBlank, iBlank, jBlank - 1);
            neighbors.add(new Board(this.N, newBlocks));
        }
        if (jBlank < dimension() - 1) {
            char[] newBlocks = Arrays.copyOf(this.blocks, this.blocks.length);
            swap(this.N, newBlocks, iBlank, jBlank, iBlank, jBlank + 1);
            neighbors.add(new Board(this.N, newBlocks));
        }
        return neighbors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d%n", dimension()));
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension() - 1; j++) {
                sb.append(String.format("%2d ", (int) value(i, j)));
            }
            sb.append(String.format("%2d%n", (int) value(i, dimension() - 1)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}
