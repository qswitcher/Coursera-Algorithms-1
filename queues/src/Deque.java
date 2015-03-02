import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> oldHead = head;
        head = new Node<Item>();
        head.value = item;
        head.parent = oldHead;
        if (oldHead != null) oldHead.next = head;
        if (tail == null)            tail = head;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> oldTail = tail;
        tail = new Node<Item>();
        tail.value = item;
        tail.next = oldTail;
        if (oldTail != null) oldTail.parent = tail;
        if (head == null)              head = tail;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item value = head.value;
        Node<Item> newHead = head.parent;
        if (newHead == null) {
            head = null;
            tail = null;
        } else {
            newHead.next = null;
            head.parent = null;
            head = newHead;
        }
        size--;
        return value;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item value = tail.value;
        Node<Item> newTail = tail.next;
        if (newTail == null) {
            head = null;
            tail = null;
        } else {
            newTail.parent = null;
            tail.next = null;
            tail = newTail;
        }
        size--;
        return value;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(head);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("Actual=" + deque.isEmpty() + " expected=true");
        deque.addFirst(2);
        StdOut.println("Actual=" + deque.isEmpty() + " expected=false");

        deque.addFirst(3);
        // 2 3
        StdOut.println("Actual=" + deque.removeFirst() + " expected=3");
        // 2
        deque.addFirst(4);
        // 2 4
        StdOut.println("Actual=" + deque.removeLast() + " expected=2");
        // 4
        deque.addFirst(7);
        // 4 7
        deque.addLast(8);
        // 8 4 7
        deque.addLast(9);
        // 9 8 4 7
        StdOut.println("Actual=" + deque.removeFirst() + " expected=7");
        // 9 8 4
        StdOut.println("Actual=" + deque.removeLast() + " expected=9");
        // 8 4
        deque.addFirst(1);
        // 8 4 1
        deque.addLast(0);
        // 0 8 4 1
        deque.addFirst(1);
        // 0 8 4 1 1
        StdOut.println("Actual=" + deque.removeFirst() + " expected=1");
        // 0 8 4 1
        StdOut.println("Actual=" + deque.removeFirst() + " expected=1");
        // 0 8 4
        StdOut.println("Actual=" + deque.removeLast() + " expected=0");
        // 8 4
        StdOut.println("Actual=" + deque.removeLast() + " expected=8");
        // 4
        StdOut.println("Actual=" + deque.isEmpty() + " expected=false");
        StdOut.println("Actual=" + deque.removeFirst() + " expected=4");
        StdOut.println("Actual=" + deque.isEmpty() + " expectd=true");
        // -
        deque.addFirst(13);
        // 13
        deque.addLast(14);
        // 14 13
        deque.addFirst(12);
        // 14 13 12
        StdOut.println("Actual=" + deque.removeLast() + " expected=14");
        // 13 12
        deque.addFirst(15);
        // 13 12 15
        deque.addLast(13);
        // 13 13 12 15
        StdOut.println("Actual=" + deque.removeFirst() + " expected=15");
        StdOut.println("Actual=" + deque.removeLast() + " expected=13");
        StdOut.println("Actual=" + deque.removeLast() + " expected=13");
        StdOut.println("Actual=" + deque.removeFirst() + " expected=12");
        StdOut.println("Actual=" + deque.isEmpty() + " expected=true");

        boolean thrown = false;
        try {
            deque.removeFirst();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        StdOut.println("Actual=" + thrown + " expected=true");

        thrown = false;
        try {
            deque.removeLast();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        StdOut.println("Actual=" + thrown + " expected=true");


        // fill up Dequeue and iterate
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(2);
        deque.addLast(0);
        deque.addFirst(9);
        deque.addFirst(5);

        // expected
        // 5 9 2 1 3 4 0
        for (int value : deque) {
            StdOut.println("Value=" + value);
        }
    }

    private static class Node<Item> {
        private Item value;
        private Node<Item> parent;
        private Node<Item> next;
    }

    /**
     * Wrapper around the LinkedList iterator which doesn't support remove.
     * @param <Item>
     */
    private static class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> pointer;

        public DequeIterator(Node<Item> head) {
            this.pointer = head;
        }

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item value = pointer.value;
            pointer = pointer.parent;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
