package hyenas.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * A shield that can be added to a ship.
 * @author Alex
 */
public class Shield implements ShipyardBuyable {
    /**
     * The shield type.
     */
    private ShieldType type;
    /**
     * The shield name.
     */
    private String name;
    /**
     * The price of the shield.
     */
    private int price;
    /**
     * The shield strength.
     */
    private int strength;
    
    
    /**
     * A ShieldType, used to distinguish between the types of shields.
     */
    public enum ShieldType {
        /**
         * The weakest shield type.
         */
        CIVILIAN_ENERGY,
        /**
         * The medium strength shield type.
         */
        MILITIA_ENERGY,
        /**
         * The strongest shield type.
         */
        MILITARY_ENERGY,
        /**
         * The weakest shield type. Also reflects damage.
         */
        CIVILIAN_REFLECTIVE,
        /**
         * The medium strength shield type. Also reflects damage.
         */
        MILITIA_REFLECTIVE,
        /**
         * The stronger shield type. Also reflects damage.
         */
        MILITARY_REFLECTIVE
    }
    
    /**
     * Initializes an instance of Shield.
     * @param ptype the type of shield
     */
    public Shield(ShieldType ptype) {
        switch (ptype) {
            case CIVILIAN_ENERGY:
                name = "Civilian Energy";
                price = 100;
                strength = 100;
                break;
            case MILITIA_ENERGY:
                name = "Militia Energy";
                price = 200;
                strength = 200;
                break;
            case MILITARY_ENERGY:
                name = "Military Energy";
                price = 300;
                strength = 400;
                break;
            case CIVILIAN_REFLECTIVE:
                name = "Civilian Reflective";
                price = 400;
                strength = 600;
                break;
            case MILITIA_REFLECTIVE:
                name = "Militia Reflective";
                price = 500;
                strength = 800;
                break;
            case MILITARY_REFLECTIVE:
                name = "Military Reflective";
                price = 600;
                strength = 1000;
                break;
            default:
                break;
        }
        this.type = ptype;
    }
    
    /**
     * Get the name of the shield.
     * @return the name of the shield
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the type of shield.
     * @return type the type of shield
     */
    public ShieldType getType() {
        return type;
    }
    
    /**
     * Get the strength of the shield.
     * @return the strength of the shield
     */
    public int getStrength() {
        return strength;
    }
    
    @Override
    public int getPrice() {
        return price;
    }
    
    @Override
    public boolean hasFreeSlots(Ship ship) {
        return ship.getShieldSlots() > ship.getShields().size();
    }
    
    @Override
    public boolean hasSufficientTechLevel(Planet planet) {
        return true;
    }

    @Override
    public List getShipItems(Ship ship) {
        return ship.getShields();
    }
    
    /**
     * Get the list of default shields.
     * @return the list of shields
     */
    public static List getDefaultShields() {
        ArrayList<Shield> shields = new ArrayList<>(ShieldType.values().length);
        for (ShieldType type: ShieldType.values()) {
            Shield shield = new Shield(type);
            shields.add(shield);
        }
        return shields;
    }
}
