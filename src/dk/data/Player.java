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
    
    public void calculateBA() {
        int hits = 0;
        double ab = 1.0;
        double ba = hits / ab;
        setBA(ba);
    }
    
    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;
        return getFirstName().compareTo(otherPlayer.getFirstName());
    }
}
