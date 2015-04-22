package dk.handler;

//import static draftkit.DK_PropertyType.DRAFT_SAVED_MESSAGE;
import dk.data.DraftDataManager;
import dk.error.ErrorHandler;
import dk.file.DraftFileManager;
import dk.gui.DK_GUI;
import dk.gui.MessageDialog;
import static draftkit.DK_PropertyType.NEW_DRAFT_CREATED_MESSAGE;
import java.io.IOException;
import properties_manager.PropertiesManager;

/**
 * This handler class provides responses to interactions with the buttons in the
 * file toolbar.
 *
 * @author Matthew Wong
 */
public class FileHandler {
    //keep track of when something has not been saved
    private boolean saved;
    
    //read/write course data
    private DraftFileManager draftIO;
    
    //export    NOT FOR HW5
    //private DraftExporter exporter;
    
    //provide feedback to user if something goes wrong
    ErrorHandler errorHandler;
    
    //provide feedback to user after work has completed
    MessageDialog messageDialog;
    
    //ask yes/no/cancel questions       NOT FOR HW 5
    //YesNoCancelDialog yesNoCancelDialog;
    
    //get our verification feedback
    PropertiesManager properties;
    
    /**
     * Default constructor that starts the program without a draft file being edited.
     * @param initMessageDialog message dialog.
     * @param initDraftIO object that will be reading/writing draft data.
     */
    public FileHandler(MessageDialog initMessageDialog, DraftFileManager initDraftIO) {
        //nothing yet
        saved = true;
        
        //keep for later
        draftIO = initDraftIO;
        
        //get ready for errors
        errorHandler = ErrorHandler.getErrorHandler();
        
        //get ready to provide feedback
        messageDialog = initMessageDialog;
        properties = PropertiesManager.getPropertiesManager();
    }
    
    /**
     * This method let's us know that the current Draft has been edited since it's
     * been saved. UI then gets updated.
     * @param gui the UI editing the Draft.
     */
    public void markAsEdited(DK_GUI gui) {
        //Draft object is now dirty
        saved = false;
        
        //let UI know
        gui.updateTopToolbarControls(saved);
    }
    
    /**
     * This method starts the process of editing a new Draft. If a draft is already
     * being edited, it will prompt the user to save (NOT FOR HW 5)
     * @param gui the UI editing the Draft.
     */
    public void handleNewDraftRequest(DK_GUI gui) {
        //try {
            //we may need to save
            //boolean continueToMakeNew = true;
            //if (!saved) {
            //  user can opt out with a cancel
            //  continueToMakeNew = promptToSave(gui);
            //}
            
            //if the user REALLY wants to make a new Draft
            //if (continueToMakeNew) {
                //reset data
                DraftDataManager dataManager = gui.getDataManager();
                dataManager.reset();
                saved = false;
                
                //refresh the GUI, to enable/disable appropriate controls
                //gui.updateTopToolbarControls(saved);
                
                //tell the user the Draft has been created
                messageDialog.show(properties.getProperty(NEW_DRAFT_CREATED_MESSAGE));
            //}
        //} catch (IOException ioe) {
            //something went wrong, provide feedback
        //    errorHandler.handleNewDraftError();
        //}
    }
    
    /**
     * This method lets the user open a Draft saved to a file. It will also make
     * sure data for the current Draft is not lost. NOT FOR HW 5
     * @param gui the UI editing the Draft
     */
    /*public void handleLoadDraftRequest(DK_GUI gui) {
        try {
            //we may have to save
            boolean continueToOpen = true;
            if (!saved) {
                //the user can opt out with a cancel
                continueToOpen = promptToSave(gui);
            }

            //if the user REALLY wants to load a Draft
            if (continueToOpen) {
                //load the Draft
                promptToOpen(gui);
            }
        } catch (IOException ioe) {
            //something went wrong
            errorHandler.handleLoadCourseError();
        }
    }*/
    
}
