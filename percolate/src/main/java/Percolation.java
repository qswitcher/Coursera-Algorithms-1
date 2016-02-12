import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;

    private int BOTTOM;
    private WeightedQuickUnionUF filled;
    private int N;
    private boolean[] opened;
    private WeightedQuickUnionUF percolates;


    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be a positive number!");
        }

        this.N = N;
        this.BOTTOM = N*N + 1;

        this.opened = new boolean[N*N + 1];
        this.opened[TOP] = true;

        // Only have top row to determine if filled
        filled = new WeightedQuickUnionUF(N*N + 1);

        // add extra rows to account for the 'virtual' TOP and BOTTOM
        percolates = new WeightedQuickUnionUF(N*N + 2);

    }

    private int translate(int i, int j) {
        return 1 + N*(i - 1) + (j - 1);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) {
            int val = translate(i, j);
            this.opened[val] = true;

            // check neighbors and join them
            int left = translate(i, j - 1);
            int right = translate(i, j + 1);
            int up = translate(i - 1, j);
            int down = translate(i + 1, j);

            if (j > 1 && opened[left]) {
                // join left
                filled.union(val, left);
                percolates.union(val, left);
            }

            if (j < N && opened[right]) {
                // join right
                filled.union(val, right);
                percolates.union(val, right);
            }

            if (i == 1) {
                // join to TOP
                filled.union(val, TOP);
                percolates.union(val, TOP);
            } else if (opened[up]) {
                filled.union(val, up);
                percolates.union(val, up);
            }

            // if it's a bottom row, check if it percolated
            if (i == N) {
                // connect jth element of last row to bottom
                percolates.union(val, BOTTOM);
            }

            if (i < N && opened[down]) {
                filled.union(val, down);
                percolates.union(val, down);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return this.opened[translate(i, j)];
    }

    private void validate(int i, int j) {
        if (i > N || i <= 0) {
            throw new IndexOutOfBoundsException(
                    String.format("i is out of bounds %d", i));
        }
        if (j > N || j <= 0) {
            throw new IndexOutOfBoundsException(
                    String.format("j is out of bounds %d", j));
        }
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        return filled.connected(translate(i, j), TOP);
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates.connected(TOP, BOTTOM);
    }

    public static void main(String[] args) {

    }
}
