import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

        // TODO добавить проверку на нерешаемость пазла ЧЕРЕЗ TWIN BOARD!!!
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.priority));
        pq.add(new Node(initial, null, 0, (int) method.invoke(initial)));
        while (!pq.isEmpty()) {
            Node searchNode = pq.poll();
            System.out.println(pq.size());
            if (!searchNode.board.isGoal()) {
                for (Board b : searchNode.board.neighbors()) {
                    if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
                        Node node = new Node(b, searchNode,
                                             searchNode.moves + 1,
                                             searchNode.moves + 1 + (int) method.invoke(b));
                        pq.add(node);
                    }
                }
            }
            else {
                moves = searchNode.moves;
                solution = new Stack<>();
                while (searchNode != null) {
                    solution.add(searchNode.board);
                    searchNode = searchNode.previous;
                }
                break;
            }
        }
        // TODO return в else и удалить это условие
        if (solution.isEmpty()) {
            moves = -1;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;
        // TODO есть ли способ проверить решаемость пазла без решения его?
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public List<Board> solution() {
        return solution;
    }

    private class Node {
        Board board;
        int priority;
        int moves;
        Node previous;

        //TODO переписать priority на Double для Евклидовой эвристики
        public Node(Board board, Node previous, int moves, int priority) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = priority;
        }
    }

    public static void main(String[] args) {
        //TODO написать юнит тесты
    }
}
