import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class NPuzzle {
    private static final String COLOR = "\u001B[34m", RESET = "\u001B[0m";
    private static int[][] tiles = new int[0][0];
    private static int n = 0;

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong number of program arguments: " + args.length);
        }

        parse(args[0]);

        long startTime = System.nanoTime();

        Board board = new Board(tiles, n);
        Solver solver = null;
        try {
            solver = new Solver(board);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the file: heuristic.properties", e);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("An error occurred during reflection usage", e);
        } catch (OutOfMemoryError ignored) {}

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        printResult(solver, duration);
    }

    private static void parse(String fileName) {
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
        System.out.println();
    }

    private static void printResult(Solver solver, long duration) {
        printLine();
        if (solver == null) {
            System.out.println(COLOR + "This is too big... Try a smaller puzzle or one with fewer moves" + RESET);
        } else {
            if (solver.moves() == -1) {
                System.out.println(COLOR + "Unsolvable puzzle" + RESET);
            } else {
                int i = 0;
                while (!solver.solution().isEmpty()) {
                    if (i == 0) System.out.println(COLOR + "Initial board: ");
                    else System.out.println(COLOR + "Move " + i + ": ");
                    System.out.println(RESET + solver.solution().pop());
                    i++;
                }
                printLine();
                System.out.println("Minimum number of moves: " + COLOR + solver.moves() + RESET);
            }
        }
        printLine();
        System.out.println("Total number of states ever selected (complexity in time): "
                + COLOR + String.format("%,d", Solver.complexityInTime()) + RESET);
        System.out.println("Maximum number of states represented in memory ATST (complexity in size): "
                + COLOR + String.format("%,d", Solver.complexityInSize()) + RESET);
        printLine();
        System.out.println("Duration: " + COLOR + duration / 1000D + " s"  + RESET);
        printLine();
    }

    private static void printLine() {
        System.out.println(COLOR + "------------------------------------------------------------------------------------"
                + RESET);
    }
    //TODO Пройтись по сабжу
    //TODO Пройтись по чеклисту
    //TODO Выбрать алгоритм а-стар: мой или из сабжа
    //TODO не создавать соседа если он не нужен?
    //TODO добавить формулы в ридми
    //TODO добавить в ридми инфо про варианты а-стар
    //todo утечки памяти?
}
