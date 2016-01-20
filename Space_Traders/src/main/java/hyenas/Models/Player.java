package hyenas.Models;

import hyenas.Models.Ship.ShipType;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player.
 * @author saikrishna
 */
public class Player {
    /**
     * The player name.
     */
    private String name;
    /**
     * The player points.
     */
    private int points;
    /**
     * The player pilot skill.
     */
    private int pilotSkill;
    /**
     * The player fighter skill.
     */
    private int fighterSkill;
    /**
     * The player trader skill.
     */
    private int traderSkill;
    /**
     * The player engineer skill.
     */
    private int engineerSkill;
    /**
     * The player investor skill.
     */
    private int investorSkill;
    /**
     * The system the player is currently on.
     */
    private SolarSystem currentSystem;
    /**
     * The planet the player is currently on.
     */
    private Planet currentPlanet;
    /**
     * The player ship.
     */
    private Ship ship;
    /**
     * The player credits.
     */
    private int credits;
    /**
     * The player state. For use with database
     */
    private boolean state;
    /**
     * The common player instance. For use with singleton.
     */
    private static volatile Player instance;

    /**
     * Initializes an instance of Player, sets initial values.
     */
    private Player() {
        // KEEP PRIVATE - use getInstance instead
        ship = new Ship(ShipType.FLEA);
        Random rand = new Random();
        SolarSystem[] solarSystems = Galaxy.getInstance().getSolarSystems().values()
                .toArray(new SolarSystem[Galaxy.getInstance().getSolarSystems().values().size()]);
        currentSystem = solarSystems[rand.nextInt(solarSystems.length)];
        currentPlanet = currentSystem.getPlanets().get(0);
        credits = 2000;
        state = false;
    }

    /**
     * Getter for Player singleton.
     * @return the common player instance
     */
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    /**
     * For if the player's ship dies.
     */
    public void death() {
        boolean escapePod = false;
        for (Gadget gadget : ship.getGadgets())  {
            if (gadget.getType() == Gadget.GadgetType.ESCAPE_POD)    {
                escapePod = true;
                break;
            }
        }
        if (escapePod)  {
            ship = new Ship(ShipType.GNAT);
        }
        else    {
            instance = new Player();
        }
    }

    /**
     * Get player's name.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Set player's name.
     * @param pname the String name of the player
     */
    public void setName(String pname) {
        this.name = pname;
    }

    /**
     * Get player's total points.
     * @return the player's total points
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Set player's total points.
     * @param ppoints the points
     */
    public void setPoints(int ppoints) {
        this.points = ppoints;
    }
    
    /**
     * Get player's pilot skill.
     * @return the pilot skill of the player
     */
    public int getPilotSkill() {
        return pilotSkill;
    }

    /**
     * Set player's pilot skill.
     * @param ppilotSkill the pilot skill of the player
     */
    public void setPilotSkill(int ppilotSkill) {
        this.pilotSkill = ppilotSkill;
    }

    /**
     * Get player's fighter skill.
     * @return the fighter skill of the player
     */
    public int getFighterSkill() {
        return fighterSkill;
    }

    /**
     * Set player's fighter skill.
     * @param pfighterSkill the fighter skill of the player
     */
    public void setFighterSkill(int pfighterSkill) {
        this.fighterSkill = pfighterSkill;
    }

    /**
     * Get player's trader skill.
     * @return the trader skill of the player
     */
    public int getTraderSkill() {
        return traderSkill;
    }

    /**
     * Set player's trader skill.
     * @param ptraderSkill the trader skill of the player
     */
    public void setTraderSkill(int ptraderSkill) {
        this.traderSkill = ptraderSkill;
    }

    /**
     * Get player's engineer skill.
     * @return the engineer skill of the player
     */
    public int getEngineerSkill() {
        return engineerSkill;
    }

    /**
     * Set player's engineer skill.
     * @param pengineerSkill the engineer skill of the player
     */
    public void setEngineerSkill(int pengineerSkill) {
        this.engineerSkill = pengineerSkill;
    }

    /**
     * Get player's investor skill.
     * @return the investor skill of the player
     */
    public int getInvestorSkill() {
        return investorSkill;
    }

    /**
     * Set player's investor skill.
     * @param pinvestorSkill the investor skill of the player
     */
    public void setInvestorSkill(int pinvestorSkill) {
        this.investorSkill = pinvestorSkill;
    }

    /**
     * Get player's current system.
     * @return the current SolarSystem of the player
     */
    public SolarSystem getCurrentSystem() {
        return currentSystem;
    }

    /**
     * Set player's current system.
     * @param pcurrentSystem the player's current SolarSystem
     */
    public void setCurrentSystem(SolarSystem pcurrentSystem) {
        this.currentSystem = pcurrentSystem;
    }

    /**
     * Get player's current planet.
     * @return the current Planet of the player
     */
    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    /**
     * Set player's current planet.
     * @param pcurrentPlanet the current planet of the player
     */
    public void setCurrentPlanet(Planet pcurrentPlanet) {
        this.currentPlanet = pcurrentPlanet;
    }

    /**
     * Get player's ship.
     * @return the player's ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Set player's ship.
     * @param pship the Ship of the player
     */
    public void setShip(Ship pship) {
        this.ship = pship;
    }

    /**
     * Get player's credits.
     * @return the amount of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Set player's credits.
     * @param pcredits the amount of credits
     */
    public void setCredits(int pcredits) {
        this.credits = pcredits;
    }
    
    /**
     * Get player's game state.
     * @return the player's game state
     */
    public boolean getState() {
        return state;
    }
    
    /**
     * Set player's state.
     * @param pstate the game state
     */
    public void setState(boolean pstate) {
        this.state = pstate;
    }
    
    /**
     * Check to see if the player can travel to the solar system.
     * @param solarSystem system to travel to
     * @return true if the player can travel there; otherwise, false
     */
    public boolean canTravelToSystem(SolarSystem solarSystem) {
        if (currentSystem == solarSystem) {
            return true;
        }
        double fuel = ship.getFuel();
        double distance = DijkstraHelper.getDijkstraDistance(currentSystem, solarSystem);
        if (distance == -1) {
            return false;
        }
        return (fuel > distance);
    }
}
