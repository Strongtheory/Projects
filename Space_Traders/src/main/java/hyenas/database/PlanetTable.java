package hyenas.database;

import hyenas.Models.Galaxy;
import hyenas.Models.Planet;
import hyenas.Models.SolarSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the Planet table in the database, and allows getting
 * and updating information in the table.
 * @author Saikrishna Arcot
 */
public class PlanetTable implements Table<Planet, SolarSystem> {

    /**
     * Connection to the database.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Planet "
            + "(ID INTEGER NOT NULL, " + "Name VARCHAR(20) NOT NULL, "
            + "Radius INTEGER NOT NULL, "
            + "ClockwiseOrbit BOOLEAN NOT NULL, " + "Size DOUBLE NOT NULL, "
            + "Tech INTEGER NOT NULL, " + "Type INTEGER NOT NULL, "
            + "SSID INTEGER, " + "PRIMARY KEY (ID), "
            + "FOREIGN KEY (SSID) REFERENCES SolarSystem (ID))";

    /**
     * Create the planet table manager.
     * @param connArgs connection to the database
     */
    public PlanetTable(Connection connArgs) {
        this.conn = connArgs;
    }
    
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet planets = stmt.executeQuery("SELECT Planet.Name,"
                    + " Planet.Radius, Planet.ClockwiseOrbit, Planet.Size,"
                    + " Planet.Tech, Planet.Type, SolarSystem.Name"
                    + " FROM Planet INNER JOIN SolarSystem"
                    + " ON Planet.SSID = SolarSystem.ID");
            while (planets.next()) {
                String solarSystemName = planets.getString(7);
                SolarSystem solarSystem = Galaxy.getInstance()
                        .getSolarSystemForName(solarSystemName);

                Planet planet = new Planet(planets.getString(1),
                        planets.getBoolean(3), planets.getInt(5),
                        planets.getInt(6));
                planet.setSize(planets.getDouble(4));
                planet.setOrbitRadius(planets.getInt(2));

                solarSystem.getPlanets().add(planet);
            }
            planets.close();
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

/**************************************************************************/
    
    /**
     * Test that the Planet table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
// **************************************************************************

    @Override
    public void addRow(Planet planet, SolarSystem system) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Planet "
                    + "(Name, Radius, ClockwiseOrbit, Size, "
                    + "Tech, Type, SSID) VALUES(?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, planet.getPlanetName());
            stmt.setInt(2, planet.getOrbitRadius());
            stmt.setBoolean(3, planet.isClockwiseOrbit());
            stmt.setDouble(4, planet.getSize());
            stmt.setInt(5, planet.getTechLevel().ordinal());
            stmt.setInt(6, planet.getPlanetType().ordinal());

            int solarSystemId = getSolarSystemResultSet(system);

            stmt.setInt(7, solarSystemId);
            stmt.executeUpdate();
            // Id & ssid are generated based on how the System is generated.
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void update(Planet planet, SolarSystem system) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Planet "
                    + "SET Radius = ?, ClockwiseOrbit = ?, Size = ?, "
                    + "Tech = ?, Type = ? WHERE SSID = ? AND Name = ?")) {
            stmt.setInt(1, planet.getOrbitRadius());
            stmt.setBoolean(2, planet.isClockwiseOrbit());
            stmt.setDouble(3, planet.getSize());
            stmt.setInt(4, planet.getTechLevel().ordinal());
            stmt.setInt(5, planet.getPlanetType().ordinal());

            int solarSystemId = getSolarSystemResultSet(system);

            stmt.setInt(6, solarSystemId);
            stmt.setString(7, planet.getPlanetName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void remove(Planet planet, SolarSystem system) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Planet "
                    + "WHERE SSID = ? AND Name = ?")) {
            int solarSystemId = getSolarSystemResultSet(system);

            stmt.setInt(1, solarSystemId);
            stmt.setString(2, planet.getPlanetName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Get the Solar System ID of the given solar system object.
     * @param system solar system to get the ID of
     * @return ID of the solar system
     * @throws SQLException 
     */
    private int getSolarSystemResultSet(SolarSystem system) throws SQLException {
        try (PreparedStatement systemStatement = conn.prepareStatement(
                    "SELECT ID FROM SolarSystem WHERE Name = ?")) {
            systemStatement.setString(1, system.getSystemName());
            try (ResultSet systemIDResultSet = systemStatement.executeQuery()) {
                if (!systemIDResultSet.next()) {
                    systemIDResultSet.close();
                    throw new IllegalArgumentException();
                }
                return systemIDResultSet.getInt(1);
            }
        }
    }

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Planet");
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Planet");
        } catch (SQLException e) {
            Logger.getLogger(PlanetTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
}