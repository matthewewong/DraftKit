package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total home runs and sorts them into a list.
 * Note that the home runs will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamHomeRunsComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int homeRuns1 = t1.getTotHomeRuns();
        int homeRuns2 = t2.getTotHomeRuns();
        if (homeRuns1 < homeRuns2)
            return -1;
        else if (homeRuns1 == homeRuns2)
            return 0;
        else
            return 1;
    }
}