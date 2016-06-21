package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.sqrt;

/**
 * Created by ra on 19.06.16.
 * Part of randomProjections, in package algorithm.
 */
class Motif {
    private final String motif;
    private final int index;
    private final List<Integer> friends = new ArrayList<>();
    private final Map<Integer, Integer> closeFriends = new HashMap<>(); // maps close friend to proximity
    private double score = 1;
    private boolean friendsChanged = true;

    public Motif(String motif, int index) {
        this.motif = util.SymbolConverter.flatSharpInflater(motif);
        this.index = index;
    }

    public String getMotif() {
        return motif;
    }

    int getIndex() {
        return index;
    }

    void setFriends(List<Map.Entry<Integer, Integer>> allFriends, int quorum) {
        friendsChanged = true;
        allFriends.forEach(e -> {
            if (e.getValue() > quorum) {
                closeFriends.put(e.getKey(), e.getValue());
            } else {
                this.friends.add(e.getKey());
            }
        });
    }

    @Override
    public String toString() {
        return String.format("%s, index: %d, score: %.3f, friends: %d, close friends: %d",
                motif, index, calculateScore(), friends.size(), closeFriends.size());
    }

    Double calculateScore() {
        if (friendsChanged) {
            // Perhaps the 'weight' of a close friend should be taken into account?
            score = sqrt(friends.size()) + closeFriends.size();
        }
        friendsChanged = false;
        return score;
    }
}
