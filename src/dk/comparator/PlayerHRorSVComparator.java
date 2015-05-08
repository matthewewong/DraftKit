package dk.comparator;

import dk.data.Player;
import java.util.Comparator;

/**
 * This class compares the players' home runs or saves and sorts them into a list.
 * Note that the stats will be sorted descendingly.
 *
 * @author Matthew Wong
 */
public class PlayerHRorSVComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.isAHitter()) { //we're comparing hitters
            int hr1 = p1.getHomeRuns();
            int hr2 = p2.getHomeRuns();
            if (hr1 < hr2)
                return 1;
            else if (hr1 == hr2)
                return 0;
            else
                return -1;
        }
        else { //we're comparing pitchers
            int saves1 = p1.getSaves();
            int saves2 = p2.getSaves();
            if (saves1 < saves2)
                return 1;
            else if (saves1 == saves2)
                return 0;
            else
                return -1;
        }
    }
}
