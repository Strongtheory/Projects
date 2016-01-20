package hyenas.database;

import hyenas.Models.Galaxy;
import hyenas.Models.SolarSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the Solar System table in the database, and allows getting
 * and updating information in the table.
 * @author Saikrishna Arcot
 */
public class SolarSystemTable implements Table<SolarSystem, Void> {

    /**
     * Connection to the database.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS SolarSystem "
            + "(ID INTEGER NOT NULL, " + "Name VARCHAR(20) NOT NULL, "
            + "XPoint INTEGER NOT NULL, " + "YPoint INTEGER NOT NULL, "
            + "PRIMARY KEY (ID))";

    /**
     * Create the solar system table manager.
     * @param connArgs connection to the database
     */
    public SolarSystemTable(Connection connArgs) {
        this.conn = connArgs;
    }

    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Test that the SolarSystem table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(SolarSystem solarSystem, Void unused) {
        String query = "INSERT INTO "
                    + "SolarSystem (Name, XPoint, YPoint) "
                    + "VALUES(?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, solarSystem.getSystemName());
            stmt.setInt(2, solarSystem.getX());
            stmt.setInt(3, solarSystem.getY());
            stmt.executeUpdate();
            // Id is generated based on how the System is generated.
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void update(SolarSystem solarSystem, Void unused) {
        String query = "UPDATE "
                + "SolarSystem SET XPoint = ?, YPoint = ? "
                + "WHERE Name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, solarSystem.getX());
            stmt.setInt(2, solarSystem.getY());
            stmt.setString(3, solarSystem.getSystemName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void remove(SolarSystem solarSystem, Void unused) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + "SolarSystem WHERE Name = ?")) {
            stmt.setString(1, solarSystem.getSystemName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet solarSystems = stmt.executeQuery("SELECT * FROM [SolarSystem]")) {
                while (solarSystems.next()) {
                    String systemName = solarSystems.getString(2);
                    SolarSystem solarSystem = Galaxy.getInstance()
                            .getSolarSystemForName(systemName);
                    if (solarSystem != null) {
                        solarSystem.getPlanets().clear();
                        solarSystem.setX(solarSystems.getInt(3));
                        solarSystem.setY(solarSystems.getInt(4));
                    }
                }
            }
            Galaxy.getInstance().setLocationsSet(true);
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE SolarSystem");
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM SolarSystem");
        } catch (SQLException e) {
            Logger.getLogger(SolarSystemTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
}
