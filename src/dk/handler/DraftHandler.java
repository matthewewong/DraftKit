package dk.handler;

import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.Player;
import dk.data.PlayerValueComparator;
import dk.data.Team;
import dk.gui.DK_GUI;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public final int MAX_PLAYER_SIZE = 23;
    
    public DraftHandler(DK_GUI gui, PlayerHandler playerHandler, StandingsHandler standingsHandler) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        draftedPlayers = FXCollections.observableArrayList();
        rankedValuedPlayers = FXCollections.observableArrayList(draft.getPlayers());
        pH = playerHandler;
        sH = standingsHandler;
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
        int i = 0;
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        ObservableList<Team> teams = draft.getTeams();
        Team luckyTeam = teams.get(i); //gets the first team
        for (; i < teams.size(); i++) {
            luckyTeam = teams.get(i);
            if (luckyTeam.getStartingPlayers().size() == MAX_PLAYER_SIZE) {
                //DO NOTHING, INCREMENT THE COUNTER
            }
            else {
                break; //break the for loop!
            }
        }
        
        //RESET i
        i = 0;
        
        while (!isDrafted) {
            Player p = rankedValuedPlayers.get(i); //gets the next best player
            ObservableList<String> positions = p.getPositionsArray();  //get the player positions
            ObservableList<String> teamPositions = luckyTeam.getAvailablePositions(); //get the team positions
            for (int index = 0; index < positions.size(); index++) {
                //search through the array of available positions, starting with the first one.
                //if it is found in the available positions, add the player to the team.
                if (teamPositions.indexOf(positions.get(index)) != -1) { //we found it!
                    addPlayerToTeam(gui, draft, luckyTeam, p, positions.get(index), standings);
                    isDrafted = true;
                    break;
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
    }
}
