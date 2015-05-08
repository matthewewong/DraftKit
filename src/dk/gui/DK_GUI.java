package dk.gui;

import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.DraftDataView;
import dk.data.Player;
import dk.data.Team;
import dk.file.DraftFileManager;
import dk.handler.FileHandler;
import dk.handler.MLBHandler;
import dk.handler.PlayerHandler;
import dk.handler.StandingsHandler;
import dk.handler.TeamHandler;
import static draftkit.DK_StartupConstants.*;
import draftkit.DK_PropertyType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
/**
 * This class provides the GUI for this application, managing all the UI components
 * for editing a draft and exporting it to a site.
 *
 * @author Matthew Wong
 */
public class DK_GUI implements DraftDataView {
    //constants that grabs the style sheet to get a nice presentation
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "dk_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_SUBSUBHEADING_LABEL = "subsubheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;
    
    //these are for the buttons
    static final String PLAYERS_BUTTON = "Players";
    static final String TEAM_BUTTON = "Team";
    static final String STANDINGS_BUTTON = "Standings";
    static final String DRAFT_BUTTON = "Draft";
    static final String MLB_TEAMS_BUTTON = "MLB Teams";
    
    String lastSelection;
    String newSelection = "";
    
    //these are for the radio buttons
    static final String ALL_RADIO_BUTTON = "All";
    static final String HITTERS_RADIO_BUTTON = "Hitters"; //utility
    static final String PITCHERS_RADIO_BUTTON = "Pitchers"; //pitchers
    static final String C_RADIO_BUTTON = "Catcher";
    static final String FIRSTBASE_RADIO_BUTTON = "First Base";
    static final String SECONDBASE_RADIO_BUTTON = "Second Base";
    static final String THIRDBASE_RADIO_BUTTON = "Third Base";
    static final String SS_RADIO_BUTTON = "Shortstop";
    static final String CI_RADIO_BUTTON = "Corner Infield";
    static final String MI_RADIO_BUTTON = "Middle Infield";
    static final String OF_RADIO_BUTTON = "Outfield";
    
    String lastRadioButtonClicked;
    
    //manage all the data
    DraftDataManager dataManager;
    
    //manage the draft I/O
    DraftFileManager draftFileManager;
    
    //handles interactions with files
    FileHandler fileHandler;
    
    //handles requests to add or edit player stuff
    PlayerHandler playerHandler;
    
    //handles requests to add or edit team stuff
    TeamHandler teamHandler;
    
    //handles requests to edit the standings screen
    StandingsHandler standingsHandler;
    
    //handles requests to edit draft screen stuff
    //DraftHandler draftHandler; NOT FOR HW 6
    
    //handles requests to edit the mlb teams screen
    MLBHandler mlbHandler;
    
    //application window
    Stage primaryStage;
    
    //stage's scene graph
    Scene primaryScene;
    
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
    GridPane playerDataPane;
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
    Label draftNameLabel;
    TextField draftNameTextField;
    HBox teamToolbar;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    ComboBox teamSelectComboBox;
    Label teamSelectLabel;
    Label startingLineupHeadingLabel;
    Label taxiSquadHeadingLabel;
    ScrollPane teamScrollPane;
    
    //used for the standings pane
    GridPane standingsDataPane;
    Label standingsHeadingLabel;
    
    //used for the draft pane
    GridPane draftOptionsPane;
    Label draftHeadingLabel;
    HBox draftToolbar;
    Button draftBestPlayerButton;
    Button startAutoDraftButton;
    Button pauseAutoDraftButton;
    
    //used for the mlbTeams pane
    GridPane mlbTeamsDataPane;
    Label mlbTeamsHeadingLabel;
    ComboBox mlbTeamsComboBox;
    
    //used for getting the players
    ArrayList<String> hitters;
    ArrayList<String> pitchers;
    
    //tables
    TableView<Player> playersTable;
    TableView<Player> teamsStartingTable;
    TableView<Player> teamsTaxiTable;
    TableView<Team> standingsTable;
    TableView<Player> draftTable;
    TableView<Player> mlbTeamsTable;
    
    //table columns
    TableColumn firstNameColumn;
    TableColumn lastNameColumn;
    TableColumn proTeamColumn;
    TableColumn positionsColumn;
    TableColumn yearOfBirthColumn;
    
    TableColumn runsColumn;
    TableColumn homeRunsColumn;
    TableColumn RBIsColumn;
    TableColumn stolenBaseColumn;
    TableColumn baColumn;
    
    TableColumn winsColumn;
    TableColumn savesColumn;
    TableColumn strikeoutsColumn;
    TableColumn eraColumn;
    TableColumn whipColumn;
    
    TableColumn valueColumn;
    TableColumn notesColumn;
    
    TableColumn RorWColumn;
    TableColumn HRorSVColumn;
    TableColumn RBIorKColumn;
    TableColumn SBorERAColumn;
    TableColumn BAorWHIPColumn;
    
    //team columns
    TableColumn startingPositionColumn;
    TableColumn startingFirstNameColumn;
    TableColumn startingLastNameColumn;
    TableColumn startingProTeamColumn;
    TableColumn startingPositionsColumn;
    TableColumn startingRorWColumn;
    TableColumn startingHRorSVColumn;
    TableColumn startingRBIorKColumn;
    TableColumn startingSBorERAColumn;
    TableColumn startingBAorWHIPColumn;
    TableColumn startingValueColumn;
    TableColumn startingContractColumn;
    TableColumn startingSalaryColumn;
    
    TableColumn taxiPositionColumn;
    TableColumn taxiFirstNameColumn;
    TableColumn taxiLastNameColumn;
    TableColumn taxiProTeamColumn;
    TableColumn taxiPositionsColumn;
    TableColumn taxiRorWColumn;
    TableColumn taxiHRorSVColumn;
    TableColumn taxiRBIorKColumn;
    TableColumn taxiSBorERAColumn;
    TableColumn taxiBAorWHIPColumn;
    TableColumn taxiValueColumn;
    TableColumn taxiContractColumn;
    TableColumn taxiSalaryColumn;
    
    //mlb teams columns
    TableColumn mlbFirstNameColumn;
    TableColumn mlbLastNameColumn;
    TableColumn mlbPositionsColumn;
    
    //fantasy standings columns
    TableColumn teamNameColumn;
    TableColumn playersNeededColumn;
    TableColumn moneyLeftColumn;
    TableColumn moneyPerPlayerColumn;
    TableColumn totRunsColumn;
    TableColumn totHomeRunsColumn;
    TableColumn totRBIsColumn;
    TableColumn totSBColumn;
    TableColumn avgBattingAverageColumn;
    TableColumn totWinsColumn;
    TableColumn totSavesColumn;
    TableColumn totStrikeoutsColumn;
    TableColumn avgERAColumn;
    TableColumn avgWHIPColumn;
    TableColumn totPointsColumn;
    
    //draft table columns
    TableColumn pickNumColumn;
    TableColumn draftFirstNameColumn;
    TableColumn draftLastNameColumn;
    TableColumn draftTeamNameColumn;
    TableColumn draftContractColumn;
    TableColumn draftSalaryColumn;
    
    //and the column description
    static final String COL_FIRST_NAME = "First";
    static final String COL_LAST_NAME = "Last";
    static final String COL_PRO_TEAM = "Pro Team";
    static final String COL_POSITIONS = "Positions";
    static final String COL_YEAR_OF_BIRTH = "Year Of Birth";
    static final String COL_ESTIMATED_VALUE = "Estimated Value";
    static final String COL_NOTES = "Notes";
    
    static final String COL_RUNS = "R";
    static final String COL_HOMERUNS = "HR";
    static final String COL_RBIS = "RBI";
    static final String COL_STOLEN_BASES = "SB";
    static final String COL_BATTING_AVERAGE = "BA";
    
    static final String COL_WINS = "W";
    static final String COL_SAVES = "SV";
    static final String COL_STRIKEOUTS = "K";
    static final String COL_ERA = "ERA";
    static final String COL_WHIP = "WHIP";
    
    static final String COL_RORW = "R/W";
    static final String COL_HRORSV = "HR/SV";
    static final String COL_RBIORK = "RBI/K";
    static final String COL_SBORERA = "SB/ERA";
    static final String COL_BAORWHIP = "BA/WHIP";
    
    static final String COL_TEAM_POSITION = "Position";
    static final String COL_CONTRACT = "Contract";
    static final String COL_SALARY = "Salary";
    
    static final String COL_TEAM_NAME = "Team Name";
    static final String COL_PLAYERS_NEEDED = "Players Needed";
    static final String COL_MONEY_LEFT = "$ Left";
    static final String COL_MONEY_PER_PLAYER = "$ PP";
    static final String COL_TOTAL_POINTS = "Total Points";
    
    static final String COL_PICK_NUMBER = "Pick #";
    
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
     * Accessor method for the site exporter. NOT USED FOR HW 5 
     * 
     * @return the SiteExporter used by this UI.
     */
    /*public DraftExporter getDraftExporter() {
        return draftExporter;
    }*/
    
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
    
    public ComboBox getTeamsComboBox() {
        return teamSelectComboBox;
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
     * Mutator method for the draft file manager.
     *
     * @param initDraftFileManager the DraftFileManager to be used by this UI.
     */
    public void setDraftFileManager(DraftFileManager initDraftFileManager) {
        draftFileManager = initDraftFileManager;
    }

    /**
     * Mutator method for the site exporter.  NOT USED FOR HW 5
     *
     * @param initSiteExporter the draftExporter to be used by this UI.
     */
    /*public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        draftExporter = initSiteExporter;
    }*/
    
    /**
     * initialize the user interface for use.
     *
     * @param windowTitle the text to appear in the UI window's title bar.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle, ArrayList<String> hitters, ArrayList<String> pitchers) throws IOException {
        //get the players
        this.hitters = hitters;
        this.pitchers = pitchers;
        dataManager.getDraft().setHitters(hitters);
        dataManager.getDraft().setPitchers(pitchers);
        dataManager.getDraft().setPlayers();

        // init dialogs
        initDialogs();
        
        // init the top toolbar
        initTopToolbar();
        
        // init the bot toolbar
        initBotToolbar();

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
            draftPane.setCenter(teamPane);
            draftPane.setBottom(botToolbarPane);
            lastSelection = TEAM_BUTTON;
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
        
        draftPane.setCenter(teamPane);
        screenSelectHandler(lastSelection, TEAM_BUTTON);
        allButton.setSelected(true);
        playerHandler.initLists(this);      //gets the updated player lists
        playerHandler.handleAllRadioButtonRequest(this, playersTable);
        lastRadioButtonClicked = ALL_RADIO_BUTTON;
        teamSelectComboBox.getItems().clear();
        teamHandler.handleLoadComboBoxRequest(this, teamSelectComboBox);
        draftNameTextField.setText(dataManager.getDraft().getDraftName());
        teamHandler.updateButtons(this, addTeamButton, removeTeamButton, editTeamButton);
        standingsHandler.editStandingsTableContents(standingsTable, draftToReload);
        mlbTeamsComboBox.getSelectionModel().select(0);
        mlbTeamsTable.setItems(mlbHandler.getAtlantaTeamList());
        dataManager.getDraft().calcEstimatedValue();
    }
    
    /**
     * This method is used to activate/deactivate topToolbar buttons when
     * they can and cannot be used so as to provide foolproof design. NOT FOR HW 5
     * 
     * @param saved Describes whether the loaded Course has been saved or not.
     */
    public void updateTopToolbarControls(boolean saved) {
        //tells the button whether it should be saved
        saveDraftButton.setDisable(saved);

        //once editing, these buttons are enabled
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);

        //new, load and exit buttons are never disabled
    }
    
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
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
                
        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }
    
    //initializes controls in the player screen
    private void initPlayerScreenControls() throws IOException {
        //entire player screen; make it look nice
        playersPane = new VBox();
        playersPane.setPadding(new Insets(10, 20, 20, 20));
        playersPane.setSpacing(10);
        
        //holds the search bar, add/remove a player, and the player label
        playerDataPane = new GridPane();
        playersHeadingLabel = initGridLabel(playerDataPane, DK_PropertyType.PLAYERS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        
        //gets the toolbar for the table
        playersToolbar = initGridHBox(playerDataPane, 0, 2, 1, 1);
        addPlayerButton = initChildButton(playersToolbar, DK_PropertyType.ADD_ICON, DK_PropertyType.ADD_PLAYER_TOOLTIP, false);
        removePlayerButton = initChildButton(playersToolbar, DK_PropertyType.MINUS_ICON, DK_PropertyType.REMOVE_PLAYER_TOOLTIP, false);
        searchPlayerLabel = initGridLabel(playerDataPane, DK_PropertyType.SEARCH_PLAYER_LABEL, CLASS_PROMPT_LABEL, 4, 2, 1, 1);
        searchPlayerTextField = initGridTextField(playerDataPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 5, 2, 5, 1);
        
        //holds the radio buttons
        playersRadioButton = initGridHBox(playerDataPane, 1, 4, 5, 1);
        group = new ToggleGroup();
        allButton = new RadioButton("All");
        allButton.setToggleGroup(group);
        allButton.setSelected(true);
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
        
        //for getting the correct table
        lastRadioButtonClicked = ALL_RADIO_BUTTON;
        
        //make it look nicer
        playersRadioButton.setPadding(new Insets(20, 30, 30, 30));
        playersRadioButton.setSpacing(20);
        
        //add the table
        playersTable = new TableView();
        
        //and the columns
        firstNameColumn = new TableColumn(COL_FIRST_NAME);
        lastNameColumn = new TableColumn(COL_LAST_NAME);
        proTeamColumn = new TableColumn(COL_PRO_TEAM);
        positionsColumn = new TableColumn(COL_POSITIONS);
        yearOfBirthColumn = new TableColumn(COL_YEAR_OF_BIRTH);
        RorWColumn = new TableColumn(COL_RORW);
        HRorSVColumn = new TableColumn(COL_HRORSV);
        RBIorKColumn = new TableColumn(COL_RBIORK);
        SBorERAColumn = new TableColumn(COL_SBORERA);
        BAorWHIPColumn = new TableColumn(COL_BAORWHIP);
        valueColumn = new TableColumn(COL_ESTIMATED_VALUE);
        notesColumn = new TableColumn(COL_NOTES);
        
        //columns not used at the beginning
        runsColumn = new TableColumn(COL_RUNS);
        homeRunsColumn = new TableColumn(COL_HOMERUNS);
        RBIsColumn = new TableColumn(COL_RBIS);
        stolenBaseColumn = new TableColumn(COL_STOLEN_BASES);
        baColumn = new TableColumn(COL_BATTING_AVERAGE);
        
        winsColumn = new TableColumn(COL_WINS);
        savesColumn = new TableColumn(COL_SAVES);
        strikeoutsColumn = new TableColumn(COL_STRIKEOUTS);
        eraColumn = new TableColumn(COL_ERA);
        whipColumn = new TableColumn(COL_WHIP);
        
        //link the columns to the data
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        proTeamColumn.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        positionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("positions"));
        yearOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("yearOfBirth"));
        RorWColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RorW"));
        HRorSVColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("HRorSV"));
        RBIorKColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RBIorK"));
        SBorERAColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("SBorERA"));
        BAorWHIPColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("BAorWHIP"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<String, String>("value"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<String, String>("notes"));
        
        //cannot sort columns when you are in the ALL button
        RorWColumn.setSortable(false);
        HRorSVColumn.setSortable(false);
        RBIorKColumn.setSortable(false);
        SBorERAColumn.setSortable(false);
        BAorWHIPColumn.setSortable(false);
        
        //let these columns look nicer by setting its width
        firstNameColumn.setMinWidth(75.0);
        lastNameColumn.setMinWidth(95.0);
        RorWColumn.setMinWidth(45.0);
        HRorSVColumn.setMinWidth(60.0);
        RBIorKColumn.setMinWidth(60.0);
        SBorERAColumn.setMinWidth(65.0);
        BAorWHIPColumn.setMinWidth(75.0);
        
        runsColumn.setMinWidth(RorWColumn.getMinWidth());
        homeRunsColumn.setMinWidth(HRorSVColumn.getMinWidth());
        RBIsColumn.setMinWidth(RBIorKColumn.getMinWidth());
        stolenBaseColumn.setMinWidth(SBorERAColumn.getMinWidth());
        baColumn.setMinWidth(BAorWHIPColumn.getMinWidth());
        
        winsColumn.setMinWidth(RorWColumn.getMinWidth());
        savesColumn.setMinWidth(HRorSVColumn.getMinWidth());
        strikeoutsColumn.setMinWidth(RBIorKColumn.getMinWidth());
        eraColumn.setMinWidth(SBorERAColumn.getMinWidth());
        whipColumn.setMinWidth(BAorWHIPColumn.getMinWidth());
        
        //so we can see the notes
        notesColumn.setMinWidth(100.0);
        
        //the user can edit the notes column, so we set the table to editable
        playersTable.setEditable(true);
        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn()); //MAKES COLUMN EDITABLE
        
        //not used at the beginning
        runsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("runs"));
        homeRunsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("homeRuns"));
        RBIsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("rbi"));
        stolenBaseColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("stolenBases"));
        baColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("ba"));
        
        winsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("wins"));
        savesColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("saves"));
        strikeoutsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("strikeouts"));
        eraColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("era"));
        whipColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("whip"));
        
        playersTable.getColumns().add(firstNameColumn);
        playersTable.getColumns().add(lastNameColumn);
        playersTable.getColumns().add(proTeamColumn);
        playersTable.getColumns().add(positionsColumn);
        playersTable.getColumns().add(yearOfBirthColumn);
        playersTable.getColumns().add(RorWColumn);
        playersTable.getColumns().add(HRorSVColumn);
        playersTable.getColumns().add(RBIorKColumn);
        playersTable.getColumns().add(SBorERAColumn);
        playersTable.getColumns().add(BAorWHIPColumn);
        playersTable.getColumns().add(valueColumn);
        playersTable.getColumns().add(notesColumn);
        playersTable.setItems(dataManager.getDraft().getPlayers());
        
        //add everything to the screen
        playersPane.getChildren().add(playerDataPane);
        playersPane.getChildren().add(playersTable);
        playersPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //initializes controls in the team screen
    private void initTeamScreenControls() throws IOException {        
        teamPane = new VBox();
        teamPane.setPadding(new Insets(10, 20, 20, 20));
        teamPane.setSpacing(10);
        
        //scrollpane
        teamScrollPane = new ScrollPane();
        teamScrollPane.setContent(teamPane);
        teamScrollPane.setFitToWidth(true);
        
        //holds the controls for the team screen
        teamDataPane = new GridPane();
        teamHeadingLabel = initGridLabel(teamDataPane, DK_PropertyType.TEAM_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        
        //name of the draft
        draftNameLabel = initGridLabel(teamDataPane, DK_PropertyType.DRAFT_NAME_LABEL, CLASS_PROMPT_LABEL, 0, 2, 1, 1);
        draftNameTextField = initGridTextField(teamDataPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 2, 5, 1);
        
        //team toolbar
        teamToolbar = initGridHBox(teamDataPane, 0, 3, 1, 1);
        addTeamButton = initChildButton(teamToolbar, DK_PropertyType.ADD_ICON, DK_PropertyType.ADD_TEAM_TOOLTIP, false);
        removeTeamButton = initChildButton(teamToolbar, DK_PropertyType.MINUS_ICON, DK_PropertyType.ADD_TEAM_TOOLTIP, true);
        editTeamButton = initChildButton(teamToolbar, DK_PropertyType.EDIT_ICON, DK_PropertyType.EDIT_TEAM_TOOLTIP, true);
        teamSelectLabel = initGridLabel(teamDataPane, DK_PropertyType.TEAM_SELECT_LABEL, CLASS_PROMPT_LABEL, 4, 3, 1, 1);
        teamSelectComboBox = initGridComboBox(teamDataPane, 5, 3, 2, 1);
        
        //labels for the tables
        startingLineupHeadingLabel = initLabel(DK_PropertyType.TEAM_STARTING_LINEUP_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        taxiSquadHeadingLabel = initLabel(DK_PropertyType.TEAM_TAXI_SQUAD_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        
        //tables for the tables
        teamsStartingTable = new TableView();
        teamsStartingTable.setMinHeight(250);
        teamsTaxiTable = new TableView();
        
        //columns for the tables
        startingPositionColumn = new TableColumn(COL_TEAM_POSITION);
        startingFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        startingLastNameColumn = new TableColumn(COL_LAST_NAME);
        startingProTeamColumn = new TableColumn(COL_PRO_TEAM);
        startingPositionsColumn = new TableColumn(COL_POSITIONS);
        startingRorWColumn = new TableColumn(COL_RORW);
        startingHRorSVColumn = new TableColumn(COL_HRORSV);
        startingRBIorKColumn = new TableColumn(COL_RBIORK);
        startingSBorERAColumn = new TableColumn(COL_SBORERA);
        startingBAorWHIPColumn = new TableColumn(COL_BAORWHIP);
        startingValueColumn = new TableColumn(COL_ESTIMATED_VALUE);
        startingContractColumn = new TableColumn(COL_CONTRACT);
        startingSalaryColumn = new TableColumn(COL_SALARY);
        
        taxiPositionColumn = new TableColumn(COL_TEAM_POSITION);
        taxiFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        taxiLastNameColumn = new TableColumn(COL_LAST_NAME);
        taxiProTeamColumn = new TableColumn(COL_PRO_TEAM);
        taxiPositionsColumn = new TableColumn(COL_POSITIONS);
        taxiRorWColumn = new TableColumn(COL_RORW);
        taxiHRorSVColumn = new TableColumn(COL_HRORSV);
        taxiRBIorKColumn = new TableColumn(COL_RBIORK);
        taxiSBorERAColumn = new TableColumn(COL_SBORERA);
        taxiBAorWHIPColumn = new TableColumn(COL_BAORWHIP);
        taxiValueColumn = new TableColumn(COL_ESTIMATED_VALUE);
        taxiContractColumn = new TableColumn(COL_CONTRACT);
        taxiSalaryColumn = new TableColumn(COL_SALARY);
        
        startingPositionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("teamPosition"));
        startingFirstNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        startingLastNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        startingProTeamColumn.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        startingPositionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("positions"));
        startingRorWColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RorW"));
        startingHRorSVColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("HRorSV"));
        startingRBIorKColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RBIorK"));
        startingSBorERAColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("SBorERA"));
        startingBAorWHIPColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("BAorWHIP"));
        startingValueColumn.setCellValueFactory(new PropertyValueFactory<String, String>("value"));
        startingContractColumn.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        startingSalaryColumn.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        
        taxiPositionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("teamPosition"));
        taxiFirstNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        taxiLastNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        taxiProTeamColumn.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        taxiPositionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("positions"));
        taxiRorWColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RorW"));
        taxiHRorSVColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("HRorSV"));
        taxiRBIorKColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("RBIorK"));
        taxiSBorERAColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("SBorERA"));
        taxiBAorWHIPColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("BAorWHIP"));
        taxiValueColumn.setCellValueFactory(new PropertyValueFactory<String, String>("value"));
        taxiContractColumn.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        taxiSalaryColumn.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        
        //cannot sort this column
        startingPositionColumn.setSortable(false);
        startingFirstNameColumn.setSortable(false);
        startingLastNameColumn.setSortable(false);
        startingProTeamColumn.setSortable(false);
        startingPositionsColumn.setSortable(false);
        startingRorWColumn.setSortable(false);
        startingHRorSVColumn.setSortable(false);
        startingRBIorKColumn.setSortable(false);
        startingSBorERAColumn.setSortable(false);
        startingBAorWHIPColumn.setSortable(false);
        startingValueColumn.setSortable(false);
        startingContractColumn.setSortable(false);
        startingSalaryColumn.setSortable(false);
        
        taxiPositionColumn.setSortable(false);
        taxiFirstNameColumn.setSortable(false);
        taxiLastNameColumn.setSortable(false);
        taxiProTeamColumn.setSortable(false);
        taxiPositionsColumn.setSortable(false);
        taxiRorWColumn.setSortable(false);
        taxiHRorSVColumn.setSortable(false);
        taxiRBIorKColumn.setSortable(false);
        taxiSBorERAColumn.setSortable(false);
        taxiBAorWHIPColumn.setSortable(false);
        taxiValueColumn.setSortable(false);
        taxiContractColumn.setSortable(false);
        taxiSalaryColumn.setSortable(false);
        
        //link to the tables
        teamsStartingTable.getColumns().add(startingPositionColumn);
        teamsStartingTable.getColumns().add(startingFirstNameColumn);
        teamsStartingTable.getColumns().add(startingLastNameColumn);
        teamsStartingTable.getColumns().add(startingProTeamColumn);
        teamsStartingTable.getColumns().add(startingPositionsColumn);
        teamsStartingTable.getColumns().add(startingRorWColumn);
        teamsStartingTable.getColumns().add(startingHRorSVColumn);
        teamsStartingTable.getColumns().add(startingRBIorKColumn);
        teamsStartingTable.getColumns().add(startingSBorERAColumn);
        teamsStartingTable.getColumns().add(startingBAorWHIPColumn);
        teamsStartingTable.getColumns().add(startingValueColumn);
        teamsStartingTable.getColumns().add(startingContractColumn);
        teamsStartingTable.getColumns().add(startingSalaryColumn);
        
        teamsTaxiTable.getColumns().add(taxiPositionColumn);
        teamsTaxiTable.getColumns().add(taxiFirstNameColumn);
        teamsTaxiTable.getColumns().add(taxiLastNameColumn);
        teamsTaxiTable.getColumns().add(taxiProTeamColumn);
        teamsTaxiTable.getColumns().add(taxiPositionsColumn);
        teamsTaxiTable.getColumns().add(taxiRorWColumn);
        teamsTaxiTable.getColumns().add(taxiHRorSVColumn);
        teamsTaxiTable.getColumns().add(taxiRBIorKColumn);
        teamsTaxiTable.getColumns().add(taxiSBorERAColumn);
        teamsTaxiTable.getColumns().add(taxiBAorWHIPColumn);
        teamsTaxiTable.getColumns().add(taxiValueColumn);
        teamsTaxiTable.getColumns().add(taxiContractColumn);
        teamsTaxiTable.getColumns().add(taxiSalaryColumn);
        
        //add everything to the pane
        teamPane.getChildren().add(teamDataPane);
        teamPane.getChildren().add(startingLineupHeadingLabel);
        teamPane.getChildren().add(teamsStartingTable);
        teamPane.getChildren().add(taxiSquadHeadingLabel);
        teamPane.getChildren().add(teamsTaxiTable);
        teamPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //initializes controls in the standings screen
    private void initStandingsScreenControls() throws IOException {
        standingsPane = new VBox();
        
        //used for the data
        standingsDataPane = new GridPane();
        standingsHeadingLabel = initGridLabel(standingsDataPane, DK_PropertyType.STANDINGS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        
        standingsTable = new TableView();
    
        teamNameColumn = new TableColumn(COL_TEAM_NAME);
        playersNeededColumn = new TableColumn(COL_PLAYERS_NEEDED);
        moneyLeftColumn = new TableColumn(COL_MONEY_LEFT);
        moneyPerPlayerColumn = new TableColumn(COL_MONEY_PER_PLAYER);
        totRunsColumn = new TableColumn(COL_RUNS);
        totHomeRunsColumn = new TableColumn(COL_HOMERUNS);
        totRBIsColumn = new TableColumn(COL_RBIS);
        totSBColumn = new TableColumn(COL_STOLEN_BASES);
        avgBattingAverageColumn = new TableColumn(COL_BATTING_AVERAGE);
        totWinsColumn = new TableColumn(COL_WINS);
        totSavesColumn = new TableColumn(COL_SAVES);
        totStrikeoutsColumn = new TableColumn(COL_STRIKEOUTS);
        avgERAColumn = new TableColumn(COL_ERA);
        avgWHIPColumn = new TableColumn(COL_WHIP);
        totPointsColumn = new TableColumn(COL_TOTAL_POINTS);
        
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("teamName"));
        playersNeededColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("playersNeeded"));
        moneyLeftColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("moneyLeft"));
        moneyPerPlayerColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("moneyPerPlayer"));
        totRunsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totRuns"));
        totHomeRunsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totHomeRuns"));
        totRBIsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totRBIs"));
        totSBColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totStolenBases"));
        avgBattingAverageColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("avgBattingAverage"));
        totWinsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totWins"));
        totSavesColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totSaves"));
        totStrikeoutsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("totStrikeouts"));
        avgERAColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("avgERA"));
        avgWHIPColumn.setCellValueFactory(new PropertyValueFactory<Double, String>("avgWHIP"));
        totPointsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("totalPoints"));
        
        teamNameColumn.setMinWidth(150.0);
        playersNeededColumn.setMinWidth(100.0);
        moneyLeftColumn.setMinWidth(70.0);
        moneyPerPlayerColumn.setMinWidth(70.0);
        totRunsColumn.setMinWidth(45.0);
        totHomeRunsColumn.setMinWidth(60.0);
        totRBIsColumn.setMinWidth(60.0);
        totSBColumn.setMinWidth(65.0);
        avgBattingAverageColumn.setMinWidth(75.0);
        totWinsColumn.setMinWidth(45.0);
        totSavesColumn.setMinWidth(60.0);
        totStrikeoutsColumn.setMinWidth(60.0);
        avgERAColumn.setMinWidth(65.0);
        avgWHIPColumn.setMinWidth(75.0);
        totPointsColumn.setMinWidth(70.0);
        
        standingsTable.getColumns().add(teamNameColumn);
        standingsTable.getColumns().add(playersNeededColumn);
        standingsTable.getColumns().add(moneyLeftColumn);
        standingsTable.getColumns().add(moneyPerPlayerColumn);
        standingsTable.getColumns().add(totRunsColumn);
        standingsTable.getColumns().add(totHomeRunsColumn);
        standingsTable.getColumns().add(totRBIsColumn);
        standingsTable.getColumns().add(totSBColumn);
        standingsTable.getColumns().add(avgBattingAverageColumn);
        standingsTable.getColumns().add(totWinsColumn);
        standingsTable.getColumns().add(totSavesColumn);
        standingsTable.getColumns().add(totStrikeoutsColumn);
        standingsTable.getColumns().add(avgERAColumn);
        standingsTable.getColumns().add(avgWHIPColumn);
        standingsTable.getColumns().add(totPointsColumn);
        
        standingsPane.getChildren().add(standingsDataPane);
        standingsPane.getChildren().add(standingsTable);
        standingsPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //initializes controls in the draft screen
    private void initDraftScreenControls() throws IOException {
        draftSelectPane = new VBox();
        
        //used for the data
        draftOptionsPane = new GridPane();
        draftHeadingLabel = initGridLabel(draftOptionsPane, DK_PropertyType.DRAFT_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        draftToolbar = initGridHBox(draftOptionsPane, 0, 2, 1, 1);
        draftBestPlayerButton = initChildButton(draftToolbar, DK_PropertyType.STAR_ICON, DK_PropertyType.DRAFT_BEST_PLAYER_TOOLTIP, false);
        startAutoDraftButton = initChildButton(draftToolbar, DK_PropertyType.PLAY_ICON, DK_PropertyType.START_AUTO_DRAFT_TOOLTIP, false);
        pauseAutoDraftButton = initChildButton(draftToolbar, DK_PropertyType.PAUSE_ICON, DK_PropertyType.PAUSE_AUTO_DRAFT_TOOLTIP, true);
        
        draftTable = new TableView();
        
        draftSelectPane.getChildren().add(draftOptionsPane);
        draftSelectPane.getChildren().add(draftTable);
        draftSelectPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //initializes controls in the MLB Teams screen
    private void initMLBTeamsScreenControls() throws IOException {
        MLBTeamsPane = new VBox();
        
        //used to get the data
        mlbTeamsDataPane = new GridPane();
        mlbTeamsDataPane.setPadding(new Insets(10, 20, 20, 20));
        mlbTeamsDataPane.setHgap(5);
        mlbTeamsHeadingLabel = initGridLabel(mlbTeamsDataPane, DK_PropertyType.MLB_TEAMS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL, 0, 0, 4, 1);
        mlbTeamsComboBox = initGridComboBox(mlbTeamsDataPane, 91, 5, 5, 4);
        fillMLBTeamsComboBox(mlbTeamsComboBox);
        mlbTeamsComboBox.getSelectionModel().select(0);
        
        mlbTeamsTable = new TableView();
        mlbTeamsTable.setMaxWidth(515.0);
        mlbTeamsTable.setMinHeight(485.0);
        
        //columns
        mlbFirstNameColumn = new TableColumn(COL_FIRST_NAME);
        mlbLastNameColumn = new TableColumn(COL_LAST_NAME);
        mlbPositionsColumn = new TableColumn(COL_POSITIONS);
        
        mlbFirstNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        mlbLastNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        mlbPositionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("positions"));
        
        mlbFirstNameColumn.setMinWidth(120.0);
        mlbLastNameColumn.setMinWidth(160.0);
        mlbPositionsColumn.setMinWidth(100.0);
        
        mlbTeamsTable.getColumns().add(mlbFirstNameColumn);
        mlbTeamsTable.getColumns().add(mlbLastNameColumn);
        mlbTeamsTable.getColumns().add(mlbPositionsColumn);
        
        MLBTeamsPane.getChildren().add(mlbTeamsDataPane);
        MLBTeamsPane.getChildren().add(mlbTeamsTable);
        MLBTeamsPane.setAlignment(Pos.CENTER);
        MLBTeamsPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    //set the window
    private void initWindow(String windowTitle) {
        //set the title
        primaryStage.setTitle(windowTitle);
        
        //get the size of the screen
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        //and size the window
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        //add the top toolbar only
        draftPane = new BorderPane();
        draftPane.setTop(topToolbarPane);
        primaryScene = new Scene(draftPane);
        
        //tie the scene to the window, select the stylesheet, and open the window
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    //init the event handlers
    private void initEventHandlers() throws IOException {
        //file handlers
        fileHandler = new FileHandler(messageDialog, yesNoCancelDialog, draftFileManager);
        
        newDraftButton.setOnAction(e -> {
            fileHandler.handleNewDraftRequest(this);
        });
        
        saveDraftButton.setOnAction(e -> {
            fileHandler.handleSaveDraftRequest(this, dataManager.getDraft());
        });
        
        loadDraftButton.setOnAction(e -> {
            fileHandler.handleLoadDraftRequest(this);
        });
        
        exitButton.setOnAction(e -> {
            fileHandler.handleExitRequest(this);
        });
        
        playerScreenButton.setOnAction(e -> {
            newSelection = PLAYERS_BUTTON;
            screenSelectHandler(lastSelection, newSelection);
        });
        
        teamScreenButton.setOnAction(e -> {
            newSelection = TEAM_BUTTON;
            screenSelectHandler(lastSelection, newSelection);
        });
        
        standingsScreenButton.setOnAction(e -> {
            newSelection = STANDINGS_BUTTON;
            screenSelectHandler(lastSelection, newSelection);
        });
        
        draftScreenButton.setOnAction(e -> {
            newSelection = DRAFT_BUTTON;
            screenSelectHandler(lastSelection, newSelection);
        });
        
        mlbTeamsButton.setOnAction(e -> {
            newSelection = MLB_TEAMS_BUTTON;
            screenSelectHandler(lastSelection, newSelection);
        });
        
        //player handlers
        playerHandler = new PlayerHandler(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog, this);
        
        allButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, RorWColumn, HRorSVColumn, RBIorKColumn, SBorERAColumn, BAorWHIPColumn);
            playerHandler.handleAllRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = ALL_RADIO_BUTTON;
        });
        
        cButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleCatcherRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = C_RADIO_BUTTON;
        });
        
        firstBaseButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleFirstBaseRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = FIRSTBASE_RADIO_BUTTON;
        });
        
        ciButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleCornerInfieldRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = CI_RADIO_BUTTON;
        });
        
        thirdBaseButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleThirdBaseRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = THIRDBASE_RADIO_BUTTON;
        });
        
        secondBaseButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleSecondBaseRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = SECONDBASE_RADIO_BUTTON;
        });
        
        miButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleMiddleInfieldRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = MI_RADIO_BUTTON;
        });
        
        ssButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleShortstopRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = SS_RADIO_BUTTON;
        });
        
        ofButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleOutfieldRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = OF_RADIO_BUTTON;
        });
        
        uButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, runsColumn, homeRunsColumn, RBIsColumn, stolenBaseColumn, baColumn);
            playerHandler.handleUtilityRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = HITTERS_RADIO_BUTTON;
        });
        
        pButton.setOnAction(e -> {
            playerHandler.handleChangePlayersTableRequest(playersTable, winsColumn, savesColumn, strikeoutsColumn, eraColumn, whipColumn);
            playerHandler.handlePitcherRadioButtonRequest(this, playersTable);
            lastRadioButtonClicked = PITCHERS_RADIO_BUTTON;
        });
        
        //register the search players textfield
        registerSearchPlayerTextFieldController(searchPlayerTextField);
        
        //register the draft name text field
        registerDraftNameTextFieldController(draftNameTextField);
        
        //add/edit players
        addPlayerButton.setOnAction(e -> {
            playerHandler.handleAddNewPlayerRequest(this, mlbHandler);
        });
        
        removePlayerButton.setOnAction(e -> {
           playerHandler.handleRemovePlayerRequest(this, playersTable.getSelectionModel().getSelectedItem(), mlbHandler);
        });
        
        playersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                //open the playerhandler editor
                Player p = playersTable.getSelectionModel().getSelectedItem();
                playerHandler.handleEditPlayerRequest(this, p, teamsStartingTable, teamsTaxiTable, standingsTable);
            }
        });
        
        //team handlers
        teamHandler = new TeamHandler(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        
        teamSelectComboBox.setOnAction(e -> {
            teamHandler.handleLoadTeam(this, teamSelectComboBox.getSelectionModel().getSelectedItem().toString(), teamsStartingTable, teamsTaxiTable);
        });
        
        addTeamButton.setOnAction(e -> {
            teamHandler.handleAddTeamRequest(this, standingsTable);
            teamHandler.updateButtons(this, addTeamButton, removeTeamButton, editTeamButton);
        });
        
        editTeamButton.setOnAction(e -> {
            teamHandler.handleEditTeamRequest(this, teamSelectComboBox.getSelectionModel().getSelectedItem().toString(), teamsStartingTable, teamsTaxiTable, standingsTable);
        });
        
        removeTeamButton.setOnAction(e -> {
            teamHandler.handleRemoveTeamRequest(this, teamSelectComboBox.getSelectionModel().getSelectedItem().toString(), standingsTable);
            teamHandler.updateButtons(this, addTeamButton, removeTeamButton, editTeamButton);
            playerHandler.initLists(this);
        });
        
        teamsStartingTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                //open the playerhandler editor
                Player p = teamsStartingTable.getSelectionModel().getSelectedItem();
                playerHandler.handleEditPlayerRequest(this, p, teamsStartingTable, teamsTaxiTable, standingsTable);
            }
        });
        
        //standings handler
        standingsHandler = new StandingsHandler(dataManager.getDraft());
        
        //mlb teams handler
        mlbHandler = new MLBHandler(this);
        mlbTeamsTable.setItems(mlbHandler.getAtlantaTeamList());
        
        mlbTeamsComboBox.setOnAction(e -> {
           mlbHandler.handleTeamChangeRequest(mlbTeamsTable, mlbTeamsComboBox.getSelectionModel().getSelectedItem().toString());
        });
    }
    
    //register the event listener for a text field
    private void registerSearchPlayerTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String text = textField.textProperty().getValue();
            playerHandler.handleTextFieldChangeRequest(this, lastRadioButtonClicked, playersTable, text);
        });
    }
    
    private void registerDraftNameTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String text = textField.textProperty().getValue();
            teamHandler.handleTextFieldChangeRequest(this, text);
        });
    }
    
    //init a button and add it to a container in a toolbar
    private Button initChildButton(Pane toolbar, DK_PropertyType icon, DK_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    
    //init a label and set its stylesheet class
    private Label initLabel(DK_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }
    
    //init a label and place it in a GridPane
    private Label initGridLabel(GridPane container, DK_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }
    
    //init a combo box and put it in a grid pane
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }
    
    //init a text field and put it in a GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
    
    //init an HBox and put it in a GridPane
    private HBox initGridHBox(GridPane container, int col, int row, int colSpan, int rowSpan) {
        HBox hBox = new HBox();
        container.add(hBox, col, row, colSpan, rowSpan);
        return hBox;
    }
    
    //selects the creen to be loaded to the GUI.
    private void screenSelectHandler(String lastSelection, String newSelection) {
        //making the disabled button to be enabled
        if (lastSelection.equals(PLAYERS_BUTTON))
            playerScreenButton.setDisable(false);
        else if (lastSelection.equals(TEAM_BUTTON))
            teamScreenButton.setDisable(false);
        else if (lastSelection.equals(STANDINGS_BUTTON))
            standingsScreenButton.setDisable(false);
        else if (lastSelection.equals(DRAFT_BUTTON))
            draftScreenButton.setDisable(false);
        else
            mlbTeamsButton.setDisable(false);
        
        //make the enabled (pressed) button disabled
        if (newSelection.equals(PLAYERS_BUTTON))
            playerScreenButton.setDisable(true);
        else if (newSelection.equals(TEAM_BUTTON))
            teamScreenButton.setDisable(true);
        else if (newSelection.equals(STANDINGS_BUTTON))
            standingsScreenButton.setDisable(true);
        else if (newSelection.equals(DRAFT_BUTTON))
            draftScreenButton.setDisable(true);
        else
            mlbTeamsButton.setDisable(true);
        
        //make the lastSelection the newSelection
        this.lastSelection = newSelection;
        //open the correct screen
        if (newSelection.equals(PLAYERS_BUTTON))
            draftPane.setCenter(playersPane);
        else if (newSelection.equals(TEAM_BUTTON))
            draftPane.setCenter(teamPane);
        else if (newSelection.equals(STANDINGS_BUTTON))
            draftPane.setCenter(standingsPane);
        else if (newSelection.equals(DRAFT_BUTTON))
            draftPane.setCenter(draftSelectPane);
        else
            draftPane.setCenter(MLBTeamsPane);
    }
    
    private void fillMLBTeamsComboBox(ComboBox cb) {
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
}
