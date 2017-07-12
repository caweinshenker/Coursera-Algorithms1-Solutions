import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import java.lang.Float;
import java.util.ArrayList;
import java.lang.*;


public class KdTree {
    
    private static class Node {
        
        private static final int XSPLIT = 0;
        private static final int YSPLIT = 1;
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        
        public Node(Point2D p, RectHV rect){
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
        
    }
    
    private int size;
    private Node root;
    
    public KdTree() {
        root = null;
        size = 0;
        
    }
    
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public int size() {
        return size;
    }
    
    
    private Node insert_helper(Node root, Point2D p, RectHV rect, int orientation){
        if (root == null){
            root = new Node(p, rect);
        }
        if (orientation == Node.XSPLIT) {
            orientation = Node.YSPLIT;
            if (p.x() < root.p.x()) root.lb = insert_helper(
                                     root.lb,
                                     p, 
                                     new RectHV(rect.xmin(), root.rect.ymin(), root.rect.xmax(), root.rect.ymax()),
                                     orientation);
            if (p.x() > root.p.x()) root.rt = insert_helper(
                                     root.rt,
                                     p, 
                                     new RectHV(root.rect.xmin(), root.rect.ymin(), rect.xmax(), root.rect.ymax()),
                                     orientation);          
        }
        else if (orientation == Node.YSPLIT) {
             orientation = Node.XSPLIT;
             if (p.y() < root.p.y()) root.lb = insert_helper(
                                     root.lb, 
                                     p,
                                     new RectHV(root.rect.xmin(), rect.ymin(), root.rect.xmax(), root.rect.ymax()),
                                     orientation);
             if (p.y() > root.p.y()) root.rt = insert_helper(
                                     root.rt, 
                                     p,
                                     new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), rect.ymax()),
                                     orientation);
            
        }
        return root;
    }
    
    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        root = insert_helper(root, p, new RectHV(0, 0, 1, 1), Node.XSPLIT);
    }
    
    private Node get(Point2D p){
        return get(root, p, Node.XSPLIT);   
    }
    
    private Node get(Node root, Point2D p, int orientation) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        if (orientation == Node.XSPLIT) {
            orientation = Node.YSPLIT;
            if (p.x() < root.p.x()) return get(root.lb, p, orientation);
            if (p.x() > root.p.x()) return get(root.rt, p, orientation);
        }
        else if (orientation == Node.YSPLIT) {
            orientation = Node.XSPLIT;
            if (p.y() < root.p.y()) return get(root.lb, p, orientation);
            if (p.y() > root.p.y()) return get(root.rt, p, orientation);
        }
        return root;   
     }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
       
    }
    
    private void range(Node root,RectHV rect, ArrayList<Point2D> range) {
        if (root == null) return;
        if (root.rect.contains(root.p)) range.add(root.p);
        if (root.p.x() < rect.xmin() || root.p.y() < rect.ymin()) {
            range(root.lb, rect, range);
        }
        if (root.p.x() > rect.xmax() || root.p.y() > rect.ymax()) {
            range(root.rt, rect, range);
        }
    }
    
    private Iterable<Point2D> range(Node root, RectHV rect) {
        ArrayList<Point2D> range = new ArrayList<>();
        range(root, rect, range);
        return range;
        
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        return range(root, rect);
    }
    
    private void nodes(Node root, ArrayList<Node> nodes) {
        if (root == null) return;
        nodes.add(root);
        nodes(root.lb, nodes);
        nodes(root.rt, nodes);
    }
    
    private Iterable<Node> nodes(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes(root, nodes);
        return nodes;
        
    }
    
    public void draw() {
        for (Node n : this.nodes()) {
            n.p.draw();
            n.rect.draw();
            StdOut.println(n.p.toString());
            StdOut.println(n.rect.toString());
        }
    }
    
   
    public static void main(String args[]) {
        
        
    }
    
    
    
    
}