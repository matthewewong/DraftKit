package dk.gui;

import dk.data.Draft;
import dk.data.Player;
import static dk.gui.DK_GUI.CLASS_HEADING_LABEL;
import static dk.gui.DK_GUI.CLASS_PROMPT_LABEL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class makes a dialog for the player screen (add/editing players)
 *
 * @author Matthew Wong
 */
public class PlayerDialog extends Stage {
    Player player;
    
    //controls for the dialog
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label playerFirstNameLabel;
    TextField playerFirstNameTextField;
    Label playerLastNameLabel;
    TextField playerLastNameTextField;
    Label playerProTeamLabel;
    ComboBox playerProTeamComboBox;
    CheckBox catcherCheckBox;
    CheckBox firstBaseCheckBox;
    CheckBox secondBaseCheckBox;
    CheckBox thirdBaseCheckBox;
    CheckBox shortstopCheckBox;
    CheckBox outfieldCheckBox;
    CheckBox pitcherCheckBox;
    Button completeButton;
    Button cancelButton;
    
    //see which button the user pressed
    String selection;
    
    //constants
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String PLAYER_FIRST_NAME_PROMPT = "First Name: ";
    public static final String PLAYER_LAST_NAME_PROMPT = "Last Name: ";
    public static final String PLAYER_PRO_TEAM_PROMPT = "Pro Team:" ;
    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    
    public PlayerDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        //make others wait until we finish the dialog options
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //get our container
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        //heading label; depends on when we're adding or editing
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        //get the player first name
        playerFirstNameLabel = new Label(PLAYER_FIRST_NAME_PROMPT);
        playerFirstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        playerFirstNameTextField = new TextField();
        playerFirstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setFirstName(newValue);
        });
        
        //and the player last name
        playerLastNameLabel = new Label(PLAYER_FIRST_NAME_PROMPT);
        playerLastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        playerLastNameTextField = new TextField();
        playerLastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setLastName(newValue);
        });
        
        //and the combo box
        playerProTeamLabel = new Label(PLAYER_PRO_TEAM_PROMPT);
        playerProTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        playerProTeamComboBox = new ComboBox();
        //LOAD COMBO BOX
        playerProTeamComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String proTeam = newValue.toString();
                player.setProTeam(proTeam);
            }
        });
        
        //CHECKBOXES
        
        //and the buttons
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        //event handlers for buttons
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        
        //arrange them in a grid pane
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(playerFirstNameLabel, 0, 1, 1, 1);
        gridPane.add(playerFirstNameTextField, 1, 1, 1, 1);
        gridPane.add(playerLastNameLabel, 0, 2, 1, 1);
        gridPane.add(playerLastNameTextField, 1, 2, 1, 1);
        gridPane.add(playerProTeamLabel, 0, 3, 1, 1);
        gridPane.add(playerProTeamComboBox, 1, 3, 1, 1);
        //CHECKBOXES
        gridPane.add(completeButton, 0, 6, 1, 1);
        gridPane.add(cancelButton, 1, 6, 1, 1);

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
