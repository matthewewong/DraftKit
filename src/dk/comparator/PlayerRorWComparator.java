package dk.comparator;

import dk.data.Player;
import java.util.Comparator;

/**
 * This class compares the players' runs or wins and sorts them into a list. Note
 * that the stats will be sorted descendingly.
 *
 * @author Matthew Wong
 */
public class PlayerRorWComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.isAHitter()) { //we're comparing hitters
            int runs1 = p1.getRuns();
            int runs2 = p2.getRuns();
            if (runs1 < runs2)
                return 1;
            else if (runs1 == runs2)
                return 0;
            else
                return -1;
        }
        else { //we're comparing pitchers
            int wins1 = p1.getWins();
            int wins2 = p2.getWins();
            if (wins1 < wins2)
                return 1;
            else if (wins1 == wins2)
                return 0;
            else
                return -1;
        }
    }
}