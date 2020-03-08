import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;



public class PointSET
{
    // Variables
    private TreeSet<Point2D> set;



    // Operations
    // construct an empty set of points
    public PointSET()
    {
        this.set = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    // number of points in the set
    public int size()
    {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        // Else
        if (set.contains(p))
            return;
        else
            set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw()
    {
        for (Point2D point : set)
            point.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
            throw new IllegalArgumentException();
        // Else
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D point : set)
        {
            if (rect.contains(point))
                queue.enqueue(point);
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (set.isEmpty())
            return null;
        // Else
        // Retrieve the first point and its distance to p
        Point2D finalPoint = set.first();
        double finalDistance = finalPoint.distanceSquaredTo(p);

        for (Point2D point : set)
        {
            double distance = point.distanceSquaredTo(p);
            if (distance < finalDistance)
            {
                    finalPoint = point;
                    finalDistance = distance;
            }
        }
        return finalPoint;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args)
    {

    }
}