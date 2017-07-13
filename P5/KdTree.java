import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Float;
import java.util.ArrayList;
import java.lang.*;
import java.util.*;


public class KdTree {
    
    private static class Node {
        
        private static final int XSPLIT = 0;
        private static final int YSPLIT = 1;
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int split;
        
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
        root = new Node(new Point2D(1, 1), new RectHV(0, 0, 1, 1));
        size = 0;
        
    }
    
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public int size() {
        return size;
    }
    
    
    private Node insert_helper(Node rt, Point2D p, double xmin, double ymin, double xmax, double ymax, int or) {
        if (rt == null) {
            if (size == 0) rt = new Node(p, new RectHV(xmin, ymin, p.x(), ymax));
            else if (size == 1) rt = new Node(p, new RectHV(xmin, p.y(), xmax, ymax));
            else rt = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            size++;
        }
        else if (or == Node.XSPLIT) {
            StdOut.println("X SPLIT");
            or = Node.YSPLIT;
            if (p.x() < rt.p.x()) rt.lb = insert_helper(rt.lb, p, xmin, ymin, rt.p.x(), ymax, or);
            if (p.x() > rt.p.x()) rt.rt = insert_helper(rt.rt, p, rt.p.x(), ymin, xmax, ymax, or);   
        }
        else if (or == Node.YSPLIT) {
            StdOut.println("Y SPLIT");
             or = Node.XSPLIT;
             if (p.y() < rt.p.y()) rt.lb = insert_helper(rt.lb, p, xmin, ymin, xmax, rt.p.y(), or);
             if (p.y() > rt.p.y()) rt.rt = insert_helper(rt.rt, p, xmin, rt.p.y(), xmax, ymax, or);
        }
        return rt;
    }
    
    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        root = insert_helper(root, p, 0.0, 0.0, 1.0, 1.0, Node.XSPLIT);

    }
    
    private Node get(Point2D p){
        return get(root, p, Node.XSPLIT);   
    }
    
    private Node get(Node rt, Point2D p, int orientation) {
        if (p == null) throw new IllegalArgumentException();
        if (rt == null) return null;
        if (orientation == Node.XSPLIT) {
            orientation = Node.YSPLIT;
            if (p.x() < rt.p.x()) return get(rt.lb, p, orientation);
            if (p.x() > rt.p.x()) return get(rt.rt, p, orientation);
        }
        else if (orientation == Node.YSPLIT) {
            orientation = Node.XSPLIT;
            if (p.y() < rt.p.y()) return get(rt.lb, p, orientation);
            if (p.y() > rt.p.y()) return get(rt.rt, p, orientation);
        }
        return rt;   
     }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
       
    }
    
    private void range(Node root,RectHV rect, ArrayList<Point2D> range) {
        if (root == null) return;
        if (rect.contains(root.p)) range.add(root.p);
        if (rect.intersects(root.rect)) {
            range(root.lb, rect, range);
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
    
    private Point2D nearest(Node root, Point2D p, double min_dist, int orientation) {
        Point2D candidate1;
        Point2D candidate2;
        double dist = root.rect.distanceSquaredTo(p);
        if (min_dist <= dist) return root.p;
        else {
            min_dist = dist;
            if (orientation == Node.XSPLIT) {
                if (p.x() < root.p.x()) {
                    orientation = Node.YSPLIT;
                    candidate1 = nearest(root.lb, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate1) < min_dist) return candidate1;
                    candidate2 = nearest(root.rt, p, min_dist, orientation);
                     if (root.rect.distanceSquaredTo(candidate2) < min_dist) return candidate2;
                }
                else if (p.x() > root.p.x()) {
                    candidate1 = nearest(root.rt, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate1) < min_dist) return candidate1;
                    candidate2 = nearest(root.lb, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate2) < min_dist) return candidate2;
                }
            }
            else {
                orientation = Node.XSPLIT;
                if (p.y() < root.p.y()) {
                    candidate1 = nearest(root.lb, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate1) < min_dist) return candidate1;
                    candidate2 = nearest(root.rt, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate2) < min_dist) return candidate2;
                }
                else if (p.y() > root.p.y()) {
                    candidate1 = nearest(root.rt, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate1) < min_dist) return candidate1;
                    candidate2 = nearest(root.lb, p, min_dist, orientation);
                    if (root.rect.distanceSquaredTo(candidate2) < min_dist) return candidate2;
                }
            }
        }
        return root.p;
    }
    
    public Point2D nearest(Point2D p) {
        return nearest(root, p, Double.MAX_VALUE, Node.XSPLIT);
        
    }
    
    public void draw() {
        int count = 0;
        ArrayList<Node> nodes = (ArrayList<Node>) nodes();
        for (int i = 1; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            if (count % 2 == 0) StdDraw.setPenColor(StdDraw.RED);
            else StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            n.rect.draw();
            StdOut.println(n.p.toString() + " " + n.rect.toString());
            count++;
        }
    }
    
   
    public static void main(String args[]) {
        
        
    }
    
    
    
    
}