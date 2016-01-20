package hyenas.Models;

import hyenas.Models.Planet.PlanetTechLevel;
import java.util.ArrayList;
import java.util.List;

/**
 * A gadget that can be added to a ship.
 * @author Alex
 */
public class Gadget implements ShipyardBuyable {
    /**
     * The gadget type.
     */
    private GadgetType type;
    /**
     * The gadget name.
     */
    private String name;
    /**
     * The gadget price.
     */
    private int price;
    /**
     * The min tech level require to purchase.
     */
    private PlanetTechLevel minTechLevel;
    
    /**
     * A GadgetType, used to distinguish between the types of gadget.
     */
    public enum GadgetType {
        /**
         * Adds extra cargo slots.
         */
        EXTRA_CARGO,
        /**
         * Reduces fuel consumption.
         */
        NAVIGATION_SYSTEM,
        /**
         * Repairs ship automatically over time.
         */
        AUTO_REPAIR_SYSTEM,
        /**
         * Improves damage done when in combat.
         */
        TARGETING_SYSTEM,
        /**
         * Makes encountering pirates less likely.
         */
        CLOAKING_DEVICE,
        /**
         * Allows you to survive if ship is destroyed.
         */
        ESCAPE_POD
    }
    
    /**
     * Initializes an instance of Gadget.
     * Sets default values based on the gadget type
     * @param ptype the type of gadget
     */
    public Gadget(GadgetType ptype) {
        switch (ptype) {
            case EXTRA_CARGO:
                name = "Extra Cargo";
                price = 100;
                minTechLevel = PlanetTechLevel.PREAGRICULTURE;
                break;
            case NAVIGATION_SYSTEM:
                name = "Navigation System";
                price = 200;
                minTechLevel = PlanetTechLevel.MEDIEVAL;
                break;
            case AUTO_REPAIR_SYSTEM:
                name = "Auto-Repair System";
                price = 300;
                minTechLevel = PlanetTechLevel.MEDIEVAL;
                break;
            case TARGETING_SYSTEM:
                name = "Targeting System";
                price = 300;
                minTechLevel = PlanetTechLevel.RENAISSANCE;
                break;
            case CLOAKING_DEVICE:
                name = "Cloaking Device";
                price = 1000;
                minTechLevel = PlanetTechLevel.POSTINDUSTRIAL;
                break;
            case ESCAPE_POD:
                name = "Escape Pod";
                price = 200;
                minTechLevel = PlanetTechLevel.PREAGRICULTURE;
                break;
            default:
                break;
        }
        this.type = ptype;
    }
    
    /**
     * Get the name of the gadget.
     * @return name the name of the gadget
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the type of the gadget.
     * @return type the type of the gadget
     */
    public GadgetType getType() {
        return type;
    }
    
    /**
     * Get the minimum required tech level.
     * @return minTechLevel the min tech level
     */
    public String getMinTechLevelString() {
        return Planet.techLevelString(minTechLevel);
    }
    
    @Override
    public int getPrice() {
        return price;
    }
    
    @Override
    public boolean hasFreeSlots(Ship ship) {
        return ship.getGadgetSlots() > ship.getGadgets().size();
    }

    @Override
    public boolean hasSufficientTechLevel(Planet planet) {
        return planet.getTechLevel().ordinal() >= minTechLevel.ordinal();
    }

    @Override
    public List getShipItems(Ship ship) {
        return ship.getGadgets();
    }
    
    /**
     * Get the list of default buyable gadgets.
     * @return gadgets the list of gadgets
     */
    public static List<Gadget> getDefaultGadgets() {
        ArrayList<Gadget> gadgets = new ArrayList<>(GadgetType.values().length);
        for (GadgetType type: GadgetType.values()) {
            Gadget gadget = new Gadget(type);
            gadgets.add(gadget);
        }
        return gadgets;
    }
}
