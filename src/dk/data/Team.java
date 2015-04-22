package dk.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Wong
 */
public class Team {
    ObservableList<Player> startingPlayers;
    ObservableList<Player> taxiPlayers;
    String draftName;
    String teamName;
    String ownerName;
    
    public Team() {
        startingPlayers = FXCollections.observableArrayList();
        taxiPlayers = FXCollections.observableArrayList();
    }
    
    public String getDraftName() {
        return draftName;
    }
    
    public void setDraftName(String newDraftName) {
        draftName = newDraftName;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String newTeamName) {
        teamName = newTeamName;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String newOwnerName) {
        ownerName = newOwnerName;
    }
    
    public void addStartingPlayer(Player p) {
        startingPlayers.add(p);
    }
    
    public void removeStartingPlayer(Player p) {
        startingPlayers.remove(p);
    }
    
    public ObservableList<Player> getStartingPlayers() {
        return startingPlayers;
    }
    
    public void setStartingPlayers(ObservableList<Player> newPlayers) {
        startingPlayers = newPlayers;
    }
    
    public void clearStartingPlayers() {
        startingPlayers.clear();
    }
    
    public void addTaxiPlayer(Player p) {
        taxiPlayers.add(p);
    }
    
    public void removeTaxiPlayer(Player p) {
        taxiPlayers.remove(p);
    }
    
    public ObservableList<Player> getTaxiPlayers() {
        return taxiPlayers;
    }
    
    public void setTaxiPlayers(ObservableList<Player> newPlayers) {
        taxiPlayers = newPlayers;
    }
    
    public void clearTaxiPlayers() {
        taxiPlayers.clear();
    }
}
