import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] grid;
    private final int n;

    public Board(int[][] tiles) {
        // 2 ≤ n < 128 по условию
        n = tiles.length;
        grid = createCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(" ").append(grid[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0)
                    continue;
                if ((i * n) + j + 1 != grid[i][j])
                    result++;
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0)
                    continue;
                if (grid[i][j] != (i * n) + j + 1) {
                    result += Math.abs((grid[i][j] - 1) / n - i);
                    result += Math.abs((grid[i][j] - 1) % n - j);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        return Arrays.deepEquals(grid, ((Board) y).grid);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neigbours = new ArrayList<>();
        int row = -1;
        int col = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
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
        int changeRow = grid[0][0] != 0 && grid[0][1] != 0 ? 0 : 1;
        return createBoardWithSwap(changeRow, 0,
                                   changeRow, 1, grid[changeRow][0]);
    }

    private Board createBoardWithSwap(int rowA, int colA,
                                      int rowB, int colB, int value) {
        int[][] copy = createCopy(grid);
        copy[rowA][colA] = copy[rowB][colB];
        copy[rowB][colB] = value;
        return new Board(copy);
    }

    private int[][] createCopy(int[][] tiles) {
        int[][] copy = new int[n][];
        for (int i = 0; i < n; i++) copy[i] = Arrays.copyOf(tiles[i], n);
        return copy;
    }
}
