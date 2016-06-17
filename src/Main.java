import static algorithm.algorithm.randomProjections;

/**
 * A motif is understood as a reappearing sequence.
 * Part of randomProjections, in package src.
 */
public class Main {
    public static void main(String[] args) {
        // motifs of length 4 might be: some, byte, (sequ, eque, ..., ence)
        // somesequence is a motif of length 4 even if it is obfuscated a little
        String data = "someseruenceterabytesomtsequencenoisebiterepeatsomesequencdwhatisbyttspace somezequencequencesomesaucegingersomesequebcebyteis8bitsomesequence";
        randomProjections(data, 4, 1, 5);
    }
}
