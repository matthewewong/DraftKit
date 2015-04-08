package dk.gui;

import static draftkit.DK_StartupConstants.*;
import draftkit.DK_PropertyType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
    FileHandler fileHander;
    
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
    
    //organize components in a border pane
    BorderPane workspacePane;
    boolean workspaceActivated;
    
    //have VBox's for the different screens in this application
    VBox playersPane;
    VBox teamPane;
    VBox standingsPane;
    VBox draftSelectPane;
    VBox MLBTeamsPane;
    
    //label for the panes
    Label draftHeadingLabel;
    
    //used for the players pane
    GridPane playerDataPane;
    RadioButton playersRadioButton;
    TextField searchPlayerTextField;
    Label searchPlayerLabel;
    Button addPlayerButton;
    Button removePlayerButton;
    
    //used for the teams pane
    GridPane teamDataPane;
    TextField teamNameTextField;
    Label teamNameLabel;
    TextField teamOwnerTextField;
    Label teamOwnerLabel;
    Button addTeamButton;
    Button removeTeamButton;
    ComboBox teamSelectComboBox;
    Label teamSelectLabel;
    
    //used for the draft pane
    GridPane draftOptionsPane;
    Button draftBestPlayerButton;
    Button startAutoDraftButton;
    Button pauseAutoDraftButton;
    
    //tables
    TableView<Player> playersTable;
    TableView<Player> teamsTable;
    TableView<Team> standingsTable;
    TableView<Player> draftTable;
    TableView<Player> mlbTeamsTable;
    
}
