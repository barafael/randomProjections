package algorithm;

import java.util.List;
import java.util.Map;

/**
 * Algorithm to find a motif in a string of data by using random projections(random selections of indices).
 * Part of randomProjections, in package algorithm.
 */
public class algorithm {
    /** Terminology:
     * data: Long string containing instances of planted or real motifs, flawed by up to a specified number of differences from the model.
     * n: Length of data
     * Motif: recurring contiguous subsequence in data, containing some noise.
     * L: Length of motif searched for.
     *
     * |c|, #c: cardinality of datastructure/collection c.
     * Noise, differences, distortions: mismatches of model and instance of motif.
     * support of a motif/model: Amount of occurrences of motif/model in data.
     *
     * ĸ-scatter of s: String that is concatenation of characters s[i] where i in ĸ. ĸ is a collection of integers.
     'Mathematical' notation: [ s[i] | i in ĸ]. Length of ĸ-scatter: |k|.
     * k: Array or collection containing up to L-d random indices where L is the length of the motif and d is the error tolerance.
     If there were more indices, the k-scatter and the set of indices of deviations in a motif could never be disjoint.
     The indices can be in the range [0, L-1].

     * Friend: 2 strings a and  b in data are defined to be friends if they share some k-scatters (i.e. if for some random projections, the scatters of a and b are equal).
     */

    /**
     * Random Projections algorithm
     * Takes a reference to data, a motif length and the amount of permitted errors.
     *
     * @param data            Long string containing instances of planted or real motifs, flawed by up to {@code permittedErrors} differences from the model.
     * @param motifLength     Length of motif
     * @param permittedErrors Allowed distance of an instance of a motif from the implicit model
     * @return Returns a mapping between motifs and their support
     */
    public static Map<String, Integer> randomProjections(String data, int motifLength, int permittedErrors) {
        return null;
    }

    /**
     * Hashes all substrings of specified length in data to buckets. Two substrings a and b are hashed to the same bucket exactly if
     * a[i] == b[i] for all i in k. That means, all substrings in one bucket have the same k-scatter.
     * <p>
     * A map of strings to lists of integers is returned. A list from the entry set contains the indices at which strings s start.
     * All s have the same k-scatter, which is used as key.
     * All lists are guaranteed to be ordered. Additionally, each index from data appears exactly once in the entire hash, possibly opening up
     * opportunities for concurrent computation.
     *
     * @param data          String to be hashed
     * @param lengthOfMotif length of substrings to be hashed
     * @param chosenIndices k. Array containing up to L-d random indices.
     *                      If there were more indices, the k-scatter and the set of deviations in a motif could never be disjoint.
     *                      The indices can be in the range [0, L-1].
     * @return a mapping between k-scatters ks of substrings of data and the indexes i where [ data[i + j] | j in k] == ks,
     * i. e. where substrings share the same scatter.
     */
    private Map<String, List<Integer>> hash(String data, int lengthOfMotif, int[] chosenIndices) {
        return null;
    }

    /**
     * This method constructs a 'friend list' for a given list of hashes.
     * For all indexes i of data, the bucket containing i is selected from every hash.
     * The indices in the bucket, ignoring i itself but preserving other duplicates, are added to the friends list of i.
     *
     * Returns a mapping of indices i to lists of indices j where the substring of data starting at i is friends with all
     * the substrings of data starting at j, with the length of the substrings being L.
     *
     * @param hashes List of (hashes of (k-scatters to list of indices))
     * @return map of (indices of motifs m to lists of (indices of friends of m))
     */
    private Map<Integer, List<Integer>> friendsList(List<Map<Integer, List<Integer>>> hashes) {
        return null;
    }
}

