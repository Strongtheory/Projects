package hyenas.database;

import hyenas.Models.Player;
import hyenas.Models.Ship;
import hyenas.Models.Weapon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The weapons table.
 * @author Abhishek
 */
public class WeaponsTable implements Table<Weapon, Ship> {

    /**
     * The address of the host server of the database server.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Weapons "
            + "(ID INTEGER NOT NULL, "
            + "Ship INTEGER NOT NULL, "
            + "PRIMARY KEY (ID), FOREIGN KEY (Ship) "
            + "REFERENCES Ship (ID))";

    /**
     * Initializes a connection manager.
     * @param connArgs 
     */
    public WeaponsTable(Connection connArgs) {
        this.conn = connArgs;
    }
    
    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Test that the Weapons table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(Weapon weapon, Ship ship) {
        String query = "INSERT INTO Weapons "
                    + "VALUES(?, ?)";
        doAddOrDeleteCommand(query, ship, weapon);
    }

    /**
     * Make a INSERT and DELETE SQL query, and execute it.
     * @param query query to run
     * @param ship ship data to use
     * @param weapon weapon data to use
     */
    private void doAddOrDeleteCommand(String query, Ship ship, Weapon weapon) {
        try (PreparedStatement stmt = conn.prepareStatement(query);
                PreparedStatement sysStmt = conn.prepareStatement(
                        "SELECT ID FROM Ship WHERE Type = ?")) {
            sysStmt.setInt(1, ship.getShipType().ordinal());
            try (ResultSet shipIDResultSet = sysStmt.executeQuery()) {
                if (!shipIDResultSet.next()) {
                    throw new IllegalArgumentException();
                }
                
                stmt.setInt(1, weapon.getType().ordinal());
                stmt.setInt(2, shipIDResultSet.getInt(1));
                stmt.execute();
            }
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, "Query: " + query, e);
        }
    }

    @Override
    public void update(Weapon weapon, Ship ship) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void remove(Weapon weapon, Ship ship) {
        String info = "DELETE FROM Weapons "
                + "WHERE ID = ? AND Ship = ?";
        doAddOrDeleteCommand(info, ship, weapon);
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            String query = "SELECT ID, Ship FROM Weapons";
            try (ResultSet shipInfo = stmt.executeQuery(query)) {
                while (shipInfo.next()) {
                    Weapon.WeaponType weaponType = Weapon.WeaponType.values()[
                            shipInfo.getInt(1)];
                    Weapon weapon = new Weapon(weaponType);
                    Player.getInstance().getShip().getWeapons().add(weapon);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Weapons");
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Weapons");
        } catch (SQLException e) {
            Logger.getLogger(WeaponsTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
}
