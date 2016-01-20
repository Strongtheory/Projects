package hyenas;

import hyenas.Models.Player;
import hyenas.UI.AlertPane;
import hyenas.UI.AlertPane.AlertPaneType;
import hyenas.database.PlayerTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for allocating skill points and setting up the player.
 *
 * @author Abhishek
 */
public class AllocationController implements Initializable {
    /**
     * The allocation controller main anchor pane.
     */
    @FXML
    private AnchorPane anchorPane;
    /**
     * The allocation controller reset button.
     */
    @FXML
    private Button reset;
    /**
     * The allocation controller pilot button.
     */
    @FXML
    private Button pilot;
    /**
     * The allocation controller fighter button.
     */
    @FXML
    private Button fighter;
    /**
     * The allocation controller trader button.
     */
    @FXML
    private Button trader;
    /**
     * The allocation controller engineer button.
     */
    @FXML
    private Button engineer;
    /**
     * The allocation controller investor button.
     */
    @FXML
    private Button investor;
    /**
     * The allocation controller name field.
     */
    @FXML
    private TextField name;
    /**
     * The allocation controller pilot skill counter label.
     */
    @FXML
    private Label pCounter;
    /**
     * The allocation controller fighter skill counter label.
     */
    @FXML
    private Label fCounter;
    /**
     * The allocation controller trader skill counter label.
     */
    @FXML
    private Label tCounter;
    /**
     * The allocation controller engineer skill counter label.
     */
    @FXML
    private Label eCounter;
    /**
     * The allocation controller investor skill counter label.
     */
    @FXML
    private Label iCounter;
    /**
     * The allocation controller points remaining label.
     */
    @FXML
    private Label point;
    
    /**
     * The skill points remaining to allocate.
     */
    private int pointsRemaining = 8;
    /**
     * The allocation controller pilot skill value.
     */
    private int pValue = 1;
    /**
     * The allocation controller fighter skill value.
     */
    private int fValue = 1;
    /**
     * The allocation controller trader skill value.
     */
    private int tValue = 1;
    /**
     * The allocation controller engineer skill value.
     */
    private int eValue = 1;
    /**
     * The allocation controller investor skill value.
     */
    private int iValue = 1;
    /**
     * A string that represents the remaining number of points.
     */
    private static final String POINTS_REMAINING_STRING = " point(s) remaining";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        point.setText(Integer.toString(pointsRemaining) + POINTS_REMAINING_STRING);
        
        pilot.setOnAction((ActionEvent t) -> {
                updatePoints(++pValue, pCounter);
            });

        fighter.setOnAction((ActionEvent t) -> {
                updatePoints(++fValue, fCounter);
            });

        trader.setOnAction((ActionEvent t) -> {
                updatePoints(++tValue, tCounter);
            });

        engineer.setOnAction((ActionEvent t) -> {
                updatePoints(++eValue, eCounter);
            });

        investor.setOnAction((ActionEvent t) -> {
                updatePoints(++iValue, iCounter);
            });

        reset.setOnAction((ActionEvent t) -> {
                pointsRemaining = 8;
                pValue = 1;
                fValue = 1;
                tValue = 1;
                eValue = 1;
                iValue = 1;
                point.setText(Integer.toString(pointsRemaining) + POINTS_REMAINING_STRING);
                pCounter.setText(Integer.toString(pValue));
                fCounter.setText(Integer.toString(fValue));
                tCounter.setText(Integer.toString(tValue));
                eCounter.setText(Integer.toString(eValue));
                iCounter.setText(Integer.toString(iValue));
                name.setText("");
            });
    }

    /**
     * Updates the current skill points.
     * @param points the new skill points
     * @param label the label to update
     */
    private void updatePoints(int points, Label label) {
        if (pointsRemaining > 0) {
            pointsRemaining--;
            label.setText(Integer.toString(points));
            point.setText(Integer.toString(pointsRemaining) + POINTS_REMAINING_STRING);
        }
    }

    /**
     * Creates the player object and sets up the database.
     * @param e unused action trigger
     */
    public void create(ActionEvent e) {
        if (validInput()) {
            Player player = Player.getInstance();
            player.setName(name.getText());
            player.setPilotSkill(pValue);
            player.setFighterSkill(fValue);
            player.setTraderSkill(tValue);
            player.setEngineerSkill(eValue);
            player.setInvestorSkill(iValue);
            player.setState(true);
            PlayerTable newPlayers = HyenasLoader.getInstance()
                    .getConnectionManager().getPlayerTable();
            newPlayers.addRow(player, null);
            
            HyenasLoader.getInstance().getConnectionManager().getShipTable()
                    .addRow(player.getShip(), player);
            
            HyenasLoader.getInstance().goToMapScreen();
        } else {
            AlertPane alertPane = new AlertPane(AlertPaneType.ONEBUTTON);
            alertPane.setTitleText("Invalid Setup");
            alertPane.setMessageText("Please make sure you have set a player name and have allocated all skill points.");
            EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
            };
            alertPane.getCloseButton().setOnAction(closeAction);
            anchorPane.getChildren().add(alertPane);
            
        }
    }
    
    /**
     * Determines whether the input in the fields is valid.
     * @return true if input is valid; false otherwise
     */
    private boolean validInput() {
        return name.getText().length() != 0;
    }
    
    /**
     * Changes screens back to the home screen.
     * @param e unused action trigger
     */
    public void goBack(ActionEvent e) {
        HyenasLoader.getInstance().goToHomeScreen();
    }
}
