

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF wquf;
    private int openSites;
    
    /**
     * Initializes an empty Percolation data structure with an nxn percolation 
     * grid
     * 
     * @param n the length of the percolation grid
     */
    public Percolation(int n) {
        grid = new int[n][n];
        wquf = new WeightedQuickUnionUF(n * n);
        openSites = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                grid[i][j] = 0;
            }
        }
    }
    
    
    /*
     * Return whether a given index pair is in bounds
     * 
     * @param  row the row index
     * @param  col the column index
     */
    private boolean isinBounds(int row, int col){
      int length = grid[0].length;
      return ( (1 <= row && row <= length) && (col >= 1 && col <= length));
    }
    
    
    /*
     * Check bounds
     * 
     * @param  row the row index to check
     * @param  col the column index to check
     * @throws IndexOutofBoundsException if not 1 < row <= n and 1 < col <= n
     */
    private void boundsCheck(int row, int col) {
        if (!isinBounds(row, col)) {
            throw new IndexOutOfBoundsException("Row " + row + " and column "
                                                + col + " is out of bounds"); 
        }
    }
    
    private void connect(int row1, int col1, int row2, int col2) {
        //Adjust the indices
        row1--; row2--; col1--; col2--;
        int n = grid[0].length;
        if (isinBounds(row2 + 1, col2 + 1)){
            wquf.union(row1 * n + col1, row2 * n + col2);
        }
    }
    
    
    /*
     * Open a site at the given index if it is not open already
     * 
     * @param  row the row index
     * @param  col the column index
     */
    public void open(int row, int col) {
        boundsCheck(row, col);
        if (!isOpen(row, col)) { 
            grid[row - 1][col - 1] = 1;
            openSites++;
            connect(row, col, row, col - 1);
            connect(row, col, row, col + 1);
            connect(row - 1, col, row, col);
            connect(row + 1, col, row, col);
        }
    }
    
    /*
     *Check whether a given site is open 
     * 
     * @param   row the row index to check
     * @param   col the column index to check
     * @return  whether the specified site is oepn
     */
    public boolean isOpen(int row, int col) {
        boundsCheck(row, col);
        return grid[row - 1][col - 1] == 1;
    }
    
    
    /*
     * Returns whether a site is full
     * (i.e., whether there is an open path to a parent in the top row)
     * 
     * @param  row the row index
     * @param  col the column index
     */
    public boolean isFull(int row, int col) {
        int n = grid[0].length;   
        boundsCheck(row, col);
        row--; col--;
        for (int i = 0; i < n; i++){
            if (wquf.connected(i, row * n + col)){
                return true;
            }
        }
        return false;
    }
        
    
    /*
     *Return the number of open sites 
     */
    public int numberofOpenSites() {
        return openSites;
    }
    
    /*
     * Return whether an open path exists from the bottom of the grid to the top
     */
    public boolean percolates(){
        int n = grid[0].length;
        for (int i = n * (n - 1); i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (wquf.connected(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
    
        
    
    
    
    