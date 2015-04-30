package dk.data;

import java.util.Comparator;

/**
 * This class simply compares the players and sorts them into a list for the user
 * to see and easily understand. Note these positions will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class PlayerPositionsComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        int position1 = p1.getPositionNumber();
        int position2 = p2.getPositionNumber();
        if (position1 < position2)
            return -1;
        else if (position1 == position2)
            return 0;
        else
            return 1;
    }
}
