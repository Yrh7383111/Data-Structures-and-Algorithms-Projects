import java.util.ArrayList;
import java.util.Arrays;



public class FastCollinearPoints
{
    // Variable
    private LineSegment[] lineSegments;


    // Operations

    // Finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        // Corner case
        if (points == null)
            throw new  IllegalArgumentException();


        // Variables
        Point[] sortedPoints = points.clone();
        // Mergesort
        Arrays.sort(sortedPoints);


        // Corner cases
        if (checkPoints(sortedPoints) || checkDuplicates(sortedPoints))
            throw new  IllegalArgumentException();
        // Else
        final int length = points.length;
        final ArrayList<LineSegment> collinearLineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < length; i++)
        {
            int counter = 1;
            Point element = sortedPoints[i];                                        // Iterate through each element in the sortedPoints[]
            Point[] sortedSlope = sortedPoints.clone();
            Arrays.sort(sortedSlope, element.slopeOrder());                         // Points are sorted based on the slope with the "origin"

            while (counter < length)
            {
                // Variable
                final double slopeReference = element.slopeTo(sortedSlope[counter]);
                ArrayList<Point> pointsContainer = new ArrayList<Point>();

                // Operations
                pointsContainer.add(sortedSlope[counter]);
                counter++;

                while (counter < length && element.slopeTo(sortedSlope[counter]) == slopeReference)
                    pointsContainer.add(sortedSlope[counter++]);


                if (pointsContainer.size() >= 3 && element.compareTo(pointsContainer.get(0)) < 0)
                {
                    Point firstPoint = element;
                    Point lastPoint = pointsContainer.get(pointsContainer.size() - 1);
                    collinearLineSegments.add(new LineSegment(firstPoint, lastPoint));
                }
            }
        }
        lineSegments = collinearLineSegments.toArray(new LineSegment[collinearLineSegments.size()]);
    }

    // Number of line segments
    public int numberOfSegments()
    {
        return lineSegments.length;
    }

    // Line segments
    public LineSegment[] segments()
    {
        return lineSegments.clone();
    }

    private boolean checkPoints(Point[] points)
    {
        for (Point point : points)
        {
            if (point == null)
                return true;
        }
        return false;
    }

    private boolean checkDuplicates(Point[] points)
    {
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i + 1; j < points.length; j++)
            {
                if (points[i].compareTo(points[j]) == 0)
                    return true;
            }
        }
        return false;
    }
}