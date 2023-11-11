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
    private static long size = 0;
    private static long time = 2;

    public Solver(Board initial) throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, OutOfMemoryError {
        if (initial == null) throw new IllegalArgumentException();

        Properties properties = new Properties();
        properties.load(new FileInputStream("srcs/main/resources/heuristic.properties"));
        String functionName = properties.getProperty("heuristic");
        Method method = Board.class.getMethod(functionName);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        pq.add(new Node(initial, null, 0, (double) method.invoke(initial)));

        PriorityQueue<Node> twinPq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
        twinPq.add(new Node(initial.twin(), null, 0, (double) method.invoke(initial)));

        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            int allElems = pq.size() + twinPq.size();
            if (size < allElems) size = allElems;
            Node searchNode = pq.poll();
            Node twinSearchNode = twinPq.poll();
//            if (!searchNode.board.equals(goal) && !twinSearchNode.board.equals(goal)) {
            if (!searchNode.board.isGoal() && !twinSearchNode.board.isGoal()) {
                for (Board b : searchNode.board.neighbors()) {
                    time++;
                    if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
                        addNewNode(pq, searchNode, b, method);
                    }
                }
                for (Board b : twinSearchNode.board.neighbors()) {
                    time++;
                    if (twinSearchNode.previous == null || !b.equals(twinSearchNode.previous.board)) {
                        addNewNode(twinPq, twinSearchNode, b, method);
                    }
                }
            } else if (twinSearchNode.board.isGoal()) {
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

//    private Solver(Board initial, int x) {
//        PriorityQueue<Node> opened = new PriorityQueue<>(Comparator.comparingDouble(o -> o.priority));
//        List<Board> closed = new ArrayList<>();
//        boolean success = false;
//        Node node = new Node(initial, null, 0, 0);
//        opened.add(node);
////        Board goal = createGoal(initial.dimension());
//
//        // e - searchNode
//        // s - b, n
//        while (!opened.isEmpty() && !success) {
//            Node searchNode = opened.poll();
//            if (!searchNode.board.isGoal()) {
//                opened.remove(searchNode);
//                closed.add(searchNode.board);
//                for (Board b : searchNode.board.neighbors()) {
//                    Node n = new Node(b, searchNode, searchNode.moves + 1, searchNode.moves + b.hamming());
//                    if (!closed.contains(n.board) && !opened.contains(n)) {
//                        opened.add(n);
//                    }
//                    else {
//                        if (n.priority < searchNode.priority) {
//                            if (closed.contains(n.board)) {
//                                opened.add(n);
//                                closed.remove(n.board);
//                            }
//                        }
//                    }
//                }
//            } else {
//                success = true;
//                moves = searchNode.moves;
//                solution = new Stack<>();
//                while (searchNode != null) {
//                    solution.add(searchNode.board);
//                    searchNode = searchNode.previous;
//                }
//            }
//        }
//        if (!success) {
//            moves = -1;
//        }
//    }

    private void addNewNode(PriorityQueue<Node> pq, Node previous, Board board, Method method)
            throws InvocationTargetException, IllegalAccessException {
        Node node = new Node(board, previous,
                previous.moves + 1,
                previous.moves + 1 + (double) method.invoke(board));
        pq.add(node);
    }

    public int moves() {
        return moves;
    }


    //◦ Общее количество состояний, когда-либо выбранных в «открытом» наборе (сложность во времени)
    public static long complexityInTime() {
        return time;
    }

    //◦ Максимальное количество состояний, когда-либо представленных в памяти одновременно во время поиска (сложность по размеру)
    public static long complexityInSize() {
        return size;
    }

    public List<Board> solution() {
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
