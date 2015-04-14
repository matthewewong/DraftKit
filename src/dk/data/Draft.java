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
    //things we'll put in the page
    ObservableList<String> players;
    //ObservableList<Team> teams; //not for HW 5
    ObservableList<String> hitters;
    ObservableList<String> pitchers;
    
    /**
     * Constructor for setting up a Draft
     */
    public Draft() {
        //init the lists
        players = FXCollections.observableArrayList();
        //teams = FXCollections.observableArrayList(); //not for HW5
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
    }
    
    //Accessor and mutator methods for a draft.
    public void clearPlayers() {
        players.clear();
    }
    
    /*public void clearTeams() {
        teams.clear();
    }*/
    
    public ObservableList<String> getPlayers() {
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
    }
    
    public void setPitchers(ArrayList<String> pitchers) {
        this.pitchers = FXCollections.observableArrayList(pitchers);
    }
    
    public void setPlayers() {
        //add hitters
        for (String s : hitters) {
            players.add(s);
        }
        
        //add pitchers
        for (String s : pitchers) {
            players.add(s);
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
    
    public void addPlayer(String p) {
        players.add(p);
    }
    
    /*public void addTeam(Team t) {
        teams.add(t);
    }*/
}
