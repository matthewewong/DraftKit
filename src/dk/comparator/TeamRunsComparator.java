package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total runs and sorts them into a list. Note that
 * the runs will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamRunsComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int runs1 = t1.getTotRuns();
        int runs2 = t2.getTotRuns();
        if (runs1 < runs2)
            return -1;
        else if (runs1 == runs2)
            return 0;
        else
            return 1;
    }
}
