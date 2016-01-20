package hyenas;

import hyenas.Models.Company;
import hyenas.Models.Galaxy;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import hyenas.Models.Player;
import hyenas.Models.Ware;
import hyenas.UI.AlertPane;
import hyenas.UI.MaketTableView;
import hyenas.UI.MarketInfoPane;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author JR
 */


public class StockMarketController implements Initializable {
    /**
     * The stock market controller title label.
     */
    @FXML
    private Label titleLabel;
    /**
     * The market controller planet table view.
     */
    private MaketTableView marketTable = new MaketTableView(MaketTableView.MarketTableType.STOCK);
    /**
     * The market controller player table view.
     */
    private MaketTableView playerTable = new MaketTableView(MaketTableView.MarketTableType.PLAYER_2);
    /**
     * The market controller info pane.
     */
    private MarketInfoPane infoPane;
    /**
     * The market controller main anchor pane.
     */
    @FXML
    private AnchorPane anchorPane;
    
    /**
     * The market controller border pane.
     */
    @FXML
    private BorderPane borderPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font titleFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/BlenderPro-Book.otf").toExternalForm(), 40);
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: rgba(0,231,255, .9); -fx-effect: dropshadow( gaussian, rgba(0,0,0,1), 0,0,2,2);");
        
        BorderPane tablesPane = new BorderPane();
        marketTable.setPrefHeight(400.0);
        marketTable.setPrefWidth(350.0);
        marketTable.setEditable(false);
        
        updateMarketTableView();
        marketTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setSelectedCompany((Company) newValue));    
        
        BorderPane emptyLeftTablePane = new BorderPane();
        emptyLeftTablePane.setPrefWidth(150.0);
        tablesPane.setLeft(emptyLeftTablePane);
        
        BorderPane emptyBottomTablePane = new BorderPane();
        emptyBottomTablePane.setPrefHeight(150.0);
        tablesPane.setBottom(emptyBottomTablePane);
        
        tablesPane.setCenter(marketTable);
        borderPane.setCenter(tablesPane);
        
        VBox rightBox = new VBox();
        rightBox.setSpacing(10.0);
        infoPane = new MarketInfoPane();
        Button buyButton = infoPane.getBuyButton();
        EventHandler<ActionEvent> buyEvent = (ActionEvent e) -> {
            buyStock(e);
        };
        buyButton.setOnAction(buyEvent);
        Button sellButton = infoPane.getSellButton();
        EventHandler<ActionEvent> sellEvent = (ActionEvent e) -> {
            sellStock(e);
        };
        sellButton.setOnAction(sellEvent);
        
        rightBox.getChildren().addAll(infoPane);
        borderPane.setRight(rightBox);
        
        
        borderPane.setPadding(new Insets(40));
        BorderPane.setMargin(marketTable, new Insets(50, 50, 50, 20));
        BorderPane.setMargin(rightBox, new Insets(50, 0, 0, 0));      
    }
    
    /**
     * Sets the selected company to buy/sell.
     * @param company the company to buy/sell
     */
    private void setSelectedCompany(Company company) {
        if (company == null) {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(true);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(true);
        } else {
            Button buyButton = infoPane.getBuyButton();
            buyButton.setDisable(false);
            Button sellButton = infoPane.getSellButton();
            sellButton.setDisable(false);
        }
    }
    
    /**
     * Sell a stock
     * @param e unused
     */
    public void sellStock(ActionEvent e) {
        removeAlert();
        Company company = (Company) marketTable.getSelectionModel().getSelectedItem();
        Player player = Player.getInstance();
        int own = company.getPlayerAmount();
        int price = company.getPrice();
        int credits = player.getCredits();
        int remaining = company.getTotalStocks() - own;
        if (own > 0) {
            player.setCredits(credits + price);
            company.setPlayerAmount(company.getPlayerAmount() - 1);
            company.setAvailable(company.getAvailable() + 1);
            infoPane.updateInfo();
            updateMarketTableView();
        } else {
            displayAlert("No Shares", "You don't have any of shares of this company to sell.");
        }
    }
    
    /**
     * Updates market table view.
     */
    private void updateMarketTableView() {
        ObservableList<Company> currentItems = marketTable.getItems();
        currentItems.clear();
        
        List<Company> companies = Galaxy.getInstance().getCompanies();
        ObservableList<Company> marketTableData = FXCollections.observableArrayList(companies);
        marketTable.setItems(marketTableData);
    }
    
    /**
     * Buy a stock
     * @param e unused
     */
    public void buyStock(ActionEvent e) {
        removeAlert();
        Company company = (Company) marketTable.getSelectionModel().getSelectedItem();// change this
        Player player = Player.getInstance();
        int own = company.getPlayerAmount();
        int price = company.getPrice();
        int credits = player.getCredits();
        int remaining = company.getAvailable();
        if (remaining > 0) {
            if (credits >= price) {
                player.setCredits(credits - price);
                company.setPlayerAmount(company.getPlayerAmount() + 1);
                company.setAvailable(company.getAvailable() - 1);
                infoPane.updateInfo();
                updateMarketTableView();
            } else {
                displayAlert("Insufficient Credits", "You don't have enough credits.");
            }   
        } else {
            displayAlert("No Shares Remaining", "There are no shares of this company available for purchase");
        }
    }
    
    /**
     * Displays an alert.
     * @param title the alert title
     * @param message the alert message
     */
    private void displayAlert(String title, String message) {
        AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON, title, message);
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
     * Changes to system screen.
     * @param e unused action trigger
     */
    public void goBack(ActionEvent e) {
        HyenasLoader.getInstance().goToSystemScreen();
    }   
}