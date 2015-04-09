package dk.gui;

import static draftkit.DK_StartupConstants.*;
import draftkit.DK_PropertyType;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * This class provides the GUI for this application, managing all the UI components
 * for editing a draft and exporting it to a site.
 *
 * @author Matthew Wong
 */
public class DK_GUI {
    //constants that grabs the style sheet to get a nice presentation
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "csb_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;
    
    //manage all the data
    DraftDataManager dataManager;
    
    //manage the draft I/O
    DraftFileManager draftFileManager;
    
    //manages the exporting of the pages
    DraftExporter draftExporter;
    
    //handles interactions with files
    FileHandler fileHandler;
    
    //handles screen selections
    ScreenSelectHandler screenHandler;
    
    //handles interactions with info controls
    DraftEditHandler editHandler;
    
    //handles requests to add or edit player stuff
    PlayerHandler playerHandler;
    
    //handles requests to add or edit team stuff
    TeamHandler teamHandler;
    
    //hhands requests to edit draft stuff
    DraftHandler draftHandler;
    
    //application window
    Stage primaryStage;
    
    //organizes the big picture containers for the application GUI
    BorderPane draftPane;
    
    //top toolbar and its components
    FlowPane topToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;
    
    //bottom toolbar and components
    FlowPane botToolbarPane;
    Button playerScreenButton;
    Button teamScreenButton;
    Button standingsScreenButton;
    Button draftScreenButton;
    Button mlbTeamsButton;
    
    //organize components in a border pane
    BorderPane workspacePane;
    boolean workspaceActivated;
    
    //have VBox's for the different screens in this application
    VBox playersPane;
    VBox teamPane;
    VBox standingsPane;
    VBox draftSelectPane;
    VBox MLBTeamsPane;
    
    //used for the players pane
    Pane playerDataPane;
    Label playersHeadingLabel;
    HBox playersToolbar;
    ToggleGroup group;
    RadioButton allButton;
    RadioButton cButton;
    RadioButton firstBaseButton;
    RadioButton ciButton;
    RadioButton thirdBaseButton;
    RadioButton secondBaseButton;
    RadioButton miButton;
    RadioButton ssButton;
    RadioButton ofButton;
    RadioButton uButton;
    RadioButton pButton;
    HBox playersRadioButton;
    TextField searchPlayerTextField;
    Label searchPlayerLabel;
    Button addPlayerButton;
    Button removePlayerButton;
    
    //used for the teams pane
    GridPane teamDataPane;
    Label teamHeadingLabel;
    TextField teamNameTextField;
    Label teamNameLabel;
    TextField teamOwnerTextField;
    Label teamOwnerLabel;
    Button addTeamButton;
    Button removeTeamButton;
    ComboBox teamSelectComboBox;
    Label teamSelectLabel;
    
    //used for the standings pane
    Label standingsHeadingLabel;
    
    //used for the draft pane
    GridPane draftOptionsPane;
    Label draftHeadingLabel;
    Button draftBestPlayerButton;
    Button startAutoDraftButton;
    Button pauseAutoDraftButton;
    
    //used for the mlbTeams pane
    Label mlbTeamsHeadingLabel;
    
    //tables
    TableView<Player> playersTable;
    TableView<Player> teamsTable;
    TableView<Team> standingsTable;
    TableView<Player> draftTable;
    TableView<Player> mlbTeamsTable;
    
    //dialogs
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    /**
     * Constructor for making the GUI.
     * 
     * @param initPrimaryStage Window inside which the GUI will be displayed.
     */
    public DK_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }
    
    /**
     * Accessor method for the data manager.
     * 
     * @return The DraftDataManager used by this UI.
     */
    public DraftDataManager getDataManager() {
        return dataManager;
    }
    
    /**
     * Accessor method for the file handler
     * 
     * @return the FileHandler used by this UI.
     */
    public FileHandler getFileController() {
        return fileHandler;
    }
    
    /**
     * Accessor method for the draft file manager.
     * 
     * @return the DraftFileManager used by this UI.
     */
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }
    
    /**
     * Accessor method for the site exporter.
     * 
     * @return the SiteExporter used by this UI.
     */
    public DraftExporter getDraftExporter() {
        return draftExporter;
    }
    
    /**
     * Accessor method for the window (stage).
     * 
     * @return the window (Stage) used by this UI.
     */
    public Stage getWindow() {
        return primaryStage;
    }
    
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }
    
    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }
    
    /**
     * Mutator method for the data manager.
     *
     * @param initDataManager the DraftDataManager to be used by this UI.
     */
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    /**
     * Mutator method for the course file manager.
     *
     * @param initDraftFileManager the DraftFileManager to be used by this UI.
     */
    public void setDraftFileManager(DraftFileManager initCourseFileManager) {
        draftFileManager = initCourseFileManager;
    }

    /**
     * Mutator method for the site exporter.
     *
     * @param initSiteExporter the draftExporter to be used by this UI.
     */
    public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        draftExporter = initSiteExporter;
    }
    /**
     * initialize the user interface for use.
     *
     * @param windowTitle the text to appear in the UI window's title bar.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle) throws IOException {
        // init dialogs
        initDialogs();
        
        // init the toolbar
        initFileToolbar();

        // init the center workspace
        initWorkspace();

        // set up the event handlers
        initEventHandlers();

        // start up the window
        initWindow(windowTitle);
    }

    /**
     * When called, this function puts the workspace into the window,
     * revealing the controls for editing a Draft.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // put the workspace in the GUI
            draftPane.setCenter(teamDataPane);
            workspaceActivated = true;
        }
    }
    
    /**
     * This function takes all of the data out of the draftToReload 
     * argument and loads its values into the user interface controls.
     * 
     * @param draftToReload the draft whose data we'll load into the GUI.
     */
    @Override
    public void reloadDraft(Draft draftToReload) {
        // if necessary, activate the workspace
        if (!workspaceActivated) {
            activateWorkspace();
        }
        
        // we don't want to respond to events when initializing the selections
        draftHandler.enable(false);

        //get the players table
        
        // enable the handler so we can respond to user interactions
        draftHandler.enable(true);
    }
    
    /**
     * This method is used to activate/deactivate topToolbar buttons when
     * they can and cannot be used so as to provide foolproof design. NOT FOR HW 5
     * 
     * @param saved Describes whether the loaded Course has been saved or not.
     */
    /*public void updateTopToolbarControls(boolean saved) {
        //tells the button whether it should be saved
        saveDraftButton.setDisable(saved);

        //once editing, these buttons are enabled
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);

        //new, load and exit buttons are never disabled
    }*/
    
    //Initialize dialogs
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }
     
    
    //Initialize topToolbar buttons
    private void initTopToolbar() {
        topToolbarPane = new FlowPane();

        // some start enabled (new, load, exit) and others disabled
        newDraftButton = initChildButton(topToolbarPane, DK_PropertyType.NEW_DRAFT_ICON, DK_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(topToolbarPane, DK_PropertyType.LOAD_DRAFT_ICON, DK_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(topToolbarPane, DK_PropertyType.SAVE_DRAFT_ICON, DK_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(topToolbarPane, DK_PropertyType.EXPORT_DRAFT_ICON, DK_PropertyType.EXPORT_DRAFT_TOOLTIP, true);
        exitButton = initChildButton(topToolbarPane, DK_PropertyType.EXIT_ICON, DK_PropertyType.EXIT_TOOLTIP, false);
    }
    
    //Initialize botToolbar buttons
    private void initBotToolbar() {
        botToolbarPane = new FlowPane();

        // only team button is disabled, and others enabled
        playerScreenButton = initChildButton(botToolbarPane, DK_PropertyType.PLAYER_SELECT_ICON, DK_PropertyType.PLAYER_SELECT_TOOLTIP, false);
        teamScreenButton = initChildButton(botToolbarPane, DK_PropertyType.TEAM_SELECT_ICON, DK_PropertyType.TEAM_SELECT_TOOLTIP, true);
        standingsScreenButton = initChildButton(botToolbarPane, DK_PropertyType.STANDINGS_SELECT_ICON, DK_PropertyType.STANDINGS_SELECT_TOOLTIP, false);
        draftScreenButton = initChildButton(botToolbarPane, DK_PropertyType.DRAFT_SELECT_ICON, DK_PropertyType.DRAFT_SELECT_TOOLTIP, false);
        mlbTeamsButton = initChildButton(botToolbarPane, DK_PropertyType.MLB_TEAMS_SELECT_ICON, DK_PropertyType.MLB_TEAMS_SELECT_TOOLTIP, false);
    }
    
    //create and set up all the controls to go in the app workspace
    private void initWorkspace() throws IOException {
        // the screen controls
        initPlayerScreenControls();
        initTeamScreenControls();
        initStandingsScreenControls();
        initDraftScreenControls();
        initMLBTeamsScreenControls();

        //this holds all the workspace components
        workspacePane = new BorderPane();
        workspacePane.setTop(topToolbarPane);
        workspacePane.setCenter(teamPane);
        workspacePane.setBottom(botToolbarPane);
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);

        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }
    
    //initializes controls in the player screen
    private void initPlayerScreenControls() throws IOException {
        //entire player screen
        playersPane = new VBox();
        
        //holds the search bar, add/remove a player, and the player label
        playerDataPane = new GridPane();
        playersHeadingLabel = initGridLabel(playerDataPane, DK_PropertyType.PLAYERS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        
        //gets the toolbar for the table
        playersToolbar = initGridHBox(playerDataPane, 0, 1, 1, 1);
        addPlayerButton = initChildButton(playersToolbar, DK_PropertyType.ADD_ICON, DK_PropertyType.ADD_PLAYER_TOOLTIP, false);
        removePlayerButton = initChildButton(playersToolbar, DK_PropertyType.MINUS_ICON, DK_PropertyType.REMOVE_PLAYER_TOOLTIP, false);
        searchPlayerLabel = initGridLabel(playerDataPane, DK_PropertyType.SEARCH_PLAYER_LABEL, CLASS_PROMPT_LABEL, 2, 1, 1, 1);
        searchPlayerTextField = initGridTextField(playerDataPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 3, 1, 1, 1);
        
        //holds the radio buttons
        playersRadioButton = initGridHBox(playerDataPane, 1, 3, 5, 1);
        group = new ToggleGroup();
        allButton = new RadioButton("All");
        allButton.setToggleGroup(group);
        cButton = new RadioButton("C");
        cButton.setToggleGroup(group);
        firstBaseButton = new RadioButton("1B");
        firstBaseButton.setToggleGroup(group);
        ciButton = new RadioButton("CI");
        ciButton.setToggleGroup(group);
        thirdBaseButton = new RadioButton("3B");
        thirdBaseButton.setToggleGroup(group);
        secondBaseButton = new RadioButton("2B");
        secondBaseButton.setToggleGroup(group);
        miButton = new RadioButton("MI");
        miButton.setToggleGroup(group);
        ssButton = new RadioButton("SS");
        ssButton.setToggleGroup(group);
        ofButton = new RadioButton("OF");
        ofButton.setToggleGroup(group);
        uButton = new RadioButton("U");
        uButton.setToggleGroup(group);
        pButton = new RadioButton("P");
        pButton.setToggleGroup(group);
        playersRadioButton.getChildren().add(allButton);
        playersRadioButton.getChildren().add(cButton);
        playersRadioButton.getChildren().add(firstBaseButton);
        playersRadioButton.getChildren().add(ciButton);
        playersRadioButton.getChildren().add(thirdBaseButton);
        playersRadioButton.getChildren().add(secondBaseButton);
        playersRadioButton.getChildren().add(miButton);
        playersRadioButton.getChildren().add(ssButton);
        playersRadioButton.getChildren().add(ofButton);
        playersRadioButton.getChildren().add(uButton);
        playersRadioButton.getChildren().add(pButton);
        
        playersTable = new TableView();
        playersPane.getChildren().add(playerDataPane);
        playersPane.getChildren().add(playersTable);
        playersPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //initializes controls in the team screen
    private void initTeamScreenControls() throws IOException {
        teamPane = new VBox();
        //NOT FOR HW 5
    }
    
    //initializes controls in the standings screen
    private void initStandingsScreenControls() throws IOException {
        standingsPane = new VBox();
        //NOT FOR HW 5
    }
    
    //initializes controls in the draft screen
    private void initDraftScreenControls() throws IOException {
        draftSelectPane = new VBox();
    }
    
    //initializes controls in the MLB Teams screen
    private void initMLBTeamsScreenControls() throws IOException {
        MLBTeamsPane = new VBox();
    }
}
