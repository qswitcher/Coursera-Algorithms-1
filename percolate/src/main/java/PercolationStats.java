import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double std;
    private double T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be a positive number!");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T must be a positive number!");
        }
        this.T = T;

        double[] xi = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                count++;
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                while (p.isOpen(i, j)) {
                    i = StdRandom.uniform(N) + 1;
                    j = StdRandom.uniform(N) + 1;
                }
                p.open(i, j);
            }
            xi[t] = ((double) count)/(N*N);
        }

        // compute mean
        mean = StdStats.mean(xi);

        // compute std
        std = StdStats.stddev(xi);

    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return std;
    }

    private double za() {
        return 1.96*stddev()/Math.sqrt(T);
    }

    public double confidenceLo() {
        return mean() - za();
    }

    public double confidenceHi() {
        return mean() + za();
    }

    public static void main(String[] args) {
        PercolationStats stats =
                new PercolationStats(
                        Integer.parseInt(args[0]),
                        Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = "
                + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}