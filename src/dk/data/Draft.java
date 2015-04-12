package dk.data;

import dk.data.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a draft to be edited and then used to generate a site.
 *
 * @author Matthew Wong
 */
public class Draft {
    //things we'll put in the page
    ObservableList<Player> players;
    //ObservableList<Team> teams; //not for HW 5
    
    /**
     * Constructor for setting up a Draft
     */
    public Draft() {
        //init the lists
        players = FXCollections.observableArrayList();
        //teams = FXCollections.observableArrayList(); //not for HW5
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
