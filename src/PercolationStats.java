import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] arrayThreshold;
    private  int numOfExp;
    private double factor = 1.96;
    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) { throw new IllegalArgumentException("All the inputs must larger than 0"); }
        numOfExp = trials;
        arrayThreshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                percolation.open(row, col);
            }

            arrayThreshold[i] = (double) (percolation.numberOfOpenSites())/(double) ((n * n));


        }

    }
    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(arrayThreshold);
    };
    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(arrayThreshold);

    };
    public double confidenceLo() { // low  endpoint of 95% confidence interval
        return mean()-(factor*stddev())/java.lang.Math.sqrt(numOfExp);
    };
    public double confidenceHi() { // high endpoint of 95% confidence interval
        return mean()+(factor*stddev())/java.lang.Math.sqrt(numOfExp);
    };

    public static void main(String[] args) { // test client (described below)

    }
}