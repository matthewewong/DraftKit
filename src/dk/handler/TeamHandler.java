package dk.handler;

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
        if (!(gui.getTeamsComboBox().getItems().isEmpty())) {
            addTeam.setDisable(false);
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
        else if (gui.getTeamsComboBox().getItems().size() == 12) {
            addTeam.setDisable(true);
        }
        else if (gui.getTeamsComboBox().getItems().isEmpty()) {
            removeTeam.setDisable(true);
            removeTeam.setDisable(true);
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
            
            //add it to the combo box
            gui.getTeamsComboBox().getItems().add(team.getTeamName());
            
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //do nothing
        }
    }
}
