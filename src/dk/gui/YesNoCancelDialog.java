package dk.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class presents a dialog with three options to the user: Yes, No, or Cancel
 * and let's one access which was selected.
 *
 * @author Matthew Wong
 */
public class YesNoCancelDialog extends Stage {
    //GUI controls
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    String selection;
    
    //choices (constants)
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";
    
    /**
     * Initializes this dialog so that it can be used repeatedly for all kinds
     * of messages
     * @param primaryStage The owner of this modal dialog.
     */
    public YesNoCancelDialog(Stage primaryStage) {
        //make this modal (others will wait until it goes away)
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //display the custom message
        messageLabel = new Label();        

        EventHandler yesNoCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            YesNoCancelDialog.this.selection = sourceButton.getText();
            YesNoCancelDialog.this.hide();
        };
        
        //Yes, No, Cancel buttons
        yesButton = new Button(YES);
        noButton = new Button(NO);
        cancelButton = new Button(CANCEL);
        yesButton.setOnAction(yesNoCancelHandler);
        noButton.setOnAction(yesNoCancelHandler);
        cancelButton.setOnAction(yesNoCancelHandler);

        //organize buttons to Yes/No/Cancel
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(noButton);
        buttonBox.getChildren().add(cancelButton);
        
        //put everything here
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(buttonBox);
        
        //make it look beautiful
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        //put it in the window
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }

    /**
     * Accessor method for getting the selection the user made.
     * @return Either YES, NO, or CANCEL, depending on which button the user selected.
     */
    public String getSelection() {
        return selection;
    }
 
    /**
     * This method loads a custom message into the label and then pops open the dialog.
     * @param message Message to appear inside the dialog.
     */
    public void show(String message) {
        messageLabel.setText(message);
        this.showAndWait();
    }
}
