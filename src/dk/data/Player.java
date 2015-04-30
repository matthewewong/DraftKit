package dk.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
 *
 * @author Matthew Wong
 */
public class Player implements Comparable {
    final StringProperty first;
    final StringProperty last;
    final StringProperty proTeam;
    final StringProperty positions;
    final IntegerProperty yearOfBirth;
    final StringProperty nationOfBirth;
    final IntegerProperty r;
    final IntegerProperty hr;
    final IntegerProperty rbi;
    final DoubleProperty sb;
    final DoubleProperty ba;
    final IntegerProperty value;
    final StringProperty notes;
    final IntegerProperty win;
    final IntegerProperty sv;
    final IntegerProperty k;
    final DoubleProperty era;
    final DoubleProperty whip;

    //used for ALL players
    final IntegerProperty RorW;
    final IntegerProperty HRorSV;
    final IntegerProperty RBIorK;
    final DoubleProperty SBorERA;
    final DoubleProperty BAorWHIP;
    boolean isHitter = true; //notify us if this is a hitter
    int positionNumber; //tells us what position number the player is for sorting
    
    ArrayList<String> positionsArray;
    String fantasyTeamName;
    
    //starting tables
    final StringProperty teamPosition;
    final StringProperty startingFirst;
    final StringProperty startingLast;
    final StringProperty startingProTeam;
    final StringProperty startingPositions;
    final IntegerProperty startingRorW;
    final IntegerProperty startingHRorSV;
    final IntegerProperty startingRBIorK;
    final DoubleProperty startingSBorERA;
    final DoubleProperty startingBAorWHIP;
    final IntegerProperty startingValue;
    final StringProperty contract;
    final IntegerProperty salary;
    
    final IntegerProperty startingR;
    final IntegerProperty startingHR;
    final IntegerProperty startingRBI;
    final DoubleProperty startingSB;
    final DoubleProperty startingBA;
    final IntegerProperty startingW;
    final IntegerProperty startingSV;
    final IntegerProperty startingK;
    final DoubleProperty startingERA;
    final DoubleProperty startingWHIP;
    
    public Player() {
        first = new SimpleStringProperty();
        last = new SimpleStringProperty();
        proTeam = new SimpleStringProperty();
        positions = new SimpleStringProperty();
        yearOfBirth = new SimpleIntegerProperty();
        nationOfBirth = new SimpleStringProperty();
        r = new SimpleIntegerProperty();
        hr = new SimpleIntegerProperty();
        rbi = new SimpleIntegerProperty();
        sb = new SimpleDoubleProperty();
        ba = new SimpleDoubleProperty();
        value = new SimpleIntegerProperty();
        notes = new SimpleStringProperty();
        win = new SimpleIntegerProperty();
        sv = new SimpleIntegerProperty();
        k = new SimpleIntegerProperty();
        era = new SimpleDoubleProperty();
        whip = new SimpleDoubleProperty();
        RorW = new SimpleIntegerProperty();
        HRorSV = new SimpleIntegerProperty();
        RBIorK = new SimpleIntegerProperty();
        SBorERA = new SimpleDoubleProperty();
        BAorWHIP = new SimpleDoubleProperty();
        teamPosition = new SimpleStringProperty();
        startingFirst = new SimpleStringProperty();
        startingLast = new SimpleStringProperty();
        startingProTeam = new SimpleStringProperty();
        startingPositions = new SimpleStringProperty();
        startingRorW = new SimpleIntegerProperty();
        startingHRorSV = new SimpleIntegerProperty();
        startingRBIorK = new SimpleIntegerProperty();
        startingSBorERA = new SimpleDoubleProperty();
        startingBAorWHIP = new SimpleDoubleProperty();
        startingValue = new SimpleIntegerProperty();
        contract = new SimpleStringProperty();
        salary = new SimpleIntegerProperty();
        startingR = new SimpleIntegerProperty();
        startingHR = new SimpleIntegerProperty();
        startingRBI = new SimpleIntegerProperty();
        startingSB = new SimpleDoubleProperty();
        startingBA = new SimpleDoubleProperty();
        startingW = new SimpleIntegerProperty();
        startingSV = new SimpleIntegerProperty();
        startingK = new SimpleIntegerProperty();
        startingERA = new SimpleDoubleProperty();
        startingWHIP = new SimpleDoubleProperty();
        positionsArray  = new ArrayList<String>();
        fantasyTeamName = "";
        positionNumber = 0;
    }
    
    public boolean isAHitter() {
        return isHitter;
    }
    
    public void setHitter(boolean b) {
        isHitter = b;
    }
    
    public String getFirstName() {
        return first.get();
    }
    
    public void setFirstName(String initFirstName) {
        first.set(initFirstName);
        startingFirst.set(initFirstName);
    }
    
    public StringProperty firstNameProperty() {
        return first;
    }
    
    public StringProperty startingFirstNameProperty() {
        return startingFirst;
    }

    public String getLastName() {
        return last.get();
    }
    
    public void setLastName(String initLastName) {
        last.set(initLastName);
        startingLast.set(initLastName);
    }
    
    public StringProperty lastNameProperty() {
        return last;
    }
    
    public StringProperty startingLastNameProperty() {
        return startingLast;
    }
    
    public String getProTeam() {
        return proTeam.get();
    }
    
    public void setProTeam(String initProTeam) {
        proTeam.set(initProTeam);
        startingProTeam.set(initProTeam);
    }
    
    public StringProperty proTeamProperty() {
        return proTeam;
    }
    
    public StringProperty startingProTeamProperty() {
        return startingProTeam;
    }
    
    public String getPositions() {
        return positions.get();
    }
    
    public void setPositions(String initPositions) {
        positions.set(initPositions);
        startingPositions.set(initPositions);
    }
    
    public StringProperty positionsProperty() {
        return positions;
    }
    
    public StringProperty startingPositionsProperty() {
        return startingPositions;
    }
    
    public int getYearOfBirth() {
        return yearOfBirth.get();
    }
    
    public void setYearOfBirth(int initYOB) {
        yearOfBirth.set(initYOB);
    }
    
    public IntegerProperty yearOfBirthProperty() {
        return yearOfBirth;
    }
    
    public String getNationOfBirth() {
        return nationOfBirth.get();
    }
    
    public void setNationOfBirth(String initNOB) {
        nationOfBirth.set(initNOB);
    }
    
    public StringProperty nationOfBirthProperty() {
        return nationOfBirth;
    }
    
    public int getRuns() {
        return r.get();
    }
    
    public void setRuns(int initR) {
        r.set(initR);
        startingR.set(initR);
    }
    
    public IntegerProperty runsProperty() {
        return r;
    }
    
    public int getHomeRuns() {
        return hr.get();
    }
    
    public void setHomeRuns(int initHR) {
        hr.set(initHR);
        startingHR.set(initHR);
    }
    
    public IntegerProperty homeRunsProperty() {
        return hr;
    }
    
    public int getRBIs() {
        return rbi.get();
    }
    
    public void setRBIs(int initRBI) {
        rbi.set(initRBI);
        startingRBI.set(initRBI);
    }
    
    public IntegerProperty rbiProperty() {
        return rbi;
    }
    public double getStolenBases() {
        return sb.get();
    }
    
    public void setStolenBases(double initSB) {
        sb.set(initSB);
        startingSB.set(initSB);
    }
    
    public DoubleProperty stolenBasesProperty() {
        return sb;
    }
    
    public double getBA() {
        return ba.get();
    }
    
    public void setBA(double initBA) {
        ba.set(initBA);
        startingBA.set(initBA);
    }
    
    public DoubleProperty baProperty() {
        return ba;
    }
    
    public int getValue() {
        return value.get();
    }
    
    public void setValue(int initValue) {
        value.set(initValue);
        startingValue.set(initValue);
    }
    
    public IntegerProperty valueProperty() {
        return value;
    }
    
    public IntegerProperty startingValueProperty() {
        return startingValue;
    }
    
    public String getNotes() {
        return notes.get();
    }
    
    public void setNotes(String initNotes) {
        notes.set(initNotes);
    }
    
    public StringProperty notesProperty() {
        return notes;
    }
    
    public int getWins() {
        return win.get();
    }
    
    public void setWins(int initWins) {
        win.set(initWins);
        startingW.set(initWins);
    }
    
    public IntegerProperty winsProperty() {
        return win;
    }
    
    public int getSaves() {
        return sv.get();
    }
    
    public void setSaves(int initSv) {
        sv.set(initSv);
        startingSV.set(initSv);
    }
    
    public IntegerProperty savesProperty() {
        return sv;
    }
    
    public int getStrikeouts() {
        return k.get();
    }
    
    public void setStrikeouts(int initK) {
        k.set(initK);
        startingK.set(initK);
    }
    
    public IntegerProperty strikeoutsProperty() {
        return k;
    }
    
    public double getERA() {
        return era.get();
    }
    
    public void setERA(double initERA) {
        era.set(initERA);
        startingERA.set(initERA);
    }
    
    public DoubleProperty eraProperty() {
        return era;
    }
    
    public double getWHIP() {
        return whip.get();
    }
    
    public void setWHIP(double initWHIP) {
        whip.set(initWHIP);
        startingWHIP.set(initWHIP);
    }
    
    public DoubleProperty whipProperty() {
        return whip;
    }

    public IntegerProperty RorWProperty() {
        if (isHitter)
            return r;
        else
            return win;
    }
    
    public IntegerProperty startingRorWProperty() {
        if (isHitter)
            return startingR;
        else
            return startingW;
    }

    public IntegerProperty HRorSVProperty() {
        if (isHitter)
            return hr;
        else
            return sv;
    }
    
    public IntegerProperty startingHRorSVProperty() {
        if (isHitter)
            return startingHR;
        else
            return startingSV;
    }

    public IntegerProperty RBIorKProperty() {
        if (isHitter)
            return rbi;
        else
            return k;
    }
    
    public IntegerProperty startingRBIorKProperty() {
        if (isHitter)
            return startingRBI;
        else
            return startingK;
    }

    public DoubleProperty SBorERAProperty() {
        if (isHitter)
            return sb;
        else
            return era;
    }
    
    public DoubleProperty startingSBorERAProperty() {
        if (isHitter)
            return startingSB;
        else
            return startingERA;
    }
    
    public DoubleProperty BAorWHIPProperty() {
        if (isHitter)
            return ba;
        else
            return whip;
    }
    
    public DoubleProperty startingBAorWHIPProperty() {
        if (isHitter)
            return startingBA;
        else
            return startingWHIP;
    }
    
    public String getTeamPosition() {
        return teamPosition.get();
    }
    
    public void setTeamPosition(String initTeamPosition) {
        teamPosition.set(initTeamPosition);
    }
    
    public StringProperty teamPositionProperty() {
        return teamPosition;
    }

    public String getContract() {
        return contract.get();
    }
    
    public void setContract(String initContract) {
        contract.set(initContract);
    }
    
    public StringProperty contractProperty() {
        return contract;
    }
    
    public int getSalary() {
        return salary.get();
    }
    
    public void setSalary(int initSalary) {
        salary.set(initSalary);
    }
    
    public IntegerProperty salaryProperty() {
        return salary;
    }
    
    public void calculateBA(int hits, int ab) {
        DecimalFormat df = new DecimalFormat("0.000");
        double ba;
        if (ab == 0)
            ba = 0.000;
        else
            ba = ((double)hits / ab);
        ba = Double.parseDouble(df.format(ba));
        setBA(ba);
    }
    
    public void calculateERA(double IP, int ER) {
        DecimalFormat df = new DecimalFormat("#.00");
        double era;
        if (IP == 0.0)
            era = 0.00;
        else
            era = ((ER * 9) / IP);
        era = Double.parseDouble(df.format(era));
        setERA(era);
    }
    
    public void calculateWHIP(int hits, int walks, double IP) {
        DecimalFormat df = new DecimalFormat("#.00");
        double whip;
        if (IP == 0.0)
            whip = 0.00;
        else
            whip = ((hits + walks) / IP);
        whip = Double.parseDouble(df.format(whip));
        setWHIP(whip);
    }
    
    public ObservableList<String> getPositionsArray() {
        ObservableList<String> positions = FXCollections.observableArrayList();
        String positionsString = this.getPositions();
        String[] positionsArray = positionsString.split("_");
        for (String s : positionsArray) {
            positions.add(s);
        }
        
        //check for CI
        int index = positions.indexOf("1B");
        if (index != -1) //first base is there, add CI
            positions.add(index + 1, "CI");
        else {
            index = positions.indexOf("3B");
            if (index != -1) //third base is there, add CI
                positions.add(index, "CI");
        }
        
        //check for MI
        index = positions.indexOf("2B");
        if (index != -1) //second base is there, add MI
            positions.add(index + 1, "MI");
        else {
            index = positions.indexOf("SS");
            if (index != -1) //shortstop is there, add MI
                positions.add(index, "MI");
        }
        return positions;
    }
    
    public void selectPositions(String position, boolean isSelected) {
        boolean b = false;
        if (isSelected) {
            if (!positionsArray.contains(position))
                positionsArray.add(position);
        }
        else {
            positionsArray.remove(position);
        }
        String positionsForPrint = "";
        if (!(positionsArray.isEmpty())) {
            for (int i = 0; i < positionsArray.size(); i++) {
                positionsForPrint += positionsArray.get(i) + "_";
                if (!(positionsArray.get(i).equals("P")))
                    b = true;
            }
            if (!b) //it's a pitcher
                positionsForPrint = positionsForPrint.substring(0, positionsForPrint.length() - 1);
            else
                positionsForPrint += "U";
            this.setPositions(positionsForPrint);
        }
    }
    
    public String getFantasyTeam() {
        return fantasyTeamName;
    }
    
    public void setFantasyTeam(String teamName) {
        fantasyTeamName = teamName;
    }
    
    public int getPositionNumber() {
        return positionNumber;
    }
    
    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }
    
    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;
        return getLastName().compareTo(otherPlayer.getLastName());
    }
}
