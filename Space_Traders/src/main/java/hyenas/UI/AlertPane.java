package hyenas.UI;

import hyenas.UI.StandardButton.StandardButtonType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Alert Panes, for use when displaying alerts/messages to user.
 * @author Alex
 */
public class AlertPane extends BorderPane {
    
    /**
     * The alert pane's title label.
     */
    @FXML
    private Label titleLabel;

     /**
     * The alert pane's message label.
     */
    @FXML
    private Label messageLabel;

     /**
     * The alert pane's action button.
     */
    @FXML
    private Button actionButton;
    
    /**
     * The alert pane's close button.
     */
    @FXML
    private Button closeButton;
    
    /**
     * Combat alert pane's pulse button
     */
    @FXML
    private Button pulseButton;
    
    /**
     * Combat alert pane's beam button
     */
    @FXML
    private Button beamButton;
    
    /**
     * Combat alert pane's missile button
     */
    @FXML
    private Button missileButton;
    
    /**
     * The alert pane's type.
     */
    private AlertPaneType type;
    
    /**
     * Alert pane width in pixels.
     */
    private static final int ALERT_PANE_WIDTH = 300;
    /**
     * Alert pane height in pixels.
     */
    private static final int ALERT_PANE_HEIGHT = 135;
    /**
     * Alert pane width in pixels.
     */
    private static final String DEFAULT_ACTION_TEXT = "Action";
    /**
     * Alert pane height in pixels.
     */
    private static final String DEFAULT_CLOSE_TEXT = "Close";
    
    /**
     * A AlertPaneType, used to distinguish between alert pane types.
     */
    public enum AlertPaneType {
        /**
         * An alert with one button.
         */
        ONEBUTTON,
        /**
         * An alert with two buttons.
         */
        TWOBUTTONS,
        /**
         * An alert with a loading indicator and close button.
         */
        LOADING,
        /**
         * 
         */
        FIGHTBUTTONS
    }
    
    /**
     * Title label for Alert Pane.
     */
    public static class AlertPaneTitleLabel extends Label {
        /**
         * Initializes an AlertPaneTitleLabel.
         */
        public AlertPaneTitleLabel() {
            this("");
        }
        
        /**
         * Initializes an AlertPaneTitleLabel.
         * @param text the default text to set
         */
        public AlertPaneTitleLabel(String text) {
            super(text);
            getStyleClass().add("alertPaneTitleLabel");
        }
    }
    
    /**
     * Message label for Alert Pane.
     */
    public static class AlertPaneMessageLabel extends Label {
        /**
         * Initializes an AlertPaneMessageLabel.
         */
        public AlertPaneMessageLabel() {
            this("");
        }
        
        /**
         * Initializes an AlertPaneMessageLabel.
         * @param text the default text to set
         */
        public AlertPaneMessageLabel(String text) {
            super(text);
            getStyleClass().add("alertPaneMessageLabel");
        }
    }
    
    /**
     * Initializes an AlertPane with a given title and message.
     * @param ptype the alert pane type
     */
    public AlertPane(AlertPaneType ptype) {
        this(ptype, "", "");
    }
    
    /**
     * Initializes an AlertPane and creates required label/button elements.
     * @param ptype the alert pane type
     * @param title the title of the alert
     * @param message the message of the alert
     */
    public AlertPane(AlertPaneType ptype, String title, String message) {
        this.type = ptype;
        setPrefSize(ALERT_PANE_WIDTH, ALERT_PANE_HEIGHT);
        getStyleClass().add("alertPane");

        titleLabel = new AlertPaneTitleLabel(title);
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-padding: 5 0 0 0;");
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(titleLabel);
        titlePane.setPrefHeight(10.0);
        setTop(titlePane);
        
        messageLabel = new AlertPaneMessageLabel(message);
        messageLabel.setWrapText(true);
        messageLabel.setPrefHeight(88.0);
        messageLabel.setStyle("-fx-padding: 0 10 0 10;");
        setCenter(messageLabel);
        
        if (type == AlertPaneType.ONEBUTTON) {
            closeButton = new StandardButton(DEFAULT_CLOSE_TEXT, StandardButtonType.SMALL);
            
            BorderPane buttonPane = new BorderPane();
            buttonPane.setCenter(closeButton);
            buttonPane.setPrefHeight(35.0);
            setBottom(buttonPane);
        } else if (type == AlertPaneType.TWOBUTTONS) {
            actionButton = new StandardButton(DEFAULT_ACTION_TEXT, StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(actionButton, 5.0);
            AnchorPane.setLeftAnchor(actionButton, 41.0);
            
            closeButton = new StandardButton(DEFAULT_CLOSE_TEXT, StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(closeButton, 5.0);
            AnchorPane.setRightAnchor(closeButton, 41.0);
            
            AnchorPane buttonPane = new AnchorPane();
            buttonPane.setPrefHeight(35.0);
            buttonPane.getChildren().addAll(actionButton, closeButton);
            setBottom(buttonPane);
        } else if (type == AlertPaneType.LOADING) {
            BorderPane progressPane = new BorderPane();
            progressPane.setPrefHeight(50.0);
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            progressBar.setPrefWidth(150.0);
            progressPane.setCenter(progressBar);
            setBottom(progressPane);
        }
        else if (type == AlertPaneType.FIGHTBUTTONS)    {
            setPrefSize(ALERT_PANE_WIDTH, ALERT_PANE_HEIGHT+40);
            pulseButton = new StandardButton("Fire Pulse \n"
                    + "Weapons", StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(pulseButton, 5.0+30+StandardButton.SMALL_PREF_HEIGHT);
            AnchorPane.setLeftAnchor(pulseButton, 30.0);
            beamButton = new StandardButton("Fire Beam \n"
                    + "Weapons", StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(beamButton, 5.0+30+StandardButton.SMALL_PREF_HEIGHT);
            AnchorPane.setRightAnchor(beamButton, 30.0);
            missileButton = new StandardButton("Fire Missile \n"
                    + "Weapons", StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(missileButton, 20.0);
            AnchorPane.setLeftAnchor(missileButton, 30.0);
            closeButton = new StandardButton(DEFAULT_CLOSE_TEXT, StandardButtonType.SMALL);
            AnchorPane.setBottomAnchor(closeButton, 20.0);
            AnchorPane.setRightAnchor(closeButton, 30.0);
            
            AnchorPane buttonPane = new AnchorPane();
            buttonPane.setPrefHeight(80.0);
            buttonPane.getChildren().addAll(pulseButton, beamButton, missileButton, closeButton);
            setBottom(buttonPane);
        }
        
        setLayoutX((UIHelper.getScreenSize().getWidth() / 2) - (getPrefWidth() / 2));
        setLayoutY((UIHelper.getScreenSize().getHeight() / 2) - (getPrefHeight() / 2));
    }
    
    /**
     * Set the alert pane's title text.
     * @param text the title text
     */
    public void setTitleText(String text) {
        titleLabel.setText(text);
    }
    
    /**
     * Set the alert pane's message text.
     * @param text the message text
     */
    public void setMessageText(String text) {
        messageLabel.setText(text);
        
        // Move the message label down to make room for title
        if (titleLabel.getText().length() == 0) {
            setCenter(null);
            setTop(messageLabel);
        }
    }
    
    /**
     * Get the alert pane's action button.
     * @return the action button
     */
    public Button getActionButton() {
        return actionButton;
    }
    
    /**
     * Get the alert pane's close button.
     * @return the close button
     */
    public Button getCloseButton() {
        return closeButton;
    }
    
    /**
     * Get the alert pane's pulse button
     * @return the pulse button
     */
    public Button getPulseButton()  {
        return pulseButton;
    }
    
    /**
     * Get the alert pane's beam button
     * @return the beam button
     */
    public Button getBeamButton()  {
        return beamButton;
    }
    
    /**
     * Get the alert pane's missile button
     * @return the missile button
     */
    public Button getMissileButton()    {
        return missileButton;
    }
}
