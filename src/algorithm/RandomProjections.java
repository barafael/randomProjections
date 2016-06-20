package algorithm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Algorithm to find a motif in a string of data by using random projections(random selections of indices).
 * Part of randomProjections, in package algorithm.
 */
public class RandomProjections {
    /**
     * Random Projections algorithm
     * Takes a reference to data, a motif length and the amount of permitted errors.
     * see <a href="https://github.com/medium-endian/randomProjections/blob/master/README.md">README.md</a> for terminology and paper.
     *
     * @param data            Long string containing instances of planted or real motifs, flawed by up to {@code permittedErrors} differences from the model.
     * @param motifLength     Length of motif
     * @param permittedErrors Allowed distance of an instance of a motif from the implicit model
     * @param iterations      Number of times to repeat process
     * @return Returns a mapping between motifs and their support
     */
    public static Map<String, Integer> randomProjections(String data, int motifLength, int permittedErrors, int iterations, int quorum) {
        if (data.length() < 15 || motifLength > data.length() / 3 || motifLength < 4 || permittedErrors > motifLength / 2) {
            System.err.println("Arguments are not going to lead to satisfactory result!"); //TODO change parameters to more accurately reflect valid input ranges
        }

        List<Map<String, List<Integer>>> hashes = new ArrayList<>(iterations); // list of (mapping of string s to list l of integers i) where s are k-samples in data and l contains the indices of data at which k-similar substrings occur
        for (int i = 0; i < iterations; i++) {
            int[] k = create_k(motifLength);
            hashes.add(hashSubstring(data, motifLength, k));
        }
        Map<Integer, List<Integer>> friendLists = makeFriendlists(hashes);
        List<Motif> motifList = new ArrayList<>();
        friendLists.entrySet().stream().forEach(
                entry -> {
                    String mot = data.substring(entry.getKey(), entry.getKey() + motifLength); // check: is that right length?
                    motifList.add(new Motif(mot, entry.getKey(), entry.getValue()));
                }
        );



        printHashes(hashes);
        printFriendlists(friendLists);

        List<Map.Entry<Integer, List<Integer>>> entryList = friendLists.entrySet().stream().sorted(
                (a, b) -> (a.getValue().size() - b.getValue().size())
        ).collect(toList()); // a list of (indices mapped to sorted lists of friends) sorted by length of the indices' friendslist

        for (Map.Entry<Integer, List<Integer>> entry : entryList) {
            if (entry.getValue().size() > quorum) {

            }
        }

        Map<String, Integer> result = new HashMap<>();
        System.out.println();
        return result;
    }


    /**
     * Hashes all substrings of specified length in data to buckets. Two substrings a and b are hashed to the same bucket exactly if
     * a[i] == b[i] for all i in k. That means, all substrings in one bucket have the same k-sample.
     * <p>
     * A map of strings to lists of integers is returned. A list from the entry set contains the indices at which strings s start.
     * All s have the same k-sample, which is used as key.
     * All lists are guaranteed to be ordered. Additionally, each index from data appears exactly once in the entire hash, possibly opening up
     * opportunities for concurrent computation.
     *
     * @param data          String to be hashed
     * @param motifLength   length of substrings to be hashed
     * @param chosenIndices k. Array containing up to L-d random indices.
     *                      If there were more indices, the k-sample and the set of deviations in a motif could never be disjoint.
     *                      The indices can be in the range [0, L-1].
     * @return a mapping between k-samples ks of substrings s of data and the indexes i where [ data[i + j] | j in k] == ks,
     * i. e. where substrings share the same sample.
     */
    private static Map<String, List<Integer>> hashSubstring(final String data, final int motifLength, final int[] chosenIndices) {
        // Instant now = Instant.now();
        Map<String, List<Integer>> mapping = new HashMap<>();
        int relevantIndices = data.length() - motifLength; // no overshoot at the end!
        for (int i = 0; i <= relevantIndices; i++) {
            String substring = data.substring(i, i + motifLength); // if endIndex is largestIndex + 1 (==data.length()) then the last character is included in substring
            String k_sample = applyScatter(substring, chosenIndices);
            if (mapping.containsKey(k_sample)) {
                List<Integer> indices = mapping.get(k_sample);
                indices.add(i);
            } else {
                List<Integer> indices = new ArrayList<>();
                indices.add(i);
                mapping.put(k_sample, indices);
            }
        }
        // System.out.println("hashes: " + Duration.between(now, Instant.now()));
        return mapping;
    }

    /**
     * Creates the k-sample of the passed substring
     *
     * @param substring     Substring to sample
     * @param chosenIndices Indices to choose from substring
     * @return a string for which the characters are chosen as the characters at i for i in chosenIndex(the k-sample of substring)
     */
    private static String applyScatter(String substring, int[] chosenIndices) {
        if (chosenIndices.length > substring.length()) {
            throw new IllegalArgumentException();
        }
        StringBuilder buddy = new StringBuilder(chosenIndices.length);
        for (int chosenIndex : chosenIndices) {
            buddy.append(substring.charAt(chosenIndex));
        }
        return buddy.toString();
    }

    /**
     * create and populate k with k_size random numbers between 0 and L-1
     * size of k has to be in range 0..L-d
     * k too large: overlap with error index, k too small: insufficient accuracy
     */
    private static int[] create_k(int motifLength) {
        int k_size = (int) (motifLength * 0.4);
        int[] k = new int[k_size];
        Random randomInt = new Random();
        // System.out.println("k: " + k_size + " random ints between 0 and L(excluding L). L: " + motifLength);
        for (int i = 0; i < k_size; ) {
            int randomIndex = randomInt.nextInt(motifLength);
            boolean vacant = IntStream.of(k).noneMatch(integer -> integer == randomIndex); // check for duplicates
            if (vacant) {
                k[i] = randomIndex;
                i++; // only increase counter if actually adding a new index
            }
        }
        Arrays.sort(k);
        // Arrays.stream(k).forEach(System.out::println);
        return k;
    }


    /**
     * This method constructs a 'friend list' for a given list of hashes.
     * For all indexes i of data, the bucket containing i is selected from every hash.
     * The indices in the bucket, ignoring i itself but preserving other duplicates, are added to the friends list of i.
     * <p>
     * Returns a mapping of indices i to lists of indices j where the substring of data starting at i is friends with all
     * the substrings of data starting at j, with the length of the substrings being L.
     *
     * @param hashes List of (hashes of (k-samples to list of indices))
     * @return map of (indices of motifs m to lists of (indices of friends of m))
     */
    private static Map<Integer, List<Integer>> makeFriendlists(List<Map<String, List<Integer>>> hashes) {
        // Instant now = Instant.now();
        ConcurrentHashMap<Integer, List<Integer>> result = new ConcurrentHashMap<>();
        hashes.parallelStream().map(hashmap -> hashmap.entrySet().parallelStream())
                .forEach(entries -> entries.forEach(e -> {
                            List<Integer> friends = e.getValue();
                            for (Integer index : friends) {
                                if (result.containsKey(index)) {
                                    List<Integer> indices = result.get(index);
                                    friends.stream().filter(i -> !i.equals(index)).forEach(indices::add);
                                } else {
                                    List<Integer> indices = new ArrayList<>();
                                    friends.stream().filter(i -> !i.equals(index)).forEach(indices::add);
                                    result.put(index, indices);
                                }
                            }
                        }
                ));
        result.entrySet().parallelStream().map(Map.Entry::getValue).forEach(Collections::sort);
        // System.out.println("Friendlists: " + Duration.between(now, Instant.now()));
        return result;
    }

    /**
     * Gets a list of integers and returns a list with tuples of integers and the count of their occurence, sorted as most frequent first
     */
    private static List<Map.Entry<Integer, Integer>> duplicateCount(List<Integer> list) {
        Collections.sort(list);
        Map<Integer, Integer> duplicates = new HashMap<>(); // maps indices to the count of occurrences
        for (Integer num : list) {
            if (duplicates.containsKey(num)) {
                duplicates.put(num, duplicates.get(num) + 1);
            } else {
                duplicates.put(num, 1);
            }
        }
        return duplicates.entrySet().stream()
                .sorted(
                        (a, b) -> a.getValue().compareTo(b.getValue()) // check: right sorting?
                ).collect(toList());
    }

    private static void printHashes(List<Map<String, List<Integer>>> hashes) {
        System.out.println("Hashes:");
        hashes.forEach(h ->
                h.forEach((string, integers) -> {
                    System.out.println(string);
                    System.out.println("\t" + integers);
                }));
    }

    private static void printFriendlists(Map<Integer, List<Integer>> friendLists) {
        System.out.println("Friendlists:");
        friendLists.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey());
            System.out.println("\t" + entry.getValue());
        });
    }
}
