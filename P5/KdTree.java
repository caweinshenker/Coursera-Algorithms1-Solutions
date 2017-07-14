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
        root = null;
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
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        //Ignore duplicate nodes
        else if (rt.p.equals(p)) return rt;
        else if (or == Node.XSPLIT) {
            or = Node.YSPLIT;
            if (p.x() < rt.p.x()) rt.lb = insert_helper(rt.lb, p, xmin, ymin, rt.p.x(), ymax, or);
            else if (p.x() >= rt.p.x()) rt.rt = insert_helper(rt.rt, p,  rt.p.x(), ymin, xmax, ymax, or); 
        }
        else if (or == Node.YSPLIT) {
             or = Node.XSPLIT;
             if (p.y() < rt.p.y()) rt.lb = insert_helper(rt.lb, p, xmin, ymin, xmax, rt.p.y(), or);
             else if (p.y() >= rt.p.y()) rt.rt = insert_helper(rt.rt, p, xmin, rt.p.y(), xmax, ymax, or);
        }
        return rt;
    }
    
    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        else root = insert_helper(root, p, 0.0, 0.0, 1.0, 1.0, Node.XSPLIT);

    }
    
    private Node get(Node rt, Point2D p, int orientation) {
        if (p == null) throw new IllegalArgumentException();
        if (rt == null) return null;
        if (rt.p.equals(p)) return rt;
        else if (orientation == Node.XSPLIT) {
            orientation = Node.YSPLIT;
            if (p.x() < rt.p.x()) return get(rt.lb, p, orientation);
            if (p.x() >= rt.p.x()) return get(rt.rt, p, orientation);
        }
        else if (orientation == Node.YSPLIT) {
            orientation = Node.XSPLIT;
            if (p.y() < rt.p.y()) return get(rt.lb, p, orientation);
            if (p.y() >= rt.p.y()) return get(rt.rt, p, orientation);
            return get(rt, p, orientation);
        }
        return rt;   
     }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p, Node.XSPLIT) != null;
       
    }
    
    private void range(Node rt,RectHV rect, ArrayList<Point2D> range) {
        if (rt == null) return;
        if (rect.intersects(rt.rect)) {
            if (rect.contains(rt.p)) range.add(rt.p);
            range(rt.lb, rect, range);
            range(rt.rt, rect, range);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> range = new ArrayList<>();
        range(root, rect, range);
        return range;
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
    
    private Point2D nearest(Node rt, Point2D p, Point2D nearest) {
        //StdOut.println(nearest.toString());
        if (rt != null) {
            double min_dist = nearest.distanceSquaredTo(p);
            double dist = rt.rect.distanceSquaredTo(p);
            if (min_dist >= dist) {
                if (rt.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                    nearest = rt.p;
                }
                //Check which side of the splitting line to check first
                if (rt.lb != null && rt.lb.rect.contains(p)) {
                    nearest = nearest(rt.lb, p, nearest);
                    nearest = nearest(rt.rt, p, nearest);
                }
                else {
                    nearest = nearest(rt.rt, p, nearest);
                    nearest = nearest(rt.lb, p, nearest);
                }
            }
        }
        return nearest;
    }
    
    public Point2D nearest(Point2D p) {
        if (size == 0) return null;
        return nearest(root, p, root.p);
        
    }
    
    private void draw(Node rt, int orientation) {     
            if (rt == null) return;
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            rt.p.draw();
            StdDraw.setPenRadius();
            if (orientation == Node.XSPLIT) {
               StdDraw.setPenColor(StdDraw.RED);
               StdDraw.line(rt.p.x(), rt.rect.ymin(), rt.p.x(), rt.rect.ymax());
            }
            else {
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(rt.rect.xmin(), rt.p.y(), rt.rect.xmax(), rt.p.y());
            }
            orientation = (orientation == Node.XSPLIT) ? Node.YSPLIT: Node.XSPLIT;
            draw(rt.lb, orientation);
            draw(rt.rt, orientation);
    }
    
    public void draw() {
        draw(root, Node.XSPLIT);
    }
    
   
    public static void main(String args[]) {
        
        
    }
    
    
}