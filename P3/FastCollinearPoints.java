
import java.util.*;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private HashMap<Double, List<Point>> knownSegments = new HashMap<>();
    private List<LineSegment> segments = new ArrayList<>();
    
    
    public FastCollinearPoints(Point[] points) {
       
        Point[] pointscp = Arrays.copyOf(points, points.length);
        double prevSlope;
        double curSlope;
        Point lo_endpt, hi_endpt;
        int lo, collinear_count;
        
        check_input(Arrays.copyOf(points, points.length));
        
        for (Point base : points) {
            
            Arrays.sort(pointscp, base.slopeOrder());
            collinear_count = 1;
            prevSlope = Double.NEGATIVE_INFINITY;
            
            for (int i = 1; i < pointscp.length; i++) { //Base is always first
                curSlope = base.slopeTo(pointscp[i]);
                if (curSlope == prevSlope) collinear_count++;
                else {
                    if (collinear_count >= 3){
                        
                       //Now that we've found a subarray of collinear points
                       // sort them to find the maximal line segment thru

                       /**
                       StdOut.println(" ");
                       StdOut.println("Base " + base);
                       StdOut.println("Slope " + prevSlope);                         
                       StdOut.println("Points sorted by slope: " + Arrays.toString(pointscp));
                       **/ 
                        
                       Arrays.sort(pointscp, i - collinear_count, i);
                       lo_endpt = pointscp[i - collinear_count];
                       hi_endpt = pointscp[i - 1];
                       
                       
                       /**
                       Point[] collinear_pts = Arrays.copyOfRange(pointscp, i - collinear_count, i);
                       StdOut.println("Sorted Collinear points: " + Arrays.toString(collinear_pts));
                       StdOut.println("Low endpoint: " + lo_endpt);
                       StdOut.println("High endpoint: " + hi_endpt);
                       StdOut.println(" ");
                       **/
                       
                       
                       
                       //Check whether the base point should be an endpoint
                       //Since it will always have slope -INF w/ itself
                       if (base.compareTo(lo_endpt) < 0) lo_endpt = base;
                       else if (base.compareTo(hi_endpt) > 0) hi_endpt = base;
                       
                       
                       //Add this line segment if it is not a subsegment of 
                       //another
                       addSegment(prevSlope, lo_endpt, hi_endpt); 
                       
                    }
                    collinear_count = 1;
                }
                prevSlope = curSlope;
            }
            
            //Check for collinearity at the end of the points
            if (collinear_count >= 3) {
 
                int i = pointscp.length;
                Arrays.sort(pointscp, i - collinear_count, i);
                lo_endpt = pointscp[i - collinear_count];
                hi_endpt = pointscp[i - 1];
                
                //Check whether the base point should be an endpoint
                //Since it will always have slope -INF w/ itself
                if (base.compareTo(lo_endpt) < 0) lo_endpt = base;
                else if (base.compareTo(hi_endpt) > 0) hi_endpt = base;
                
                
                //Add this line segment if it is not a subsegment of 
                //another
                addSegment(prevSlope, lo_endpt, hi_endpt); 
                
            }
            
        }
    }
    
    
    private void addSegment(double slope, Point lo_endpt, Point hi_endpt) {
        
        List<Point> endpts = knownSegments.get(slope);
        if (endpts == null) {
            segments.add(new LineSegment(lo_endpt, hi_endpt));
            endpts = new ArrayList<>();
            endpts.add(hi_endpt);
            knownSegments.put(slope, endpts);
        }
        else{
            for (Point endpt: endpts) {
                if (hi_endpt.compareTo(endpt) == 0) return;
            }
            endpts.add(hi_endpt);
            knownSegments.put(slope, endpts);
            segments.add(new LineSegment(lo_endpt, hi_endpt));
        }
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
        return segments.size();
        
    }
    
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
}