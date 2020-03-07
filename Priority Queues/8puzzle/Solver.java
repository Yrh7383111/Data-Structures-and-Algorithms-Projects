import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;



public class Solver
{
    // Variables
    private MinPQ<searchNode> PQ;
    private MinPQ<searchNode> twinPQ;
    private Stack<Board> stack = new Stack<Board>();
    private boolean isSolvable = true;

    // A search node of the game is a board, the number of moves made to reach the board, and the previous search node
    private class searchNode
    {
        // Variables
        private Board board;
        private int moves;
        private searchNode parentNode;


        // Constructor
        public searchNode (Board board, int moves, searchNode parentNode)
        {
            this.board = board;
            this.moves = moves;
            this.parentNode = parentNode;
        }
    }

    // Compare manhattan distance
    private class Priority implements Comparator<searchNode>
    {
        public int compare(searchNode first, searchNode second)
        {
            // Variables
            int firstPureManhattanDistance = first.board.manhattan();
            int secondPureManhattanDistance = second.board.manhattan();
            int firstTotalManhattanDistance = firstPureManhattanDistance + first.moves;
            int secondTotalManhattanDistance = secondPureManhattanDistance + second.moves;


            // Operations
            if (firstTotalManhattanDistance == secondTotalManhattanDistance)
            {
                return Integer.compare(firstPureManhattanDistance, secondPureManhattanDistance);
            }
            else if (firstTotalManhattanDistance > secondTotalManhattanDistance)
                return 1;
            else
                return -1;
        }
    }


    // Operations

    // Find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (initial == null)
            throw new NullPointerException();
        // Else
        // Variable configurations
        searchNode node = new searchNode(initial, 0, null);
        searchNode twinNode = new searchNode(initial.twin(), 0, null);
        PQ = new MinPQ<searchNode>(new Priority());                                                 // One of the default PQ constructor
        twinPQ = new MinPQ<searchNode>(new Priority());                                             // One of the default PQ constructor

        PQ.insert(node);
        twinPQ.insert(twinNode);


        // Operations
        while (!PQ.isEmpty())
        {
            // Check if the initial twin Priority Queue is the answer
            twinNode = twinPQ.delMin();
            if (twinNode.board.isGoal())
            {
                isSolvable = false;
                return;
            }

            // Check if the initial Priority Queue is the answer
            node = PQ.delMin();
            if (node.board.isGoal())
            {
                aggregateSolution(node);
                return;
            }
            else {
                algorithm(PQ, node);
                algorithm(twinPQ, twinNode);
            }
        }
    }

    // Is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return isSolvable;
    }

    // Min number of moves to solve initial board
    public int moves()
    {
        // Check if the board is solvable
        if (isSolvable)
            return (stack.size() - 1);
        else
            return -1;
    }

    // Sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
        // Check if the board is solvable
        if (isSolvable())
            return stack;
        else
            return null;
    }


    // Helper functions
    private void aggregateSolution(searchNode node)
    {
        while (node != null)
        {
            stack.push(node.board);
            node = node.parentNode;
        }
    }

    // A* algorithm
    private void algorithm(MinPQ<searchNode> PQ, searchNode node)
    {
        // Variables
        searchNode child;

        // The critical optimization.
        // A* search has one annoying feature: search nodes corresponding to the same board are enqueued on the priority queue many times
        // (e.g., the bottom-left search node in the game-tree diagram above).
        // To reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node,
        // don’t enqueue a neighbor if its board is the same as the board of the previous search node in the game tree.
        boolean criticalOptimization = true;


        // Operations
        for (Board board : node.board.neighbors())
        {
            if (criticalOptimization)
            {
                if (node.parentNode != null && board.equals(node.parentNode.board))
                {
                    criticalOptimization = false;
                }
                else {
                    child = new searchNode(board, node.moves + 1, node);
                    PQ.insert(child);
                }
            }
            else {
                child = new searchNode(board, node.moves + 1, node);
                PQ.insert(child);
            }
        }
    }



    // Test client (see below)
    public static void main(String[] args)
    {
        // Variables

        // Create initial board from file
        int[][] matrix = new int[][]
        {
                {1, 6, 2, 4},
                {5, 0, 3, 8},
                {9, 10, 7, 11},
                {13, 14, 15, 12}
        };

        Board initial = new Board(matrix);

        // Solve the puzzle
        Solver solver = new Solver(initial);


        // Operations

        // Print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}