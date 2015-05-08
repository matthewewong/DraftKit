package dk.handler;

//import static draftkit.DK_PropertyType.DRAFT_SAVED_MESSAGE;
import dk.data.Draft;
import dk.data.DraftDataManager;
import dk.error.ErrorHandler;
import dk.file.DraftFileManager;
import dk.gui.DK_GUI;
import dk.gui.MessageDialog;
import dk.gui.YesNoCancelDialog;
import static draftkit.DK_PropertyType.DRAFT_SAVED_MESSAGE;
import static draftkit.DK_PropertyType.ILLEGAL_DRAFT_NAME;
import static draftkit.DK_PropertyType.NEW_DRAFT_CREATED_MESSAGE;
import static draftkit.DK_PropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static draftkit.DK_StartupConstants.PATH_DRAFTS;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
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
    
    //ask yes/no/cancel questions
    YesNoCancelDialog yesNoCancelDialog;
    
    //get our verification feedback
    PropertiesManager properties;
    
    /**
     * Default constructor that starts the program without a draft file being edited.
     * @param initMessageDialog message dialog.
     * @param initDraftIO object that will be reading/writing draft data.
     */
    public FileHandler(MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog, DraftFileManager initDraftIO) {
        //nothing yet
        saved = true;
        
        //keep for later
        draftIO = initDraftIO;
        
        //get ready for errors
        errorHandler = ErrorHandler.getErrorHandler();
        
        //get ready to provide feedback
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
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
        try {
            //we may need to save
            boolean continueToMakeNew = true;
            if (!saved) {
            //  user can opt out with a cancel
             continueToMakeNew = promptToSave(gui);
            }
            
            //if the user REALLY wants to make a new Draft
            if (continueToMakeNew) {
                //reset data
                DraftDataManager dataManager = gui.getDataManager();
                dataManager.reset();
                saved = false;
                
                //refresh the GUI, to enable/disable appropriate controls
                gui.updateTopToolbarControls(saved);
                
                //tell the user the Draft has been created
                messageDialog.show(properties.getProperty(NEW_DRAFT_CREATED_MESSAGE));
            }
        } catch (IOException ioe) {
            //something went wrong, provide feedback
            errorHandler.handleNewDraftError();
        }
    }
    
    /**
     * This method lets the user open a Draft saved to a file. It will also make
     * sure data for the current Draft is not lost. NOT FOR HW 5
     * @param gui the UI editing the Draft
     */
    public void handleLoadDraftRequest(DK_GUI gui) {
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
            errorHandler.handleLoadDraftError();
        }
    }
    
    /**
     * This method will save the current draft to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     * 
     * @param gui The user interface editing the Draft.
     * 
     * @param draftToSave The draft being edited that is to be saved to a file.
     */
    public void handleSaveDraftRequest(DK_GUI gui, Draft draftToSave) {
        try {
            // SAVE IT TO A FILE
            if (draftToSave.getDraftName() == null) //no draft name!
                messageDialog.show(properties.getProperty(ILLEGAL_DRAFT_NAME));
            else if (draftToSave.getDraftName().equals("")) //no draft name!
                messageDialog.show(properties.getProperty(ILLEGAL_DRAFT_NAME));
            else {
                draftIO.saveDraft(draftToSave);

                // MARK IT AS SAVED
                saved = true;

                // TELL THE USER THE FILE HAS BEEN SAVED
                messageDialog.show(properties.getProperty(DRAFT_SAVED_MESSAGE));

                // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                gui.updateTopToolbarControls(saved);
            }
        } catch (IOException ioe) {
            errorHandler.handleSaveDraftError();
        }
    }
    
    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     * 
     * @param gui
     */
    public void handleExitRequest(DK_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handleExitError();
        }
    }
    
    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action, NO means don't save the 
     * work but continue with the other action, CANCEL means don't save the work 
     * and don't continue with the other action.
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave(DK_GUI gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(properties.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            // SAVE THE DRAFT
            DraftDataManager dataManager = gui.getDataManager();
            if (dataManager.getDraft().getDraftName() == null) { //no draft name!
                messageDialog.show(properties.getProperty(ILLEGAL_DRAFT_NAME));
                return false;
            }
            else if (dataManager.getDraft().getDraftName().equals("")) {//no draft name!
                messageDialog.show(properties.getProperty(ILLEGAL_DRAFT_NAME));
                return false;
            }
            else {
                draftIO.saveDraft(dataManager.getDraft());
                saved = true;
            }
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen(DK_GUI gui) {
        // AND NOW ASK THE USER FOR THE DRAFT TO OPEN
        FileChooser draftFileChooser = new FileChooser();
        draftFileChooser.setInitialDirectory(new File(PATH_DRAFTS));
        File selectedFile = draftFileChooser.showOpenDialog(gui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                Draft draftToLoad = gui.getDataManager().getDraft();
                draftIO.loadDraft(draftToLoad, selectedFile.getAbsolutePath());
                gui.reloadDraft(draftToLoad);
                saved = true;
                gui.updateTopToolbarControls(saved);
            } catch (Exception e) {
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleLoadDraftError();
            }
        }
    }
    
}
