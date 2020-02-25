import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation
{

    // Data Structure - (n * n) 2D array
    private boolean[][] sites;                                              // false - blocked | true - opened

    // Sizes for three trees
    private int siteLength;                                                 // Length of "sites" (square)
    private int oneExtraVirtualNodeSize;                                    // Size of the Quick Union object with one extra virtual node
    private int twoExtraVirtualNodesSize;                                   // Size of the Quick Union object with two extra virtual nodes
    private int openSiteNumber;                                             // To keep track of the number of opened sites

    // Two Quick Union objects with extra virtual nodes
    private WeightedQuickUnionUF quickUnionIsFull;                          // To check isFull() - one extra virtual node
    private WeightedQuickUnionUF quickUnionPercolates;                      // To check percolates() - two extra virtual node


    // To return the index position of a specific site
    private int indexPosition(int row, int col)
    {
        return (row - 1) * siteLength + col;                                // To return the index of a specific site (Range: 1 - n(2))
    }

    // Validate if parameters are valid or not
    private void validateParameters(int row, int col)
    {
        if (row < 1 || row > siteLength || col < 1 || col > siteLength)
        {
            throw new IllegalArgumentException("Please check the statement: 1 <= row <= N, 1 <= col <= N");
        }
    }

    // Constructor
    // Create n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException("Site numbers should be more than 0");
        }
        // Else

        // Configure three sizes
        openSiteNumber = 0;
        this.siteLength = n;
        oneExtraVirtualNodeSize = n * n + 1;
        twoExtraVirtualNodesSize = n * n + 2;

        // Distribute memory for "sites" and two Quick Union objects with extra virtual nodes
        sites = new boolean[n][n];
        quickUnionIsFull = new WeightedQuickUnionUF(oneExtraVirtualNodeSize);
        quickUnionPercolates = new WeightedQuickUnionUF(twoExtraVirtualNodesSize);

        // Initialize "sites" to be all blocked (false)
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                sites[i][j] = false;
            }
        }

        // Connect the top virtual node with all the sites in the first line, and same logic for the bottom node
        for (int i = 1; i <= n; i++)
        {
            quickUnionIsFull.union(0, i);
            quickUnionPercolates.union(0, i);
            quickUnionPercolates.union(twoExtraVirtualNodesSize - 1, twoExtraVirtualNodesSize - 1 - i);
        }

    }

    // Open the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        // Set the value in "sites" to be false
        sites[row - 1][col - 1] = true;
        openSiteNumber ++;

        // Check top, bottom, left, right nodes of the newly opened node

        int index = indexPosition(row, col);

        // Top
        if (row > 1 && isOpen(row - 1, col))
        {
            quickUnionIsFull.union(index, index - siteLength);
            quickUnionPercolates.union(index, index - siteLength);
        }

        // Bottom
        if (row < siteLength && isOpen(row + 1, col))
        {
            quickUnionIsFull.union(index, index + siteLength);
            quickUnionPercolates.union(index, index + siteLength);
        }

        // Left
        if (col > 1 && isOpen(row, col - 1))
        {
            quickUnionIsFull.union(index, index - 1);
            quickUnionPercolates.union(index, index - 1);
        }

        // Right
        if (col < siteLength && isOpen(row, col + 1))
        {
            quickUnionIsFull.union(index, index + 1);
            quickUnionPercolates.union(index, index + 1);
        }
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        // Validate parameters
        validateParameters(row, col);

        return sites[row - 1][col - 1];
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        // Validate parameters
        validateParameters(row, col);

        boolean connectionToTopNode = quickUnionIsFull.connected(0, indexPosition(row, col));

        // Need to be opened and connected to the top node
        if (isOpen(row, col) && connectionToTopNode)
        {
            return true;
        }
        else {
            return false;
        }

    }

    // Return the number of open sites
    public int numberOfOpenSites()
    {
        return openSiteNumber;
    }

    // Does the system percolate?
    public boolean percolates()
    {
        // Special case
        if (siteLength == 1)
        {
            return isOpen(1, 1);
        }
        // Else

        // Percolates iff virtual top site is connected to virtual bottom site
        return quickUnionPercolates.connected(0, twoExtraVirtualNodesSize - 1);
    }


    // Test client (optional)
    public static void main(String[] args)
    {

    }
}