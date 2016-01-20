package hyenas;

import hyenas.Models.Gadget;
import hyenas.Models.Player;
import hyenas.Models.Shield;
import hyenas.Models.Ship;
import hyenas.Models.ShipyardBuyable;
import hyenas.Models.Weapon;
import hyenas.UI.AlertPane;
import hyenas.UI.AlertPane.AlertPaneType;
import hyenas.UI.ShipInfoPane;
import hyenas.UI.ShipInfoPane.ShipInfoPaneType;
import hyenas.UI.ShipyardItemsTableView;
import hyenas.UI.ShipyardPlayerTableView;
import hyenas.UI.ShipyardTab.ShipyardTabType;
import hyenas.UI.ShipyardTab;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 * FXML controller for buying and upgrading ships.
 * @author Alex
 */
public class ShipyardController implements Initializable {
    /**
     * The shipyard controller main anchor pane.
     */
    @FXML
    private AnchorPane anchorPane;
    /**
     * The shipyard controller title label.
     */
    @FXML
    private Label titleLabel;
    /**
     * The shipyard controller center pane.
     */
    @FXML
    private BorderPane centerPane;
    
    /**
     * The shipyard controller player ship table. Data changes to reflect
     * selected tab.
     */
    private ShipyardPlayerTableView playerShipTable =
            new ShipyardPlayerTableView(ShipyardTabType.SHIPS);
    /**
     * The shipyard controller ships table.
     */
    private ShipyardItemsTableView shipsTable =
            new ShipyardItemsTableView(ShipyardTabType.SHIPS);
    /**
     * The shipyard controller weapons table.
     */
    private ShipyardItemsTableView weaponsTable =
            new ShipyardItemsTableView(ShipyardTabType.WEAPONS);
    /**
     * The shipyard controller gadgets table.
     */
    private ShipyardItemsTableView gadgetsTable =
            new ShipyardItemsTableView(ShipyardTabType.GADGETS);
    /**
     * The shipyard controller shields table.
     */
    private ShipyardItemsTableView shieldsTable =
            new ShipyardItemsTableView(ShipyardTabType.SHIELDS);
    /**
     * The shipyard controller current table view (based on the selected tab).
     */
    private ShipyardItemsTableView currentTableView;
    /**
     * The shipyard controller info pane. Displays info about the ship.
     */
    private ShipInfoPane infoPane;
    /**
     * The tab pane width.
     */
    private static final int TAB_PANE_WIDTH = 600;
    /**
     * The player ship.
     */
    private Ship ship;
    /**
     * The shipyard controller tab pane, which contains the current table view.
     */
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font titleFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/BlenderPro-Book.otf").toExternalForm(), 40);
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: rgba(0,231,255, .9); -fx-effect: dropshadow( gaussian, rgba(0,0,0,1), 0,0,2,2);");
        
        ship = Player.getInstance().getShip();
        
        tabPane = new TabPane();
        tabPane.setPrefWidth(TAB_PANE_WIDTH);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab shipTab = new ShipyardTab(ShipyardTabType.SHIPS);
        Tab weaponsTab = new ShipyardTab(ShipyardTabType.WEAPONS);
        Tab shieldTab = new ShipyardTab(ShipyardTabType.SHIELDS);
        Tab gadgetTab = new ShipyardTab(ShipyardTabType.GADGETS);
        shipTab.setContent(shipsTable);
        weaponsTab.setContent(weaponsTable);
        shieldTab.setContent(shieldsTable);
        gadgetTab.setContent(gadgetsTable);
        tabPane.getTabs().addAll(shipTab, weaponsTab, shieldTab, gadgetTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setupForTabChange(newValue));
        
        setupForTabChange(shipTab);
        
        playerShipTable.setEditable(false);
        playerShipTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setSelectedSellItem(newValue));
        shipsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setSelectedBuyItem(newValue));
        weaponsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setSelectedBuyItem(newValue));
        shieldsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setSelectedBuyItem(newValue));
        gadgetsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setSelectedBuyItem(newValue));
        
        infoPane = new ShipInfoPane(ShipInfoPaneType.SHIPYARD);
        Button buyButton = infoPane.getBuyButton();
        EventHandler<ActionEvent> buyEvent = (ActionEvent e) -> {
            buyItem(e);
        };
        buyButton.setOnAction(buyEvent);
        Button sellButton = infoPane.getSellButton();
        EventHandler<ActionEvent> sellEvent = (ActionEvent e) -> {
            sellItem(e);
        };
        sellButton.setOnAction(sellEvent);
        
        centerPane.setRight(infoPane);
        centerPane.setCenter(playerShipTable);
        centerPane.setLeft(tabPane);
        
        BorderPane.setMargin(tabPane, new Insets(50, 20, 50, 80));
        BorderPane.setMargin(infoPane, new Insets(50, 80, 50, 20));
        BorderPane.setMargin(playerShipTable, new Insets(80, 0, 50, 0));
    }
    
    /**
     * Set the item the user clicked on to be the selected item to sell.
     * @param item item the user clicked on
     */
    private void setSelectedSellItem(Object item) {
        if (item == null) {
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(true);
        } else {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(true);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(false);
            if (currentTableView != null) {
                currentTableView.getSelectionModel().clearSelection();
            }
        }
    }
    
    /**
     * Set the item the user clicked on to be the selected item to buy.
     * @param item item the user clicked on
     */
    private void setSelectedBuyItem(Object item) {
        if (item == null) {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(true);
        } else {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(false);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(true);
            playerShipTable.getSelectionModel().clearSelection();
        }
    }
    
    /**
     * Change the tab.
     * @param tab new tab to display
     */
    public void setupForTabChange(Tab tab) {
        ShipyardTab shipyardTab;
        if (tab instanceof ShipyardTab) {
            shipyardTab = (ShipyardTab) tab;
        } else {
            return;
        }
        playerShipTable.setupTableForType(shipyardTab.getType());
        playerShipTable.getSelectionModel().clearSelection();
        centerPane.setCenter(playerShipTable);
        
        if (currentTableView != null) {
            currentTableView.getSelectionModel().clearSelection();
        }
        
        if (shipyardTab.getType() == ShipyardTabType.SHIPS) {
            currentTableView = shipsTable;
        } else if (shipyardTab.getType() == ShipyardTabType.WEAPONS) {
            currentTableView = weaponsTable;
        } else if (shipyardTab.getType() == ShipyardTabType.SHIELDS) {
            currentTableView = shieldsTable;
        } else if (shipyardTab.getType() == ShipyardTabType.GADGETS) {
            currentTableView = gadgetsTable;
        } else {
            currentTableView = null;
        }
        
        Label placeholderLabel = new Label(shipyardTab.getPlaceholder());
        placeholderLabel.getStyleClass().add("alertPaneTitleLabel");
        playerShipTable.setPlaceholder(placeholderLabel);
    }
    
    /**
     * Buy an item from the shipyard.
     * @param e unused action trigger
     */
    public void buyItem(ActionEvent e) {
        ShipyardTab shipyardTab = (ShipyardTab) tabPane.getSelectionModel()
                .getSelectedItem();
        if (shipyardTab.getType() == ShipyardTabType.SHIPS) {
            buyShip();
        } else {
            if (currentTableView != null) {
                ShipyardBuyable item = (ShipyardBuyable) currentTableView.getSelectionModel().getSelectedItem();
                Player player = Player.getInstance();
                if (item.hasFreeSlots(ship)) {
                    if (item.hasSufficientTechLevel(player.getCurrentPlanet())) {
                        if (player.getCredits() >= item.getPrice()) {
                            item.getShipItems(ship).add(item);
                            player.setCredits(player.getCredits() - item.getPrice());
                            player.getShip().updateShip();

                            if (shipyardTab.getType() == ShipyardTabType.GADGETS) {
                                HyenasLoader.getInstance().getConnectionManager()
                                        .getGadgetsTable().addRow((Gadget) item, ship);
                            } else if (shipyardTab.getType() == ShipyardTabType.WEAPONS) {
                                HyenasLoader.getInstance().getConnectionManager()
                                        .getWeaponsTable().addRow((Weapon) item, ship);
                            } else if (shipyardTab.getType() == ShipyardTabType.SHIELDS) {
                                HyenasLoader.getInstance().getConnectionManager()
                                        .getShieldsTable().addRow((Shield) item, ship);
                            }
                        } else {
                            displayInsufficientCreditsAlert();
                        }
                    } else {
                        displayAlert("Insufficient Tech Level", "This planet doesn't have the tech level to sell this item.");
                    }
                } else {
                    displayAlert("No Slots", "There are no available slots for this item on your ship.");
                }
            }
            Player player = Player.getInstance();
            infoPane.updateInfo(player, player.getShip());
            updatePlayerShipTable();
        }
    }

    /**
     * Buy a ship.
     */
    public void buyShip() {
        Player player = Player.getInstance();
        if (currentTableView != null) {
            Ship item = (Ship) currentTableView.getSelectionModel().getSelectedItem();
            if (item.getShipType() != ship.getShipType()) {
                int currentShipValue = (int) (ship.getPrice() * .8);
                if (player.getCredits() + currentShipValue >= item.getPrice()) {
                    int credits = player.getCredits();
                    player.setShip(item);
                    ship = item;
                    player.setCredits(credits + currentShipValue - item.getPrice());
                    
                    HyenasLoader.getInstance().getConnectionManager()
                            .getPlayerTable().update(player, null);
                    HyenasLoader.getInstance().getConnectionManager().getShipTable()
                            .update(item, player);
                    infoPane.updateInfo(player, player.getShip());
                    updatePlayerShipTable();
                } else {
                    displayInsufficientCreditsAlert();
                }
            } else {
                displayAlert("You already have this ship", "It doesn't make sense to buy a ship you already own.");
            }
        }
    }
    
    /**
     * Sell an item to the shipyard.
     * @param e unused
     */
    public void sellItem(ActionEvent e) {
        ShipyardTab shipyardTab = (ShipyardTab) tabPane.getSelectionModel()
                .getSelectedItem();
        
        if (shipyardTab.getType() == ShipyardTabType.SHIPS) {
            displayAlert("You can't sell your ship", "Your ship is automatically sold when you buy a new ship.");
        } else {
            Player player = Player.getInstance();
            
            ShipyardBuyable item = (ShipyardBuyable) playerShipTable.getSelectionModel().getSelectedItem();
            boolean removed = item.getShipItems(ship).remove(item);
            if (removed) {
                player.setCredits(player.getCredits() + item.getPrice());
            } else {
                displayAlert("No Item", "You do not have an item of this type on your ship.");
            }

            if (shipyardTab.getType() == ShipyardTabType.GADGETS) {
                HyenasLoader.getInstance().getConnectionManager()
                        .getGadgetsTable().remove((Gadget) item, ship);
            } else if (shipyardTab.getType() == ShipyardTabType.WEAPONS) {
                HyenasLoader.getInstance().getConnectionManager()
                        .getWeaponsTable().remove((Weapon) item, ship);
            } else if (shipyardTab.getType() == ShipyardTabType.SHIELDS) {
                HyenasLoader.getInstance().getConnectionManager()
                        .getShieldsTable().remove((Shield) item, ship);
            }
        }
        Player player = Player.getInstance();
        infoPane.updateInfo(player, player.getShip());
        updatePlayerShipTable();
    }
    
    /**
     * Updates the player ship table.
     */
    private void updatePlayerShipTable() {
        ShipyardTab shipyardTab = (ShipyardTab) tabPane.getSelectionModel()
                .getSelectedItem();
        playerShipTable.setupTableForType(shipyardTab.getType());
    }
    
    /**
     * Return to the system screen.
     * @param e unused
     */
    public void goBack(ActionEvent e) {
        HyenasLoader.getInstance().goToSystemScreen();
    }
    
    /**
     * Display an alert message.
     * @param title title of the message
     * @param message message itself
     */
    private void displayAlert(String title, String message) {
        AlertPane alertPane = new AlertPane(AlertPaneType.ONEBUTTON, title, message);
        EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
            anchorPane.getChildren().remove(alertPane);
        };
        alertPane.getCloseButton().setOnAction(closeAction);
        anchorPane.getChildren().add(alertPane);
    }
    
    /**
     * Displays an alert for use when the player has insufficient credits to
     * purchase an item.
     */
    private void displayInsufficientCreditsAlert() {
        displayAlert("Insufficient Credits", "You don't have enough credits to afford that.");
    }
}
