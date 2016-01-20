package hyenas.UI;

import hyenas.Models.Gadget;
import hyenas.Models.Player;
import hyenas.Models.Shield;
import hyenas.Models.Ship;
import hyenas.Models.Weapon;
import hyenas.UI.ShipyardTab.ShipyardTabType;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * For displaying the player's ship items in the shipyard.
 * @author Alex
 */
public class ShipyardPlayerTableView extends TableView {
    /**
     * The shipyard items tab type. Corresponds with a shipyard tab.
     */
    private ShipyardTabType type;
    /**
     * The name property value for use with populating column.
     */
    private static final String NAME_PROPERTY_VALUE = "name";
    
    /**
     * Initializes a table.
     * @param ptype the tab type
     */
    public ShipyardPlayerTableView(ShipyardTabType ptype) {
        setupTableForType(ptype);
        this.type = ptype;
        setEditable(false);
    }
    
    /**
     * Sets up the table for a given tab type.
     * @param ptype the tab type
     */
    public final void setupTableForType(ShipyardTabType ptype) {
        Player player = Player.getInstance();
        Ship ship = player.getShip();
        switch (ptype) {
            case SHIPS: {
                TableColumn mainCol = new MarketTableColumn("Your Ship");
                mainCol.setCellValueFactory(new PropertyValueFactory<>(NAME_PROPERTY_VALUE));
                mainCol.prefWidthProperty().bind(widthProperty());
                getColumns().setAll(mainCol);

                List<Ship> ships = new ArrayList<>();
                ships.add(ship);
                ObservableList<Ship> shipData = FXCollections.observableArrayList(ships);
                setItems(shipData);
                break;
            }
            case WEAPONS: {
                TableColumn mainCol = new MarketTableColumn("Your Weapons");
                mainCol.setCellValueFactory(new PropertyValueFactory<>(NAME_PROPERTY_VALUE));
                mainCol.prefWidthProperty().bind(widthProperty());
                getColumns().setAll(mainCol);

                List<Weapon> weapons = ship.getWeapons();
                ObservableList<Weapon> weaponData = FXCollections.observableArrayList(weapons);
                setItems(weaponData);
                break;
            }
            case SHIELDS: {
                TableColumn mainCol = new MarketTableColumn("Your Shields");
                mainCol.setCellValueFactory(new PropertyValueFactory<>(NAME_PROPERTY_VALUE));
                mainCol.prefWidthProperty().bind(widthProperty());
                getColumns().setAll(mainCol);

                List<Shield> shields = ship.getShields();
                ObservableList<Shield> shieldData = FXCollections.observableArrayList(shields);
                setItems(shieldData);
                break;
            }
            case GADGETS: {
                TableColumn mainCol = new MarketTableColumn("Your Gadgets");
                mainCol.setCellValueFactory(new PropertyValueFactory<>(NAME_PROPERTY_VALUE));
                mainCol.prefWidthProperty().bind(widthProperty());
                getColumns().setAll(mainCol);

                List<Gadget> gadgets = ship.getGadgets();
                ObservableList<Gadget> gadgetData = FXCollections.observableArrayList(gadgets);
                setItems(gadgetData);
                break;
            }
        }
    }
    
    /**
     * Get the type of the shipyard tab.
     * @return type the type of the shipyard tab
     */
    public ShipyardTabType getType() {
        return type;
    }
}
