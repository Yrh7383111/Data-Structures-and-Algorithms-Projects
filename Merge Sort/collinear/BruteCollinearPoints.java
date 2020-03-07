import java.util.ArrayList;
import java.util.Arrays;



public class BruteCollinearPoints
{
    // Variable
    private LineSegment[] lineSegments;


    // Operations

    // Find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        // Corner case
        if (points == null)
            throw new  IllegalArgumentException();


        // Variables
        ArrayList<LineSegment> arrayList = new ArrayList<LineSegment>();
        Point[] sortedPoints = points.clone();

        // Mergesort
        Arrays.sort(sortedPoints);


        // Corner cases
        if (checkPoints(sortedPoints) || checkDuplicates(sortedPoints))
            throw new  IllegalArgumentException();
        // Else
        final int size = points.length;

        for (int p = 0; p < size - 3; p++)
        {
            for (int q = p + 1; q < size - 2; q++)
            {
                for (int r = q + 1; r < size - 1; r++)
                {
                    for (int s = r + 1; s < size; s++)
                    {
                        // Transitivity
                        if (sortedPoints[p].slopeTo(sortedPoints[q]) == sortedPoints[p].slopeTo(sortedPoints[r]) &&
                                sortedPoints[p].slopeTo(sortedPoints[r]) == sortedPoints[p].slopeTo(sortedPoints[s]))
                            arrayList.add(new LineSegment(points[p], points[s]));
                    }
                }
            }
        }
        lineSegments = arrayList.toArray(new LineSegment[arrayList.size()]);
    }

    // Number of line segments
    public int numberOfSegments()
    {
        return lineSegments.length;
    }

    // Line segments
    public LineSegment[] segments()
    {
        // Copy of the original array
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