import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;
        int count = 0;
        int total = 0;

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            total++;
            if (count < k) {
                queue.enqueue(value);
                count++;
            } else {
                int p = StdRandom.uniform(0, total);
                if (p < count) {
                    queue.dequeue();
                    queue.enqueue(value);
                }
            }
        }

        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}
