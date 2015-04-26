package dk.handler;

import static draftkit.DK_PropertyType.REMOVE_TEAM_MESSAGE;
import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.Player;
import dk.data.Team;
import dk.gui.DK_GUI;
import dk.gui.MessageDialog;
import dk.gui.TeamDialog;
import dk.gui.YesNoCancelDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 * This class deals with all the stuff in the teams screen
 *
 * @author Matthew Wong
 */
public class TeamHandler {
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    TeamDialog td;
    
    //table
    TableView<Player> teamStartingTable;
    TableView<Player> teamTaxiTable;
    
    //list of players = team
    ObservableList<Player> teamStartingPlayers;
    ObservableList<Player> teamTaxiPlayers;
    
    public TeamHandler(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        teamStartingPlayers = FXCollections.observableArrayList();
        td = new TeamDialog(initPrimaryStage, draft, messageDialog);
    }
    
    public void updateButtons(DK_GUI gui, Button addTeam, Button removeTeam, Button editTeam) {
        if (!(gui.getDataManager().getDraft().getTeams().isEmpty()) && gui.getDataManager().getDraft().getTeams().size() < 12) { //THERE ARE TEAMS!!
            addTeam.setDisable(false);
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
        else if (gui.getDataManager().getDraft().getTeams().size() == 12) { //FULL LEAGUE!
            addTeam.setDisable(true);
        }
        else if (gui.getDataManager().getDraft().getTeams().isEmpty()) { //NO TEAMS!
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }
    
    public void handleAddTeamRequest(DK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        td.showAddFantasyTeamDialog();
        
        //did the user confirm?
        if (td.wasCompleteSelected()) {
            //get the team
            Team team = td.getTeam();
            
            //add it to the combo box AND the draft
            gui.getTeamsComboBox().getItems().add(team.getTeamName());
            draft.addTeam(team);
            
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //do nothing
        }
    }
    
    public void handleEditTeamRequest(DK_GUI gui, String teamName) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        Team teamToEdit = draft.getTeam(teamName);
        td.showEditFantasyTeamDialog(teamToEdit);
        
        //did the user confirm?
        if (td.wasCompleteSelected()) {
            //update the team
            Team team = td.getTeam();
            gui.getTeamsComboBox().getItems().remove(teamName);
            teamToEdit.setTeamName(team.getTeamName());
            teamToEdit.setOwnerName(team.getOwnerName());
            gui.getTeamsComboBox().getItems().add(team.getTeamName());
            
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //do nothing
        }        
    }
    
    public void handleRemoveTeamRequest(DK_GUI gui, String teamName) {
        //prompt to save unsaved work
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_TEAM_MESSAGE));
        
        //get the selection
        String selection = yesNoCancelDialog.getSelection();

        //if the selection was yes, remove the team
        if (selection.equals(YesNoCancelDialog.YES)) {
            DraftDataManager ddm = gui.getDataManager();
            Draft draft = ddm.getDraft();
            Team teamToRemove = draft.getTeam(teamName);
            draft.removeTeam(teamToRemove);
            gui.getTeamsComboBox().getItems().remove(teamName);
            
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
    }
}
