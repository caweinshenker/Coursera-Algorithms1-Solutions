
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.StringBuffer;
import edu.princeton.cs.algs4.StdRandom;


public class Board {
    
    private final static int SPACE = 0;
    private final int [][] board;
    private int hamming_dist;
    private int manhattan_dist;
    private int spaceX;
    private int spaceY;
    
    public Board(int [][] blocks) {
        board = copy_arr(blocks);
        compute_hamming();
        compute_manhattan();
        
    }
    
    public int dimension() {
        return board[0].length;
    }
    
    
    private int[][] copy_arr(int[][] in) {
        int [][] copy = new int[in.length][in.length];
        for (int i = 0; i < in.length; i++) {
            copy[i] = Arrays.copyOf(in[i], in.length);
        }
        return copy;
    }
    
    private int manhattan(int val, int row, int col) {
        int correctRow;
        int correctCol;
        if (val == 0) return 0;
        correctRow = (val - 1) / dimension();
        correctCol = (val - 1) % dimension();
        return  Math.abs(correctRow - row) + Math.abs(correctCol - col);
    }
    
    private void compute_hamming() {
        int val;
        hamming_dist = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                val = board[row][col];
                if (val != SPACE) {
                    hamming_dist += (val == (row * dimension() + col + 1)) ? 0 : 1;
                }
                else {
                   spaceX = row;
                   spaceY = col;
                }
            }    
        }
    }
        
    private void compute_manhattan() {
        int correctRow;
        int correctCol;
        int val;
        manhattan_dist = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                val = board[row][col];
                if (val == SPACE) continue;
                correctRow = (val - 1) / dimension();
                correctCol = (val - 1) % dimension();
                manhattan_dist +=  Math.abs(correctRow - row) + Math.abs(correctCol - col);
            }
        }
    }
            
    public int hamming() {
        return hamming_dist;
    }
    
    public int manhattan() {
        return manhattan_dist;
    }
    
    public boolean isGoal() {
        return (hamming_dist == 0);
    }
    
    public Board twin() {
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension() - 1; col++) {
                if (board[row][col] != SPACE && board[row][col+1] != SPACE) {
                    return(swap(row, col, row, col + 1));
                }
            }
        }
        return null;
    }
        
    
    private Board swap(int x1, int y1, int x2, int y2) {
        int [][] copy;
        int swap_val; 
        copy = copy_arr(board);
        swap_val = copy[x1][y1];
        copy[x1][y1] = copy[x2][y2];
        copy[x2][y2] = swap_val;
        return new Board(copy);
    }
    
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board yBoard = (Board) y;
        if (dimension() != yBoard.dimension()) return false;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (board[row][col] != yBoard.board[row][col]) return false;
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int dim = dimension();
       
        if (spaceX > 0) neighbors.add(swap(spaceX, spaceY, spaceX - 1, spaceY));
        if (spaceX < dim - 1) neighbors.add(swap(spaceX, spaceY, spaceX + 1, spaceY));
        if (spaceY > 0) neighbors.add(swap(spaceX, spaceY, spaceX, spaceY - 1));
        if (spaceY < dim - 1) neighbors.add(swap(spaceX, spaceY, spaceX, spaceY + 1));
        return neighbors;
    }
    
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
   
}