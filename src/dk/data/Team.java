package dk.data;

import java.util.Collections;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Wong
 */
public class Team {
    ObservableList<Player> startingPlayers;
    ObservableList<Player> taxiPlayers;
    String ownerName;
    
    StringProperty teamName;
    IntegerProperty playersNeeded;
    IntegerProperty moneyLeft;
    DoubleProperty moneyPerPlayer;
    IntegerProperty totR;
    IntegerProperty totHR;
    IntegerProperty totRBI;
    DoubleProperty totSB;
    DoubleProperty avgBA;
    IntegerProperty totW;
    IntegerProperty totSV;
    IntegerProperty totK;
    DoubleProperty avgERA;
    DoubleProperty avgWHIP;
    IntegerProperty totPoints;
    
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
    public final int MAX_MONEY = 260;
    public final int MAX_STARTING_PLAYERS = 23;
    
    public Team() {
        startingPlayers = FXCollections.observableArrayList();
        taxiPlayers = FXCollections.observableArrayList();
    
        teamName = new SimpleStringProperty();
        playersNeeded = new SimpleIntegerProperty();
        moneyLeft = new SimpleIntegerProperty();
        moneyPerPlayer = new SimpleDoubleProperty();
        totR = new SimpleIntegerProperty();
        totHR = new SimpleIntegerProperty();
        totRBI = new SimpleIntegerProperty();
        totSB = new SimpleDoubleProperty();
        avgBA = new SimpleDoubleProperty();
        totW = new SimpleIntegerProperty();
        totSV = new SimpleIntegerProperty();
        totK = new SimpleIntegerProperty();
        avgERA = new SimpleDoubleProperty();
        avgWHIP = new SimpleDoubleProperty();
        totPoints = new SimpleIntegerProperty();
        
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
        return teamName.get();
    }
    
    public void setTeamName(String newTeamName) {
        teamName.set(newTeamName);
    }
    
    public StringProperty teamNameProperty() {
        return teamName;
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
    
    public int getPlayersNeeded() {
        return playersNeeded.get();
    }
    
    public void setPlayersNeeded(int pn) {
        playersNeeded.set(pn);
    }
    
    public IntegerProperty playersNeededProperty() {
        return playersNeeded;
    }
    
    public int getMoneyLeft() {
        return moneyLeft.get();
    }
    
    public void setMoneyLeft(int moneyLeft) {
        this.moneyLeft.set(moneyLeft);
    }
    
    public IntegerProperty moneyLeftProperty() {
        return moneyLeft;
    }
    
    public double getMoneyPerPlayer() {
        return moneyPerPlayer.get();
    }
    
    public void setMoneyPerPlayer(double mpp) {
        moneyPerPlayer.set(mpp);
    }
    
    public DoubleProperty moneyPerPlayerProperty() {
        return moneyPerPlayer;
    }
    
    public int getTotRuns() {
        return totR.get();
    }
    
    public void setTotRuns(int totRuns) {
        totR.set(totRuns);
    }
    
    public IntegerProperty totRunsProperty() {
        return totR;
    }
    
    public int getTotHomeRuns() {
        return totHR.get();
    }
    
    public void setTotHomeRuns(int totHomeRuns) {
        totHR.set(totHomeRuns);
    }
    
    public IntegerProperty totHomeRunsProperty() {
        return totHR;
    }
    
    public int getTotRBIs() {
        return totRBI.get();
    }
    
    public void setTotRBIs(int totRBIs) {
        totRBI.set(totRBIs);
    }
    
    public IntegerProperty totRBIsProperty() {
        return totRBI;
    }
    
    public double getTotStolenBases() {
        return totSB.get();
    }
    
    public void setTotStolenBases(double totStolenBases) {
        totSB.set(totStolenBases);
    }
    
    public DoubleProperty totStolenBasesProperty() {
        return totSB;
    }
    
    public double getAvgBattingAverage() {
        return avgBA.get();
    }
    
    public void setAvgBattingAverage(double avgBattingAverage) {
        avgBA.set(avgBattingAverage);
    }
    
    public DoubleProperty avgBattingAverageProperty() {
        return avgBA;
    }
    
    public int getTotWins() {
        return totW.get();
    }
    
    public void setTotWins(int totWins) {
        totW.set(totWins);
    }
    
    public IntegerProperty totWinsProperty() {
        return totW;
    }
    
    public int getTotSaves() {
        return totSV.get();
    }
    
    public void setTotSaves(int totSaves) {
        totSV.set(totSaves);
    }
    
    public IntegerProperty totSavesProperty() {
        return totSV;
    }
    
    public int getTotStrikeouts() {
        return totK.get();
    }
    
    public void setTotStrikeouts(int totKs) {
        totK.set(totKs);
    }
    
    public IntegerProperty totStrikeoutsProperty() {
        return totK;
    }
    
    public double getAvgERA() {
        return avgERA.get();
    }
    
    public void setAvgERA(double avgERA) {
        this.avgERA.set(avgERA);
    }
    
    public DoubleProperty avgERAProperty() {
        return avgERA;
    }
    
    public double getAvgWHIP() {
        return avgWHIP.get();
    }
    
    public void setAvgWHIP(double avgWHIP) {
        this.avgWHIP.set(avgWHIP);
    }
    
    public DoubleProperty avgWHIPProperty() {
        return avgWHIP;
    }
    
    public int getTotalPoints() {
        return totPoints.get();
    }
    
    public void setTotalPoints(int totalPoints) {
        totPoints.set(totalPoints);
    }
    
    public IntegerProperty totalPointsProperty() {
        return totPoints;
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
    
    // MAX SIZE (23) - STARTING PLAYERS
    public void calcPlayersNeeded() {
        playersNeeded.set(MAX_STARTING_PLAYERS - startingPlayers.size());
    }
    
    // MAX_MONEY (260) - (salary for each player)
    public void calcMoneyLeft() {
        int total = 0;
        for (Player p : startingPlayers) {
            total += p.getSalary();
        }
        moneyLeft.set(MAX_MONEY - total);
    }
    
    //  MONEY LEFT / PLAYERS NEEDED
    public void calcMoneyPerPlayer() {
        if (playersNeeded.get() == 0)
            moneyPerPlayer.set(-1.0);
        else
            moneyPerPlayer.set((double)moneyLeft.get() / playersNeeded.get());
    }
    
    public void calcTotalR() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (p.isHitter) //ONLY IF THE PLAYER IS A HITTER
                total += p.getRuns();
        }
        totR.set(total);
    }
    
    public void calcTotalHR() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (p.isHitter) //ONLY IF THE PLAYER IS A HITTER
                total += p.getHomeRuns();
        }
        totHR.set(total);
    }
    
    public void calcTotalRBI() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (p.isHitter) //ONLY IF THE PLAYER IS A HITTER
                total += p.getRBIs();
        }
        totRBI.set(total);
    }
    
    public void calcTotalSB() {
        double total = 0.0;
        for (Player p : startingPlayers) {
            if (p.isHitter) //ONLY IF THE PLAYER IS A HITTER
                total += p.getStolenBases();
        }
        totSB.set(total);
    }
    
    public void calcAvgBA() {
        double hits = 0.0;
        int ab = 0;
        for (Player p : startingPlayers) {
            if (p.isHitter) { //ONLY IF THE PLAYER IS A HITTER
                hits += (double)p.getHits();
                ab += p.getAtBats();
            }
        }
        if (ab == 0)
            avgBA.set(0.000);
        else
            avgBA.set(hits / ab);
    }
    
    public void calcTotW() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (!(p.isHitter)) //IS A PITCHER
                total += p.getWins();
        }
        totW.set(total);
    }
    
    public void calcTotSV() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (!(p.isHitter)) //IS A PITCHER
                total += p.getSaves();
        }
        totSV.set(total);
    }
    
    public void calcTotK() {
        int total = 0;
        for (Player p : startingPlayers) {
            if (!(p.isHitter)) //IS A PITCHER
                total += p.getStrikeouts();
        }
        totK.set(total);
    }
    
    public void calcAvgERA() {
        double ip = 0.0;
        int er = 0;
        for (Player p : startingPlayers) {
            if (!(p.isHitter)) {//IS A PITCHER
                ip += p.getInningsPitched();
                er += p.getEarnedRuns();
            }
        }
        if (ip == 0.0)
            avgERA.set(0.00);
        else
            avgERA.set((er * 9) / ip);
    }
    
    public void calcAvgWHIP() {
        double ip = 0.0;
        int bb = 0;
        int h = 0;
        for (Player p : startingPlayers) {
            if (!(p.isHitter)) {//IS A PITCHER
                ip += p.getInningsPitched();
                bb += p.getWalksAllowed();
                h += p.getHitsAllowed();
            }
        }
        if (ip == 0.0)
            avgWHIP.set(0.00);
        else
            avgWHIP.set((h + bb) / ip);
    }
}
