package dk.handler;

import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.data.Player;
import dk.gui.DK_GUI;
import dk.gui.MessageDialog;
import dk.gui.YesNoCancelDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * This class deals with all of the stuff in the player screen
 *
 * @author Matthew Wong
 */
public class PlayerHandler {
    MessageDialog messageDialog; //NOT FOR HW 5
    YesNoCancelDialog yesNoCancelDialog; //NOT FOR HW 5
    
    //table
    TableView<Player> playersTable;
    
    //player lists
    ObservableList<Player> playerList; //sent to specific table
    ObservableList<Player> regularPlayerList; //list we loaded
    
    public PlayerHandler(MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        playerList = FXCollections.observableArrayList();
        regularPlayerList = FXCollections.observableArrayList();
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
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getPlayers()); //gets all the players
        playersTable = table;
        
        playersTable.setItems(regularPlayerList);
    }
    
    /**
     * Gets all the catchers and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleCatcherRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the CATCHERS
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("C"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the first basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleFirstBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the FIRST BASEMEN
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("1B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the corner infielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleCornerInfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the CORNER INFIELDERS
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("1B"); //finds all the players at first base
            if (findPlayers != -1) //we found a player at first base; that's a CI position!
                playerList.add(p);
            else {
                findPlayers = positions.indexOf("3B"); //finds all players at third base
                if (findPlayers != -1) //we found a player at third base; that's a CI position!
                    playerList.add(p);
            }
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the third basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleThirdBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the THIRD BASEMEN
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("3B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the second basemen and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleSecondBaseRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the SECOND BASEMEN
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("2B"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the middle infielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleMiddleInfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the MIDDLE INFIELDERS
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("2B"); //finds all the players at second base
            if (findPlayers != -1) //we found a player at second base; that's a MI position!
                playerList.add(p);
            else {
                findPlayers = positions.indexOf("SS"); //finds all players at shortstop
                if (findPlayers != -1) //we found a player at shortstop; that's a MI position!
                    playerList.add(p);
            }
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the shortstops and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleShortstopRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the SHORTSTOPS
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("SS"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the outfielders and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleOutfieldRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        
        playersTable = table;
        
        //now find the OUTFIELDERS
        for (Player p : regularPlayerList) {
            String positions = p.getPositions(); //gets the positions
            int findPlayers = positions.indexOf("OF"); //finds all the players at the position
            if (findPlayers != -1) //we found a player
                playerList.add(p);
        }
        playersTable.setItems(playerList);
    }
    
    /**
     * Gets all the Utility players (hitters) and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handleUtilityRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservableHitters());
        playersTable = table;
        
        playersTable.setItems(regularPlayerList);
    }
    
    /**
     * Gets all the pitchers and places them into the table.
     * @param gui the UI
     * @param table the table
     */
    public void handlePitcherRadioButtonRequest(DK_GUI gui, TableView<Player> table) {
        //before we do anything, clear the tables
        playerList.clear();
        regularPlayerList.clear();
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        regularPlayerList = FXCollections.observableArrayList(draft.getObservablePitchers());
        playersTable = table;
        
        playersTable.setItems(regularPlayerList);
    }
}
