import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int[][] tiles;
    private final int n;
    private static final Map<Integer, Coordinate> GOAL = new HashMap<>();

    public Board(int[][] tiles, int n) {
        this.n = n;
        this.tiles = createCopy(tiles);
    }

//    public boolean isSolvable() {
//        // n*n represents empty tile (0)
//        int count = 0;
//        int rowOfEmpty = 0;
//        int colOfEmpty = 0;
//        for (int x = 1; x <= GOAL.size(); x++) {
//            for (int y = x+1; y <= GOAL.size(); y++) {
//                int i1 = GOAL.get(x).i;
//                int j1 = GOAL.get(x).j;
//                int i2 = GOAL.get(y).i;
//                int j2 = GOAL.get(y).j;
//                if (tiles[i1][j1] != 0 && tiles[i2][j2] != 0
//                    && tiles[i1][j1] > tiles[i2][j2])
//                    count++;
//                else if (tiles[i1][j1] == 0) {
//                    rowOfEmpty = i1;
//                    colOfEmpty = j1;
//                }
//            }
//        }
//        System.out.println(rowOfEmpty + ", " + colOfEmpty);
//        if (isEven(n))  return isEven(count) ^ isEven(n-rowOfEmpty);
//        else            return isEven(count);
//    }

    private boolean isEven(int x) {
        return x % 2 == 0;
    }

    public static void createGoal(int n) {
        int value = 1;
        int row = 0, col = 0;
        for (int count = n-1; count > 0; count -= 2, row++, col++) {
            for (int i = 0; i < count; i++) {
                GOAL.put(value++, new Coordinate(row, col++));
            }
            for (int i = 0; i < count; i++) {
                GOAL.put(value++, new Coordinate(row++, col));
            }
            for (int i = 0; i < count; i++) {
                GOAL.put(value++, new Coordinate(row, col--));
            }
            for (int i = 0; i < count; i++) {
                GOAL.put(value++, new Coordinate(row--, col));
            }
        }
        GOAL.putIfAbsent(n*n, new Coordinate(row, col));
    }

    public int dimension() {
        return n;
    }

    // Misplaced Tiles Heuristic: number of tiles out of place
    //  1 2 0          1 2 3
    //  8 4 3   --->   8 0 4   // 3 and 4 are not on their places, hamming() = 2
    //  7 6 5          7 6 5
    public double hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (GOAL.get(tiles[i][j]).i != i || GOAL.get(tiles[i][j]).j != j)
                    result++;
            }
        }
        return result;
    }

    // Manhattan Distance Heuristic: sum of distances between tiles and goal
    //  1 2 0          1 2 3
    //  8 4 3   --->   8 0 4   // 3 and 4 are both one step away from goal, manhattan() = 2
    //  7 6 5          7 6 5
    public double manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                int goalI = GOAL.get(tiles[i][j]).i;
                int goalJ = GOAL.get(tiles[i][j]).j;
                if (goalI != i) result += Math.abs(goalI - i);
                if (goalJ != j) result += Math.abs(goalJ - j);
            }
        }
        return result;
    }

    // Euclidean Distance Heuristic: sum of Euclidean distances between tiles and goal
    //  1 2 0          1 2 3
    //  8 4 3   --->   8 0 4   // sqrt(0+1) + sqrt(1+0), euclidean() = 2
    //  7 6 5          7 6 5
    public double euclidean() {
        double result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                int goalI = GOAL.get(tiles[i][j]).i;
                int goalJ = GOAL.get(tiles[i][j]).j;
                if (goalI != i || goalJ != j) {
                    result += Math.sqrt(Math.pow(goalI - i, 2) + Math.pow(goalJ - j, 2));
                }
            }
        }
        return result;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (GOAL.get(tiles[i][j]).i != i || GOAL.get(tiles[i][j]).j != j)
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbours() {
        List<Board> neighbours = new ArrayList<>();
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
        if (col != 0) neighbours.add(createBoardWithSwap(row, col, row, col - 1, 0));
        if (row != 0) neighbours.add(createBoardWithSwap(row, col, row - 1, col, 0));
        if (col != n - 1) neighbours.add(createBoardWithSwap(row, col, row, col + 1, 0));
        if (row != n - 1) neighbours.add(createBoardWithSwap(row, col, row + 1, col, 0));
        return neighbours;
    }

    public Board twin() {
        int changeRow = tiles[0][0] != 0 && tiles[0][1] != 0 ? 0 : n-1;
        return createBoardWithSwap(changeRow, 0,
                                   changeRow, 1, tiles[changeRow][0]);
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }

    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }

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

    private static class Coordinate {
        private final int i;
        private final int j;
        public Coordinate(int row, int col) {
            this.i = row;
            this.j = col;
        }
    }
}
