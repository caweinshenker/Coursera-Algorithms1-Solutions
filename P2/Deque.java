

//import edu.princeton.cs.algs4;
import java.util.*;
import java.lang.*;
    
    
public class Deque<Item> implements Iterable<Item> {
    
    private class Node<Item>{
       Item item;
       Node<Item> next;
       Node<Item> prev;
       
    }
    
    private int size;
    private Node<Item> head;
    private Node<Item> tail;
    
    public Deque() {
        
        size = 0;   
        head = null;
        tail = null;
    }
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> newNode = new Node<Item>();
        newNode.item = item;
        newNode.next = head;
        newNode.prev = null;
        if (head != null) head.prev = newNode;
        if (tail == null) tail = newNode;
        head = newNode;
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> newNode = new Node<Item>();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = tail;
        if (tail != null) tail.next = newNode;
        if (head == null) head = newNode;
        tail = newNode;
        size++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node<Item> oldHead = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        else {
            head = null;
            tail = null;
        }
        size--;
        return oldHead.item;
    }
    
    
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node<Item> oldTail = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        else {
            head = null;
            tail = null;
        }
        size--;
        return oldTail.item;
    }
    

        
    private class DequeIterator implements Iterator<Item> {
        
        private Node<Item> current = head;
        
        public boolean hasNext() { 
            return (current != null) ;
        }
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public Iterator<Item> iterator() { return new DequeIterator(); }
    
    public static void main(String[] args) {
        
        Deque<Integer> dq = new Deque<Integer>();
        
        for (int i = 0; i < 10; i++) {
            dq.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            Integer ints = dq.removeFirst();
            System.out.println(ints);
        }
        
        for (int i = 0; i < 10; i++) {
            dq.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            Integer ints = dq.removeLast();
            System.out.println(ints);
        }
        
        for (int i = 0; i < 10; i++) {
            dq.addFirst(i);
        }
        
        
    }
       
}