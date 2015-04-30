package dk.handler;

import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.Player;
import dk.data.Team;
import dk.gui.DK_GUI;
import dk.gui.MessageDialog;
import dk.gui.PlayerDialog;
import dk.gui.YesNoCancelDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * This class deals with all of the stuff in the player screen
 *
 * @author Matthew Wong
 */
public class PlayerHandler {
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    PlayerDialog pd;
    PlayerDialog pdEdit;
    
    //table
    TableView<Player> playersTable;
    
    TableView<Player> teamStartingTable;
    TableView<Player> teamTaxiTable;
    
    //player lists
    ObservableList<Player> regularPlayerList; //list we loaded
    ObservableList<Player> hitterList; //all the hitters
    ObservableList<Player> pitcherList; //all the pitchers
    ObservableList<Player> allPlayersList; //all the players list
    ObservableList<Player> catcherList; //catcher list
    ObservableList<Player> firstBaseList; //first base list
    ObservableList<Player> thirdBaseList; //third base list
    ObservableList<Player> ciList; //middle infield list
    ObservableList<Player> secondBaseList; //second base list
    ObservableList<Player> ssList; //shortstop list
    ObservableList<Player> miList; //middle infield list
    ObservableList<Player> ofList; //outfield list
    ObservableList<Player> utilityList; //utility list (hitters)
    ObservableList<Player> pitchersList; //pitchers list
    
    //constant strings
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
    public static final String FREE_AGENT = "Free Agent";
    
    //used for adding a player to a team
    public final String CATCHERS = "C";
    public final String FIRST_BASE = "1B";
    public final String SECOND_BASE = "2B";
    public final String THIRD_BASE = "3B";
    public final String SHORTSTOP = "SS";
    public final String CORNER_INFIELD = "CI";
    public final String MIDDLE_INFIELD = "MI";
    public final String OUTFIELD = "OF";
    public final String UTILITY = "U";
    public final String PITCHERS = "P";
    
    public PlayerHandler(Stage primaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog, DK_GUI gui) {
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        pdEdit = new PlayerDialog(primaryStage, draft, messageDialog);
        pd = new PlayerDialog(primaryStage, draft);
        regularPlayerList = FXCollections.observableArrayList();
        hitterList = FXCollections.observableArrayList();
        pitcherList = FXCollections.observableArrayList();
        allPlayersList = FXCollections.observableArrayList();
        catcherList = FXCollections.observableArrayList();
        firstBaseList = FXCollections.observableArrayList();
        thirdBaseList = FXCollections.observableArrayList();
        ciList = FXCollections.observableArrayList();
        secondBaseList = FXCollections.observableArrayList();
        ssList = FXCollections.observableArrayList();
        miList = FXCollections.observableArrayList();
        ofList = FXCollections.observableArrayList();
        utilityList = FXCollections.observableArrayList();
        pitchersList = FXCollections.observableArrayList();
        initLists(gui);
    }
    
    public void initLists(DK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getPlayers()); //gets all the players
        hitterList = FXCollections.observableArrayList(draft.getObservableHitters()); //gets all the hitters
        pitcherList = FXCollections.observableArrayList(draft.getObservablePitchers()); //gets all the pitchers
        
        //CLEAR THE LISTS
        allPlayersList.clear();
        catcherList.clear();
        firstBaseList.clear();
        secondBaseList.clear();
        thirdBaseList.clear();
        ssList.clear();
        ciList.clear();
        miList.clear();
        ofList.clear();
        utilityList.clear();
        pitchersList.clear();
        
        //fill the ALL list
        for (Player p : regularPlayerList) {
            allPlayersList.add(p);
        }
        
        //fill the CATCHERS list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("C"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                catcherList.add(p);
        }
        
        //fill the FIRST BASEMEN list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("1B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                firstBaseList.add(p);
        }
        
        //fill the CORNER INFIELD list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("1B"); //finds all the players at first base
            if (findPlayers != -1) //we found a player at first base; that's a CI position!
                ciList.add(p);
            else {
                findPlayers = positions.indexOf("3B"); //finds all players at third base
                if (findPlayers != -1) //we found a player at third base; that's a CI position!
                    ciList.add(p);
            }
        }
        
        //fill the THIRD BASE list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("3B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                thirdBaseList.add(p);
        }
        
        //fill hte SECOND BASE list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("2B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                secondBaseList.add(p);
        }
        
        //fill the MIDDLE INFIELD list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("2B"); //finds all the players at second base
            if (findPlayers != -1) //we found a player at second base; that's a MI position!
                miList.add(p);
            else {
                findPlayers = positions.indexOf("SS"); //finds all players at shortstop
                if (findPlayers != -1) //we found a player at shortstop; that's a MI position!
                    miList.add(p);
            }
        }
        
        //fill the SHORTSTOP list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("SS"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                ssList.add(p);
        }
        
        //fill the OUTFIELD list
        for (Player p : hitterList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("OF"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                ofList.add(p);
        }
        
        //fill the UTILITY list
        for (Player p : hitterList) {
            utilityList.add(p);
        }
        
        //fill the PITCHERS list
        for (Player p : pitcherList) {
            pitchersList.add(p);
        }
    }
    
    /**
     * This method edits the table and the columns, sent by the user
     * @param table the table to be edited
     * @param col1 new column
     * @param col2 new column
     * @param col3 new column
     * @param col4 new column
     * @param col5 new column
     */
    public void handleChangePlayersTableRequest(TableView<Player> table, TableColumn col1, 
                                                TableColumn col2, TableColumn col3, 
                                                TableColumn col4, TableColumn col5) {
        playersTable = table;
        playersTable.getColumns().remove(5);
        playersTable.getColumns().add(5, col1);
        playersTable.getColumns().remove(6);
        playersTable.getColumns().add(6, col2);
        playersTable.getColumns().remove(7);
        playersTable.getColumns().add(7, col3);
        playersTable.getColumns().remove(8);
        playersTable.getColumns().add(8, col4);
        playersTable.getColumns().remove(9);
        playersTable.getColumns().add(9, col5);
    }
    
    /**
     * Gets all the players and puts them in a table.
     * @param gui the UI
     * @param table the table
     */
    public void handleAllRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(regularPlayerList);
    }
    
    /**
     * Gets all the catchers and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleCatcherRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(catcherList);
    }
    
    /**
     * Gets all the first basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleFirstBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(firstBaseList);
    }
    
    /**
     * Gets all the corner infielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleCornerInfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(ciList);
    }
    
    /**
     * Gets all the third basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleThirdBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(thirdBaseList);
    }
    
    /**
     * Gets all the second basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleSecondBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(secondBaseList);
    }
    
    /**
     * Gets all the middle infielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleMiddleInfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(miList);
    }
    
    /**
     * Gets all the shortstops and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleShortstopRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(ssList);
    }
    
    /**
     * Gets all the outfielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleOutfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(ofList);
    }
    
    /**
     * Gets all the Utility players (hitters) and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleUtilityRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(utilityList);
    }
    
    /**
     * Gets all the pitchers and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handlePitcherRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        playersTable = table;
        playersTable.setItems(pitchersList);
    }
    
    public void handleTextFieldChangeRequest(DK_GUI gui, String selection, TableView<Player> table, String text) {
        playersTable = table;
        ObservableList<Player> unchangedList = FXCollections.observableArrayList();
        ObservableList<Player> changedList = FXCollections.observableArrayList();
        
        //get the list we want
        if (selection.equals(ALL_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(allPlayersList);
        else if (selection.equals(C_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(catcherList);
        else if (selection.equals(FIRSTBASE_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(firstBaseList);
        else if (selection.equals(CI_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(ciList);
        else if (selection.equals(THIRDBASE_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(thirdBaseList);
        else if (selection.equals(SECONDBASE_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(secondBaseList);
        else if (selection.equals(MI_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(miList);
        else if (selection.equals(SS_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(ssList);
        else if (selection.equals(OF_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(ofList);
        else if (selection.equals(HITTERS_RADIO_BUTTON))
            unchangedList = FXCollections.observableArrayList(utilityList);
        else
            unchangedList = FXCollections.observableArrayList(pitcherList);
        
        //get the list to present to the table
        text = text.toLowerCase();
        if (text.equals("")) //empty string
        {
            changedList = unchangedList;
            playersTable.setItems(changedList);
        }
        else {
            for (Player p : unchangedList) { //scroll through each player
                String firstName = p.getFirstName().toLowerCase();
                String lastName = p.getLastName().toLowerCase();
                if (firstName.startsWith(text) || lastName.startsWith(text)) //player matches text
                    changedList.add(p);
            }
        }
        playersTable.setItems(changedList);
    }
    
    public void handleAddNewPlayerRequest(DK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        pd.showAddPlayerDialog();
        
        //did the user confirm?
        if (pd.wasCompleteSelected()) {
            //get the player
            Player player = pd.getPlayer();
            
            //add it to the available players list
            draft.addPlayer(player);
            initLists(gui);
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //do nothing
        }
    }
    
    public void handleEditPlayerRequest(DK_GUI gui, Player playerToEdit, TableView<Player> starting, TableView<Player> taxi) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        pdEdit.showEditPlayerDialog(playerToEdit, gui);
        
        teamStartingTable = starting;
        teamTaxiTable = taxi;
        
        //did the user confirm?
        if (pdEdit.wasCompleteSelected()) {
            //get the player
            Player player = pdEdit.getPlayer();
            playerToEdit.setContract(player.getContract());
            playerToEdit.setSalary(player.getSalary());
            playerToEdit.setTeamPosition(player.getTeamPosition());
            
            if (!(playerToEdit.getFantasyTeam().equals(player.getFantasyTeam()))) {
                //different teams, so move it
                if (playerToEdit.getFantasyTeam().equals("")) {
                    //player was a free agent
                    Team luckyTeam = draft.getTeam(player.getFantasyTeam());
                    luckyTeam.addStartingPlayer(playerToEdit);
                    draft.removePlayer(playerToEdit);
                    initLists(gui);
                    
                    playerToEdit.setFantasyTeam(player.getFantasyTeam()); //we do this regardless
                }
                else if (player.getFantasyTeam().equals(FREE_AGENT)) {
                    //player is now put into the free agents list
                    Team unluckyTeam = draft.getTeam(playerToEdit.getFantasyTeam()); //get the previous team
                    unluckyTeam.removeStartingPlayer(playerToEdit);
                    draft.addPlayer(playerToEdit); //add the player to the free agent list
                    initLists(gui);
                    
                    playerToEdit.setFantasyTeam("");
                }
                else {
                    Team unluckyTeam = draft.getTeam(playerToEdit.getFantasyTeam()); //get the previous team
                    unluckyTeam.removeStartingPlayer(playerToEdit);
                    
                    Team luckyTeam = draft.getTeam(player.getFantasyTeam()); //get the new team
                    luckyTeam.addStartingPlayer(playerToEdit);
                    
                    playerToEdit.setFantasyTeam(player.getFantasyTeam());
                }
            }
            
            //since the draft was edited since it was last saved, update the top toolbar controls
            gui.getFileController().markAsEdited(gui);
        }
        else {
            //do nothing
        }
    }
}
