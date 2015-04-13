package dk.file;

import dk.data.Draft;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

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
    String JSON_EXT = ".json";
    String SLASH = "\"";
    
    /**
     * Saves all the data associated with a draft to a JSON file; NOT FOR HW 5
     * @param draftToSave the draft whose data we are saving.
     * @throws IOException issues writing to the JSON file.
     */
    public void saveDraft(Draft draftToSave) throws IOException {
        // BUILD THE FILE PATH
        //String draftListing = "";
        //String jsonFilePath = PATH_COURSES + SLASH + draftListing + JSON_EXT;
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
}
