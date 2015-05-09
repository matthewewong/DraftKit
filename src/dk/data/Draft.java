package dk.data;

import dk.comparator.PlayerBAorWHIPComparator;
import dk.comparator.PlayerHRorSVComparator;
import dk.comparator.PlayerRBIorKComparator;
import dk.comparator.PlayerRorWComparator;
import dk.comparator.PlayerSBorERAComparator;
import dk.data.Player;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a draft to be edited and then used to generate a site.
 *
 * @author Matthew Wong
 */
public class Draft {
    //Strings of the players
    ObservableList<Player> players;
    ObservableList<Team> teams;
    ObservableList<String> hitters;
    ObservableList<String> pitchers;
    
    //Player lists
    ObservableList<Player> hittersList;
    ObservableList<Player> pitchersList;
    
    ObservableList<Player> hitterSortList;
    ObservableList<Player> pitcherSortList;
    
    //draft name
    String draftName;
    
    //draft list
    ObservableList<Player> draftList;
    
    /**
     * Constructor for setting up a Draft
     */
    public Draft() {
        //init the lists
        players = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
        
        //init the Player lists
        hittersList = FXCollections.observableArrayList();
        pitchersList = FXCollections.observableArrayList();
        
        //init the draft list
        draftList = FXCollections.observableArrayList();
    }
    
    //Accessor and mutator methods for a draft.
    
    public String getDraftName() {
        return draftName;
    }
    
    public void setDraftName(String newDraftName) {
        draftName = newDraftName;
    }
    
    public void clearPlayers() {
        players.clear();
    }
    
    public void clearTeams() {
        //removes every player on every team and adds them to the FA list
        for (Team t : teams) {
            for (Player p : t.getStartingPlayers()) {
                addPlayer(p);
            }
            
            for (Player p : t.getTaxiPlayers()) {
                addPlayer(p);
            }
            t.clearStartingPlayers();
            t.clearTaxiPlayers();
        }
        teams.clear();
    }
    
    public ObservableList<Player> getPlayers() {
        return players;
    }
    
    public ObservableList<Team> getTeams() {
        return teams;
    }
    
    public Team getTeam(String teamName) {
        Team team = new Team();
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamName().equals(teamName))
                team = teams.get(i);
        }
        return team;
    }
    
    public ObservableList<Player> getDraftList() {
        return draftList;
    }
    
    public void setDraftList(ObservableList<Player> draftList) {
        this.draftList = draftList;
    }
    
    public ObservableList<String> getHitters() {
        return hitters;
    }
    
    public ObservableList<String> getPitchers() {
        return pitchers;
    }
    
    public void setHitters(ArrayList<String> hitters) {
        this.hitters = FXCollections.observableArrayList(hitters);
        
        //create a Player object for all the hitters
        for (String s : hitters) {
            Player p = new Player();
            String[] values = s.split(","); //split the string with the commas, so now we have an array of stats
            values[0] = values[0].substring(6); //gets rid of the "TEAM:" part of the string
            p.setProTeam(values[0]);
            values[1] = values[1].substring(10); //gets rid of the "LAST_NAME:" part of the string
            p.setLastName(values[1]);
            values[2] = values[2].substring(11); //gets rid of "FIRST_NAME:"
            p.setFirstName(values[2]);
            values[3] = values[3].substring(3); //gets rid of "QP:"
            values[3] = values[3] + "_U"; //adds Utility
            p.setPositions(values[3]);
            values[4] = values[4].substring(3); //gets rid of "AB:"
            p.setAtBats(Integer.parseInt(values[4]));
            values[5] = values[5].substring(2); //gets rid of "R:"
            p.setRuns(Integer.parseInt(values[5]));
            values[6] = values[6].substring(2); //gets rid of "H:"
            p.calculateBA(Integer.parseInt(values[6]), Integer.parseInt(values[4])); //BA = hits/ab
            p.setHits(Integer.parseInt(values[6]));
            values[7] = values[7].substring(3); //gets rid of "HR:"
            p.setHomeRuns(Integer.parseInt(values[7]));
            values[8] = values[8].substring(4); //gets rid of "RBI:"
            p.setRBIs(Integer.parseInt(values[8]));
            values[9] = values[9].substring(3); //gets rid of "SB:"
            p.setStolenBases(Double.parseDouble(values[9]));
            values[10] = values[10].substring(6); //gets rid of "NOTES:"
            p.setNotes(values[10]);
            values[11] = values[11].substring(14); //gets rid of "YEAR_OF_BIRTH:"
            p.setYearOfBirth(Integer.parseInt(values[11]));
            values[12] = values[12].substring(16, values[12].length() - 1); //gets rid of "NATION_OF_BIRTH:"
            p.setNationOfBirth(values[12]);
            p.setTeamPosition("");
            p.setContract("-");
            p.setSalary(0);
            hittersList.add(p);
        }
    }
    
    public void setPitchers(ArrayList<String> pitchers) {
        this.pitchers = FXCollections.observableArrayList(pitchers);
        
        //create a Player object for all the hitters
        for (String s : pitchers) {
            Player p = new Player();
            String[] values = s.split(","); //split the string with the commas, so now we have an array of stats
            values[0] = values[0].substring(6); //gets rid of the "TEAM:" part of the string
            p.setProTeam(values[0]);
            values[1] = values[1].substring(10); //gets rid of the "LAST_NAME:" part of the string
            p.setLastName(values[1]);
            values[2] = values[2].substring(11); //gets rid of "FIRST_NAME:"
            p.setFirstName(values[2]);
            values[3] = values[3].substring(3); //gets rid of "IP:"
            p.setInningsPitched(Double.parseDouble(values[3]));
            values[4] = values[4].substring(3); //gets rid of "ER:"
            p.calculateERA(Double.parseDouble(values[3]), Integer.parseInt(values[4])); //ERA = (ER * 9) / IP
            p.setEarnedRuns(Integer.parseInt(values[4]));
            values[5] = values[5].substring(2); //gets rid of "W:"
            p.setWins(Integer.parseInt(values[5]));
            values[6] = values[6].substring(3); //gets rid of "SV:"
            p.setSaves(Integer.parseInt(values[6]));
            values[7] = values[7].substring(2); //gets rid of "H:"
            p.setHitsAllowed(Integer.parseInt(values[7]));
            values[8] = values[8].substring(3); //gets rid of "BB:"
            p.calculateWHIP(Integer.parseInt(values[7]), Integer.parseInt(values[8]), Double.parseDouble(values[3])); //WHIP = (H+BB)/IP
            p.setWalksAllowed(Integer.parseInt(values[8]));
            values[9] = values[9].substring(2); //gets rid of "K:"
            p.setStrikeouts(Integer.parseInt(values[9]));
            values[10] = values[10].substring(6); //gets rid of "NOTES:"
            p.setNotes(values[10]);
            values[11] = values[11].substring(14); //gets rid of "YEAR_OF_BIRTH:"
            p.setYearOfBirth(Integer.parseInt(values[11]));
            values[12] = values[12].substring(16, values[12].length() - 1); //gets rid of "NATION_OF_BIRTH:"
            p.setNationOfBirth(values[12]);
            p.setPositions("P");
            p.setTeamPosition("");
            p.setContract("-");
            p.setSalary(0);
            p.setHitter(false);
            pitchersList.add(p);
        }
    }
    
    public void setPlayers() {
        //add hitters
        for (Player p : hittersList) {
            players.add(p);
        }
        
        //add pitchers
        for (Player p : pitchersList) {
            players.add(p);
        }
        
        //sort by name
        Collections.sort(players);
    }
    
    public void setObservablePlayers(ObservableList<Player> players) {
        this.players = players;
    }
    
    public ObservableList<Player> getObservableHitters() {
        return hittersList;
    }
    
    public void setObservableHitters(ObservableList<Player> hitters) {
        hittersList = hitters;
    }
    
    public ObservableList<Player> getObservablePitchers() {
        return pitchersList;
    }
    
    public void setObservablePitchers(ObservableList<Player> pitchers) {
        pitchersList = pitchers;
    }
    
    public void removePlayer(Player playerToRemove) {
        players.remove(playerToRemove);
        if (playerToRemove.isHitter)
            hittersList.remove(playerToRemove);
        else
            pitchersList.remove(playerToRemove);
    }
    
    public void removeTeam(Team teamToRemove) {
        for (Player p : teamToRemove.getStartingPlayers()) {
                p.setFantasyTeam("");
                addPlayer(p);
            }
            
            for (Player p : teamToRemove.getTaxiPlayers()) {
                p.setFantasyTeam("");
                addPlayer(p);
            }
            teamToRemove.clearStartingPlayers();
            teamToRemove.clearTaxiPlayers();
            teams.remove(teamToRemove);
    }
    
    public void addPlayer(Player p) {
        boolean b = false;
        players.add(p);
        ObservableList<String> positions = p.getPositionsArray();
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals("P")) {      //he's a pitcher
                pitchersList.add(p);
                b = true;
            }
        }
        if (!b) //he's a hitter
            hittersList.add(p);
        
        Collections.sort(players);
        Collections.sort(pitchersList);
        Collections.sort(hittersList);
    }
    
    public void addTeam(Team t) {
        teams.add(t);
    }
    
    public void setTeams(ObservableList<Team> teams) {
        this.teams = teams;
    }
    
    public void calcEstimatedValue() {
        hitterSortList = FXCollections.observableArrayList(hittersList);
        pitcherSortList = FXCollections.observableArrayList(pitchersList);
        int totalMoneyRemaining = 0;
        double medianSalaryHitters;
        double medianSalaryPitchers;
        int estimatedValue;
        for (Team t : teams) {
            totalMoneyRemaining += t.getMoneyLeft(); //calculates the total money remaining in the draft
        }
        
        for (Player p : hittersList) {
            calcHittersRank(p); //calculates the average rank for each hitter and sets it
        }
        
        for (Player p : pitchersList) {
            calcPitchersRank(p); //calculates the average rank for each pitcher and sets it
        }
        
        medianSalaryHitters = (double)totalMoneyRemaining / (2 * hittersList.size()); //sets median hitter salary
        medianSalaryPitchers = (double)totalMoneyRemaining / pitchersList.size(); //sets median pitcher salary
        
        for (Player p : players) {
            if (p.isAHitter()) { //player is a hitter
                estimatedValue = (int)((medianSalaryHitters * (hittersList.size() * (2.0 / p.getAvgRank()))) + 0.5);
                p.setValue(estimatedValue); //sets the player's esimated value
            }
            else { //player is a pitcher
                estimatedValue = (int)((medianSalaryPitchers * (pitchersList.size() * (2.0 / p.getAvgRank()))) + 0.5);
                p.setValue(estimatedValue); //sets the player's esimated value
            }
        }
    }
    
    public void calcHittersRank(Player p) {
        Collections.sort(hitterSortList, new PlayerRorWComparator());
        int runsRank = (hitterSortList.indexOf(p) + 1);
        Collections.sort(hitterSortList, new PlayerHRorSVComparator());
        int hrRank = (hitterSortList.indexOf(p) + 1);
        Collections.sort(hitterSortList, new PlayerRBIorKComparator());
        int rbiRank = (hitterSortList.indexOf(p) + 1);
        Collections.sort(hitterSortList, new PlayerSBorERAComparator());
        int sbRank = (hitterSortList.indexOf(p) + 1);
        Collections.sort(hitterSortList, new PlayerBAorWHIPComparator());
        int baRank = (hitterSortList.indexOf(p) + 1);
        
        int avgRank = (int)(((runsRank + hrRank + rbiRank + sbRank + baRank) / 5.0) + 0.5);
        p.setAvgRank(avgRank);
    }
    
    public void calcPitchersRank(Player p) {
        Collections.sort(pitcherSortList, new PlayerRorWComparator());
        int winsRank = (pitcherSortList.indexOf(p) + 1);
        Collections.sort(pitcherSortList, new PlayerHRorSVComparator());
        int savesRank = (pitcherSortList.indexOf(p) + 1);
        Collections.sort(pitcherSortList, new PlayerRBIorKComparator());
        int ksRank = (pitcherSortList.indexOf(p) + 1);
        Collections.sort(pitcherSortList, new PlayerSBorERAComparator());
        int eraRank = (pitcherSortList.indexOf(p) + 1);
        Collections.sort(pitcherSortList, new PlayerBAorWHIPComparator());
        int whipRank = (pitcherSortList.indexOf(p) + 1);
        
        int avgRank = (int)(((winsRank + savesRank + ksRank + eraRank + whipRank) / 5.0) + 0.5);
        p.setAvgRank(avgRank);
    }
}
