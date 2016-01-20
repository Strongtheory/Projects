package hyenas.Models;

import java.util.List;

/**
 * An buyable item at the shipyard.
 * @author Alex
 */
public interface ShipyardBuyable {
    /**
     * Get the price of the item.
     * @return the price of the item
     */
    int getPrice();
    
    /**
     * Checks whether the ship has free slots for the item.
     * @param ship the ship to check
     * @return true if the ship has free slots; false otherwise
     */
    boolean hasFreeSlots(Ship ship);
    
    /**
     * Whether the planet has a sufficient tech level to sell this item. Default
     * is true.
     * @param planet the planet to check
     * @return true if the planet can sell the item; false otherwise
     */
    boolean hasSufficientTechLevel(Planet planet);
    
    /**
     * Get the list of corresponding items on the ship.
     * @param ship the ship to get the items from
     * @return the list of corresponding items
     */
    List<ShipyardBuyable> getShipItems(Ship ship);
}
