package dk.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface provides an abstraction of what a file manager should do.
 *
 * @author MAVIRI
 */
public interface DraftFileManager {
    public void                 saveDraft(Draft draftToSave) throws IOException;
    public void                 loadDraft(Draft draftToLoad, String coursePath) throws IOException;
}
