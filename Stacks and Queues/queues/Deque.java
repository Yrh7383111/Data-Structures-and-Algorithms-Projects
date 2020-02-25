import java.util.Iterator;
import java.util.NoSuchElementException;



// Deque implementation must support each deque operation (including construction) in constant worst-case time.
public class Deque<Item> implements Iterable<Item>
{
    // Dequeue variables
    private Node<Item> first;                                       // Beginning of queue
    private Node<Item> last;                                        // End of queue
    private int n;                                                  // Number of elements on deque


    // construct an empty deque
    public Deque()
    {
        first = null;
        last = null;
        n = 0;
    }

    // Doubly linked-list
    private static class Node<Item>
    {
        // Node variables
        private Item item;                                          // Data
        private Node<Item> next;                                    // Pointer to the next node
        private Node<Item> previous;                                // Pointer to the previous node

        // Node constructor
        public Node(Item item)
        {
            this.item = item;
            this.next = null;
            this.previous = null;
        }
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return n == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        // Else
        if (isEmpty())
        {
            first = new Node<Item>(item);
            last = first;
        }
        else {
            Node node = new Node<Item>(item);
            node.next = first;
            first.previous = node;
            first = node;
        }

        n++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        // Else
        if (isEmpty())
        {
            last = new Node<Item>(item);
            first = last;
        }
        else {
            Node node = new Node<Item>(item);
            node.previous = last;
            last.next = node;
            last = node;
        }

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        // Else
        Item item = first.item;
        first = first.next;

        if (first == null)
            last = null;                                            // To avoid loitering
        else {
            first.previous = null;
        }

        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        // Else
        Item item = last.item;
        last = last.previous;

        if (last == null)
            first = null;                                            // To avoid loitering
        else {
            last.next = null;
        }

        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new ListIterator(first);
    }

    // An iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item>
    {
        // Pointer to iterate through a linked-list based queue
        private Node<Item> current;


        // Constructor
        public ListIterator(Node<Item> first)
        {
            current = first;
        }

        // To check if the pointer reaches the end of linked-list based queue
        public boolean hasNext()
        {
            return current != null;
        }

        // Optional
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        // In normal order
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            // Else
            Item item = current.item;                               // Remember the data of the node
            current = current.next;                                 // Move the pointer to the next node
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> deque = new Deque<Integer>();


        for (int i = 0; i < 10; i++)
        {
            deque.addFirst(i);
            deque.addLast(i * 2);
        }
//        for (Integer element : deque)
//        {
//            System.out.print(element + " ");
//        }
//        Node current = deque.first;
//        do {
//            System.out.print(current.item + " ");
//            current = current.next;
//        }
//        while (current != null);

        while (!deque.isEmpty())
        {
            System.out.print(deque.removeFirst() + " ");
        }
    }
}