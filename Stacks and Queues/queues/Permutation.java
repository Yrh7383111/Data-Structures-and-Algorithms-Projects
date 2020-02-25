import edu.princeton.cs.algs4.StdIn;



public class Permutation
{
    public static void main(String[] args)
    {
        // Variables
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();


        // Operations
        while (!StdIn.isEmpty())
            randomizedQueue.enqueue(StdIn.readString());
        for (int i = 0; i < k; ++i)
            System.out.println(randomizedQueue.dequeue());
    }
}