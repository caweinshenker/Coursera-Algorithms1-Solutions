
import edu.princeton.cs.algs4.StdOut;
import org.junit.rules.ExpectedException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class DequeTests {
    
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    Deque <Integer> d;
    ExpectedException thrown;
    
    @Before
    public void setUp() {
        
        d = new Deque<Integer>();
        thrown = ExpectedException.none();
        
    }
    
    @Test
    public void testaddFirst() {
        for (int i = 0; i < 10; i++){
            d.addFirst(i);
        }
        assertTrue("Deque size equals 10", d.size == 10);
    }
    
    @Test
    public void testaddLast() {
        for (int i = 0; i < 10; i++){
            d.addLast(i);
        }
        assertTrue("Deque size equals 10", d.size == 10);
    }
    
    @Test
    public void testRemoveFirst() {
        for (int i = 0; i < 10; i++){
            d.addFirst(i);
        }
        for (int i = 10; i < 0; i++){
            Integer removed = d.removeFirst();
            assertTrue("", i == removed);
            System.out.println("Done");
        }
    }
    
   
    @Test(expected=java.util.NoSuchElementException.class)
    public void testRemoveException() {
        d.removeFirst();
    }
    
    @Test(expected=java.lang.NullPointerException.class) 
    public void testAddFirstFail() {
        d.addFirst(null);
    }
    
    @Test(expected=java.lang.NullPointerException.class) 
    public void testAddLastFail() {
        d.addLast(null);
    }
    
    @Test
    public void testRemoveLast() {
        for (int i = 0; i < 10; i++){
            d.addLast(i);
        }
        for (int i = 10; i < 0; i++){
            Integer removed = d.removeLast();
            assertTrue("", i == removed);
            System.out.println("Done");
        }
    }
    
    @Test
    public void testisEmpty() {
        assertTrue("Deque is empty", d.isEmpty() == true);
        d.addFirst(1);
        assertTrue("Deque is not empty", d.isEmpty() != true); 
    }
    
    @Test
    public void testIterator() {
        for (int i = 0; i < 10; i++){
            d.addLast(i);
        }
        for (Integer i : d) {
            StdOut.println(i);
        }
          
    }
    
    
    
}
