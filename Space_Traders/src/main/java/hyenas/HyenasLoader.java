package hyenas;

import hyenas.database.ConnectionManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application driver class.
 * @author Alex
 */
public final class HyenasLoader extends Application {
    /**
     * The common hyenas loader instance. For use with singleton.
     */
    private static HyenasLoader instance;
    /**
     * The connection manager. For use with database.
     */
    private ConnectionManager connectionManager;
    /**
     * The hyenas loader stage.
     */
    private Stage stage;
    /**
     * The hyenas loader stage.
     */
    private static final String MAIN_WINDOW_FXML_FILE = "MainWindow.fxml";

    /**
     * Gets the common HyenasLoader instance, since we should only have one.
     * @return the current HyenasLoader instance
     */
    public static HyenasLoader getInstance() {
        return instance;
    }

    @Override
    public void start(Stage pstage) throws Exception {
        instance = this;

        connectionManager = new ConnectionManager("jdbc:sqlite:database.db");
        connectionManager.openConnection();
        
        // Create the tables. If the tables are already created, this will
        // do nothing.
        connectionManager.getSolarSystemTable().createTable();
        connectionManager.getPlanetTable().createTable();
        connectionManager.getPlayerTable().createTable();
        connectionManager.getItemsTable().createTable();
        connectionManager.getShipTable().createTable();
        connectionManager.getGadgetsTable().createTable();
        connectionManager.getWeaponsTable().createTable();
        connectionManager.getShieldsTable().createTable();

        this.stage = pstage;
        stage.setFullScreen(true);
        Parent root = FXMLLoader.load(getClass().getResource(
                    MAIN_WINDOW_FXML_FILE));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("hyenas/Common.css");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets the database connection manager.
     * @return the connection manager
     */
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
    
    /**
     * Changes screens to the allocation screen and resets all the tables.
     */
    public void goToAllocationScreen() {
        connectionManager.getShieldsTable().clearTable();
        connectionManager.getWeaponsTable().clearTable();
        connectionManager.getGadgetsTable().clearTable();
        connectionManager.getShipTable().clearTable();
        connectionManager.getItemsTable().clearTable();
        connectionManager.getPlayerTable().clearTable();
        connectionManager.getPlanetTable().clearTable();
        connectionManager.getSolarSystemTable().clearTable();

        loadScreen("AllocationUI.fxml");
    }

    /**
     * Changes screens to the map UI screen.
     */
    public void goToMapScreen() {
        loadScreen("MapUI.fxml");
    }
    
    /**
     * Changes screens to the Settings screen.
     */
    public void goToSettingsScreen() {
        loadScreen("Settings.fxml");
    }
    
    /**
     * Changes screens to the Home screen.
     */
    public void goToHomeScreen() {
        loadScreen(MAIN_WINDOW_FXML_FILE);
    }
    
    /**
     * Changes screens to the Marketplace screen.
     */
    public void goToMarketplace() {
        loadScreen("MarketUI.fxml");
    }
    
    /**
     * Changes screens to the System screen.
     */
    public void goToSystemScreen() {
        loadScreen("SystemUI.fxml");
    }
    
    /**
     * Changes screens to the Shipyard screen.
     */
    public void goToShipyard() {
        loadScreen("ShipyardUI.fxml");
    }
    
    /**
     * Changes screens to the Shipyard screen.
     */
    public void goToStockMarket() {
        loadScreen("StockMarketUI.fxml");
    }    
    
    /**
     * Changes screens to the Combat screen.
     */
    public void goToCombat() {
        loadScreen("CombatUI.fxml");
    }
    
    /**
     * Loads a given screen and catches errors if necessary.
     * @param screen the screen to load
     */
    private void loadScreen(String screen) {
        changePage(screen);
    }

    /**
     * Changes to the given page.
     * @param pageName the name of the page
     */
    private void changePage(String pageName) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(pageName));
            stage.getScene().setRoot(page);
        } catch (IOException ex) {
            Logger.getLogger(HyenasLoader.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Restore last point in game in SolarSystem view.
     */
    public void continueGame() {
        connectionManager.getSolarSystemTable().loadTable();
        connectionManager.getPlanetTable().loadTable();
        connectionManager.getPlayerTable().loadTable();
        connectionManager.getItemsTable().loadTable();
        connectionManager.getShipTable().loadTable();
        connectionManager.getGadgetsTable().loadTable();
        connectionManager.getWeaponsTable().loadTable();
        connectionManager.getShieldsTable().loadTable();
        goToMapScreen();
    }

    /**
     * Close connection to database if not already closed, and exit application.
     */
    public void closeGame() {
        // In the main menu the game should not be connected to the database!
        connectionManager.closeConnection();
    }

/**********************************************************************/

    /**
     * Launches the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Gets the stage.
     * @return the current stage
     */
    public Stage getStage() {
        return stage;
    }
}