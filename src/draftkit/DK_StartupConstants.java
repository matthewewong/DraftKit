package draftkit;

/**
 * This class stores all the constants used by the Draft Kit application 
 * at startup. This includes getting properties.xml.
 * 
 * @author Matthew Wong
 */
public class DK_StartupConstants {
    public static final String PROPERTIES_FILE_NAME = "properties.xml";
    public static final String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";    
    public static final String PATH_DATA = "./data/";
    public static final String PATH_CSS = "csb/css/";
    public static final String PATH_EMPTY = ".";
    public static final String JSON_FILE_PATH_HITTERS = PATH_DATA + "subjects.json";
    public static final String JSON_FILE_PATH_PITCHERS = PATH_DATA + "hitters.json";
    
    // error message associated with file loading errors
    public static String PROPERTIES_FILE_ERROR_MESSAGE = "Error Loading properties.xml";

    // error dialog control
    public static String CLOSE_BUTTON_LABEL = "Close";
}
