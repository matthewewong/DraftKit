package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total RBIs and sorts them into a list. Note that
 * the RBIs will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamRBIsComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int rbis1 = t1.getTotRBIs();
        int rbis2 = t2.getTotRBIs();
        if (rbis1 < rbis2)
            return -1;
        else if (rbis1 == rbis2)
            return 0;
        else
            return 1;
    }
}