package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' average batting averages and sorts them into a 
 * list. Note that the batting averages will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamBattingAverageComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        double ba1 = t1.getAvgBattingAverage();
        double ba2 = t2.getAvgBattingAverage();
        if (ba1 < ba2)
            return -1;
        else if (ba1 == ba2)
            return 0;
        else
            return 1;
    }
}