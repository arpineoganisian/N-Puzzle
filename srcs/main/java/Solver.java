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
    private final List<Board> solution = new Stack<>();

    public Solver(Board initial) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (initial == null) throw new IllegalArgumentException();

        Properties properties = new Properties();
        properties.load(new FileInputStream("srcs/main/resources/heuristic.properties"));
        String functionName = properties.getProperty("heuristic");
        Method method = Board.class.getMethod(functionName);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.priority));
        List<Board> checked = new ArrayList<>();
        pq.add(new Node(initial, null, 0, (int) method.invoke(initial)));
        while (!pq.isEmpty()) {
            Node searchNode = pq.poll();
            System.out.println(pq.size());
            if (!searchNode.board.isGoal()) {
                for (Board b : searchNode.board.neighbors()) {
                    if (!checked.contains(b)) {
//                        !b.equals(searchNode.previous.board)}) {
                        Node node = new Node(b, searchNode,
                                             searchNode.moves + 1,
                                             searchNode.moves + 1 + (int) method.invoke(b));
                        pq.add(node);
                        System.out.println(node.moves);
                        checked.add(b);
                    }
                }
            }
            else {
                moves = searchNode.moves;
                while (searchNode != null) {
                    solution.add(searchNode.board);
                    searchNode = searchNode.previous;
                }
                break;
            }
        }
        if (solution.isEmpty()) {
            moves = -1;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;

        // TODO
        //Для понимания того, можно ли разрешить конкретную головоломку N-пазлов (такую как 8-пазлов),
        // можно использовать концепцию инверсий. Инверсия в контексте пазлов означает пару плиток,
        // где одна плитка находится левее другой и имеет большее значение, чем плитка справа от нее.
        //Правильно сложенный N-пазл имеет четное количество инверсий. Таким образом, если количество инверсий
        // в исходной конфигурации N-пазлов и конечной целевой конфигурации одинаково (оба четные или оба нечетные),
        // тогда головоломка разрешима. Если количество инверсий в одной конфигурации четное,
        // а в другой нечетное (или наоборот), то головоломка неразрешима.
        //Это правило применимо к 15-пазлам, 8-пазлам и другим N-пазлам с четным числом плиток.
        // Для N-пазлов с нечетным числом плиток, разрешимость может меняться в зависимости от исходной конфигурации и конечной целевой конфигурации.
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        // Return null in solution() if the board is unsolvable
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
