import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats
{
    // Data Structure
    private double[] statistics;
    private int siteLenght;
    private int trials;


    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 0 || trials < 0)
        {
            throw new IllegalArgumentException("Please check the statement: n > 0, trials > 0");
        }
        // Else

        // Configure variables
        this.siteLenght = n;
        this.trials = trials;
        double totalSites = n * n;

        // Data structure initialization
        statistics = new double[trials];

        // Perform "trials" number of trials
        for (int i = 0; i < trials; i++)
        {
            // Initialize a "Percolation" object with "siteLength" = n
            Percolation percolation = new Percolation(n);

            // Iterate until percolates
            while (!percolation.percolates())
            {
                // Index variable
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, col))
                {
                    percolation.open(row, col);
                }
            }
            int numberOfOpenSites = percolation.numberOfOpenSites();
            statistics[i] = numberOfOpenSites / totalSites;
        }
    }

    // Sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(statistics);
    }

    // Sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(statistics);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        // Variables
        double mean = mean();
        double standardDeviation = stddev();

        // Operations
        return mean - ((1.96 * standardDeviation) / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi()
    {
        // Variables
        double mean = mean();
        double standardDeviation = stddev();

        // Operations
        return mean + ((1.96 * standardDeviation) / Math.sqrt(trials));
    }


    // Test client (see below)
    public static void main(String[] args)
    {
        // Variables
        int n = Integer.parseInt(args[0]);;
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);

        // Operations
        System.out.println("mean                            = " + percolationStats.mean());
        System.out.println("stddev                          = " + percolationStats.stddev());
        System.out.println("95%% confidence interval        = " + "[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]" );
    }
}
