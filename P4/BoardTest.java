

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.rules.ExpectedException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;


public class BoardTest {
    
    Board b;
    ArrayList<int[][]> boards;
    ExceptedException thrown;
    int choice;
    int [][] board_choice;
    int [][] b1 = { {0} };
    int [][] b2 = { {0, 1}, {2, 3} };
    int [][] b3 = { {1, 0}, {3, 2} };
    int [] hammings = {0, 4, 3};
    int [] mans = {0, 0, 3};
    int [] goals = {true, true , false};
    //int [][] b3 = { {1, 3, 5}, {6, 2, 0}, {7, 8, 4} };

    
    @Before
    public void setUp() {
       boards = new ArrayList<>();
       boards.append(b1);
       boards.append(b2);
       boards.append(b3);
       choice = StdRandom.uniform(3);
       board_choice = boards[choice];
       b = new Board(board_choice);
       thrown = ExpectedException.none();
    }
    
    @Test
    public void testHamming() {
        assertTrue("Hamming distance is correct", b.hamming() == hammings[choice]);
        
    }
    
    @Test
    public void testManhattan() {
        assertTrue("Manhatan distance is correct", b.manhattan() == mans[choice]);
    }
    
    @Test
    public void testIsGoal() {
        assertTrue("Board knows whether it is goal state", b.isGoal() == goals[choice]);
        
    }
    
    @Test
    public void testTwin() {
        assertTrue("Board is not equal to a twin", !(b.equals(b.twin())));  
    }
    
    @Test
    public void testString() {
        StdOut.println(board.toString());
        
    }
}