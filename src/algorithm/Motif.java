package algorithm;

import java.util.*;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

/**
 * Created by ra on 19.06.16.
 * Part of randomProjections, in package algorithm.
 */
class Motif {
    private final String data;
    private final int index;
    private List<Integer> friends = new ArrayList<>();
    private final List<Integer> closeFriends = new ArrayList<>();

    public Motif(String data, int index) {
        this.data = data;
        this.index = index;
    }

    public Motif(String data, int index, List<Integer> friends) {
        this.data = data;
        this.index = index;
        this.friends = friends;
    }

    /*
    public void calculateCloseFriends(int quorum) {
        List<Map.Entry<Integer, Integer>> duplicateMapping = duplicateCount(friends);
        for (Map.Entry<Integer, Integer> entry: duplicateMapping) {
            if(entry.getValue() > quorum) {
                closeFriends.add(entry.getKey());
            }
        }
    }
    public void addCloseFriend(Motif friend) {
        if (friend != null) {
            closeFriends.add(friend);
        }
    }
*/

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

    public double calculateScore() {
        return sqrt(friends.size()) * closeFriends.size();
    }
}
