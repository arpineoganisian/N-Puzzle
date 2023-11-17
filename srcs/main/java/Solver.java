import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Properties;

public class Solver {

    private int moves;
    private Deque<Board> solution;
    private static long size = 0;
    private static long time = 0;

    public Solver(Board initial) throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, OutOfMemoryError {
        if (initial == null) throw new IllegalArgumentException();

        Board.createGoal(initial.dimension());
        Method heuristic = extractHeuristic();

        PriorityQueue<Node> opened = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        opened.add(new Node(initial, null, 0, (double) heuristic.invoke(initial)));
        HashSet<Node> closed = new HashSet<>();

        PriorityQueue<Node> twinOpened = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        twinOpened.add(new Node(initial.twin(), null, 0, (double) heuristic.invoke(initial.twin())));
        HashSet<Node> twinClosed = new HashSet<>();

        while (!opened.isEmpty() && !twinOpened.isEmpty()) {
            time ++;
            size = Math.max(size, opened.size() + closed.size() + twinOpened.size() + twinClosed.size());
            Node current = opened.poll();
            Node twinCurrent = twinOpened.poll();
            if (current.board.isGoal()) {
                moves = current.moves;
                solution = new ArrayDeque<>();
                while (current != null) {
                    System.out.println(current.priority);
                    solution.push(current.board);
                    current = current.previous;
                }
                return;
            } else if (twinCurrent.board.isGoal()) {
                moves = -1;
                return;
            } else {
                addNewNodes(current, opened, closed, heuristic);
                closed.add(current);
                addNewNodes(twinCurrent, twinOpened, twinClosed, heuristic);
                twinClosed.add(twinCurrent);
            }
        }
    }

    private Method extractHeuristic() throws IOException, NoSuchMethodException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("srcs/main/resources/heuristic.properties"));
        String functionName = properties.getProperty("heuristic");
        return Board.class.getMethod(functionName);
    }

    private void addNewNodes(Node current, PriorityQueue<Node> opened, HashSet<Node> closed, Method heuristic)
            throws InvocationTargetException, IllegalAccessException {
        for (Board b : current.board.neighbours()) {
            Node n = new Node(b, current, current.moves + 1,
                    current.moves + 1 + (double) heuristic.invoke(b));
            if (n.equals(current.previous)) continue;
            if (!closed.contains(n))
                opened.add(n);
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
        double priority; // f(n) = g(n) + h(n)
        int moves; // g(n)
        Node previous;

        public Node(Board board, Node previous, int moves, double priority) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            return this.board.equals(((Node) obj).board);
        }

        @Override
        public int hashCode() {
            return Objects.hash(board);
        }
    }
}
