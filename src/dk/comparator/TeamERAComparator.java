package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' average ERA and sorts them into a list. Note that
 * the ERA will be sorted descendingly.
 *
 * @author Matthew Wong
 */
public class TeamERAComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        double era1 = t1.getAvgERA();
        double era2 = t2.getAvgERA();
        if (era1 < era2)
            return 1;
        else if (era1 == era2)
            return 0;
        else
            return -1;
    }
}
