package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total stolen bases and sorts them into a list.
 * Note that the stolen bases will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamStolenBasesComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        double sb1 = t1.getTotStolenBases();
        double sb2 = t2.getTotStolenBases();
        if (sb1 < sb2)
            return -1;
        else if (sb1 == sb2)
            return 0;
        else
            return 1;
    }
}