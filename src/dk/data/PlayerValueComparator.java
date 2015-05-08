package dk.data;

import java.util.Comparator;

/**
 * This class ranks the players based on their estimated value. Note that the players
 * will be sorted descendngly (list[0] = highest valued player)
 *
 * @author MAVIRI
 */
public class PlayerValueComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        int ev1 = p1.getValue();
        int ev2 = p2.getValue();
        if (ev1 < ev2)
            return 1;
        else if (ev1 == ev2)
            return 0;
        else
            return -1;
    }
    
}
