import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import java.lang.Float;
import java.util.ArrayList;
import java.lang.*;

public class PointSET {
    
    private SET<Point2D> points;
       
    public PointSET() {

        points = new SET<Point2D>();
    }
    
    
    public boolean isEmpty() {
        return (points.size() == 0);
    }
    
    public int size() {
        return points.size();
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        points.add(p);        
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return points.contains(p);
    }
    
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }
       
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        ArrayList<Point2D> range = new ArrayList<>();
        for (Point2D p: points) {
            if (rect.contains(p)){
                range.add(p);
            }
        }
        return range;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        double min_dist = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D other : points) {
            double dist = other.distanceTo(p);
            if (dist < min_dist && dist != 0){
                min_dist = dist;
                nearest = other;
            }
        }
        return nearest;   
    } 
}