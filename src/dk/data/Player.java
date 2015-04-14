package dk.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    final IntegerProperty r;
    final IntegerProperty hr;
    final IntegerProperty rbi;
    final IntegerProperty sb;
    final DoubleProperty ba;
    final IntegerProperty value;
    final StringProperty notes;
    final IntegerProperty win;
    final IntegerProperty sv;
    final IntegerProperty k;
    final DoubleProperty era;
    final DoubleProperty whip;
    //public static final String DEFAULT_TEAM = "<ENTER TEAM>"; //not for HW 5
    //public static final String DEAFAULT_SALARY = "<ENTER SALARY>"; //not for HW 5
    public Player() {
        first = new SimpleStringProperty();
        last = new SimpleStringProperty();
        proTeam = new SimpleStringProperty();
        positions = new SimpleStringProperty();
        yearOfBirth = new SimpleIntegerProperty();
        r = new SimpleIntegerProperty();
        hr = new SimpleIntegerProperty();
        rbi = new SimpleIntegerProperty();
        sb = new SimpleIntegerProperty();
        ba = new SimpleDoubleProperty();
        value = new SimpleIntegerProperty();
        notes = new SimpleStringProperty();
        win = new SimpleIntegerProperty();
        sv = new SimpleIntegerProperty();
        k = new SimpleIntegerProperty();
        era = new SimpleDoubleProperty();
        whip = new SimpleDoubleProperty();
    }
    
    public String getFirstName() {
        return first.get();
    }
    
    public void setFirstName(String initFirstName) {
        first.set(initFirstName);
    }
    
    public StringProperty firstNameProperty() {
        return first;
    }

    public String getLastName() {
        return last.get();
    }
    
    public void setLastName(String initLastName) {
        last.set(initLastName);
    }
    
    public StringProperty lastNameProperty() {
        return last;
    }
    
    public String getProTeam() {
        return proTeam.get();
    }
    
    public void setProTeam(String initProTeam) {
        proTeam.set(initProTeam);
    }
    
    public StringProperty proTeamProperty() {
        return proTeam;
    }
    
    public String getPositions() {
        return positions.get();
    }
    
    public void setPositions(String initPositions) {
        positions.set(initPositions);
    }
    
    public StringProperty positionsProperty() {
        return positions;
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
    
    public int getRuns() {
        return r.get();
    }
    
    public void setRuns(int initR) {
        r.set(initR);
    }
    
    public IntegerProperty runsProperty() {
        return r;
    }
    
    public int getHomeRuns() {
        return hr.get();
    }
    
    public void setHomeRuns(int initHR) {
        hr.set(initHR);
    }
    
    public IntegerProperty homeRunsProperty() {
        return hr;
    }
    
    public int getRBIs() {
        return rbi.get();
    }
    
    public void setRBIs(int initRBI) {
        rbi.set(initRBI);
    }
    
    public IntegerProperty rbiProperty() {
        return rbi;
    }
    public int getStolenBases() {
        return sb.get();
    }
    
    public void setStolenBases(int initSB) {
        sb.set(initSB);
    }
    
    public IntegerProperty stolenBasesProperty() {
        return sb;
    }
    
    public double getBA() {
        return ba.get();
    }
    
    public void setBA(double initBA) {
        ba.set(initBA);
    }
    
    public DoubleProperty baProperty() {
        return ba;
    }
    
    public int getValue() {
        return value.get();
    }
    
    public void setValue(int initValue) {
        value.set(initValue);
    }
    
    public IntegerProperty valueProperty() {
        return value;
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
    
    public void calculateBA(int hits, int ab) {
        double ba;
        if (ab == 0)
            ba = 0.000;
        else
            ba = ((hits / ab) * 1000) / 1000.0;
        setBA(ba);
    }
    
    public int getWins() {
        return win.get();
    }
    
    public void setWins(int initWins) {
        win.set(initWins);
    }
    
    public IntegerProperty winsProperty() {
        return win;
    }
    
    public int getSaves() {
        return sv.get();
    }
    
    public void setSaves(int initSv) {
        sv.set(initSv);
    }
    
    public IntegerProperty savesProperty() {
        return sv;
    }
    
    public int getStrikeouts() {
        return k.get();
    }
    
    public void setStrikeouts(int initK) {
        k.set(initK);
    }
    
    public IntegerProperty strikeoutsProperty() {
        return k;
    }
    
    public double getERA() {
        return era.get();
    }
    
    public void setERA(double initERA) {
        era.set(initERA);
    }
    
    public DoubleProperty eraProperty() {
        return era;
    }
    
    public double getWHIP() {
        return whip.get();
    }
    
    public void setWHIP(double initWHIP) {
        whip.set(initWHIP);
    }
    
    public DoubleProperty whipProperty() {
        return whip;
    }
    
    public void calculateERA(double IP, int ER) {
        double era;
        if (IP == 0.0)
            era = 0.00;
        else
            era = (int)(((ER * 9) / IP) * 100) / 100.0;
        setERA(era);
    }
    
    public void calculateWHIP(int hits, int walks, double IP) {
        double whip;
        if (IP == 0.0)
            whip = 0.00;
        else
            whip = (int)(((hits + walks) / IP) * 100) / 100.0;
        setWHIP(whip);
    }
    
    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;
        return getFirstName().compareTo(otherPlayer.getFirstName());
    }
}
