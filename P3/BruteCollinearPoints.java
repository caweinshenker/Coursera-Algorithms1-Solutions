

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    
    private LineSegment[] segments;
    
    public BruteCollinearPoints(Point[] points) {
        int np; 
        Point p, q, r, s;
        double pq, qr, rs;
        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        Point[] pointscp;
     
        check_input(Arrays.copyOf(points, points.length));
        
        pointscp = Arrays.copyOf(points, points.length);
        Arrays.sort(pointscp);
        np = pointscp.length;
        
        
        for (int i = 0; i < np - 3; i++) {
            p = pointscp[i];
            for (int j = i + 1; j < np - 2; j++) {
                q = pointscp[j];
                pq = p.slopeTo(q);
                for (int k = j + 1; k < np - 1; k++) {
                    r = pointscp[k];
                    qr = q.slopeTo(r);
                    if (pq != qr) continue;
                    for (int l = k + 1; l < np; l++) {
                        s = pointscp[l];
                        rs = r.slopeTo(s);
                        if (qr != rs) continue;                     
                        foundSegments.add(new LineSegment(p, s));
                    }
                }
            }
        }
        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
    
    
    private void check_input(Point[] points) {
        if (points == null)  throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            
        }
        if (containsDuplicates(points)) throw new java.lang.IllegalArgumentException();
    }
    
    private boolean containsDuplicates(Point[] points) {
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                return true;
                
            }
        }
        return false;
    }
  
        
    public int numberOfSegments() {
        return segments.length;
    }
    
    
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
    
}