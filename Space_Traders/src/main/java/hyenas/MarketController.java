package hyenas;

import hyenas.Models.Planet;
import hyenas.Models.Player;
import hyenas.Models.Ware;
import hyenas.UI.AlertPane;
import hyenas.UI.AlertPane.AlertPaneType;
import hyenas.UI.MaketTableView;
import hyenas.UI.MaketTableView.MarketTableType;
import hyenas.UI.MarketInfoPane;
import hyenas.UI.StandardButton;
import hyenas.database.ConnectionManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class for marketplace.
 * @author Alex
 */
public class MarketController implements Initializable {
    /**
     * The market controller planet.
     */
    private Planet planet;
    /**
     * The market controller planet table view.
     */
    private MaketTableView planetTable = new MaketTableView(MarketTableType.PLANET);
    /**
     * The market controller player table view.
     */
    private MaketTableView playerTable = new MaketTableView(MarketTableType.PLAYER);
    /**
     * The market controller info pane.
     */
    private MarketInfoPane infoPane;
    
    /**
     * The market controller border pane.
     */
    @FXML
    private BorderPane borderPane;
    /**
     * The market controller main anchor pane.
     */
    @FXML
    private AnchorPane anchorPane;
    /**
     * The market controller title label.
     */
    @FXML
    private Label titleLabel;
    
    /**
     * Fuel Pane class for viewing the price of fuel and buying fuel.
     */
    private class FuelPane extends BorderPane {
        /**
         * The fuel pane fuel button.
         */
        private Button fuelButton;
        /**
         * The fuel pane fuel cost label.
         */
        private Label fuelCostLabel;
        
        /**
         * Initializes an instance of FuelPane and sets up labels and buttons.
         */
        public FuelPane() {
            getStyleClass().add("alertPane");
            
            int fuelCost = planet.getFuelCost();
            boolean canSellFuel = planet.hasShipyard();
            
            if (canSellFuel) {
                fuelCostLabel = new Label("Fuel Cost: " + fuelCost);
            } else {
                fuelCostLabel = new Label("Fuel Cost: N/A");
            }
            fuelCostLabel.getStyleClass().add("alertPaneTitleLabel");
            fuelCostLabel.setPadding(new Insets(10));
            setLeft(fuelCostLabel);

            BorderPane fuelButtonPane = new BorderPane();
            fuelButton = new StandardButton("Buy", StandardButton.StandardButtonType.MEDIUM);
            EventHandler<ActionEvent> buyFuelEvent = (ActionEvent e) -> {
                buyFuel(e);
            };
            fuelButton.setOnAction(buyFuelEvent);
            fuelButton.setDisable(!canSellFuel);
            fuelButtonPane.setCenter(fuelButton);
            setRight(fuelButtonPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Player player = Player.getInstance();
        planet = player.getCurrentPlanet();
        
        Font titleFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/BlenderPro-Book.otf").toExternalForm(), 40);
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: rgba(0,231,255, .9); -fx-effect: dropshadow( gaussian, rgba(0,0,0,1), 0,0,2,2);");
        
        BorderPane tablesPane = new BorderPane();
        planetTable.setPrefHeight(400.0);
        planetTable.setPrefWidth(350.0);
        planetTable.setEditable(false);
        playerTable.setPrefHeight(400.0);
        playerTable.setPrefWidth(202.0);
        playerTable.setEditable(false);
        
        updatePlanetTableView();
        planetTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setSelectedBuyWare((Ware) newValue));
        updatePlayerTableView();
        playerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setSelectedSellWare((Ware) newValue));
        
        BorderPane emptyLeftTablePane = new BorderPane();
        emptyLeftTablePane.setPrefWidth(150.0);
        tablesPane.setLeft(emptyLeftTablePane);
        
        BorderPane emptyBottomTablePane = new BorderPane();
        emptyBottomTablePane.setPrefHeight(150.0);
        tablesPane.setBottom(emptyBottomTablePane);
        
        
        tablesPane.setCenter(planetTable);
        tablesPane.setRight(playerTable);
        borderPane.setCenter(tablesPane);
        
        
        VBox rightBox = new VBox();
        rightBox.setSpacing(10.0);
        infoPane = new MarketInfoPane();
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
        
        
        BorderPane fuelPane = new FuelPane();
        rightBox.getChildren().addAll(infoPane, fuelPane);
        borderPane.setRight(rightBox);
        
        
        borderPane.setPadding(new Insets(40));
        BorderPane.setMargin(planetTable, new Insets(50, 50, 50, 20));
        BorderPane.setMargin(playerTable, new Insets(50, 20, 50, 20));
        BorderPane.setMargin(rightBox, new Insets(50, 0, 0, 0));
    }
    
    /**
     * Updates player table view.
     */
    private void updatePlayerTableView() {
        ObservableList<Ware> currentItems = playerTable.getItems();
        currentItems.clear();
        
        Player player = Player.getInstance();
        List<Ware> playerWares = player.getShip().getWares();
        ObservableList<Ware> playerTableData = FXCollections.observableArrayList(playerWares);
        playerTable.setItems(playerTableData);
    }
    
    /**
     * Updates planet table view.
     */
    private void updatePlanetTableView() {
        ObservableList<Ware> currentItems = planetTable.getItems();
        currentItems.clear();
        
        List<Ware> wares = planet.getWares();
        ObservableList<Ware> planetTableData = FXCollections.observableArrayList(wares);
        planetTable.setItems(planetTableData);
    }

    /**
     * Handles buying of an item, checks for edge cases.
     * @param e unused
     */
    public void buyItem(ActionEvent e) {
        removeAlert();
        Ware ware = (Ware) planetTable.getSelectionModel().getSelectedItem();
        int price = ware.getCurrentPrice();
        Player player = Player.getInstance();
        int credits = player.getCredits();
        
        int currentQuantity = ware.getCurrentQuantity();
        if (currentQuantity > 0) {
            if (price <= credits) {
                boolean success = player.getShip().addCargo(ware);
                if (success) {
                    int newCredits = credits - price;
                    player.setCredits(newCredits);
                    ware.setCurrentQuantity(--currentQuantity);
                    
                    updateDatabase();

                    infoPane.updateInfo();
                    updatePlanetTableView();
                    updatePlayerTableView();
                } else {
                    displayAlert("Ship Cargo Full", "There is no room on your ship for more items.");
                }
            } else {
                displayInsufficientCreditsAlert();
            }
        } else {
            displayAlert("No Items Remaining", "The planet has run out of this item.");
        }
    }
    
    /**
     * Displays an alert for use when the player has insufficient credits to
     * purchase an item.
     */
    private void displayInsufficientCreditsAlert() {
        displayAlert("Insufficient Credits", "You don't have enough credits to purchase this item.");
    }
    
    /**
     * Handles selling of an item.
     * @param e unused
     */
    public void sellItem(ActionEvent e) {
        removeAlert();
        Ware ware = (Ware) playerTable.getSelectionModel().getSelectedItem();
        Player player = Player.getInstance();
        
        int quantity = ware.getCurrentQuantity();
        if (quantity > 0) {
            int techLevel = planet.getTechLevel().ordinal();
            int minTechLevelToUse = ware.getMinimumTechLevelToUse();
            if (minTechLevelToUse <= techLevel) {
                player.getShip().removeCargo(ware);
                planet.addWare(ware);

                int sellValue = (int) (ware.getBasePrice() * .8);
                int credits = player.getCredits();
                player.setCredits(credits + sellValue);
                
                updateDatabase();

                infoPane.updateInfo();
                updatePlanetTableView();
                updatePlayerTableView();
            } else {
                displayAlert("Insufficient Tech Level", "The planet doesn't want to buy an item it can't use yet.");
            }
        } else {
            displayAlert("No Items", "You don't have any of this item to sell.");
        }
    }
    
    /**
     * Updates the player and items database.
     */
    private void updateDatabase() {
        Player player = Player.getInstance();
        ConnectionManager connectionManager = HyenasLoader.getInstance().getConnectionManager();
        connectionManager.getPlayerTable().update(player, null);
        connectionManager.getItemsTable().update(player.getShip().getWares(), null);
    }
    
    /**
     * Displays an alert.
     * @param title the alert title
     * @param message the alert message
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
     * Removes (hides) an alert.
     */
    private void removeAlert() {
        List children = anchorPane.getChildren();
        if (children.size() > 1) {
            children.remove(children.get(1));
        }
    }
    
    /**
     * Sets the selected ware to sell.
     * @param ware the ware to sell
     */
    private void setSelectedSellWare(Ware ware) {
        if (ware == null) {
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(true);
        } else {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(true);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(false);
            planetTable.getSelectionModel().clearSelection();
        }
    }
    
    /**
     * Sets the selected ware to buy.
     * @param ware the ware to buy
     */
    private void setSelectedBuyWare(Ware ware) {
        if (ware == null) {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(true);
        } else {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(false);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(true);
            playerTable.getSelectionModel().clearSelection();
        }
    }
    
    /**
     * The amount of fuel to buy at a time.
     */
    private static final double FUEL_INCREMENT = 10.0;
    
    /**
     * Handles buying fuel.
     * @param e unused
     */
    public void buyFuel(ActionEvent e) {
        Player player = Player.getInstance();
        int fuelCost = planet.getFuelCost();
        
        int credits = player.getCredits();
        
        double fuel = player.getShip().getFuel();
        double maxFuel = player.getShip().getMaxFuel();
        double fuelNeeded = maxFuel - fuel;

        if (fuelNeeded > 0) {
            int totalFuelCost = (int) (fuelCost * FUEL_INCREMENT);
            int newFuel = (int) (fuel + FUEL_INCREMENT);

            if (fuelNeeded < FUEL_INCREMENT) {
                totalFuelCost = (int) (fuelCost * fuelNeeded);
                newFuel = (int) (fuel + fuelNeeded);
            }

            if (credits >= totalFuelCost) {
                player.setCredits(credits - totalFuelCost);
                player.getShip().setFuel(newFuel);
                
                ConnectionManager connectionManager = HyenasLoader.getInstance().getConnectionManager();
                connectionManager.getPlayerTable().update(player, null);
                connectionManager.getShipTable().update(player.getShip(), player);
            } else {
                displayInsufficientCreditsAlert();
            }
        } else {
            displayAlert("Ship Full", "Your ship can't carry any more fuel.");
        }
        
        infoPane.updateInfo();
    }
    
    /**
     * Changes to system screen.
     * @param e unused action trigger
     */
    public void goBack(ActionEvent e) {
        HyenasLoader.getInstance().goToSystemScreen();
    }
}
