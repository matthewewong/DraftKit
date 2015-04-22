package dk.gui;

import dk.data.Draft;
import dk.data.Team;
import static dk.gui.DK_GUI.CLASS_HEADING_LABEL;
import static dk.gui.DK_GUI.CLASS_PROMPT_LABEL;
import static dk.gui.DK_GUI.PRIMARY_STYLE_SHEET;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class makes a dialog for the team screen (adding/editing teams)
 *
 * @author Matthew Wong
 */
public class TeamDialog extends Stage {
    //object data behind this UI
    Team team;
    
    //controls for the dialog
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label teamNameLabel;
    TextField teamNameTextField;
    Label ownerNameLabel;
    TextField ownerNameTextField;
    Button completeButton;
    Button cancelButton;
    
    //see which button the user pressed
    String selection;
    
    //constants
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String TEAM_NAME_PROMPT = "Team Name: ";
    public static final String OWNER_NAME_PROMPT = "Owner Name: ";
    public static final String FANTASY_TEAM_HEADING = "Fantasy Team Details";
    public static final String ADD_FANTASY_TEAM_TITLE = "Add New Fantasy Team";
    public static final String EDIT_FANTASY_TEAM_TITLE = "Edit Fantasy Team";
    
    public TeamDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        //make others wait until we finish the dialog options
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //get our container
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        //heading label; depends on when we're adding or editing
        headingLabel = new Label(FANTASY_TEAM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        //get the team name
        teamNameLabel = new Label(TEAM_NAME_PROMPT);
        teamNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        teamNameTextField = new TextField();
        teamNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            team.setTeamName(newValue);
        });
        
        //and the owner name
        ownerNameLabel = new Label(OWNER_NAME_PROMPT);
        ownerNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerNameTextField = new TextField();
        ownerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            team.setOwnerName(newValue);
        });
        
        //and the buttons
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        //event handlers for buttons
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            TeamDialog.this.selection = sourceButton.getText();
            TeamDialog.this.hide();
        };
        
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        //arrange them in a grid pane
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(teamNameLabel, 0, 1, 1, 1);
        gridPane.add(teamNameTextField, 1, 1, 1, 1);
        gridPane.add(ownerNameLabel, 0, 2, 1, 1);
        gridPane.add(ownerNameTextField, 1, 2, 1, 1);
        gridPane.add(completeButton, 0, 3, 1, 1);
        gridPane.add(cancelButton, 1, 3, 1, 1);

        //and put the grid pane in the window
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return YES, NO, or CANCEL, depending on which button the user selected.
     */
    public String getSelection() {
        return selection;
    }
    
    public Team getTeam() {
        return team;
    }
    
    /**
     * This method loads a custom message into the label and pops open the dialog.
     * 
     * @return the new team
     */
    public Team showAddFantasyTeamDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_FANTASY_TEAM_TITLE);
        
        //reset the team object with default values
        team = new Team();
        
        //load UI stuff
        teamNameTextField.setText(team.getTeamName());
        ownerNameTextField.setText(team.getOwnerName());
        
        //and open it up
        this.showAndWait();
        
        return team;
    }
    
    public void loadGuiData() {
        //loads the UI stuff
        teamNameTextField.setText(team.getTeamName());
        ownerNameTextField.setText(team.getOwnerName());
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditFantasyTeamDialog(Team teamToEdit) {
        //set the dialog title
        setTitle(EDIT_FANTASY_TEAM_TITLE);
        
        //load the team into our loacl object
        team = new Team();
        team.setTeamName(teamToEdit.getTeamName());
        team.setOwnerName(teamToEdit.getOwnerName());
        
        //and load it to the gui
        loadGuiData();
               
        //and open it up
        this.showAndWait();
    }
}
