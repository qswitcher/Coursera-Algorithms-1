import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {


    private static class Node {
        private Board value;
        private Node previous;
        private int distance;
        private int moves;
        private boolean mirror = false;

        public Node(Board value, Node previous, boolean mirror) {
            this.value = value;
            this.previous = previous;
            this.distance = value.manhattan();
            if (previous != null) {
                this.moves = previous.getMoves() + 1;
            } else {
                this.moves = 0;
            }
            this.mirror = mirror;
        }


        public boolean isMirror() {
            return mirror;
        }

        public Board getValue() {
            return value;
        }

        public int getMoves() {
            return moves;
        }

        public Node getPrevious() {
            return previous;
        }

        public int getPriority() {
            return distance + moves;
        }
    }

    private static Comparator<Node> manhattanDistance = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            int value = o1.getPriority() - o2.getPriority();
            if (value == 0) return 0;
            if (value < 0) return -1;
            return 1;
        }
    };

    private boolean solvable = false;
    private Node searchNode;

    public Solver(Board initial) {
        MinPQ<Node> queue = new MinPQ<Node>(manhattanDistance);
        queue.insert(new Node(initial, null, false));
        queue.insert(new Node(initial.twin(), null, true));

        while (true) {
            searchNode = queue.delMin();
            if (searchNode.getValue().isGoal()) {
                solvable = !searchNode.isMirror();
                break;
            }

            for (Board next : searchNode.getValue().neighbors()) {
                Board previous = null;
                if (searchNode.getPrevious() != null) {
                    previous = searchNode.getPrevious().getValue();
                }

                if (!next.equals(previous)) {
                    queue.insert(new Node(next, searchNode, searchNode.isMirror()));
                }
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (solvable) {
            return searchNode.getMoves();
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;

        Stack<Board> s = new Stack<Board>();
        Node p = searchNode;
        while (p != null) {
            s.push(p.getValue());
            p = p.getPrevious();
        }
        return s;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
