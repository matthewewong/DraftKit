package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total wins and sorts them into a list. Note that
 * the wins will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamWinsComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int wins1 = t1.getTotWins();
        int wins2 = t2.getTotWins();
        if (wins1 < wins2)
            return -1;
        else if (wins1 == wins2)
            return 0;
        else
            return 1;
    }
}