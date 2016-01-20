package hyenas.database;

import hyenas.Models.Galaxy;
import hyenas.Models.Player;
import hyenas.Models.SolarSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the Player table in the database, and allows getting
 * and updating information in the table.
 * @author Saikrishna Arcot
 */
public class PlayerTable implements Table<Player, Void> {

    /**
     * Connection to the database.
     */
    private final Connection conn;
    
    /**
     * The SQL query needed to create the table.
     */
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS Players "
            + "(ID INTEGER NOT NULL, " + "Name VARCHAR(20) NOT NULL, "
            + "Points INTEGER NOT NULL, " + "Engineer INTEGER NOT NULL, "
            + "Pilot INTEGER NOT NULL, " + "Investor INTEGER NOT NULL, "
            + "Fighter INTEGER NOT NULL, " + "Trader INTEGER NOT NULL, "
            + "Credits INTEGER, " + "SSID INTEGER, "
            + "PRIMARY KEY (ID), "
            + "FOREIGN KEY (SSID) REFERENCES SolarSystem (ID))";

    /**
     * Create the player table manager.
     * @param connArgs connection to the database
     */
    public PlayerTable(Connection connArgs) {
        this.conn = connArgs;
    }

    /**
     * Create the player table.
     */
    @Override
    public void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Test that the Player table is created based 
     * on the SQL query and connection.
     * Used for JUnit testing of above method for validation.
     * @return true if table is created, false otherwise
     */
    public boolean createTableTest() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_QUERY);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    @Override
    public void addRow(Player player, Void unused) {
        String query = "INSERT INTO Players "
                    + "(Name, Points, Engineer, Pilot, Fighter, Investor, "
                    + "Trader, Credits, SSID) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            fillAndRunUpdate(stmt, player);
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void update(Player player, Void parent) {
        String query = "UPDATE Players "
                    + "SET Name = ?, Points = ?, Engineer = ?, Pilot = ?, "
                    + "Fighter = ?, Investor = ?, Trader = ?, Credits = ?,"
                    + "SSID = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            fillAndRunUpdate(stmt, player);
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Fill all parameters to the SQL query with values from the player object.
     * @param stmt Prepared statement to fill
     * @param player player object to use
     * @throws SQLException if an error in the database occurred in updating the information
     */
    private void fillAndRunUpdate(PreparedStatement stmt, Player player) throws SQLException {
        stmt.setString(1, player.getName());
        stmt.setInt(2, player.getPoints());
        stmt.setInt(3, player.getEngineerSkill());
        stmt.setInt(4, player.getPilotSkill());
        stmt.setInt(5, player.getFighterSkill());
        stmt.setInt(6, player.getInvestorSkill());
        stmt.setInt(7, player.getTraderSkill());
        stmt.setInt(8, player.getCredits());
        
        if (player.getCurrentSystem() != null) {
            try (PreparedStatement solarSystemStmt = conn.prepareStatement("SELECT ID "
                        + "FROM SolarSystem WHERE Name = ?")) {
                solarSystemStmt.setString(1, player.getCurrentSystem().getSystemName());
                ResultSet solarSystemResult = solarSystemStmt.executeQuery();
                if (solarSystemResult.next()) {
                    stmt.setInt(9, solarSystemResult.getInt(1));
                } else {
                    stmt.setNull(9, java.sql.Types.INTEGER);
                }
                solarSystemResult.close();
            }
        } else {
            stmt.setNull(9, java.sql.Types.INTEGER);
        }
        
        stmt.executeUpdate();
    }
    
    @Override
    public void remove(Player player, Void unused) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + "Players WHERE Name = ?")) {
            stmt.setString(1, player.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    /**
     * Load the player data from the database. This is used when continuing
     * the game.
     */
    @Override
    public void loadTable() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet playerInfo = stmt.executeQuery("SELECT Players.Name,"
                    + " Players.Points, Players.Engineer, Players.Pilot,"
                    + " Players.Investor, Players.Fighter, Players.Trader,"
                    + " Players.Credits,"
                    + " SolarSystem.Name FROM Players"
                    + " INNER JOIN SolarSystem"
                    + " ON Players.SSID = SolarSystem.ID");

            if (!playerInfo.next()) {
                throw new IllegalStateException();
            }
            Player player = Player.getInstance();
            player.setName(playerInfo.getString(1));
            player.setPoints(playerInfo.getInt(2));
            player.setEngineerSkill(playerInfo.getInt(3));
            player.setPilotSkill(playerInfo.getInt(4));
            player.setInvestorSkill(playerInfo.getInt(5));
            player.setFighterSkill(playerInfo.getInt(6));
            player.setTraderSkill(playerInfo.getInt(7));
            player.setCredits(playerInfo.getInt(8));

            SolarSystem system = Galaxy.getInstance().getSolarSystemForName(
                    playerInfo.getString(9));
            player.setCurrentSystem(system);
            player.setCurrentPlanet(system.getPlanets().get(0));
            
            playerInfo.close();
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

    // Important: For now, only allow one character at a time.
    // Therefore there will be only one row per table at a time.

    @Override
    public void dropTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE Players");
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void clearTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Players");
        } catch (SQLException e) {
            Logger.getLogger(PlayerTable.class.getName()).
                    log(Level.SEVERE, null, e);
        }
    }

}
