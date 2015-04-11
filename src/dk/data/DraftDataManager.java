package dk.data;

/**
 * This class manages a Draft, which means it knows how to reset one with default
 * values.
 * 
 * @author Matthew Wong
 */
public class DraftDataManager {
    //Draft being edited
    Draft draft;
    
    //UI, which must be updated whenever our model's data changes
    DraftDataView view;
    
    //helps us load things for our draft
    DraftFileManager fileManager;
    
    //default initialization values for new drafts
    static String DEFAULT_TEAMS_TEXT = "Team";
    static String DEFAULT_OWNERS_TEXT = "Owner";
    static String DEFAULT_COMBO_BOX_TEXT = "";
    
    public DraftDataManager(DraftDataView initView) {
        view = initView;
    }
    
    //accessor method for getting the draft
    public Draft getDraft() {
        return draft;
    }
    
    //accessor method for getting the file manager
    public DraftFileManager getFileManager() {
        return fileManager;
    }
    
    //resets the draft to its default initialized settings
    public void reset() {
        //clear tables
        
        //force UI to reload the updated draft
        view.reloadDraft(draft);
    }
}
