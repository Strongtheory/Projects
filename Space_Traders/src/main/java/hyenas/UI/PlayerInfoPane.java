package hyenas.UI;

import hyenas.Models.Player;
import hyenas.Models.Ship;
import hyenas.UI.AlertPane.AlertPaneTitleLabel;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * For displaying info.
 * @author Alex
 */
public class PlayerInfoPane extends AnchorPane {
    /**
     * The player info pane's credits label.
     */
    private Label creditsLabel;
    /**
     * The player info pane's fuel label.
     */
    private Label fuelLabel;
    /**
     * The player info pane's health label.
     */
    private Label healthLabel;
    
    /**
     * The player info pane's width.
     */
    private static final int PLAYER_INFO_PANE_WIDTH = 300;
    /**
     * The player info pane's height.
     */
    private static final int PLAYER_INFO_PANE_HEIGHT = 60;
    
    /**
     * Initializer for PlayerInfoPane.
     */
    public PlayerInfoPane() {
        setPrefSize(PLAYER_INFO_PANE_WIDTH, PLAYER_INFO_PANE_HEIGHT);
        
        creditsLabel = new AlertPaneTitleLabel();
        AnchorPane.setTopAnchor(creditsLabel, 10.0);
        AnchorPane.setLeftAnchor(creditsLabel, 0.0);
        
        healthLabel = new AlertPaneTitleLabel();
        AnchorPane.setLeftAnchor(healthLabel, 0.0);
        AnchorPane.setTopAnchor(healthLabel, 30.0);
        
        fuelLabel = new AlertPaneTitleLabel();
        AnchorPane.setTopAnchor(fuelLabel, 50.0);
        AnchorPane.setLeftAnchor(fuelLabel, 0.0);
        
        updateInfo();
        
        getChildren().addAll(creditsLabel, healthLabel, fuelLabel);
    }
    
    /**
     * Updates the info in the pane.
     */
    public final void updateInfo() {
        Player player = Player.getInstance();
        Ship ship = player.getShip();
        
        String credits = "Credits: " + player.getCredits();
        String health = String.format("Health: %.0f/%.0f", Math.floor(
                ship.getHealth()), Math.floor(ship.getMaxHealth()));
        String fuel = String.format("Fuel: %.0f/%.0f", Math.floor(ship.getFuel()),
                Math.floor(ship.getMaxFuel()));
        creditsLabel.setText(credits);
        healthLabel.setText(health);
        fuelLabel.setText(fuel);
    }
}
