package dk.handler;

import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.Player;
import dk.data.PlayerValueComparator;
import dk.data.Team;
import dk.gui.DK_GUI;
import java.util.Collections;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * This class deals with all the stuff in the draft screen.
 *
 * @author Matthew Wong
 */
public class DraftHandler {
    ObservableList<Player> draftedPlayers;
    ObservableList<Player> rankedValuedPlayers;
    
    PlayerHandler pH;
    StandingsHandler sH;
    
    //constants
    public final String CONTRACT_S2 = "S2";
    public final String CONTRACT_X = "X";
    public final int SALARY = 1;
    public final int MAX_STARTER_SIZE = 23;
    public final int MAX_TAXI_SIZE = 8;
    
    //boolean for the thread
    public boolean running = false;
    
    //buttons
    Button sad;
    Button pad;
    
    public DraftHandler(DK_GUI gui, PlayerHandler playerHandler, StandingsHandler standingsHandler) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        draftedPlayers = FXCollections.observableArrayList();
        rankedValuedPlayers = FXCollections.observableArrayList();
        pH = playerHandler;
        sH = standingsHandler;
        pH.setDraftHandler(this);
        sortValuedPlayers(gui);
    }
    
    public void sortValuedPlayers(DK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        rankedValuedPlayers = FXCollections.observableArrayList(draft.getPlayers());
        Collections.sort(rankedValuedPlayers, new PlayerValueComparator());
    }
    
    public ObservableList<Player> getDraftedList() {
        return draftedPlayers;
    }
    
    public void setDraftedList(ObservableList<Player> players) {
        draftedPlayers = players;
    }
    
    public void handleDraftBestPlayerRequest(DK_GUI gui, TableView<Team> standings) {
        boolean isDrafted = false;
        boolean isStartersDone = false;
        boolean isTaxisDone = false;
        
        int i = 0;
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        ObservableList<Team> teams = draft.getTeams();
        Team luckyTeam = teams.get(i); //gets the first team
        
        //does the starting lineup
        for (; i < teams.size(); i++) {
            luckyTeam = teams.get(i);
            if (luckyTeam.getStartingPlayers().size() == MAX_STARTER_SIZE) {
                //DO NOTHING, INCREMENT THE COUNTER
                if (i == teams.size() - 1) { //we reached the end of the starting lineup!
                    isStartersDone = true;
                }
            }
            else {
                break; //break the for loop!
            }
        }
        
        //RESET i
        i = 0;
        
        if (isStartersDone) {
            //do the taxi lineup
            for (; i < teams.size(); i++) {
                luckyTeam = teams.get(i);
                if (luckyTeam.getTaxiPlayers().size() == MAX_TAXI_SIZE) {
                    //DO NOTHING, INCREMENT THE COUNTER
                    if (i == teams.size() - 1) { //we reached the end of the draft!
                        isTaxisDone = true;
                    }
                }
                else {
                    break; //break the for loop!
                }
            }
        }
        
        if (isTaxisDone) {
            running = false;
            resetButtons();
            return;
        }
        //RESET i
        i = 0;
        
        while (!isDrafted) {
            Player p = rankedValuedPlayers.get(i); //gets the next best player
            ObservableList<String> positions = p.getPositionsArray();  //get the player positions
            ObservableList<String> teamPositions = luckyTeam.getAvailablePositions(); //get the team positions
            for (int index = 0; index < positions.size(); index++) {
                if (isStartersDone) {
                    //starters lineup is done; accept everyone now!
                    addPlayerToTaxiTeam(gui, draft, luckyTeam, p, positions.get(index), standings);
                    isDrafted = true;
                    //the draft is now dirty; update the toolbar
                    gui.updateTopToolbarControls(false);
                    break;
                }
                else {
                    //search through the array of available positions, starting with the first one.
                    //if it is found in the available positions, add the player to the team.
                    if (teamPositions.indexOf(positions.get(index)) != -1) { //we found it!
                        addPlayerToTeam(gui, draft, luckyTeam, p, positions.get(index), standings);
                        isDrafted = true;
                        //the draft is now dirty; update the toolbar
                        gui.updateTopToolbarControls(false);
                        break;
                    }
                }
            }
            //if we get here (out of the for loop), increment the player (next best player)
            i++;
        }
    }
    
    public void addPlayerToTeam(DK_GUI gui, Draft draft, Team team, Player player, String position, TableView<Team> standings) {
        player.setFantasyTeam(team.getTeamName());
        player.setTeamPosition(position);
        player.setContract(CONTRACT_S2);
        player.setSalary(SALARY);
        player.setPickNumber(draftedPlayers.size() + 1);
        
        team.addStartingPlayer(player);
        draft.removePlayer(player);
        draftedPlayers.add(player);
        rankedValuedPlayers.remove(player);
        pH.initLists(gui);
        sH.editStandingsTableContents(standings, draft);
        draft.setDraftList(draftedPlayers);
    }
    
    public void addPlayerToTaxiTeam(DK_GUI gui, Draft draft, Team team, Player player, String position, TableView<Team> standings) {
        player.setFantasyTeam(team.getTeamName());
        player.setTeamPosition(position);
        player.setContract(CONTRACT_X);
        player.setSalary(SALARY);
        player.setPickNumber(draftedPlayers.size() + 1);
        
        team.addTaxiPlayer(player);
        draft.removePlayer(player);
        draftedPlayers.add(player);
        rankedValuedPlayers.remove(player);
        pH.initLists(gui);
        sH.editStandingsTableContents(standings, draft);
        draft.setDraftList(draftedPlayers);
    }
    
    //ADDS A PLAYER TO THE DRAFT TABLE
    public void addDraftedPlayer(Player p, Draft draft) {
        p.setPickNumber(draftedPlayers.size() + 1);
        draftedPlayers.add(p);
        rankedValuedPlayers.remove(p);
        draft.setDraftList(draftedPlayers);
    }
    
    //PLAYER'S CONTRACT CHANGED
    public void removeDraftedPlayerFromTable(Player p, Draft draft) {
        int index = draftedPlayers.indexOf(p); //gets the index of the player
        
        for (int i = index + 1; i < draftedPlayers.size(); i++) {
            //iterate through the players in the drafted list, decreasing their pick num by 1
            Player pickNumPlayer = draftedPlayers.get(i);
            pickNumPlayer.setPickNumber(pickNumPlayer.getPickNumber() - 1);
        }
        draftedPlayers.remove(index);
        draft.setDraftList(draftedPlayers);
    }
    
    //PLAYER MOVED TO FA
    public void removeDraftedPlayerToFreeAgency(Player p, Draft draft) {
        int index = draftedPlayers.indexOf(p); //get the index of the player to remove
        for (int i = index + 1; i < draftedPlayers.size(); i++) {
            //iterate through the players in the drafted list, decreasing their pick num by 1
            Player pickNumPlayer = draftedPlayers.get(i);
            pickNumPlayer.setPickNumber(pickNumPlayer.getPickNumber() - 1);
        }
        draftedPlayers.remove(p);
        rankedValuedPlayers.add(p);
        draft.setDraftList(draftedPlayers);
    }
    
    public void handleStartAutoDraftRequest(DK_GUI gui, TableView<Team> standings) {
        //tell the thread you can start
        running = true;
        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(running) { //while we can still run the thread
                    //update the progress
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            handleDraftBestPlayerRequest(gui, standings);
                        }
                    });
                    
                    //sleep for one second
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        
        Thread thread = new Thread(task);
        thread.start();
    }
    
    public void handlePauseAutoDraftRequest() {
        running = false;
    }
    
    public void getButtons(Button startDraft, Button pauseDraft) {
        sad = startDraft;
        pad = pauseDraft;
    }
    
    public void resetButtons() {
        sad.setDisable(false);
        pad.setDisable(true);
    }
}
