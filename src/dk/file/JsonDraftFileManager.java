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
    String JSON_DRAFT_NAME = "draft name";
    String JSON_AVAILABLE_PLAYERS = "available players";
    String JSON_OBSERVABLE_HITTERS = "hitters";
    String JSON_OBSERVABLE_PITCHERS = "pitchers";
    String JSON_TEAMS = "teams";
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
        
        // MAKE A JSON ARRAY FOR THE HITTERS AND PITCHERS
        JsonArray hittersJsonArray = makePlayersJsonArray(draftToSave.getObservableHitters());
        JsonArray pitchersJsonArray = makePlayersJsonArray(draftToSave.getObservablePitchers());
        
        // THE TEAMS ARRAY
        JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getTeams());
        
        // NOW BUILD THE DRAFT USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                                    .add(JSON_DRAFT_NAME, draftToSave.getDraftName())
                                    .add(JSON_AVAILABLE_PLAYERS, availPlayersJsonArray)
                                    .add(JSON_OBSERVABLE_HITTERS, hittersJsonArray)
                                    .add(JSON_OBSERVABLE_PITCHERS, pitchersJsonArray)
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
        //JsonObject json = loadJSONFile(jsonFilePath);
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
    
    // BUILDS AND RETURNS A JsonArray CONTAINING ALL THE PLAYERS FOR THIS DRAFT
    public JsonArray makePlayersJsonArray(List<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {
           jsb.add(p.toString());
        }
        JsonArray jA = jsb.build();
        return jA;        
    }
    
    // BUILDS AND RETURNS A JsonArray CONTAINING ALL THE TEAMS FOR THIS DRAFT
    public JsonArray makeTeamsJsonArray(List<Team> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team t : data) {
           jsb.add(t.toString());
        }
        JsonArray jA = jsb.build();
        return jA;        
    }
}
