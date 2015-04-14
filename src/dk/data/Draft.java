package dk.data;

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
    //ObservableList<Team> teams; //not for HW 5
    ObservableList<String> hitters;
    ObservableList<String> pitchers;
    
    //Player lists
    ObservableList<Player> hittersList;
    ObservableList<Player> pitchersList;
    
    /**
     * Constructor for setting up a Draft
     */
    public Draft() {
        //init the lists
        players = FXCollections.observableArrayList();
        //teams = FXCollections.observableArrayList(); //not for HW5
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
        
        //init the Player lists
        hittersList = FXCollections.observableArrayList();
        pitchersList = FXCollections.observableArrayList();
    }
    
    //Accessor and mutator methods for a draft.
    public void clearPlayers() {
        players.clear();
    }
    
    /*public void clearTeams() {
        teams.clear();
    }*/
    
    public ObservableList<Player> getPlayers() {
        return players;
    }
    
    /*public ObservableList<Team> getTeams() {
        return teams;
    }*/
    
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
            values[5] = values[5].substring(2); //gets rid of "R:"
            p.setRuns(Integer.parseInt(values[5]));
            values[6] = values[6].substring(2); //gets rid of "H:"
            p.calculateBA(Integer.parseInt(values[6]), Integer.parseInt(values[4])); //BA = hits/ab
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
            values[4] = values[4].substring(3); //gets rid of "ER:"
            p.calculateERA(Double.parseDouble(values[3]), Integer.parseInt(values[4])); //ERA = (ER * 9) / IP
            values[5] = values[5].substring(2); //gets rid of "W:"
            p.setWins(Integer.parseInt(values[5]));
            values[6] = values[6].substring(3); //gets rid of "SV:"
            p.setSaves(Integer.parseInt(values[6]));
            values[7] = values[7].substring(2); //gets rid of "H:"
            values[8] = values[8].substring(3); //gets rid of "BB:"
            p.calculateWHIP(Integer.parseInt(values[7]), Integer.parseInt(values[8]), Double.parseDouble(values[3])); //WHIP = (H+BB)/IP
            values[9] = values[9].substring(2); //gets rid of "K:"
            p.setStrikeouts(Integer.parseInt(values[9]));
            values[10] = values[10].substring(6); //gets rid of "NOTES:"
            p.setNotes(values[10]);
            values[11] = values[11].substring(14); //gets rid of "YEAR_OF_BIRTH:"
            p.setYearOfBirth(Integer.parseInt(values[11]));
            values[12] = values[12].substring(16, values[12].length() - 1); //gets rid of "NATION_OF_BIRTH:"
            p.setPositions("P");
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
    public void removePlayer(Player playerToRemove) {
        players.remove(playerToRemove);
    }
    
    /*public void removeTeam(Team teamToRemove) {
        teams.remove(teamToRemove);
    }*/
    
    public void addPlayer(Player p) {
        players.add(p);
    }
    
    /*public void addTeam(Team t) {
        teams.add(t);
    }*/
}
