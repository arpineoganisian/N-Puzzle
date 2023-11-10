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

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
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
}
