package algorithm;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

/**
 * Created by ra on 19.06.16.
 * Part of randomProjections, in package algorithm.
 */
public class Motif {
    public final String data;
    public final int index;
    private final List<Motif> friends = new ArrayList<>();
    private final List<Motif> closeFriends = new ArrayList<>();

    public Motif(String data, int index) {
        this.data = data;
        this.index = index;
    }

    public void addFriend(Motif friend) {
        if (friend != null) {
            friends.add(friend);
        }
    }

    public void addCloseFriend(Motif friend) {
        if (friend != null) {
            closeFriends.add(friend);
        }
    }

    public double calculateScore() {
        return sqrt(friends.size()) * closeFriends.size();
    }
}
