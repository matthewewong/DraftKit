package dk.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class serves to present custom text messages to the user when
 * events occur. Note that it always provides the same controls, a label
 * with a message, and a single ok button.
 *
 * @author Matthew Wong
 */
public class MessageDialog extends Stage {
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button closeButton;
    
    /**
     * Initializes this dialog so that it can be used repeatedly for all kinds 
     * of messages.
     * @param owner the owner stage of this modal dialog.
     * @param closeButtonText text to appear on the close button
     */
    public MessageDialog(Stage owner, String closeButtonText) {
        //make it modal (others will wait until it goes away)
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        //label to display the custom message
        messageLabel = new Label();        

        //close button
        closeButton = new Button(closeButtonText);
        closeButton.setOnAction(e->{ MessageDialog.this.close(); });

        //put everything in a pane
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(closeButton);
        
        //make it look nice
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        //put it in the window
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }
 
    /**
     * Load a custom message into the label and then pop open the dialog
     * @param message Message to appear inside the dialog.
     */
    public void show(String message) {
        messageLabel.setText(message);
        this.showAndWait();
    }
}