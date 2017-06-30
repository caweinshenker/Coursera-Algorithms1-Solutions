
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuffer;
import edu.princeton.cs.algs4.StdRandom;


public class Board {
    
    int [][] board;
    int hamming_dist;
    int manhattan_dist;
    int zeroX;
    int zeroY;
    
    public Board(int [][] blocks) {
        
        board = Arrays.copyOf(blocks, 0, blocks.length);
        compute_hamming();
        compute_manhattan();
        
    }
    
    public int dimension() {
        return board[0].length;
    }
    
    
    private void compute_hamming() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    continue;   
                }
                if (board[i][j] != i * dimension() + j) 
                    hamming_dist++;
            }
        }    
    }
        
        
    private void compute_manhattan() {
        int correctX;
        int correctY;
        int val;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                val = board[i][j];
                if (val == 0) continue;
                correctX = (val - 1) / dimension();
                correctY = (val - 1) % dimension();
                manhattan_dist += Math.abs(correctX - i) + Math.abs(correctY - j);
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
        return (manhattan_dist == 0 || hamming_dist == 0);
    }
    
    public Board twin() {
        if (dimension() < 2) return new Board(boardCopy);
        int x1 = StdRandom.uniform(dimension())
        int y1 = StdRandom.uniform(dimension());
        int x2 = StdRandom.uniform(dimension())
        int y2 = StdRandom.uniform(dimension());
        while ((x1 == x2 && y1 == y2) || board[x1][y1] != 0 || board[x2][y2] != 0)
        {
             int x1 = StdRandom.uniform(dimension())
             int y1 = StdRandom.uniform(dimension());
             int x2 = StdRandom.uniform(dimension());
             int y2 = StdRandom.uniform(dimension());
        }
        return swap(x1, x2, y1, y2);
    }
    
    private Board swap(x1, x2, y1, y2) {
        int dim = dimension()
        if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0
           || x1 >= dim || x2 >= dim || y1 >= dim 
           || y2 >= dim){
            throw new java.lang.IllegalArgumentException();
        }
        int [][] boardCopy = Arrays.copyOf(board, 0, board.length;);
        int tmp = boardCopy[x1][y1];
        boardCopy[x1][y1] = boardCopy[x2][y2];
        boardCopy[x2][y2] = tmp;
        return new Board(boardCopy);
    }
    
    public boolean equals(Object y) {
        if (!y.getClass() == Board.class) return false;
        return Arrays.equals(board, y.board);
   
    }
    
    public Iterable<Board> neighbors() {
        
        return new BoardIterator();
    }
    
    private class BoardIterator implements Iterator<Board>{
        
        private ArrayList<Board> neighbors = new ArrayList<>();
        int i = 0;
        
        public BoardIterator(){
            for (int i = -1; i <= 1; i += 2){
                try {
                    neighbors.append(swap(zeroX, zeroY, zeroX + i, zeroY));
                } 
                catch(java.lang.IllegalArgumentException) {          
                }
                try {
                     neighbors.append(swap(zeroX, zeroY, zeroX, zeroY + i));
                } 
                catch(java.lang.IllegalArgumentException) {          
                }
            }
        }
        
        public boolean hasNext() {
            return i < neighbors.size();
        }
        
        public Board next() {
            if (!hasNext()) throw new 
            
        }
    }
    
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(dimension[i][j]);
                if (i != dimension() - 1) sb.append(" ");
            }
            if (j != dimension() - 1) sb.append("\n");
        }
        return sb.toString();
    }
    
    
}