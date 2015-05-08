package dk.comparator;

import dk.data.Player;
import java.util.Comparator;

/**
 * This class compares the players' batting average or WHIP and sorts them into a
 * list. Note that the batting average will be sorted descendingly and the WHIP will
 * be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class PlayerBAorWHIPComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.isAHitter()) { //we're comparing hitters
            double ba1 = p1.getBA();
            double ba2 = p2.getBA();
            if (ba1 < ba2)
                return 1;
            else if (ba1 == ba2)
                return 0;
            else
                return -1;
        }
        else { //we're comparing pitchers
            double whip1 = p1.getWHIP();
            double whip2 = p2.getWHIP();
            if (whip1 < whip2)
                return -1;
            else if (whip1 == whip2)
                return 0;
            else
                return 1;
        }
    }
}