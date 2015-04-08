package draftkit;

/**
 * These are properties that will be loaded from properties.xml. They will provide
 * custom labels and other UI details for the Draft Kit application.
 *
 * @author Matthew Wong
 */
public enum DK_PropertyType {
    //loaded from properties.xml
    PROP_APP_TITLE,
    
    //app icons
    NEW_DRAFT_ICON,
    LOAD_DRAFT_ICON,
    SAVE_DRAFT_ICON,
    VIEW_DRAFT_ICON,
    EXPORT_DRAFT_ICON,
    DELETE_ICON,
    EXIT_ICON,
    ADD_ICON,
    MINUS_ICON,
    
    //tooltips for buttons
    NEW_DRAFT_TOOLTIP,
    LOAD_DRAFT_TOOLTIP,
    SAVE_DRAFT_TOOLTIP,
    VIEW_DRAFT_TOOLTIP,
    EXPORT_DRAFT_TOOLTIP,
    DELETE_TOOLTIP,
    EXIT_TOOLTIP,
    ADD_PLAYER_TOOLTIP,
    REMOVE_PLAYER_TOOLTIP,
    ADD_TEAM_TOOLTIP,
    REMOVE_TEAM_TOOLTIP,
    PLAYER_SELECT_TOOLTIP,
    FANTASY_SELECT_TOOLTIP,
    STANDINGS_SELECT_TOOLTIP,
    DRAFT_SELECT_TOOLTIP,
    MLB_TEAMS_TOOLTIP,
    
    //for the workspace
    DRAFT_HEADING_LABEL,
    PLAYERS_SCREEN_HEADING_LABEL,
    TEAM_SCREEN_HEADING_LABEL,
    STANDINGS_SCREEN_HEADING_LABEL,
    DRAFT_SCREEN_HEADING_LABEL,
    MLB_TEAMS_SCREEN_HEADING_LABEL,
    
    //players labels
    RADIO_BUTTONS_PLAYERS_LABEL,
    SEARCH_PLAYER_LABEL,
    ADD_PLAYER_LABEL,
    REMOVE_PLAYER_LABEL,
    
    //teams labels
    TEAM_SELECT_LABEL,
    TEAM_NAME_LABEL,
    TEAM_OWNER_LABEL,
    ADD_TEAM_LABEL,
    REMOVE_TEAM_LABEL,
    
    //draft labels
    DRAFT_BEST_PLAYER_LABEL,
    START_AUTO_DRAFT_LABEL,
    PAUSE_AUTO_DRAFT_LABEL,
    
    //error dialog messages
    
    //verification messages
    NEW_DRAFT_CREATED_MESSAGE,
    DRAFT_LOADED_MESSAGE,
    DRAFT_SAVED_MESSAGE,
    SITE_EXPORTED_MESSAGE,
    SAVE_UNSAVED_WORK_MESSAGE,
    REMOVE_PLAYER_MESSAGE,
    REMOVE_TEAM_MESSAGE
}