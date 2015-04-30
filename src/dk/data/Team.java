package dk.data;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author Matthew Wong
 */
public class Team {
    ObservableList<Player> startingPlayers;
    ObservableList<Player> taxiPlayers;
    String teamName;
    String ownerName;
    
    //team variables
    //numbers for the positions
    int numC; //max = 2
    int num1B; //max = 1
    int numCI; //max = 1
    int num3B; //max = 1
    int num2B; //max = 1
    int numMI; //max = 1
    int numSS; //max = 1
    int numU; //max = 1
    int numOF; //max = 5
    int numP; //max = 9
    
    //constants
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
    
    public Team() {
        startingPlayers = FXCollections.observableArrayList();
        taxiPlayers = FXCollections.observableArrayList();
        numC = 0;
        num1B = 0;
        numCI = 0;
        num3B = 0;
        num2B = 0;
        numMI = 0;
        numSS = 0;
        numU = 0;
        numOF = 0;
        numP = 0;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String newTeamName) {
        teamName = newTeamName;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String newOwnerName) {
        ownerName = newOwnerName;
    }
    
    public void addStartingPlayer(Player p) {
        startingPlayers.add(p);
        String position = p.getTeamPosition();
        if (position.equals(CATCHERS)) {
            numC++;
            p.setPositionNumber(1);
        }
        else if (position.equals(FIRST_BASE)) {
            num1B++;
            p.setPositionNumber(2);
        }
        else if (position.equals(SECOND_BASE)) {
            num2B++;
            p.setPositionNumber(5);
        }
        else if (position.equals(THIRD_BASE)) {
            num3B++;
            p.setPositionNumber(4);
        }
        else if (position.equals(SHORTSTOP)) {
            numSS++;
            p.setPositionNumber(7);
        }
        else if (position.equals(CORNER_INFIELD)) {
            numCI++;
            p.setPositionNumber(3);
        }
        else if (position.equals(MIDDLE_INFIELD)) {
            numMI++;
            p.setPositionNumber(6);
        }
        else if (position.equals(OUTFIELD)) {
            numOF++;
            p.setPositionNumber(8);
        }
        else if (position.equals(UTILITY)) {
            numU++;
            p.setPositionNumber(9);
        }
        else {
            numP++;
            p.setPositionNumber(10);
        }
        
        Collections.sort(startingPlayers, new PlayerPositionsComparator());
    }
    
    public void removeStartingPlayer(Player p) {
        startingPlayers.remove(p);
        String position = p.getTeamPosition();
        if (position.equals(CATCHERS))
            numC--;
        else if (position.equals(FIRST_BASE))
            num1B--;
        else if (position.equals(SECOND_BASE))
            num2B--;
        else if (position.equals(THIRD_BASE))
            num3B--;
        else if (position.equals(SHORTSTOP))
            numSS--;
        else if (position.equals(CORNER_INFIELD))
            numCI--;
        else if (position.equals(MIDDLE_INFIELD))
            numMI--;
        else if (position.equals(OUTFIELD))
            numOF--;
        else if (position.equals(UTILITY))
            numU--;
        else 
            numP--;
    }
    
    public ObservableList<Player> getStartingPlayers() {
        return startingPlayers;
    }
    
    public void setStartingPlayers(ObservableList<Player> newPlayers) {
        startingPlayers = newPlayers;
    }
    
    public void clearStartingPlayers() {
        startingPlayers.clear();
    }
    
    public void addTaxiPlayer(Player p) {
        taxiPlayers.add(p);
    }
    
    public void removeTaxiPlayer(Player p) {
        taxiPlayers.remove(p);
    }
    
    public ObservableList<Player> getTaxiPlayers() {
        return taxiPlayers;
    }
    
    public void setTaxiPlayers(ObservableList<Player> newPlayers) {
        taxiPlayers = newPlayers;
    }
    
    public void clearTaxiPlayers() {
        taxiPlayers.clear();
    }
    
    /**
     * This method is  used to  get all the available positions left for the team to draft.
     * These positions will go into the combo box after we finish this.
     * 
     * @return the available positions for this team to draft
     */
    public ObservableList<String> getAvailablePositions() {
        ObservableList<String> availPositions = FXCollections.observableArrayList();
        if (numC < 2)
            availPositions.add(CATCHERS);
        if (num1B < 1)
            availPositions.add(FIRST_BASE);
        if (numCI < 1)
            availPositions.add(CORNER_INFIELD);
        if (num3B < 1)
            availPositions.add(THIRD_BASE);
        if (num2B < 1)
            availPositions.add(SECOND_BASE);
        if (numMI < 1)
            availPositions.add(MIDDLE_INFIELD);
        if (numSS < 1)
            availPositions.add(SHORTSTOP);
        if (numOF < 5)
            availPositions.add(OUTFIELD);
        if (numU < 1)
            availPositions.add(UTILITY);
        if (numP < 9)
            availPositions.add(PITCHERS);
        return availPositions;
    }
    
    public int getNumC() {
        return numC;
    }
    
    public void setNumC(int initNumC) {
        numC = initNumC;
    }
    
    public int getNum1B() {
        return num1B;
    }
    
    public void setNum1B(int initNum1B) {
        num1B = initNum1B;
    }
    
    public int getNum2B() {
        return num2B;
    }
    
    public void setNum2B(int initNum2B) {
        num2B = initNum2B;
    }
    
    public int getNum3B() {
        return num3B;
    }
    
    public void setNum3B(int initNum3B) {
        num3B = initNum3B;
    }
    
    public int getNumSS() {
        return numSS;
    }
    
    public void setNumSS(int initNumSS) {
        numSS = initNumSS;
    }
    
    public int getNumCI() {
        return numCI;
    }
    
    public void setNumCI(int initNumCI) {
        numCI = initNumCI;
    }
    
    public int getNumMI() {
        return numMI;
    }
    
    public void setNumMI(int initNumMI) {
        numMI = initNumMI;
    }
    
    public int getNumOF() {
        return numOF;
    }
    
    public void setNumOF(int initNumOF) {
        numOF = initNumOF;
    }
    
    public int getNumU() {
        return numU;
    }
    
    public void setNumU(int initNumU) {
        numU = initNumU;
    }
    
    public int getNumP() {
        return numP;
    }
    
    public void setNumP(int initNumP) {
        numP = initNumP;
    }
}
