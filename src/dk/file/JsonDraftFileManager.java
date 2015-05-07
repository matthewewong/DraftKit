package dk.file;

import dk.data.Draft;
import dk.data.Player;
import dk.data.Team;
import static draftkit.DK_StartupConstants.PATH_DRAFTS;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;

/**
 * This is a DraftFileManager that uses the JSON file format to implement the 
 * necessary functions for loading and saving different data for our Draft
 *
 * @author Matthew Wong
 */
public class JsonDraftFileManager implements DraftFileManager {
    //JSON file reading and writing constants
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    String JSON_DRAFT_NAME = "draftName";
    String JSON_AVAILABLE_PLAYERS = "availablePlayers";
    String JSON_TEAMS = "teams";
    String JSON_FIRST_NAME = "firstName";
    String JSON_LAST_NAME = "lastName";
    String JSON_PRO_TEAM = "proTeam";
    String JSON_POSITIONS = "positions";
    String JSON_YEAR_OF_BIRTH = "yearOfBirth";
    String JSON_NATION_OF_BIRTH = "nationOfBirth";
    String JSON_R = "R";
    String JSON_HR = "HR";
    String JSON_RBI = "RBI";
    String JSON_SB = "SB";
    String JSON_BA = "BA";
    String JSON_H = "H";
    String JSON_AB = "AB";
    String JSON_NOTES = "notes";
    String JSON_W = "W";
    String JSON_SV = "SV";
    String JSON_K = "K";
    String JSON_IP = "IP";
    String JSON_ER = "ER";
    String JSON_H_ALLOWED = "hitsAllowed";
    String JSON_BB_ALLOWED = "walksAllowed";
    String JSON_ERA = "ERA";
    String JSON_WHIP = "WHIP";
    String JSON_IS_HITTER = "isHitter";
    String JSON_FANTASY_TEAM = "fantasyTeam";
    String JSON_VALUE = "value";
    String JSON_TEAM_POSITION = "teamPosition";
    String JSON_CONTRACT = "contract";
    String JSON_SALARY = "salary";
    String JSON_POSITION_NUMBER = "positionNumber";
    String JSON_TEAM_NAME = "teamName";
    String JSON_OWNER_NAME = "ownerName";
    String JSON_TEAM_STARTING = "startingPlayers";
    String JSON_TEAM_TAXI = "taxiPlayers";
    String JSON_TEAM_C = "numC";
    String JSON_TEAM_1B = "num1B";
    String JSON_TEAM_2B = "num2B";
    String JSON_TEAM_3B = "num3B";
    String JSON_TEAM_SS = "numSS";
    String JSON_TEAM_CI = "numCI";
    String JSON_TEAM_MI = "numMI";
    String JSON_TEAM_OF = "numOF";
    String JSON_TEAM_U = "numU";
    String JSON_TEAM_P = "numP";
    String JSON_TEAM_TOT_POINTS = "totPoints";
    String JSON_EXT = ".json";
    String SLASH = "/";
    
    /**
     * Saves all the data associated with a draft to a JSON file
     * @param draftToSave the draft whose data we are saving.
     * @throws IOException issues writing to the JSON file.
     */
    public void saveDraft(Draft draftToSave) throws IOException {
        // BUILD THE FILE PATH
        String draftListing = "" + draftToSave.getDraftName();
        String jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
        
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
        
        // MAKE A JSON ARRAY FOR THE AVAILABLE PLAYERS ARRAY
        JsonArray availPlayersJsonArray = makePlayersJsonArray(draftToSave.getPlayers());
        
        // THE TEAMS ARRAY
        JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getTeams());
        
        // NOW BUILD THE DRAFT USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                                    .add(JSON_DRAFT_NAME, draftToSave.getDraftName())
                                    .add(JSON_AVAILABLE_PLAYERS, availPlayersJsonArray)
                                    .add(JSON_TEAMS, teamsJsonArray)
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject);
        
    }
    
    /**
     * Loads the draftToLoad argument using the data found in the json file.
     * @param draftToLoad Draft to load.
     * @param jsonFilePath file containing the data to load.
     * @throws IOException thrown when IO fails.
     */
    public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE DRAFT
        draftToLoad.setDraftName(json.getString(JSON_DRAFT_NAME));
        
        // GET THE PLAYERS TO INCLUDE
        draftToLoad.clearPlayers();
        draftToLoad.getObservableHitters().clear();
        draftToLoad.getObservablePitchers().clear();
        
        JsonArray jsonPlayersArray = json.getJsonArray(JSON_AVAILABLE_PLAYERS);
        ObservableList<Player> players = createPlayerLists(jsonPlayersArray);
        
        // ADD PLAYERS TO THE DRAFT
        for (Player p : players) {
            draftToLoad.addPlayer(p);
        }
        
        // GET THE TEAMS TO INCLUDE
        draftToLoad.clearTeams();
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamsArray.size(); i++) {
            JsonObject jso = jsonTeamsArray.getJsonObject(i);
            
            // CREATE THE TEAM TO INCLUDE
            Team t = new Team();
            t.clearStartingPlayers();
            t.clearTaxiPlayers();
            t.setTeamName(jso.getString(JSON_TEAM_NAME));
            t.setOwnerName(jso.getString(JSON_OWNER_NAME));
            
            JsonArray jsonStartingArray = jso.getJsonArray(JSON_TEAM_STARTING);
            ObservableList<Player> startingPlayers = createPlayerLists(jsonStartingArray);
            t.setStartingPlayers(startingPlayers);
            JsonArray jsonTaxiArray = jso.getJsonArray(JSON_TEAM_TAXI);
            ObservableList<Player> taxiPlayers = createPlayerLists(jsonTaxiArray);
            t.setTaxiPlayers(taxiPlayers);
            
            t.setNumC(jso.getInt(JSON_TEAM_C));
            t.setNum1B(jso.getInt(JSON_TEAM_1B));
            t.setNum2B(jso.getInt(JSON_TEAM_2B));
            t.setNum3B(jso.getInt(JSON_TEAM_3B));
            t.setNumSS(jso.getInt(JSON_TEAM_SS));
            t.setNumCI(jso.getInt(JSON_TEAM_CI));
            t.setNumMI(jso.getInt(JSON_TEAM_MI));
            t.setNumOF(jso.getInt(JSON_TEAM_OF));
            t.setNumU(jso.getInt(JSON_TEAM_U));
            t.setNumP(jso.getInt(JSON_TEAM_P));
            t.setTotalPoints(jso.getInt(JSON_TEAM_TOT_POINTS));
            
            // ADD TEAMS TO THE DRAFT
            draftToLoad.addTeam(t);
        }
    }
    
    /**
     * Loads hitters from the json file.
     * @param jsonFilePath Json file containing the hitters.
     * @return list full of Hitters loaded from the file.
     * @throws IOException thrown when IO fails.
     */
    public ArrayList<String> loadHitters(String jsonFilePath) throws IOException {
        ArrayList<String> hittersArray = loadArrayFromJSONFile(jsonFilePath, JSON_HITTERS);
        ArrayList<String> cleanedArray = new ArrayList();
        for (String s : hittersArray) {
            //remove the quote characters
            s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }
    
    /**
     * Loads hitters from the json file.
     * @param jsonFilePath Json file containing the hitters.
     * @return list full of Hitters loaded from the file.
     * @throws IOException thrown when IO fails.
     */
    public ArrayList<String> loadPitchers(String jsonFilePath) throws IOException {
        ArrayList<String> hittersArray = loadArrayFromJSONFile(jsonFilePath, JSON_PITCHERS);
        ArrayList<String> cleanedArray = new ArrayList();
        for (String s : hittersArray) {
            //remove the quote characters
            s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }
    
    //helper methods
    
    //load a json file and return it
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }    
    
    //load an array of a specific name from a json file and return it as an ArrayList
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            items.add(jsV.toString());
        }
        return items;
    }
    
    //builds and returns a JsonArray containing the provided data
    public JsonArray buildJsonArray(List<Object> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Object d : data) {
           jsb.add(d.toString());
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PLAYER
    private JsonObject makePlayerJsonObject(Player player) {
        JsonObject jso;
        if (player.isAHitter()) {
            jso = Json.createObjectBuilder().add(JSON_FIRST_NAME, player.getFirstName())
                                                        .add(JSON_LAST_NAME, player.getLastName())
                                                        .add(JSON_PRO_TEAM, player.getProTeam())
                                                        .add(JSON_POSITIONS, player.getPositions())
                                                        .add(JSON_YEAR_OF_BIRTH, player.getYearOfBirth())
                                                        .add(JSON_NATION_OF_BIRTH, player.getNationOfBirth())
                                                        .add(JSON_IS_HITTER, player.isAHitter())
                                                        .add(JSON_R, player.getRuns())
                                                        .add(JSON_HR, player.getHomeRuns())
                                                        .add(JSON_RBI, player.getRBIs())
                                                        .add(JSON_SB, player.getStolenBases())
                                                        .add(JSON_H, player.getHits())
                                                        .add(JSON_AB, player.getAtBats())
                                                        .add(JSON_BA, player.getBA())
                                                        .add(JSON_VALUE, player.getValue())
                                                        .add(JSON_NOTES, player.getNotes())
                                                        .add(JSON_TEAM_POSITION, player.getTeamPosition())
                                                        .add(JSON_CONTRACT, player.getContract())
                                                        .add(JSON_SALARY, player.getSalary())
                                                        .add(JSON_FANTASY_TEAM, player.getFantasyTeam())
                                                        .add(JSON_POSITION_NUMBER, player.getPositionNumber())
                                                        .build();
        }
        else {
            jso = Json.createObjectBuilder().add(JSON_FIRST_NAME, player.getFirstName())
                                                        .add(JSON_LAST_NAME, player.getLastName())
                                                        .add(JSON_PRO_TEAM, player.getProTeam())
                                                        .add(JSON_POSITIONS, player.getPositions())
                                                        .add(JSON_YEAR_OF_BIRTH, player.getYearOfBirth())
                                                        .add(JSON_NATION_OF_BIRTH, player.getNationOfBirth())
                                                        .add(JSON_IS_HITTER, player.isAHitter())
                                                        .add(JSON_W, player.getWins())
                                                        .add(JSON_SV, player.getSaves())
                                                        .add(JSON_K, player.getStrikeouts())
                                                        .add(JSON_IP, player.getInningsPitched())
                                                        .add(JSON_ER, player.getEarnedRuns())
                                                        .add(JSON_H_ALLOWED, player.getHitsAllowed())
                                                        .add(JSON_BB_ALLOWED, player.getWalksAllowed())
                                                        .add(JSON_ERA, player.getERA())
                                                        .add(JSON_WHIP, player.getWHIP())
                                                        .add(JSON_VALUE, player.getValue())
                                                        .add(JSON_NOTES, player.getNotes())
                                                        .add(JSON_TEAM_POSITION, player.getTeamPosition())
                                                        .add(JSON_CONTRACT, player.getContract())
                                                        .add(JSON_SALARY, player.getSalary())
                                                        .add(JSON_FANTASY_TEAM, player.getFantasyTeam())
                                                        .add(JSON_POSITION_NUMBER, player.getPositionNumber())
                                                        .build();
        }
        return jso;
    }
    
    // BUILDS AND RETURNS A JsonArray CONTAINING ALL THE PLAYERS FOR THIS TEAM
    private JsonArray makePlayerJsonObjectFromArray(ObservableList<Player> players) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            jsb.add(makePlayerJsonObject(p));
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    // MAKES AND RETURNS A JSON OBJECT FOR THE TEAM
    private JsonObject makeTeamJsonObject(Team team) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM_NAME, team.getTeamName())
                                                    .add(JSON_OWNER_NAME, team.getOwnerName())
                                                    .add(JSON_TEAM_STARTING, makePlayerJsonObjectFromArray(team.getStartingPlayers()))
                                                    .add(JSON_TEAM_TAXI, makePlayerJsonObjectFromArray(team.getTaxiPlayers()))
                                                    .add(JSON_TEAM_C, team.getNumC())
                                                    .add(JSON_TEAM_1B, team.getNum1B())
                                                    .add(JSON_TEAM_2B, team.getNum2B())
                                                    .add(JSON_TEAM_3B, team.getNum3B())
                                                    .add(JSON_TEAM_SS, team.getNumSS())
                                                    .add(JSON_TEAM_CI, team.getNumCI())
                                                    .add(JSON_TEAM_MI, team.getNumMI())
                                                    .add(JSON_TEAM_OF, team.getNumOF())
                                                    .add(JSON_TEAM_U, team.getNumU())
                                                    .add(JSON_TEAM_P, team.getNumP())
                                                    .add(JSON_TEAM_TOT_POINTS, team.getTotalPoints())
                                                    .build();
        return jso;
    }
    
    // BUILDS AND RETURNS A JsonArray CONTAINING ALL THE PLAYERS FOR THIS DRAFT
    public JsonArray makePlayersJsonArray(List<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {
           jsb.add(makePlayerJsonObject(p));
        }
        JsonArray jA = jsb.build();
        return jA;        
    }
    
    // BUILDS AND RETURNS A JsonArray CONTAINING ALL THE TEAMS FOR THIS DRAFT
    public JsonArray makeTeamsJsonArray(List<Team> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team t : data) {
           jsb.add(makeTeamJsonObject(t));
        }
        JsonArray jA = jsb.build();
        return jA;        
    }
    
    private ObservableList<Player> createPlayerLists(JsonArray playersArray) {
        ObservableList<Player> players = FXCollections.observableArrayList();
        for (int i = 0; i < playersArray.size(); i++) {
            JsonObject jso = playersArray.getJsonObject(i);
            Player p = new Player();
            p.setFirstName(jso.getString(JSON_FIRST_NAME));
            p.setLastName(jso.getString(JSON_LAST_NAME));
            p.setProTeam(jso.getString(JSON_PRO_TEAM));
            p.setPositions(jso.getString(JSON_POSITIONS));
            p.setYearOfBirth(jso.getInt(JSON_YEAR_OF_BIRTH));
            p.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
            p.setHitter(jso.getBoolean(JSON_IS_HITTER));
            if (p.isAHitter()) { //(s)he's a hitter
                p.setRuns(jso.getInt(JSON_R));
                p.setHomeRuns(jso.getInt(JSON_HR));
                p.setRBIs(jso.getInt(JSON_RBI));
                p.setStolenBases(Double.parseDouble(jso.get(JSON_SB).toString()));
                p.setHits(jso.getInt(JSON_H));
                p.setAtBats(jso.getInt(JSON_AB));
                p.setBA(Double.parseDouble(jso.get(JSON_BA).toString()));
            }
            else { //(s)he's a pitcher
                p.setWins(jso.getInt(JSON_W));
                p.setSaves(jso.getInt(JSON_SV));
                p.setStrikeouts(jso.getInt(JSON_K));
                p.setInningsPitched(Double.parseDouble(jso.get(JSON_IP).toString()));
                p.setEarnedRuns(jso.getInt(JSON_ER));
                p.setHitsAllowed(jso.getInt(JSON_H_ALLOWED));
                p.setWalksAllowed(jso.getInt(JSON_BB_ALLOWED));
                p.setERA(Double.parseDouble(jso.get(JSON_ERA).toString()));
                p.setWHIP(Double.parseDouble(jso.get(JSON_WHIP).toString()));
            }
            p.setNotes(jso.getString(JSON_NOTES));
            p.setFantasyTeam(jso.getString(JSON_FANTASY_TEAM));
            p.setValue(jso.getInt(JSON_VALUE));
            p.setTeamPosition(jso.getString(JSON_TEAM_POSITION));
            p.setContract(jso.getString(JSON_CONTRACT));
            p.setSalary(jso.getInt(JSON_SALARY)); 
            p.setPositionNumber(jso.getInt(JSON_POSITION_NUMBER));
            players.add(p);
        }
        return players;
    }
}
