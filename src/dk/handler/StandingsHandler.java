package dk.handler;

import dk.comparator.TeamBattingAverageComparator;
import dk.comparator.TeamERAComparator;
import dk.comparator.TeamHomeRunsComparator;
import dk.comparator.TeamRBIsComparator;
import dk.comparator.TeamRunsComparator;
import dk.comparator.TeamSavesComparator;
import dk.comparator.TeamStolenBasesComparator;
import dk.comparator.TeamStrikeoutsComparator;
import dk.comparator.TeamWHIPComparator;
import dk.comparator.TeamWinsComparator;
import dk.data.Draft;
import dk.data.Player;
import dk.data.Team;
import java.text.DecimalFormat;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * This class deals with the standings team pane. Basically it's function is to reset
 * the data on the table.
 *
 * @author Matthew Wong
 */
public class StandingsHandler {
    ObservableList<Team> teams;
    
    ObservableList<Team> totRList;
    ObservableList<Team> totHRList;
    ObservableList<Team> totRBIList;
    ObservableList<Team> totSBList;
    ObservableList<Team> avgBAList;
    ObservableList<Team> totWList;
    ObservableList<Team> totSVList;
    ObservableList<Team> totKList;
    ObservableList<Team> avgERAList;
    ObservableList<Team> avgWHIPList;
    
    public final int MAX_MONEY = 260;
    public final int MAX_STARTING_PLAYERS = 23;
    
    public StandingsHandler(Draft draft) {
        teams = FXCollections.observableArrayList();
    }
    
    public void editStandingsTableContents(TableView<Team> table, Draft draft) {
        teams = draft.getTeams();
        for (Team t : teams) {
            calcPlayersNeeded(t);
            calcMoneyLeft(t);
            calcMoneyPerPlayer(t);
            calcTotalR(t);
            calcTotalHR(t);
            calcTotalRBI(t);
            calcTotalSB(t);
            calcAvgBA(t);
            calcTotW(t);
            calcTotSV(t);
            calcTotK(t);
            calcAvgERA(t);
            calcAvgWHIP(t);
            
        }
        table.setItems(teams);
        
        totRList = FXCollections.observableArrayList(teams);
        totHRList = FXCollections.observableArrayList(teams);
        totRBIList = FXCollections.observableArrayList(teams);
        totSBList = FXCollections.observableArrayList(teams);
        avgBAList = FXCollections.observableArrayList(teams);
        totWList = FXCollections.observableArrayList(teams);
        totSVList = FXCollections.observableArrayList(teams);
        totKList = FXCollections.observableArrayList(teams);
        avgERAList = FXCollections.observableArrayList(teams);
        avgWHIPList = FXCollections.observableArrayList(teams);
        
        Collections.sort(totRList, new TeamRunsComparator()); //sorts the runs
        Collections.sort(totHRList, new TeamHomeRunsComparator()); //sorts the homers
        Collections.sort(totRBIList, new TeamRBIsComparator()); //sorts the RBIs
        Collections.sort(totSBList, new TeamStolenBasesComparator()); //sorts the stolen bases
        Collections.sort(avgBAList, new TeamBattingAverageComparator()); //sorts the batting averages
        Collections.sort(totWList, new TeamWinsComparator()); //sorts the wins
        Collections.sort(totSVList, new TeamSavesComparator()); //sorts the saves
        Collections.sort(totKList, new TeamStrikeoutsComparator()); //sorts the strikeouts
        Collections.sort(avgERAList, new TeamERAComparator()); //sorts the ERA
        Collections.sort(avgWHIPList, new TeamWHIPComparator()); //sorts the WHIP
        
        for (Team t : teams) {
            calcTotalPoints(t);
        }
    }
    
     // MAX SIZE (23) - STARTING PLAYERS
    public void calcPlayersNeeded(Team team) {
        team.setPlayersNeeded(MAX_STARTING_PLAYERS - team.getStartingPlayers().size());
    }
    
    // MAX_MONEY (260) - (salary for each player)
    public void calcMoneyLeft(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            total += p.getSalary();
        }
        team.setMoneyLeft(MAX_MONEY - total);
    }
    
    //  MONEY LEFT / PLAYERS NEEDED
    public void calcMoneyPerPlayer(Team team) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (team.getPlayersNeeded() == 0)
            team.setMoneyPerPlayer(-1.0);
        else
            team.setMoneyPerPlayer(Double.parseDouble(df.format((double)team.getMoneyLeft() / team.getPlayersNeeded())));
    }
    
    public void calcTotalR(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (p.isAHitter()) //ONLY IF THE PLAYER IS A HITTER
                total += p.getRuns();
        }
        team.setTotRuns(total);
    }
    
    public void calcTotalHR(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (p.isAHitter()) //ONLY IF THE PLAYER IS A HITTER
                total += p.getHomeRuns();
        }
        team.setTotHomeRuns(total);
    }
    
    public void calcTotalRBI(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (p.isAHitter()) //ONLY IF THE PLAYER IS A HITTER
                total += p.getRBIs();
        }
        team.setTotRBIs(total);
    }
    
    public void calcTotalSB(Team team) {
        double total = 0.0;
        for (Player p : team.getStartingPlayers()) {
            if (p.isAHitter()) //ONLY IF THE PLAYER IS A HITTER
                total += p.getStolenBases();
        }
        team.setTotStolenBases(total);
    }
    
    public void calcAvgBA(Team team) {
        DecimalFormat df = new DecimalFormat("0.000");
        double ba;
        double hits = 0.0;
        int ab = 0;
        for (Player p : team.getStartingPlayers()) {
            if (p.isAHitter()) { //ONLY IF THE PLAYER IS A HITTER
                hits += (double)p.getHits();
                ab += p.getAtBats();
            }
        }
        if (ab == 0) {
            ba = 0.000;
            ba = Double.parseDouble(df.format(ba));
        }
        else {
            ba = hits / ab;
            ba = Double.parseDouble(df.format(ba));
        }
        team.setAvgBattingAverage(ba);
    }
    
    public void calcTotW(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (!(p.isAHitter())) //IS A PITCHER
                total += p.getWins();
        }
        team.setTotWins(total);
    }
    
    public void calcTotSV(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (!(p.isAHitter())) //IS A PITCHER
                total += p.getSaves();
        }
        team.setTotSaves(total);
    }
    
    public void calcTotK(Team team) {
        int total = 0;
        for (Player p : team.getStartingPlayers()) {
            if (!(p.isAHitter())) //IS A PITCHER
                total += p.getStrikeouts();
        }
        team.setTotStrikeouts(total);
    }
    
    public void calcAvgERA(Team team) {
        DecimalFormat df = new DecimalFormat("#.00");
        double era;
        double ip = 0.0;
        int er = 0;
        for (Player p : team.getStartingPlayers()) {
            if (!(p.isAHitter())) {//IS A PITCHER
                ip += p.getInningsPitched();
                er += p.getEarnedRuns();
            }
        }
        if (ip == 0.0) {
            era = 0.00;
            era = Double.parseDouble(df.format(era));
        }
        else {
            era = ((er * 9) / ip);
            era = Double.parseDouble(df.format(era));
        }
        team.setAvgERA(era);
    }
    
    public void calcAvgWHIP(Team team) {
        DecimalFormat df = new DecimalFormat("#.00");
        double whip;
        double ip = 0.0;
        int bb = 0;
        int h = 0;
        for (Player p : team.getStartingPlayers()) {
            if (!(p.isAHitter())) {//IS A PITCHER
                ip += p.getInningsPitched();
                bb += p.getWalksAllowed();
                h += p.getHitsAllowed();
            }
        }
        if (ip == 0.0) {
            whip = 0.00;
            whip = Double.parseDouble(df.format(whip));
        }
        else {
            whip = ((h + bb) / ip);
            whip = Double.parseDouble(df.format(whip));
        }
        team.setAvgWHIP(whip);
    }
    
    public void calcTotalPoints(Team team) {
        int runsRank = (totRList.indexOf(team) + 1);
        int homeRunsRank = (totHRList.indexOf(team) + 1);
        int RBIsRank = (totRBIList.indexOf(team) + 1);
        int SBsRank = (totSBList.indexOf(team) + 1);
        int baRank = (avgBAList.indexOf(team) + 1);
        int winsRank = (totWList.indexOf(team) + 1);
        int savesRank = (totSVList.indexOf(team) + 1);
        int ksRank = (totKList.indexOf(team) + 1);
        int eraRank = (avgERAList.indexOf(team) + 1);
        int whipRank = (avgWHIPList.indexOf(team) + 1);
        
        int totalRank = runsRank + homeRunsRank + RBIsRank + SBsRank + baRank + 
                winsRank + savesRank + ksRank + eraRank + whipRank;
        
        team.setTotalPoints(totalRank);
    }
}
