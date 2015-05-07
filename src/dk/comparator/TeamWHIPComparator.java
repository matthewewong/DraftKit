package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' average WHIP and sorts them into a list. Note 
 * that the WHIP will be sorted descendingly.
 *
 * @author Matthew Wong
 */
public class TeamWHIPComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        double whip1 = t1.getAvgWHIP();
        double whip2 = t2.getAvgWHIP();
        if (whip1 < whip2)
            return 1;
        else if (whip1 == whip2)
            return 0;
        else
            return -1;
    }
}
