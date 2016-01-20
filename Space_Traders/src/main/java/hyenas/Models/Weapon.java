package hyenas.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * A weapon that can be added to a ship.
 * @author Alex
 */
public class Weapon implements ShipyardBuyable {
    /**
     * The weapon type.
     */
    private WeaponType type;
    /**
     * The weapon name.
     */
    private String name;
    /**
     * The price of the weapon.
     */
    private int price;
    /**
     * The weapon damage to ships.
     */
    private int shipDamage;
    /**
     * The weapon damage to shields.
     */
    private int shieldDamage;
    
    /**
     * A WeaponType, used to distinguish between the types of weapon.
     */
    public enum WeaponType {
        /**
         * Pulse weapon (avg v shields, avg v hull).
         */
        PULSE,
        /**
         * Beam weapon (strong v shields, weak v hull).
         */
        BEAM,
        /**
         * missile weapon (strong v hull, weak v shields).
         */
        MISSILE,
    }
    
    /**
     * Initializes an instance of Weapon.
     * @param ptype the type of weapon
     */
    public Weapon(WeaponType ptype) {
        switch (ptype) {
            case PULSE:
                name = "Pulse";
                price = 100;
                shipDamage = 100;
                shieldDamage = 100;
                break;
            case BEAM:
                name = "Beam";
                price = 200;
                shieldDamage = 300;
                shipDamage = 50;
                break;
            case MISSILE:
                name = "Missile";
                price = 300;
                shipDamage = 300;
                shieldDamage = 50;
                break;
            default:
                break;
        }
        this.type = ptype;
    }
    
    /**
     * Get the name of the weapon.
     * @return the name of the weapon
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the damage of the weapon to health.
     * @return the damage of the weapon to health
     */
    public int getShipDamage()    {
        return shipDamage;
    }
    
    /**
     * Get the damage of the weapon to shields
     * @return the damage of the weapon to shields
     */
    public int getShieldDamage()    {
        return shieldDamage;
    }

    /**
     * Get the type of the weapon.
     * @return the type of the weapon
     */
    public WeaponType getType() {
        return type;
    }
    
    @Override
    public int getPrice() {
        return price;
    }
    
    @Override
    public boolean hasFreeSlots(Ship ship) {
        return ship.getWeaponSlots() > ship.getWeapons().size();
    }
    
    @Override
    public boolean hasSufficientTechLevel(Planet planet) {
        return true;
    }

    @Override
    public List getShipItems(Ship ship) {
        return ship.getWeapons();
    }
    
    /**
     * Get the list of default weapons.
     * @return the list of weapons
     */
    public static List getDefaultWeapons() {
        ArrayList<Weapon> weapons = new ArrayList<>(WeaponType.values().length);
        for (WeaponType atype: WeaponType.values()) {
            Weapon weapon = new Weapon(atype);
            weapons.add(weapon);
        }
        return weapons;
    }
}