package dk.comparator;

import dk.data.Player;
import java.util.Comparator;

/**
 * This class compares the players' RBIs or strikeouts and sorts them into a list.
 * Note that the stats will be sorted descendingly.
 *
 * @author Matthew Wong
 */
public class PlayerRBIorKComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.isAHitter()) { //we're comparing hitters
            int rbi1 = p1.getRBIs();
            int rbi2 = p2.getRBIs();
            if (rbi1 < rbi2)
                return 1;
            else if (rbi1 == rbi2)
                return 0;
            else
                return -1;
        }
        else { //we're comparing pitchers
            int ks1 = p1.getStrikeouts();
            int ks2 = p2.getStrikeouts();
            if (ks1 < ks2)
                return 1;
            else if (ks1 == ks2)
                return 0;
            else
                return -1;
        }
    }
}