//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.MinPQ;
//import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.StdOut;
//
//import java.util.Comparator;
//
//public class Solver {
//
//    private int moves;
//    private Stack<Board> solution = new Stack<>();
//
//    // find a solution to the initial board (using the A* algorithm)
//    public Solver(Board initial) {
//        if (initial == null) throw new IllegalArgumentException();
//
//        MinPQ<Node> pq = new MinPQ<>(comparator());
//        pq.insert(new Node(initial));
//        while (!pq.isEmpty()) {
//            Node searchNode = pq.delMin();
//            if (!searchNode.board.isGoal()) {
//                for (Board b : searchNode.board.neighbors()) {
//                    if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
//                        Node node = new Node(b, searchNode,
//                                             searchNode.moves + 1,
//                                             searchNode.moves + 1 + b.manhattan());
//                        pq.insert(node);
//                    }
//                }
//            }
//            else {
//                moves = searchNode.moves;
//                while (searchNode != null) {
//                    solution.push(searchNode.board);
//                    searchNode = searchNode.previous;
//                }
//                break;
//            }
//        }
//        if (solution.isEmpty()) {
//            moves = -1;
//        }
//    }
//
//    // is the initial board solvable? (see below)
//    public boolean isSolvable() {
//        return moves() != -1;
//    }
//
//    // min number of moves to solve initial board; -1 if unsolvable
//    public int moves() {
//        return moves;
//    }
//
//    // sequence of boards in a shortest solution; null if unsolvable
//    public Iterable<Board> solution() {
//        // Return null in solution() if the board is unsolvable
//        return solution;
//    }
//
//    private class Node {
//        Board board;
//        int priority = 0;
//        int moves = 0;
//        Node previous = null;
//
//        public Node(Board board, Node previous, int moves, int priority) {
//            this.board = board;
//            this.previous = previous;
//            this.moves = moves;
//            this.priority = priority;
//        }
//
//        public Node(Board board) {
//            this.board = board;
//        }
//    }
//
//    private Comparator<Node> comparator() {
//        return (o1, o2) -> Integer.compare(o1.priority, o2.priority);
//    }
//
//    // test client (see below)
//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
//        Board initial = new Board(tiles);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
//    }
//}
