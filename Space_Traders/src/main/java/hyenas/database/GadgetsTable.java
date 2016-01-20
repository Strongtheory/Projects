package hyenas.database;

import hyenas.Models.Gadget;
import hyenas.Models.Player;
import hyenas.Models.Ship;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The gadget table.
 * @author Abhishek
 */
public class GadgetsTable implements Table<Gadget, Ship> {

    /**
     * The connection variable to link to the database server.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Gadgets "
            + "(ID INTEGER NOT NULL, GadgetID INTEGER, "
            + "Ship INTEGER, "
            + "PRIMARY KEY (ID), FOREIGN KEY (Ship) "
            + "REFERENCES Ship (ID))";

    /**
     * Initializes a connection manager.
     * @param connArgs connection to the database
     */
    public GadgetsTable(Connection connArgs) {
        this.conn = connArgs;
    }
    
    @Override
    public void createTable() {
        // Property -> Increase Shield
        // Property -> Increase Health
        // Property -> Increase Weapon Damage (all)
        // Make the property an integer type
        // 1 -> Increase health (Random value)
        // 2 -> Increase shields (Random value)
        // 3 -> Increase Weapon Damage (all)
        
        
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Test that the Gadgets table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(Gadget item, Ship ship) {
        String info = "INSERT INTO Gadgets (GadgetID, Ship) "
                    + "VALUES(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(info)) {
            stmt.setInt(1, item.getType().ordinal());
            
            ResultSet shipIDResultSet;
            try (PreparedStatement sysStmt = conn.prepareStatement("SELECT ID FROM Ship WHERE Type = ?")) {
                sysStmt.setInt(1, ship.getShipType().ordinal());
                shipIDResultSet = sysStmt.executeQuery();
                if (!shipIDResultSet.next()) {
                    throw new IllegalArgumentException();
                }
                stmt.setInt(2, shipIDResultSet.getInt(1));
            }
            
            stmt.execute();
            shipIDResultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void update(Gadget item, Ship parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Gadget item, Ship ship) {
        String info = "DELETE FROM Gadgets WHERE GadgetID = ? AND Ship = ?";
        try (PreparedStatement stmt = conn.prepareStatement(info)) {
            stmt.setInt(1, item.getType().ordinal());
            
            ResultSet shipIDResultSet;
            try (PreparedStatement sysStmt = conn.prepareStatement("SELECT ID FROM Ship WHERE Type = ?")) {
                sysStmt.setInt(1, ship.getShipType().ordinal());
                shipIDResultSet = sysStmt.executeQuery();
                if (!shipIDResultSet.next()) {
                    throw new IllegalArgumentException();
                }
                stmt.setInt(2, shipIDResultSet.getInt(1));
            }
            
            stmt.execute();
            shipIDResultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, "Query: " + info, e);
        }
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            String query = "SELECT GadgetID, Ship FROM Gadgets";
            try (ResultSet shipInfo = stmt.executeQuery(query)) {
                while (shipInfo.next()) {
                    Gadget.GadgetType gadgetType = Gadget.GadgetType.values()[
                            shipInfo.getInt(1)];
                    Gadget gadget = new Gadget(gadgetType);
                    Player.getInstance().getShip().getGadgets().add(gadget);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Gadgets");
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Gadgets");
        } catch (SQLException e) {
            Logger.getLogger(GadgetsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
}
