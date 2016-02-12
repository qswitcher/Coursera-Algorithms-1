import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int capacity = 1;
    private Item[] list;

    public RandomizedQueue() {
        list = (Item[]) new Object[capacity];
    }

    private RandomizedQueue(int capacity) {
        list = (Item[]) new Object[capacity];
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (capacity == 0) {
            list = (Item[]) new Object[1];
            capacity = 1;
        } else if (size == capacity) {
            resize(2*capacity);
        }
        list[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int p = StdRandom.uniform(0, size);
        Item value = list[p];
        list[p] = list[size - 1];
        list[size - 1] = null;
        size--;
        if (size == capacity/2) {
            resize(capacity/2);
        }
        return value;
    }

    private void resize(int newCapacity) {
        capacity = newCapacity;
        Item[] oldList = list;
        list = (Item[]) new Object[newCapacity];
        System.arraycopy(oldList, 0, list, 0, size);
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        return list[StdRandom.uniform(0, size)];
    }

    /**
     * Returns an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new QueueItr<Item>(list, size);
    }

    private static class QueueItr<Item> implements Iterator<Item> {

        private RandomizedQueue<Item> queue;

        public QueueItr(Item[] list, int size) {
            queue = new RandomizedQueue<Item>(size);
            for (int i = 0; i < size; i++) {
                queue.enqueue(list[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            if (queue.isEmpty()) throw new NoSuchElementException();
            return queue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            StdOut.println("Sample=" + queue.sample());
        }
        StdOut.println("Actual=" + queue.isEmpty() + " expected=false");
        for (int i = 0; i < 10; i++) {
            StdOut.println("Pop=" + queue.dequeue());
        }
        StdOut.println("Actual=" + queue.isEmpty() + " expected=true");

        for (int i = 0; i < 20; i++) {
            queue.enqueue(10 + i);
        }
        for (int i = 0; i < 20; i++) {
            StdOut.println("Sample=" + queue.sample());
        }
        StdOut.println("Actual=" + queue.isEmpty() + " expected=false");
        for (int i = 0; i < 20; i++) {
            StdOut.println("Pop=" + queue.dequeue());
        }
        StdOut.println("Actual=" + queue.isEmpty() + " expected=true");

        boolean thrown = false;
        try {
            StdOut.println(queue.dequeue());
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        StdOut.println("Actual=" + thrown + " expected=true");

    }
}