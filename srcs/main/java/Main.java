import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static boolean isValid(String line) {
        return true;
    }

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong number of program arguments");
        String fileName = args[0];
        List<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();


            while (line != null) {
                int index = line.indexOf("#");
                if (index != 0) { // ignore lines that start with #
                    if (index != -1) // remove comments in the end of the line
                        line = line.substring(0, index);

                    lines.add(line);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
