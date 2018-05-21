import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int size;
    private int upNode;
    private int downNode;
    private int numOfOpenSites;
    private boolean[] matrix;
    private WeightedQuickUnionUF weightedUnion1;
    private WeightedQuickUnionUF weightedUnion2;
    public Percolation(int n)   {  // create n-by-n grid, with all sites blocked
        if (n <= 0) { throw new IllegalArgumentException("row index i out of bounds"); }
        size = n;
        upNode = n*n;
        downNode = n*n+1;
        numOfOpenSites = 0;
        weightedUnion1 = new WeightedQuickUnionUF(n*n + 2); // create n*n+2 independent sites
        weightedUnion2 = new WeightedQuickUnionUF(n*n + 1); // create another for backwash
        matrix = new boolean[n*n];
        for (int i = 0; i < n*n; i++) { // initialize the matrix with all blocked
            matrix[i] = false;
        }
    }

    private int xyTo1D(int row, int col) { // Transform the 2D coordinates into 1D's form.
        return (row-1)*size + (col-1);
    }

    public    void open(int row, int col) { // open site (row, col) if it is not open already
        if (row <= 0 || row > size) { throw new IllegalArgumentException("row index i out of bounds"); }
        if (col <= 0 || col > size) { throw new IllegalArgumentException("col index i out of bounds"); }
        if (isOpen(row, col)) { return; }
        else {
            matrix[xyTo1D(row, col)] = true; // open this site
            numOfOpenSites++;
            if (row == 1) {
                weightedUnion1.union(xyTo1D(row, col), upNode);
                weightedUnion2.union(xyTo1D(row, col), upNode);
            }
            if (row == size) {
                weightedUnion1.union(xyTo1D(row, col), downNode);
            }
            if (row > 1) {
                if (isOpen(row-1, col)) {
                    weightedUnion1.union(xyTo1D(row, col), xyTo1D(row-1, col));
                    weightedUnion2.union(xyTo1D(row, col), xyTo1D(row-1, col));
                }
            }
            if (row < size) {
                if (isOpen(row+1, col)) {
                    weightedUnion1.union(xyTo1D(row, col), xyTo1D(row+1, col));
                    weightedUnion2.union(xyTo1D(row, col), xyTo1D(row+1, col));
                }
            }
            if (col > 1) {
                if (isOpen(row, col-1)) {
                    weightedUnion1.union(xyTo1D(row, col), xyTo1D(row, col-1));
                    weightedUnion2.union(xyTo1D(row, col), xyTo1D(row, col-1));
                }
            }
            if (col < size) {
                if (isOpen(row, col+1)) {
                    weightedUnion1.union(xyTo1D(row, col), xyTo1D(row, col+1));
                    weightedUnion2.union(xyTo1D(row, col), xyTo1D(row, col+1));
                }
            }
            return;
        }
    };

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (row <= 0 || row > size) { throw new IllegalArgumentException("row index i out of bounds"); }
        if (col <= 0 || col > size) { throw new IllegalArgumentException("col index i out of bounds"); }
        return matrix[xyTo1D(row, col)];
    };
    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > size) { throw new IllegalArgumentException("row index i out of bounds"); }
        if (col <= 0 || col > size) { throw new IllegalArgumentException("col index i out of bounds"); }
        return weightedUnion2.connected(xyTo1D(row, col), upNode);
    };
    public     int numberOfOpenSites() { // number of open sites
        return numOfOpenSites;
    };
    public boolean percolates() { // does the system percolate?
        return weightedUnion1.connected(upNode, downNode);
    };

    public static void main(String[] args) {
//        Percolation percolation = new Percolation(10);
//        percolation.open(10, 10);
//        percolation.open(1, 2);
//        System.out.print(percolation.weightedUnion1.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(1, 3))+"\n");
//        System.out.print(percolation.numberOfOpenSites()+"\n");
//        System.out.print(percolation.percolates());
    };  // test client (optional)
}


