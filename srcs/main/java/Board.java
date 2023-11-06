import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private final int n;

    public Board(int[][] tiles, int n) {
        this.n = n;
        this.tiles = createCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(" ").append(tiles[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    //TODO удалить этот метод, пока просто для проверки Н
    public int dimension() {
        return n;
    }

    // Misplaced Tiles Heuristic: number of tiles out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if ((i * n) + j + 1 != tiles[i][j])
                    result++;
            }
        }
        return result;
    }

    // Manhattan Distance Heuristic: sum of distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (tiles[i][j] != (i * n) + j + 1) {
                    result += Math.abs((tiles[i][j] - 1) / n - i);
                    result += Math.abs((tiles[i][j] - 1) % n - j);
                }
            }
        }
        return result;
    }

//    Euclidean Distance Heuristic: sum of Euclidean distances between tiles and goal
    public double euclid() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (tiles[i][j] != (i * n) + j + 1) {
                    result += Math.sqrt(
                            Math.pow((tiles[i][j] - 1) / (double) n - i, 2) + Math.pow((tiles[i][j] - 1) % n - j, 2)
                    );
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (tiles[i][j] != (i * n) + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neigbours = new ArrayList<>();
        int row = -1;
        int col = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (col != 0)
            neigbours.add(createBoardWithSwap(row, col, row, col - 1, 0));
        if (row != 0)
            neigbours.add(createBoardWithSwap(row, col, row - 1, col, 0));
        if (col != n - 1)
            neigbours.add(createBoardWithSwap(row, col, row, col + 1, 0));
        if (row != n - 1)
            neigbours.add(createBoardWithSwap(row, col, row + 1, col, 0));

        return neigbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int changeRow = tiles[0][0] != 0 && tiles[0][1] != 0 ? 0 : 1;
        return createBoardWithSwap(changeRow, 0,
                                   changeRow, 1, tiles[changeRow][0]);
    }

    private Board createBoardWithSwap(int rowA, int colA,
                                      int rowB, int colB, int value) {
        int[][] copy = createCopy(tiles);
        copy[rowA][colA] = copy[rowB][colB];
        copy[rowB][colB] = value;
        return new Board(copy, n);
    }

    private int[][] createCopy(int[][] tiles) {
        int[][] copy = new int[n][];
        for (int i = 0; i < n; i++) copy[i] = Arrays.copyOf(tiles[i], n);
        return copy;
    }

    public static void main(String[] args) {
        // пазл в правильном порядке
        System.out.println("--BOARD { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }--");
        int[][] ints1 = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board1 = new Board(ints1, 3);
        System.out.println(board1);
        System.out.println(board1.hamming() == 0);
        System.out.println(board1.manhattan() == 0);
        System.out.println(board1.isGoal());
        System.out.println();

        // пазл с дыркой посередине
        System.out.println("--BOARD { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } }--");
        int[][] ints2 = new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board2 = new Board(ints2, 3);
        System.out.println(board2);
        System.out.println(board2.hamming() == 5);
        System.out.println(board2.manhattan() == 10);
        for (Board b : board2.neighbors()) {
            System.out.println("Neighbour:");
            System.out.println(b);
        }
        System.out.println(board2);
        System.out.println(!board2.isGoal());
        System.out.println(board2.twin().equals(new Board(
                new int[][] { { 1, 8, 3 }, { 4, 0, 2 }, { 7, 6, 5 } },
                3)));
        System.out.println();

        // пазл с дыркой в углу
        System.out.println("--BOARD { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } }--");
        int[][] ints3 = new int[][] { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } };
        Board board3 = new Board(ints3, 3);
        System.out.println(board3);
        for (Board b : board3.neighbors()) {
            System.out.println("Neighbour:");
            System.out.println(b);
        }
        System.out.println(board3.twin().equals(new Board(
                new int[][] { { 0, 1, 3 }, { 8, 4, 2 }, { 7, 6, 5 } },
                3)));
        System.out.println(!board3.isGoal());
        System.out.println();

        // такой же, как и предыдущий
        System.out.println("--BOARD { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } }--");
        int[][] ints4 = new int[][] { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } };
        Board board4 = new Board(ints4, 3);
        System.out.println(board4);
        System.out.println(board3.equals(board4));
        System.out.println();

        // неправильные equals
        System.out.println(!board3.equals(board1));
        System.out.println(!board3.equals(null));
        System.out.println(!board3.equals(new StringBuilder()));

        System.out.println(board1.dimension() == 3);
        System.out.println(board2.dimension() == 3);
        System.out.println(board3.dimension() == 3);
        System.out.println(board4.dimension() == 3);

        // баг из примера
        Board board5 = new Board(new int[][] { { 3, 2, 6 }, { 1, 4, 5 }, { 8, 0, 7 } }, 3);
        board5.neighbors().forEach(System.out::println);
    }
}
