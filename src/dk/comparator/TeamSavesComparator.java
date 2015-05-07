package dk.comparator;

import dk.data.Team;
import java.util.Comparator;

/**
 * This class compares the teams' total saves and sorts them into a list. Note that
 * the saves will be sorted ascendingly.
 *
 * @author Matthew Wong
 */
public class TeamSavesComparator implements Comparator<Team> {
    //compare!
    @Override
    public int compare(Team t1, Team t2) {
        int saves1 = t1.getTotSaves();
        int saves2 = t2.getTotSaves();
        if (saves1 < saves2)
            return -1;
        else if (saves1 == saves2)
            return 0;
        else
            return 1;
    }
}