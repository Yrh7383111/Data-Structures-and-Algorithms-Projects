import edu.princeton.cs.algs4.Queue;



public class Board
{
    // Variables
    private int[][] board;
    private Queue<Board> queue;
    private int dimension;
    private int hammingDistance;
    private int manhattanDistance;


    // Operations

    // Create a board from an n-by-n array of tiles,
    // Where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        // Variables assignment
        this.dimension = tiles.length;
        board = new int[dimension][dimension];
        hammingDistance = 0;
        manhattanDistance = 0;

        // Variable initialization
        boolean bottomRightTile = false;
        boolean blankTile = false;

        // Operations
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                // Value assignment
                board[i][j] = tiles[i][j];

                // Hamming distance
                if (i == dimension - 1 && j == dimension - 1)                                   // Check if reach the bottom right tile
                    bottomRightTile = true;
                if (!bottomRightTile)
                {
                    if (board[i][j] != (i * dimension) + (j + 1))                               // Check if each tile is on the correct position
                        hammingDistance++;
                }

                // Manhattan distance
                blankTile = false;
                if (board[i][j] == 0)
                    blankTile = true;                                                           // Check if find the blank tile
                if (!blankTile)
                {
                    int verticalMove = (board[i][j] - 1) / dimension;
                    int horizontalMove = (board[i][j] - 1) % dimension;
                    manhattanDistance += Math.abs(verticalMove - i) + Math.abs(horizontalMove - j);
                }
            }
        }
    }

    // String representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");

        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // Board dimension n
    public int dimension()
    {
        return dimension;
    }

    // Number of tiles out of place
    public int hamming()
    {
        return hammingDistance;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        return manhattanDistance;
    }

    // Is this board the goal board?
    public boolean isGoal()
    {
        boolean bottomRightTile = false;

        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (i == dimension - 1 && j == dimension - 1)
                    bottomRightTile = true;
                if (!bottomRightTile && board[i][j] != (i * dimension) + (j + 1))
                    return false;
            }
        }
        return true;
    }

    // Does this board equal y?
    public boolean equals(Object y)
    {
        if (y instanceof Board)                                                         // Check if y(Object) is an instance of Board type
        {
            if (((Board) y).dimension != dimension)
                return false;
            // Else
            for (int i = 0; i < dimension; i++)
            {
                for (int j = 0; j < dimension; j++)
                {
                    if (board[i][j] != ((Board) y).board[i][j])
                        return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        // Variable
        queue = new Queue<Board>();
        int[][] newBoard = new int[dimension][dimension];
        copyOfBoard(newBoard);

        int row = 0;
        int column = 0;
        boolean blankTile = false;


        // Operations

        // Search for the blank tile
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (newBoard[i][j] == 0)
                {
                    row = i;
                    column = j;
                    blankTile = true;
                    break;                                                              // Break inner loop
                }
            }
            if (blankTile)
                break;
        }

        // Four scenarios

        // #1 - not the first row
        if (row != 0)
            exchBack(newBoard, row, column, row - 1, column);

        // #2 - not the last row
        if (row != (dimension - 1))
            exchBack(newBoard, row, column, row + 1, column);

        // #3 - not the most left column
        if (column != 0)
            exchBack(newBoard, row, column, row, column - 1);

        // #4 - not the most right column
        if (column != (dimension - 1))
            exchBack(newBoard, row, column, row, column + 1);

        return queue;
    }

    // A board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        // Variables
        int row = 0;
        int[][] newBoard = new int[dimension][dimension];
        copyOfBoard(newBoard);


        // Operations
        for (int i = 0; i < dimension; i++)
        {
            if (board[0][i] == 0)
            {
                row = 1;
                break;                                                                  // Jump out of the loop
            }
        }

        newBoard[row][0] = board[row][1];
        newBoard[row][1] = board[row][0];

        return new Board(newBoard);
    }


    // Helper functions
    private void exch(int[][] matrix, int row, int column, int newRow, int newColumn)
    {
        int temporary = matrix[row][column];
        matrix[row][column] = matrix[newRow][newColumn];
        matrix[newRow][newColumn] = temporary;
    }

    private void exchBack(int[][] matrix, int row, int column, int newRow, int newColumn)
    {
        exch(matrix, row, column, newRow, newColumn);
        queue.enqueue(new Board(matrix));
        exch(matrix, row, column, newRow, newColumn);
    }

    private void copyOfBoard(int[][] matrix)
    {
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
                matrix[i][j] = board[i][j];
        }
    }



    // Unit testing (not graded)
    public static void main(String[] args)
    {
        // Variables
        int[][] matrix = new int[][]
        {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        int[][] newMatrix = new int[][]
        {
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5}
        };
        Board board = new Board(matrix);
        Board newBoard = new Board(newMatrix);


        // Function testing

        // Test toString
        System.out.println(board.toString());

        // Test dimension
        System.out.println(board.dimension());
        System.out.println();

        // Test hamming
        System.out.println(board.hamming());
        System.out.println();

        // Test manhattan
        System.out.println(board.manhattan());
        System.out.println();

        // Test isGoal
        System.out.println(board.isGoal());
        System.out.println();

        // Test equals
        System.out.println(board.equals(newBoard));
        System.out.println();

        // Test twin
        System.out.println(board.twin().toString());

        // Test neighbors
        Iterable<Board> queue = board.neighbors();
        for(Board element: queue) {
            System.out.println(element.toString());
        }
    }
}