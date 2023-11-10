import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class NPuzzle {
    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong number of program arguments: " + args.length);

        String fileName = args[0];
        int[][] tiles = new int[0][0];
        int n = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                int index = line.indexOf("#");
                if (index != 0 && !line.isEmpty()) {
                    if (index != -1) line = line.substring(0, index);
                    if (tiles.length == 0) {
                        n = Integer.parseInt(line);
                        tiles = new int[n][];
                    } else {
                        tiles[i++] = Arrays.stream(line.split(" "))
                                .filter(s -> !s.isEmpty())
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        if (tiles[i - 1].length != n)
                            throw new IllegalArgumentException("Invalid input file: " + fileName);
                    }
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + fileName);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input file: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the file: " + fileName, e);
        }

        long startTime = System.nanoTime();

        Board board = new Board(tiles, n);
        Solver solver;
        try {
            solver = new Solver(board);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the file: heuristic.properties", e);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("An error occurred during reflection usage", e);
        }

        // TODO протестировать евклида
        // TODO дописать мейкфайл
        // TODO проверить правильно ли работет метод twin и проверка solvable - unsolvable
        // TODO

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        printResult(solver, duration);
    }

    private static void printResult(Solver solver, long duration) {
        String MAGENTA = "\u001B[35m", BLUE = "\u001B[34m",
                GREEN = "\u001B[32m", RESET = "\u001B[0m";

        if (solver.solution() == null) {
            System.out.println(MAGENTA + "Unsolvable puzzle" + RESET);
        }
        else {
            System.out.println(MAGENTA + "Minimum number of moves: " + solver.moves());
            System.out.println();

            for (int i = 0; i < solver.solution().size(); i++) {
                if (solver.moves() == i)
                    System.out.println(BLUE + "Initial board: " + RESET);
                else
                    System.out.println(BLUE + "Move " + (solver.moves() - i) + ": " + RESET);
                System.out.println(solver.solution().get(i));
            }
        }
        System.out.println(GREEN + "Duration: " + duration + " ms / " + duration / 1000D + " s"  + RESET);
    }
}
