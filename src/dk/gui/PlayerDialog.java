package dk.gui;

import dk.data.Draft;
import dk.data.Player;
import static dk.gui.DK_GUI.CLASS_HEADING_LABEL;
import static dk.gui.DK_GUI.CLASS_PROMPT_LABEL;
import static dk.gui.DK_GUI.PRIMARY_STYLE_SHEET;
import static draftkit.DK_StartupConstants.PATH_IMAGES;
import static draftkit.DK_StartupConstants.PATH_IMAGES_PLAYERS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

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
    HBox playerCheckboxesHBox;
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
    
    GridPane editPlayerGridPane;
    ImageView playerImage;
    ImageView flagImage;
    Label playerNameLabel;
    Label playerPositionsLabel;
    Label fantasyTeamLabel;
    ComboBox fantasyTeamsComboBox;
    Label teamPositionLabel;
    ComboBox teamPositionComboBox;
    Label contractLabel;
    ComboBox contractComboBox;
    Label salaryLabel;
    TextField salaryTextField;
    
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
    
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String FANTASY_POSITIONS_PROMPT = "Position: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary ($): ";
    
    public final String CATCHERS = "C";
    public final String FIRST_BASE = "1B";
    public final String SECOND_BASE = "2B";
    public final String THIRD_BASE = "3B";
    public final String SHORTSTOP = "SS";
    public final String OUTFIELD = "OF";
    public final String PITCHERS = "P";
    
    //pro teams
    public final String ATLANTA = "ATL";
    public final String ARIZONA = "AZ";
    public final String CHICAGO = "CHC";
    public final String CINCINNATI = "CIN";
    public final String COLORADO = "COL";
    public final String LOS_ANGELES = "LAD";
    public final String MIAMI = "MIA";
    public final String MILWAUKEE = "MIL";
    public final String NEW_YORK = "NYM";
    public final String PHILADELPHIA = "PHI";
    public final String PITTSBURGH = "PIT";
    public final String SAN_DIEGO = "SD";
    public final String SAN_FRANCISCO = "SF";
    public final String ST_LOUIS = "STL";
    public final String WASHINGTON = "WAS";
    
    public PlayerDialog(Stage primaryStage) {
        //make others wait until we finish the dialog options
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //get our container
        editPlayerGridPane = new GridPane();
        editPlayerGridPane.setPadding(new Insets(10, 20, 20, 20));
        editPlayerGridPane.setHgap(10);
        editPlayerGridPane.setVgap(10);
        
        //heading label; depends on when we're adding or editing
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        
        //get the player images
        playerImage = new ImageView();
        flagImage = new ImageView();
        
        playerNameLabel = new Label();
        
        playerPositionsLabel = new Label();
        
        //fantasy team prompts
        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        fantasyTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        fantasyTeamsComboBox = new ComboBox();
        //LOAD THE TEAMS
        
        //LOAD EVERYTHING ELSE
    }
    
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
        loadComboBox(playerProTeamComboBox);
        playerProTeamComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String proTeam = newValue.toString();
                player.setProTeam(proTeam);
            }
        });
        
        //and the checkboxes
        playerCheckboxesHBox = new HBox();
        playerCheckboxesHBox.setSpacing(10);
        catcherCheckBox = initChildCheckBox(playerCheckboxesHBox, CATCHERS);
        firstBaseCheckBox = initChildCheckBox(playerCheckboxesHBox, FIRST_BASE);
        thirdBaseCheckBox = initChildCheckBox(playerCheckboxesHBox, THIRD_BASE);
        secondBaseCheckBox = initChildCheckBox(playerCheckboxesHBox, SECOND_BASE);
        shortstopCheckBox = initChildCheckBox(playerCheckboxesHBox, SHORTSTOP);
        outfieldCheckBox = initChildCheckBox(playerCheckboxesHBox, OUTFIELD);
        pitcherCheckBox = initChildCheckBox(playerCheckboxesHBox, PITCHERS);
        
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
        
        //set event handler for checkboxes
        catcherCheckBox.setOnAction(e -> {
            player.selectPositions(CATCHERS, catcherCheckBox.isSelected());
        });
        firstBaseCheckBox.setOnAction(e -> {
            player.selectPositions(FIRST_BASE, firstBaseCheckBox.isSelected());
        });
        secondBaseCheckBox.setOnAction(e -> {
            player.selectPositions(SECOND_BASE, secondBaseCheckBox.isSelected());
        });
        thirdBaseCheckBox.setOnAction(e -> {
            player.selectPositions(THIRD_BASE, thirdBaseCheckBox.isSelected());
        });
        shortstopCheckBox.setOnAction(e -> {
            player.selectPositions(SHORTSTOP, shortstopCheckBox.isSelected());
        });
        outfieldCheckBox.setOnAction(e -> {
            player.selectPositions(OUTFIELD, outfieldCheckBox.isSelected());
        });
        pitcherCheckBox.setOnAction(e -> {
            player.selectPositions(PITCHERS, pitcherCheckBox.isSelected());
        });
        
        //arrange them in a grid pane
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(playerFirstNameLabel, 0, 1, 1, 1);
        gridPane.add(playerFirstNameTextField, 1, 1, 1, 1);
        gridPane.add(playerLastNameLabel, 0, 2, 1, 1);
        gridPane.add(playerLastNameTextField, 1, 2, 1, 1);
        gridPane.add(playerProTeamLabel, 0, 3, 1, 1);
        gridPane.add(playerProTeamComboBox, 1, 3, 1, 1);
        gridPane.add(playerCheckboxesHBox, 0, 4, 4, 1);
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
    
    public Player getPlayer() {
        return player;
    }
    
    /**
     * This method loads a custom message into the label and pops open the dialog.
     * 
     * @return the new team
     */
    public Player showAddPlayerDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);
        
        //reset the player object with default values
        player = new Player();
        
        //load UI stuff
        playerFirstNameTextField.setText(player.getFirstName());
        playerLastNameTextField.setText(player.getLastName());
        playerProTeamComboBox.getSelectionModel().select(0);
        catcherCheckBox.setSelected(false);
        firstBaseCheckBox.setSelected(false);
        thirdBaseCheckBox.setSelected(false);
        secondBaseCheckBox.setSelected(false);
        shortstopCheckBox.setSelected(false);
        outfieldCheckBox.setSelected(false);
        pitcherCheckBox.setSelected(false);
        
        //and open it up
        this.showAndWait();
        
        return player;
    }
    
    public void loadGuiData() {
        //loads the UI stuff
        playerFirstNameTextField.setText(player.getFirstName());
        playerLastNameTextField.setText(player.getLastName());
        playerProTeamComboBox.setValue(player.getProTeam());
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditPlayerDialog(Player playerToEdit) {
        //set the dialog title
        setTitle(EDIT_PLAYER_TITLE);
        
        //load the player into our local object
        player = new Player();
        player.setFirstName(playerToEdit.getFirstName());
        player.setLastName(playerToEdit.getLastName());
        player.setProTeam(playerToEdit.getProTeam());
        
        //checkboxes
        ObservableList<String> positions = player.getPositionsArray();
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(CATCHERS))
                catcherCheckBox.setSelected(true);
            else if (positions.get(i).equals(FIRST_BASE))
                firstBaseCheckBox.setSelected(true);
            else if (positions.get(i).equals(SECOND_BASE))
                secondBaseCheckBox.setSelected(true);
            else if (positions.get(i).equals(THIRD_BASE))
                thirdBaseCheckBox.setSelected(true);
            else if (positions.get(i).equals(SHORTSTOP))
                shortstopCheckBox.setSelected(true);
            else if (positions.get(i).equals(OUTFIELD))
                outfieldCheckBox.setSelected(true);
            else if (positions.get(i).equals(PITCHERS))
                pitcherCheckBox.setSelected(true);
            
            //we don't care about the rest, so we end here
        }
        
        //and load it to the gui
        loadGuiData();
               
        //and open it up
        this.showAndWait();
    }
    
    private CheckBox initChildCheckBox(Pane container, String text) {
        CheckBox cB = new CheckBox(text);
        container.getChildren().add(cB);
        return cB;
    }
    
    private void loadComboBox(ComboBox cb) {
        cb.getItems().add(ATLANTA);
        cb.getItems().add(ARIZONA);
        cb.getItems().add(CHICAGO);
        cb.getItems().add(CINCINNATI);
        cb.getItems().add(COLORADO);
        cb.getItems().add(LOS_ANGELES);
        cb.getItems().add(MIAMI);
        cb.getItems().add(MILWAUKEE);
        cb.getItems().add(NEW_YORK);
        cb.getItems().add(PHILADELPHIA);
        cb.getItems().add(PITTSBURGH);
        cb.getItems().add(SAN_DIEGO);
        cb.getItems().add(SAN_FRANCISCO);
        cb.getItems().add(ST_LOUIS);
        cb.getItems().add(WASHINGTON);
    }
    
    private Image getPlayerImage(Player p) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES_PLAYERS + props.getProperty(p.getLastName() + p.getFirstName());
        Image playerImage = new Image(imagePath);
        if (playerImage.isError())
            playerImage = new Image("file:" + PATH_IMAGES_PLAYERS + "AAA_PhotoMissing");
        return playerImage;
    }
}
