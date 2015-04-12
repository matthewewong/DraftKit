package draftkit;

import dk.data.DraftDataManager;
import dk.data.DraftDataView;
import static draftkit.DK_PropertyType.*;
import static draftkit.DK_StartupConstants.*;
import dk.error.ErrorHandler;
import dk.gui.DK_GUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;

/**
 * DraftKit is an application that sets up the draft for a fantasy baseball league.
 * 
 * @author Matthew Wong
 */
public class DraftKit extends Application {
    
    DK_GUI gui;
    /**
     * Create the GUI and initialize all the components
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        ErrorHandler eH = ErrorHandler.getErrorHandler();
        eH.initMessageDialog(primaryStage);
        
        //load settings into the GUI and open it up
        boolean success = loadProperties();
        if (success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(PROP_APP_TITLE);
            try {                
                // save data using a json file format, so we let this object do
                // this for us
                //JsonDraftFileManager jsonFileManager = new JsonDraftFileManager();
                
                // Used for exporting, when the time comes
                //DraftExporter exporter = new DraftExporter(PATH_BASE, PATH_SITES);
                
                //ArrayList<String> subjects = jsonFileManager.loadSubjects(JSON_FILE_PATH_SUBJECTS);
                                
                // AND NOW GIVE ALL OF THIS STUFF TO THE GUI
                // INITIALIZE THE USER INTERFACE COMPONENTS
                gui = new DK_GUI(primaryStage);
                //gui.setDraftFileManager(jsonFileManager);
                //gui.setSiteExporter(exporter);
                
                // CONSTRUCT THE DATA MANAGER AND GIVE IT TO THE GUI
                DraftDataManager dataManager = new DraftDataManager(gui); 
                gui.setDataManager(dataManager);

                // start the user inferface
                gui.initGUI(appTitle);                
            }
            catch(IOException ioe) {
                eH = ErrorHandler.getErrorHandler();
                eH.handlePropertiesFileError();
            }
        }
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     * 
     * @return true if the properties file was loaded successfully, false otherwise.
     */
    public boolean loadProperties() {
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handlePropertiesFileError();
            return false;
        }        
    }

    /**
     * This is where program execution begins. Since this is a JavaFX app
     * it will simply call launch, which gets JavaFX rolling, resulting in
     * sending the properly initialized Stage (i.e. window) to our start
     * method in this class.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
}