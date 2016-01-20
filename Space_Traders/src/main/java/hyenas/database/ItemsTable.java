package hyenas.database;

import hyenas.Models.Player;
import hyenas.Models.Ship;
import hyenas.Models.Ware;
import hyenas.Models.Ware.Good;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The items table.
 * @author Abhishek
 */
public class ItemsTable implements Table<List<Ware>, Void> {

    /**
     * The address of the host server of the database server.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Items "
            + "(ItemID INTEGER NOT NULL, "
            + "Quantity INTEGER NOT NULL, " + "Player INTEGER NOT NULL, "
            + "PRIMARY KEY (ItemID) ON CONFLICT REPLACE, "
            + "FOREIGN KEY (Player) REFERENCES Players (ID))";

    /**
     * Initializes a connection manager.
     * @param connArgs 
     */
    public ItemsTable(Connection connArgs) {
        this.conn = connArgs;
    }

    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Test that the Items table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(List<Ware> wares, Void unused) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Items "
                    + "(ItemID, Quantity, Player) VALUES(?, ?, ?)")) {
            for (Ware ware : wares) {
                stmt.setInt(1, ware.getGood().ordinal());
                stmt.setInt(2, ware.getCurrentQuantity());
                stmt.setInt(3, 1); // Change to dynamic
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void update(List<Ware> wares, Void unused) {
        addRow(wares, unused);
    }

    @Override
    public void remove(List<Ware> wares, Void unused) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Items "
                    + "WHERE ItemID = ?")) {
            for (Ware ware : wares) {
                stmt.setInt(1, ware.getGood().ordinal());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet itemsInfo = stmt.executeQuery("SELECT ItemID,"
                    + " Quantity FROM Items");

            Ship ship = Player.getInstance().getShip();
            while (itemsInfo.next()) {
                Good good = Good.values()[itemsInfo.getInt(1)];
                int quantity = itemsInfo.getInt(2);
                
                for (int i = 0; i < quantity; i++) {
                    ship.getCargo().add(new Ware(good));
                }
            }
            itemsInfo.close();
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Items");
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Items");
        } catch (SQLException e) {
            Logger.getLogger(ItemsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
}
