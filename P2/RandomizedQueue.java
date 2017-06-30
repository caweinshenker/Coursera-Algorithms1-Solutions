import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import java.util.*;


public class RandomizedQueue<Item> implements Iterable<Item> {
    
        private Item[] rq;
        private int capacity;
        private int size;
        
        
        public RandomizedQueue() {
            capacity = 8;
            size = 0;
            rq = (Item[]) new Object[capacity];
        }
        
        public boolean isEmpty() {
            return (size == 0);
        }
        
        public int size() {
            return size;
        }
        
        private void resize(int newCapacity) {
          Item[] newRq = (Item[]) new Object[newCapacity];
          for (int i = 0; i < size; i++) {
              newRq[i] = rq[i];
          }
          capacity = newCapacity;
          rq = newRq;
        }
       
        public void enqueue(Item item) {
            if (item == null) throw new java.lang.NullPointerException();
            if (size == capacity) resize(capacity * 2);
            rq[size++] = item;
        }
        
        public Item dequeue() {
            if (isEmpty()) throw new java.util.NoSuchElementException();
            int random = StdRandom.uniform(0, size);
            Item item = rq[random];
            rq[random] = rq[--size];
            rq[size] = null;
            if (size <= capacity / 4) resize(capacity / 2);
            return item;
        }
        
        public Item sample() {
            if (isEmpty()) throw new java.util.NoSuchElementException();
            return rq[StdRandom.uniform(0, size)];
        }
        
        public Iterator<Item> iterator() {
            return new RandomizedQueueIterator();
        }
        
        private class RandomizedQueueIterator implements Iterator<Item> {
            
            private int current;
            private Item[] shuffled;
            
            public RandomizedQueueIterator() {
              current = 0;
              shuffled = (Item[]) new Object[size];
              for (int i = 0; i < size; i++) {
                 shuffled[i] = rq[i];
              }
              StdRandom.shuffle(shuffled);
            }
            
            
            public boolean hasNext() {return current < size;}
            public void remove() {throw new java.lang.UnsupportedOperationException();}
            public Item next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                Item curItem = shuffled[current++];
                return curItem;   
            }
        }
        
        public static void main(String[] args) {
             
            
        }

}