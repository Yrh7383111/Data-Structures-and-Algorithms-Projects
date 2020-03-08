import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;



public class KdTree
{
    // Variables
    private Node root;

    private class Node
    {
        // Variables
        private Point2D point;
        private RectHV rect;
        private boolean isVertical;
        private int size;
        private Node left, right;


        // Operations
        public Node(Point2D point, RectHV rect, int size, boolean isVertical)
        {
            this.point = point;
            this.rect = rect;
            this.size = size;
            this.isVertical = isVertical;
        }
    }


    // Operations
    public KdTree(){}

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public int size()
    {
        return size(root);
    }

    // Helper functions
    private int size(Node node)
    {
        if (node == null)
            return 0;
        else
            return node.size;
    }


    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        // Else
        if (root == null)
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), 1, true);
        else {
            insert(root, p);
        }
    }

    // Helper functions
    private void insert(Node node, Point2D p)
    {
        // Compare x coordinate
        if (node.isVertical)
        {
            int cmp = Double.compare(p.x(), node.point.x());

            // p.x() < node.point.x()
            if (cmp == -1)
            {
                // Left is not null
                if (node.left != null)
                {
                    insert(node.left, p);
                }
                // Left is null
                else {
                    RectHV parent = node.rect;
                    double xmin = parent.xmin();
                    double ymin = parent.ymin();
                    double xmax = node.point.x();
                    double ymax = parent.ymax();
                    node.left = new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1, false);
                }
            }
            // p.x() > node.point.x()
            else if (cmp == 1)
            {
                // Left is not null
                if (node.right != null)
                {
                    insert(node.right, p);
                }
                // Left is null
                else {
                    RectHV parent = node.rect;
                    double xmin = node.point.x();
                    double ymin = parent.ymin();
                    double xmax = parent.xmax();
                    double ymax = parent.ymax();
                    node.right = new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1, false);
                }
            }
            // p.x() = node.point.x()
            else {
                // We start comparing y
                int cmp2 = Double.compare(p.y(), node.point.y());

                // p.y() < node.point.y()
                if (cmp2 == -1)
                {
                    // Left is not null
                    if (node.left != null)
                    {
                        insert(node.left, p);
                    }
                    // Left is null
                    else {
                        node.left = new Node(p, node.rect, 1, false);
                    }
                }
                // p.y() > node.point.y()
                else if (cmp2 == 1)
                {
                    // Left is not null
                    if (node.right != null)
                    {
                        insert(node.right, p);
                    }
                    // Left is null
                    else {
                        node.right = new Node(p, node.rect, 1, false);
                    }
                }
            }
        }
        // Compare y coordinate
        else {
            int cmp = Double.compare(p.y(), node.point.y());

            // p.y() < node.point.y()
            if (cmp == -1)
            {
                // Left is not null
                if (node.left != null)
                {
                    insert(node.left, p);
                }
                // Left is null
                else {
                    RectHV parent = node.rect;
                    double xmin = parent.xmin();
                    double ymin = parent.ymin();
                    double xmax = parent.xmax();
                    double ymax = node.point.y();
                    node.left = new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1, true);
                }
            }
            // p.y() > node.point.y()
            else if (cmp == 1)
            {
                // Left is not null
                if (node.right != null)
                {
                    insert(node.right, p);
                }
                // Left is null
                else {
                    RectHV parent = node.rect;
                    double xmin = parent.xmin();
                    double ymin = node.point.y();
                    double xmax = parent.xmax();
                    double ymax = parent.ymax();
                    node.right = new Node(p, new RectHV(xmin, ymin, xmax, ymax), 1, true);
                }
            }
            // p.y() = node.point.y()
            else {
                // We start comparing x
                int cmp2 = Double.compare(p.x(), node.point.x());

                // p.y() < node.point.y()
                if (cmp2 == -1)
                {
                    // Left is not null
                    if (node.left != null)
                    {
                        insert(node.left, p);
                    }
                    // Left is null
                    else {
                        node.left = new Node(p, node.rect, 1, true);
                    }
                }
                // p.y() > node.point.y()
                else if (cmp2 == 1)
                {
                    // Left is not null
                    if (node.right != null)
                    {
                        insert(node.right, p);
                    }
                    // Left is null
                    else {
                        node.right = new Node(p, node.rect, 1, true);
                    }
                }
            }
        }
        node.size = 1 + size(node.left) + size(node.right);
    }


    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        // Else
        return contains(root, p);
    }

    // Helper functions
    private boolean contains(Node node, Point2D p)
    {
        if (node == null)
            return false;
        if (node.point.equals(p))
            return true;
            // Else
        else {
            // Vertical
            if (node.isVertical)
            {
                int cmp = Double.compare(p.x(), node.point.x());

                // p.x() < node.point.x()
                if (cmp == -1)
                    return contains(node.left, p);
                    // p.x() > node.point.x()
                else if (cmp == 1)
                    return contains(node.right, p);
                    // p.x() = node.point.x()
                else {
                    int cmp2 = Double.compare(p.y(), node.point.y());

                    // p.y() < node.point.y()
                    if (cmp2 == -1)
                        return contains(node.left, p);
                        // p.y() > node.point.y()
                    else if (cmp2 == 1)
                        return contains(node.right, p);
                        // p.y() > node.point.y()
                    else {
                        return true;
                    }
                }
            }
            // Horizontal
            else {
                int cmp = Double.compare(p.y(), node.point.y());

                // p.y() < node.point.y()
                if (cmp == -1)
                    return contains(node.left, p);
                    // p.y() > node.point.y()
                else if (cmp == 1)
                    return contains(node.right, p);
                    // p.y() = node.point.y()
                else {
                    int cmp2 = Double.compare(p.x(), node.point.x());

                    // p.x() < node.point.x()
                    if (cmp2 == -1)
                        return contains(node.left, p);
                        // p.x() > node.point.x()
                    else if (cmp2 == 1)
                        return contains(node.right, p);
                        // p.x() > node.point.x()
                    else {
                        return true;
                    }
                }
            }
        }
    }


    public void draw()
    {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(root);
    }

    // Helper functions
    private void draw(Node node)
    {
        if (node == null)
            return;
        // Else
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();

        if (node.isVertical)
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            Point2D start = new Point2D(node.point.x(), node.rect.ymin());
            Point2D end = new Point2D(node.point.x(), node.rect.ymax());
            start.drawTo(end);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            Point2D start = new Point2D(node.rect.xmin(), node.point.y());
            Point2D end = new Point2D(node.rect.xmax(), node.point.y());
            start.drawTo(end);
        }

        // Recursively draw all the points
        draw(node.left);
        draw(node.right);
    }


    public Iterable<Point2D> range(RectHV rect)
    {
       if (rect == null)
           throw new IllegalArgumentException();
       // Else
        if (root == null)
            return new Queue<Point2D>();
        else  {
            Queue<Point2D> queue = new Queue<Point2D>();
            range(root, rect, queue);
            return queue;
        }
    }
    // Helper functions
    private void range(Node node, RectHV rect, Queue<Point2D> queue)
    {
        if (node.rect.intersects(rect))
        {
             if (rect.contains(node.point))
                 queue.enqueue(node.point);
             if (node.left != null)
                 range(node.left, rect, queue);
             if (node.right != null)
                 range(node.right, rect, queue);
        }
    }


    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;
        return nearest(root, p, root.point);
    }

    // Helper functions
    private Point2D nearest(Node node, Point2D p, Point2D finalPoint)
    {
        if(node.point.equals(p))
            return node.point;
        // Else
        double minDistance = finalPoint.distanceTo(p);

        if (Double.compare(node.rect.distanceTo(p), minDistance) >= 0)
            return finalPoint;
        else {
            double distance = node.point.distanceTo(p);

            if (Double.compare(node.point.distanceTo(p), minDistance) == -1)
            {
                finalPoint = node.point;
                minDistance = distance;
            }
            if (node.left != null)
                finalPoint = nearest(node.left, p, finalPoint);
            if (node.right != null)
                finalPoint = nearest(node.right, p, finalPoint);
        }
        return finalPoint;
    }
}