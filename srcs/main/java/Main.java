import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {
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
                if (index != 0) {
                    if (index != -1) line = line.substring(0, index);
                    if (tiles.length == 0) {
                        n = Integer.parseInt(line);
                        tiles = new int[n][];
                    } else {
                        tiles[i++] = Arrays.stream(line.split(" "))
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input file: " + fileName);
        }


        long startTime = System.nanoTime();
        Board board = new Board(tiles, n);
        Solver solver = null;
        try {
            solver = new Solver(board);
        } catch (IOException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        // TODO протестировать евклида
        // TODO понять правильно ли работает генератор пазлов или проблема в коде

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Minimum number of moves = " + solver.moves());
        for (Board b : solver.solution()) {
            System.out.println(b);
        }
        System.out.println("Duration: " + duration + " ms");
    }
}
