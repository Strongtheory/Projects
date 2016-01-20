package hyenas.database;

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
 * This class manages the Ship table in the database, and allows getting
 * and updating information in the table.
 * @author Saikrishna Arcot
 */
public class ShipTable implements Table<Ship, Player> {

    /**
     * Connection to the database.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Ship "
            + "(ID INTEGER NOT NULL, " + "Type INTEGER NOT NULL, "
            + "Fuel DOUBLE NOT NULL, " + "Health DOUBLE NOT NULL, "
            + "ShieldStrength DOUBLE NOT NULL, "
            + "Player INTEGER NOT NULL, " + "PRIMARY KEY (ID), "
            + "FOREIGN KEY (Player) REFERENCES Players (ID))";

    /**
     * Create the ship table manager.
     * @param connArgs connection to the database
     */
    public ShipTable(Connection connArgs) {
        this.conn = connArgs;
    }
    
    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Test that the Ship table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(Ship ship, Player player) {
        String query = "INSERT INTO Ship "
                + "(Type, Fuel, Health, ShieldStrength, Player) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            fillAndRunUpdate(stmt, ship, player);
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Fill all parameters to the SQL query with values from the player object.
     * @param stmt Prepared statement to fill
     * @param ship ship object to use in reading data
     * @param player player object to use in reading data
     * @throws SQLException if an error in the database occurred in updating the information
     */
    private void fillAndRunUpdate(PreparedStatement stmt, Ship ship, Player player) throws SQLException {
        stmt.setInt(1, ship.getShipType().ordinal());
        stmt.setDouble(2, ship.getFuel());
        stmt.setDouble(3, ship.getHealth());
        stmt.setDouble(4, ship.getShieldStrength());
        
        try (PreparedStatement sysStmt = conn.prepareStatement("SELECT ID FROM Players WHERE Name = ?")) {
            sysStmt.setString(1, player.getName());
            try (ResultSet shipIDResultSet = sysStmt.executeQuery()) {
                if (!shipIDResultSet.next()) {
                    throw new IllegalArgumentException();
                }
                
                stmt.setInt(5, shipIDResultSet.getInt(1));
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(Ship ship, Player player) {
        String query = "UPDATE Ship "
                + "SET Type = ?, Fuel = ?, Health = ?, ShieldStrength = ? WHERE Player = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            fillAndRunUpdate(stmt, ship, player);
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void remove(Ship ship, Player player) {
        String query = "DELETE FROM Ship "
                    + "WHERE Type = ? AND Player = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ship.getShipType().ordinal());
            
            ResultSet shipIDResultSet = null;
            try (PreparedStatement sysStmt = conn.prepareStatement("SELECT ID FROM Players WHERE Name = ?")) {
                sysStmt.setString(1, player.getName());
                shipIDResultSet = sysStmt.executeQuery();
                if (!shipIDResultSet.next()) {
                    throw new IllegalArgumentException();
                }
            }
            
            stmt.setInt(2, shipIDResultSet.getInt(1));
            stmt.executeUpdate();
            
            shipIDResultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            String query = 
                "SELECT Ship.Type, " + "Ship.Fuel, "
                + "Ship.Health, Ship.ShieldStrength, Players.Name FROM "
                + "Ship INNER JOIN Players "
                + "ON Ship.Player = Players.ID";
            ResultSet shipInfo = stmt.executeQuery(query);
            if (!shipInfo.next()) {
                throw new IllegalStateException();
            }
            
            Player.getInstance().setShip(new Ship(Ship.ShipType.values()[shipInfo.getInt(1)]));
            Player.getInstance().getShip().setFuel(shipInfo.getDouble(2));
            Player.getInstance().getShip().setHealth(shipInfo.getDouble(3));
            Player.getInstance().getShip().setShieldStrength(shipInfo.getDouble(4));
            
            shipInfo.close();
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Ship");
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Ship");
        } catch (SQLException e) {
            Logger.getLogger(ShipTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
}
