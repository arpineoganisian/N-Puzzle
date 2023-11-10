import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Stack;

public class Solver {

    private int moves;
    private List<Board> solution;

    public Solver(Board initial) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (initial == null) throw new IllegalArgumentException();

        Properties properties = new Properties();
        properties.load(new FileInputStream("srcs/main/resources/heuristic.properties"));
        String functionName = properties.getProperty("heuristic");
        Method method = Board.class.getMethod(functionName);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        pq.add(new Node(initial, null, 0, (double) method.invoke(initial)));

        PriorityQueue<Node> twinPq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        twinPq.add(new Node(initial.twin(), null, 0, (double) method.invoke(initial)));

        Board goal = createGoal(initial.dimension());

        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            Node searchNode = pq.poll();
            Node twinSearchNode = twinPq.poll();
            if (!searchNode.board.equals(goal) && !twinSearchNode.board.equals(goal)) {
                for (Board b : searchNode.board.neighbors()) {
                    if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
                        addNewNode(pq, searchNode, b, method);
//                        Node node = new Node(b, searchNode,
//                                searchNode.moves + 1,
//                                searchNode.moves + 1 + (int) method.invoke(b));
//                        pq.add(node);
                    }
                }
                for (Board b : twinSearchNode.board.neighbors()) {
                    if (twinSearchNode.previous == null || !b.equals(twinSearchNode.previous.board)) {
                        addNewNode(twinPq, twinSearchNode, b, method);
//                        Node node = new Node(b, twinSearchNode,
//                                twinSearchNode.moves + 1,
//                                twinSearchNode.moves + 1 + (int) method.invoke(b));
//                        twinPq.add(node);
                    }
                }
            } else if (twinSearchNode.board.equals(goal)) {
                moves = -1;
                return;
            } else {
                moves = searchNode.moves;
                solution = new Stack<>();
                while (searchNode != null) {
                    solution.add(searchNode.board);
                    searchNode = searchNode.previous;
                }
                return;
            }
        }
    }

    private void addNewNode(PriorityQueue<Node> pq, Node previous, Board board, Method method)
            throws InvocationTargetException, IllegalAccessException {
        Node node = new Node(board, previous,
                previous.moves + 1,
                previous.moves + 1 + (double) method.invoke(board));
        pq.add(node);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public List<Board> solution() {
        return solution;
    }

    private static class Node {
        Board board;
        double priority;
        int moves;
        Node previous;

        //TODO переписать priority на Double для Евклидовой эвристики
        public Node(Board board, Node previous, int moves, double priority) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = priority;
        }
    }

    private Board createGoal(int n) {
        int[][] tiles = new int[n][n];

        int value = 1;
        int row = 0, col = 0;
        for (int count = n-1; count > 0; count -= 2, row++, col++) {
            for (int i = 0; i < count; i++) {
                tiles[row][col++] = value++;
            }
            for (int i = 0; i < count; i++) {
                tiles[row++][col] = value++;
            }
            for (int i = 0; i <  count; i++) {
                tiles[row][col--] = value++;
            }
            for (int i = 0; i < count; i++) {
                if (value == n*n) break;
                tiles[row--][col] = value++;
            }
        }
        return new Board(tiles, n);
    }
}
