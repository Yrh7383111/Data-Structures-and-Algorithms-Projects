import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;



// Randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time.
public class RandomizedQueue<Item> implements Iterable<Item>
{
    // Dequeue variables
    private Item[] s;                                               // Array is between 25% and 100% full.
    private int n;                                                  // Size of the queue


    // construct an empty randomized queue
    public RandomizedQueue()
    {
        s = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return n;
    }

    // To resize the array
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++)
            copy[i] = s[i];

        s = copy;                                                   // Assign the new array to be the array of "ResizingArrayStackOfStrings" class
    }

    // Knuth Shuffling
    private void shuffle(Object[] a)
    {
        int n = s.length;

        for (int i = 0; i < n; i++)
        {
            // Choose index uniformly in [0, i]
            int r = (int) (Math.random() * (i + 1));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }

    private void swap(Item[] array, int first, int second)
    {
        if (first == second)
            return;
        // Else
        Item temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        // Else
        if (n == s.length)                                          // Double size of array if necessary and recopy to front of array
            resize(2 * s.length);
        s[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        // Else
        int index = StdRandom.uniform(n);
        Item item = s[index];
        s[index] = null;
        swap(s, index, n - 1);                              // Make sure array indexes with values are all in the front, and all the null indexes are at the back
        n--;

        if (n > 0 && n == s.length / 4)
            resize(s.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        // Else
        int index = StdRandom.uniform(n);
        return s[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }

    // An iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item>
    {
        // Variables
        private int i;
        private Item[] randomQueue;


        // Constructor
        public ArrayIterator()
        {
            i = 0;
            randomQueue = (Item[]) new Object[n];

            for (int i = 0; i < n; ++i)
                randomQueue[i] = s[i];

            shuffle(randomQueue);
        }


        // To check if i less than the size of the queue
        public boolean hasNext()
        {
            return i < n;                                           // n - size of the queue
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            // Else
            Item item = randomQueue[i];
            i++;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < 10; ++i)
            rq.enqueue(i);
        for (int i = 0; i < 3; i++)
            System.out.print(rq.dequeue() + " ");
    }
}
