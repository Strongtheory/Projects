package hyenas.database;

import hyenas.Models.Player;
import hyenas.Models.Shield;
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
public class ShieldsTable implements Table<Shield, Ship> {

    /**
     * The connection variable to link to the database server.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Shields "
            + "(ID INTEGER NOT NULL, ShieldID INTEGER, "
            + "Ship INTEGER, "
            + "PRIMARY KEY (ID), FOREIGN KEY (Ship) "
            + "REFERENCES Ship (ID))";

    /**
     * Initializes a connection manager.
     * @param connArgs connection to the database
     */
    public ShieldsTable(Connection connArgs) {
        this.conn = connArgs;
    }
    
    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void addRow(Shield item, Ship ship) {
        String info = "INSERT INTO Shields (ShieldID, Ship) "
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
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void update(Shield item, Ship parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Shield item, Ship ship) {
        String info = "DELETE FROM Shields WHERE ShieldID = ? AND Ship = ?";
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
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, "Query: " + info, e);
        }
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            String query = "SELECT ShieldID, Ship FROM Shields";
            try (ResultSet shipInfo = stmt.executeQuery(query)) {
                while (shipInfo.next()) {
                    Shield.ShieldType shieldType = Shield.ShieldType.values()[
                            shipInfo.getInt(1)];
                    Shield shield = new Shield(shieldType);
                    Player.getInstance().getShip().getShields().add(shield);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Shields");
        } catch (SQLException e) {
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Shields");
        } catch (SQLException e) {
            Logger.getLogger(ShieldsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
}
