import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static algorithm.RandomProjections.randomProjections;

/**
 * A motif is understood as a reappearing sequence.
 * Part of randomProjections, in package src.
 */
class Main {
    public static void main(String[] args) {
        // motifs of length 4 might be: some, byte, (sequ, eque, ..., ence)
        // somesequence is a motif of length 4 even if it is obfuscated a little
        // String data = "someseruenceterabytesomtsequencenoisebiterepeatsomesequencdwhatisbyttspace " +
        // "somezequencequencesomesaucegingersomesequebcebyteis8bitsomesequence";
        // randomProjections(data, 10, 3, 3, 2);
        try {
            String preludio = readFile(1, "../midiParser/assets/midi/csv/preludioC.mid/tracks/track0.csv");
            randomProjections(preludio, 24, 3, 15, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(int column, String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
        List<String> parsedColumn = new ArrayList<>(lines.size());
        for (String line : lines) {
            String[] cols = line.split(", ");
            if (cols.length > column) {
                parsedColumn.add(line.split(", ")[column]);
            }
        }
        return parsedColumn.stream().reduce("", (String::concat));
    }
}
