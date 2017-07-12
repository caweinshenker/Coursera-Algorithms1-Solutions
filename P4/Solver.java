
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.*;
import java.lang.*;

public class Solver {
    
    
    private Node solutionNode;
    private int solvable;
    private int moves;
    private static final int INIT = 0;
    private static final int TWIN = 1;
    
    private class Node implements Comparable<Node> {
        
        private Node prev;
        private Board board;
        private int moves;
        
        public Node(Board board){
            this.board = board;
            this.prev = this;
            this.moves = 0;
        }
        
        public Node(Board board, Node prev) {
            this.board = board;
            this.prev = prev;
            this.moves = prev.moves + 1;
        }
        
        public int compareTo(Node n) {
            int manhattan_diff = board.manhattan() - n.board.manhattan();
            int moves_diff = moves - n.moves;
            return (manhattan_diff +  moves_diff);
        }
        
        public int cost() {
            int manhattan_diff = board.manhattan();
            int moves_diff =  moves;
            return (manhattan_diff + moves_diff);
            
        }
        
        public boolean isGoal(){
            return (board.isGoal());
            
        }
    }
    
    
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();
        solutionNode = null;
        aStar(initial);
    }
    
    
    private void aStar(Board initial) {
        MinPQ<Node> pq;
        MinPQ<Node> twinpq;
        MinPQ<Node> curpq;
        pq = new MinPQ<Node>();
        twinpq = new MinPQ<Node>();
        pq.insert(new Node(initial));
        twinpq.insert(new Node(initial.twin())); 
        curpq = pq;
        
        Node n = curpq.delMin();
        Node neighborNode;
        while (!n.isGoal()){
            int cost = n.board.manhattan() + n.moves;             
            for (Board neighbor : n.board.neighbors()) {
                if (neighbor.equals(n.prev.board) || neighbor.equals(n.prev.prev.board)) {
                    continue;
                }
                curpq.insert(new Node(neighbor, n));
            }
            //Switch priority queues
            if (curpq.equals(pq) && !(twinpq.isEmpty())) curpq = twinpq;
            else if (curpq.equals(twinpq) && !(curpq.isEmpty())) curpq = pq;
            n = curpq.delMin();
            
        }
           
        //Clean up by adding solutions
        solvable = curpq.equals(pq) ? INIT : TWIN;
        solutionNode = curpq.equals(pq) ? n : null;
        moves = curpq.equals(pq) ? solutionNode.moves : -1;
        //StdOut.println(moves());
        
    }
    
    public boolean isSolvable() {
        return (solvable == INIT);
    }
    
    
    public int moves() {
        return moves;
    }
    
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Node tmpNode = solutionNode;
        ArrayList<Board> nodes = new ArrayList<>();
        while (tmpNode != tmpNode.prev) {
            nodes.add(tmpNode.board);
            tmpNode = tmpNode.prev;
        }
        nodes.add(tmpNode.board);
        Collections.reverse(nodes);
        return nodes;
    }
    
}