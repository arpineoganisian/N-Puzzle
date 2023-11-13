import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Properties;

public class Solver {

    private int moves;
    private Deque<Board> solution;
    private static long size = 0;
    private static long time = 2;

    public Solver(Board initial) throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, OutOfMemoryError {
        if (initial == null) throw new IllegalArgumentException();

        Properties properties = new Properties();
        properties.load(new FileInputStream("srcs/main/resources/heuristic.properties"));
        String functionName = properties.getProperty("heuristic");
        Method heuristic = Board.class.getMethod(functionName);

        Board.createGoal(initial.dimension());

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        pq.add(new Node(initial, null, 0, (double) heuristic.invoke(initial)));

        PriorityQueue<Node> twinPq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        twinPq.add(new Node(initial.twin(), null, 0, (double) heuristic.invoke(initial)));

        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            int allElems = pq.size() + twinPq.size();
            if (size < allElems) size = allElems;
            Node searchNode = pq.poll();
            Node twinSearchNode = twinPq.poll();
            if (!searchNode.board.isGoal() && !twinSearchNode.board.isGoal()) {
                addNewNodes(searchNode,     pq,     heuristic);
                addNewNodes(twinSearchNode, twinPq, heuristic);
            } else if (twinSearchNode.board.isGoal()) {
                moves = -1;
                return;
            } else {
                moves = searchNode.moves;
                solution = new ArrayDeque<>();
                while (searchNode != null) {
                    solution.push(searchNode.board);
                    searchNode = searchNode.previous;
                }
                return;
            }
        }
    }


    private void addNewNodes(Node searchNode, PriorityQueue<Node> pq, Method heuristic)
            throws InvocationTargetException, IllegalAccessException {
        for (Board b : searchNode.board.neighbours()) {
            time++;
            if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
                Node n = new Node(b, searchNode, searchNode.moves + 1,
                        searchNode.moves + 1 + (double) heuristic.invoke(b));
                pq.add(n);
            }
        }
    }

    public int moves() {
        return moves;
    }

    // Общее количество состояний, когда-либо выбранных в «открытом» наборе (сложность во времени)
    public static long complexityInTime() {
        return time;
    }

    // Максимальное количество состояний, когда-либо представленных в памяти одновременно во время поиска (сложность по размеру)
    public static long complexityInSize() {
        return size;
    }

    public Deque<Board> solution() {
        return solution;
    }

    private static class Node {
        Board board;
        double priority;
        int moves;
        Node previous;

        public Node(Board board, Node previous, int moves, double priority) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = priority;
        }
    }
}
