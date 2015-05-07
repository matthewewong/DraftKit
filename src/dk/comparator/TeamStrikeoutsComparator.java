package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total strikeouts and sorts them into a list. Note 
 * that the strikeouts will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamStrikeoutsComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int ks1 = t1.getTotStrikeouts();
        int ks2 = t2.getTotStrikeouts();
        if (ks1 < ks2)
            return -1;
        else if (ks1 == ks2)
            return 0;
        else
            return 1;
    }
}