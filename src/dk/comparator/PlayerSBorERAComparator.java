package dk.comparator;

import dk.data.Player;
import java.util.Comparator;

/**
 * This class compares the players' stolen bases or ERA and sorts them into a list.
 * Note that the stolen bases will be sorted descendingly and the ERA will be sorted
 * ascendingly.
 *
 * @author Matthew Wong
 */
public class PlayerSBorERAComparator implements Comparator<Player> {
    //compare!
    @Override
    public int compare(Player p1, Player p2) {
        if (p1.isAHitter()) { //we're comparing hitters
            double sb1 = p1.getStolenBases();
            double sb2 = p2.getStolenBases();
            if (sb1 < sb2)
                return 1;
            else if (sb1 == sb2)
                return 0;
            else
                return -1;
        }
        else { //we're comparing pitchers
            double era1 = p1.getERA();
            double era2 = p2.getERA();
            if (era1 < era2)
                return -1;
            else if (era1 == era2)
                return 0;
            else
                return 1;
        }
    }
}