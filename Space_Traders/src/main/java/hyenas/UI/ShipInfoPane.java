package hyenas.UI;

import hyenas.Models.Player;
import hyenas.Models.Ship;
import hyenas.UI.AlertPane.AlertPaneMessageLabel;
import hyenas.UI.AlertPane.AlertPaneTitleLabel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * For use with Shipyard. Displays ship and player info
 * @author Alex
 */
public class ShipInfoPane extends BorderPane {
    /**
     * The ship info pane's ship name label.
     */
    private Label shipNameLabel;
    /**
     * The ship info pane's ship health label.
     */
    private Label shipHealthLabel;
    /**
     * The ship info pane's ship shields label.
     */
    private Label shipShieldStrengthLabel;
    /**
     * The ship info pane's ship cargo label.
     */
    private Label shipCargoLabel;
    /**
     * The ship info pane's ship fuel label.
     */
    private Label shipFuelLabel;
    /**
     * The ship info pane's ship weapons label.
     */
    private Label shipWeaponsLabel;
    /**
     * The ship info pane's ship shields label.
     */
    private Label shipShieldsLabel;
    /**
     * The ship info pane's ship gadgets label.
     */
    private Label shipGadgetsLabel;
    /**
     * The ship info pane's ship crew label.
     */
    private Label shipCrewLabel;
    /**
     * The ship info pane's credits label.
     */
    private Label creditsLabel;
    /**
     * The ship info pane's buy button.
     */
    private Button buyButton;
    /**
     * The ship info pane's sell button.
     */
    private Button sellButton;
    
    public enum ShipInfoPaneType {
        /**
         * For the shipyard.
         */
        SHIPYARD,
        /**
         * For combat.
         */
        COMBAT
    }
    
    /**
     * The ship info pane's type.
     */
    private ShipInfoPaneType type;
    
    /**
     * Initializer for ship info pane.
     * @param type the ship info pane type
     */
    public ShipInfoPane(ShipInfoPaneType type) {
        this.type = type;
        getStyleClass().add("market-info-pane");
        setPrefWidth(250.0);
        setPadding(new Insets(10));
        
        VBox leftBox = new VBox();
        Label shipName = new AlertPaneTitleLabel("Ship:");
        Label shipHealth = new AlertPaneTitleLabel("Health:");
        leftBox.getChildren().addAll(shipName, shipHealth);
        
        if (type == ShipInfoPaneType.SHIPYARD) {
            Label shipCargo = new AlertPaneTitleLabel("Cargo:");
            Label shipFuel = new AlertPaneTitleLabel("Fuel:");
            Label shipWeapons = new AlertPaneTitleLabel("Weapons:");
            Label shipShields = new AlertPaneTitleLabel("Shields:");
            Label shipGadgets = new AlertPaneTitleLabel("Gadgets:");
            Label shipCrew = new AlertPaneTitleLabel("Crew:");
            Label credits = new AlertPaneTitleLabel("Credits:");
            leftBox.getChildren().addAll(shipCargo, shipFuel, shipWeapons, shipShields, shipGadgets,
                shipCrew, credits);
        } else if (type == ShipInfoPaneType.COMBAT) {
            Label shipShieldStrength = new AlertPaneTitleLabel("Shield Strength:");
            leftBox.getChildren().addAll(shipShieldStrength);
        }
        
        VBox rightBox = new VBox();
        final String defaultUnsetValue = "[0/0]";
        shipNameLabel = new AlertPaneMessageLabel("[Name]");
        shipHealthLabel = new AlertPaneMessageLabel(defaultUnsetValue);
        rightBox.getChildren().addAll(shipNameLabel, shipHealthLabel);
        
        if (type == ShipInfoPaneType.SHIPYARD) {
            shipCargoLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            shipFuelLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            shipWeaponsLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            shipShieldsLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            shipGadgetsLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            shipCrewLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            creditsLabel = new AlertPaneMessageLabel("[0]");
            rightBox.getChildren().addAll(shipCargoLabel, shipFuelLabel,
                shipWeaponsLabel, shipShieldsLabel, shipGadgetsLabel,
                shipCrewLabel, creditsLabel);
        } else if (type == ShipInfoPaneType.COMBAT) {
            shipShieldStrengthLabel = new AlertPaneMessageLabel(defaultUnsetValue);
            rightBox.getChildren().addAll(shipShieldStrengthLabel);
        }
        
        BorderPane.setMargin(rightBox, new Insets(0, 0, 0, 20));
        
        Player player = Player.getInstance();
        updateInfo(player, player.getShip());
        
        BorderPane bottomBox = new BorderPane();
        bottomBox.setPrefWidth(300.0);
        bottomBox.setPrefHeight(85.0);
        buyButton = new StandardButton("Buy");
        buyButton.setDisable(true);
        
        sellButton = new StandardButton("Sell");
        sellButton.setDisable(true);
        
        BorderPane buyPane = new BorderPane();
        buyPane.setCenter(buyButton);
        BorderPane sellPane = new BorderPane();
        sellPane.setCenter(sellButton);
        bottomBox.setTop(buyPane);
        bottomBox.setBottom(sellPane);
        
        
        setLeft(leftBox);
        setCenter(rightBox);
        setBottom(bottomBox);
    }
    
    /**
     * Get the sell button.
     * @return the sell button
     */
    public Button getSellButton() {
        return sellButton;
    }
    
    /**
     * Get the buy button.
     * @return the buy button
     */
    public Button getBuyButton() {
        return buyButton;
    }
    
    /**
     * Updates the info in the pane.
     * @param player the player who owns the ship
     * @param ship the ship to display info for
     */
    public final void updateInfo(Player player, Ship ship) {
        final String integerFormatString = "%d/%d";
        shipNameLabel.setText(ship.getName());
        shipHealthLabel.setText(String.format("%.0f/%.0f", ship.getHealth(),
                ship.getMaxHealth()));
        
        if (type == ShipInfoPaneType.SHIPYARD) {
            shipCargoLabel.setText(String.format(integerFormatString,
                    ship.getCargo().size(), ship.getCargoSlots()));
            shipFuelLabel.setText(String.format("%.0f/%.0f", ship.getFuel(),
                    ship.getMaxFuel()));
            shipWeaponsLabel.setText(String.format(integerFormatString,
                    ship.getWeapons().size(), ship.getWeaponSlots()));
            shipShieldsLabel.setText(String.format(integerFormatString,
                    ship.getShields().size(), ship.getShieldSlots()));
            shipGadgetsLabel.setText(String.format(integerFormatString,
                    ship.getGadgets().size(), ship.getGadgetSlots()));
            shipCrewLabel.setText(String.format(integerFormatString,
                    ship.getCrew().size(), ship.getCrewSlots()));
            if (player == null) {
                creditsLabel.setText("N/A");
            } else {
                creditsLabel.setText("" + player.getCredits());
            }
        } else if (type == ShipInfoPaneType.COMBAT) {
            shipShieldStrengthLabel.setText(String.format("%.0f/%.0f", ship.getShieldStrength(),
                ship.getMaxShieldStrength()));
        }
    }
    
    /**
     * Get the ship health label.
     * @return the ship health label
     */
    public Label getShipHealthLabel() {
        return shipHealthLabel;
    }
    
    /**
     * Get the ship shield strength label.
     * @return the ship shield strength label
     */
    public Label getShipShieldStrengthLabel() {
        return shipShieldStrengthLabel;
    }
}
