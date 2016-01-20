package hyenas.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Ware object, for use when buying/selling Goods.
 */
public class Ware {
    /**
     * The ware name.
     */
    private String name;
    /**
     * The ware good. Used for distinguishing types of wares.
     */
    private Good good;
    /**
     * The ware minimum tech level required to produce.
     */
    private int minimumTechLevelToProduce;
    /**
     * The ware minimum tech level required to use.
     */
    private int minimumTechLevelToUse;
    /**
     * The number produced per tech level.
     */
    private int techLevelProduction;
    /**
     * The ware base price.
     */
    private int basePrice;
    /**
     * The ware price increase per level.
     */
    private int priceIncreasePerLevel;
    /**
     * The ware variance. TODO: Is this still used?
     */
    private int variance;
    /**
     * The ware min space trade price.
     */
    private int spaceTradeMinPrice;
    /**
     * The ware max space trade price.
     */
    private int spaceTradeMaxPrice;
    
    /**
     * The ware current calculated price (varies by planet).
     */
    private int currentPrice;
    /**
     * The ware current calculated quantity (varies by planet).
     */
    private int currentQuantity;
    /**
     * The ware current condition (varies by planet). Currently none, scarce
     * or abundant.
     */
    private String currentCondition;
    
    /**
     * A Good, used to distinguish between the types of goods.
     */
    public enum Good {
        /**
         * For satisfying thirst.
         */
        WATER,
        /**
         * For keeping warm.
         */
        FURS,
        /**
         * For eating.
         */
        FOOD,
        /**
         * For trading.
         */
        ORE,
        /**
         * For passing the time.
         */
        GAMES,
        /**
         * For defending against enemy boarding.
         */
        FIREARMS,
        /**
         * For healing player health damage.
         */
        MEDICINE,
        /**
         * For automating tasks, improving efficiency.
         */
        MACHINES,
        /**
         * For passing the time.
         */
        NARCOTICS,
        /**
         * For assisting in tasks.
         */
        ROBOTS
    }

    /**
     * Initializes a Ware and sets its initial values based on a given Good.
     * @param pgood the ware's good
     */
    public Ware(Good pgood) {
        this.good = pgood;
        setUp();
    }
    
    /**
     * Sets the initial default values for a Ware.
     */
    private void setUp() {
        switch (good) {
            case WATER:
                setupWater();
                break;
            case FURS:
                setupFurs();
                break;
            case FOOD:
                setupFood();
                break;
            case ORE:
                setupOre();
                break;
            case GAMES:
                setupGames();
                break;
            case FIREARMS:
                setupFirearms();
                break;
            case MEDICINE:
                setupMedicine();
                break;
            case MACHINES:
                setupMachines();
                break;
            case NARCOTICS:
                setupNarcotics();
                break;
            case ROBOTS:
                setupRobots();
                break;
        }
    }

    /**
     * Sets up the water ware.
     */
    private void setupWater() {
        name = "Water";
        minimumTechLevelToProduce = 0;
        minimumTechLevelToUse = 0;
        techLevelProduction = 2;
        basePrice = 30;
        priceIncreasePerLevel = 3;
        variance = 4;
        spaceTradeMinPrice = 30;
        spaceTradeMaxPrice = 50;
    }

    /**
     * Sets up the furs ware.
     */
    private void setupFurs() {
        name = "Furs";
        minimumTechLevelToProduce = 0;
        minimumTechLevelToUse = 0;
        techLevelProduction = 0;
        basePrice = 250;
        priceIncreasePerLevel = 10;
        variance = 10;
        spaceTradeMinPrice = 230;
        spaceTradeMaxPrice = 280;
    }

    /**
     * Sets up the food ware.
     */
    private void setupFood() {
        name = "Food";
        minimumTechLevelToProduce = 1;
        minimumTechLevelToUse = 0;
        techLevelProduction = 1;
        basePrice = 100;
        priceIncreasePerLevel = 5;
        variance = 5;
        spaceTradeMinPrice = 90;
        spaceTradeMaxPrice = 160;
    }

    /**
     * Sets up the ore ware.
     */
    private void setupOre() {
        name = "Ore";
        minimumTechLevelToProduce = 2;
        minimumTechLevelToUse = 2;
        techLevelProduction = 3;
        basePrice = 350;
        priceIncreasePerLevel = 20;
        variance = 10;
        spaceTradeMinPrice = 350;
        spaceTradeMaxPrice = 420;
    }

    /**
     * Sets up the games ware.
     */
    private void setupGames() {
        name = "Games";
        minimumTechLevelToProduce = 3;
        minimumTechLevelToUse = 1;
        techLevelProduction = 6;
        basePrice = 250;
        priceIncreasePerLevel = -10;
        variance = 5;
        spaceTradeMinPrice = 160;
        spaceTradeMaxPrice = 270;
    }

    /**
     * Sets up the firearms ware.
     */
    private void setupFirearms() {
        name = "Firearms";
        minimumTechLevelToProduce = 3;
        minimumTechLevelToUse = 1;
        techLevelProduction = 5;
        basePrice = 1250;
        priceIncreasePerLevel = -75;
        variance = 100;
        spaceTradeMinPrice = 600;
        spaceTradeMaxPrice = 1100;
    }

    /**
     * Sets up the medicine ware.
     */
    private void setupMedicine() {
        name = "Medicine";
        minimumTechLevelToProduce = 4;
        minimumTechLevelToUse = 1;
        techLevelProduction = 6;
        basePrice = 650;
        priceIncreasePerLevel = -20;
        variance = 10;
        spaceTradeMinPrice = 400;
        spaceTradeMaxPrice = 700;
    }

    /**
     * Sets up the machines ware.
     */
    private void setupMachines() {
        name = "Machines";
        minimumTechLevelToProduce = 4;
        minimumTechLevelToUse = 3;
        techLevelProduction = 5;
        basePrice = 900;
        priceIncreasePerLevel = -30;
        variance = 5;
        spaceTradeMinPrice = 600;
        spaceTradeMaxPrice = 800;
    }

    /**
     * Sets up the narcotics ware.
     */
    private void setupNarcotics() {
        name = "Narcotics";
        minimumTechLevelToProduce = 5;
        minimumTechLevelToUse = 0;
        techLevelProduction = 5;
        basePrice = 3500;
        priceIncreasePerLevel = -125;
        variance = 150;
        spaceTradeMinPrice = 2000;
        spaceTradeMaxPrice = 3000;
    }

    /**
     * Sets up the robots ware.
     */
    private void setupRobots() {
        name = "Robots";
        minimumTechLevelToProduce = 6;
        minimumTechLevelToUse = 4;
        techLevelProduction = 7;
        basePrice = 5000;
        priceIncreasePerLevel = -150;
        variance = 100;
        spaceTradeMinPrice = 3500;
        spaceTradeMaxPrice = 5000;
    }

    /**
     * Get the name of the ware.
     * @return name the name of the ware
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the ware's good.
     * @return good the good of the ware
     */
    public Good getGood() {
        return good;
    }

    /**
     * Get the minimum tech level to produce.
     * @return the min tech level to produce
     */
    public int getMinimumTechLevelToProduce() {
        return minimumTechLevelToProduce;
    }

    /**
     * Get the minimum tech level to use.
     * @return the min tech level to use
     */
    public int getMinimumTechLevelToUse() {
        return minimumTechLevelToUse;
    }

    /**
     * Get the tech level production.
     * @return the tech level production
     */
    public int getTechLevelProduction() {
        return techLevelProduction;
    }

    /**
     * Get the base price.
     * @return the base price
     */
    public int getBasePrice()   {
        return basePrice;
    }

    /**
     * Get the price increase per tech level.
     * @return the price increase per tech level
     */
    public int getPriceIncreasePerLevel() {
        return priceIncreasePerLevel;
    }

    /**
     * Get the price increase per tech level.
     * @return the price increase per tech level
     */
    public int getVariance() {
        return variance;
    }
    
    /**
     * Get the space trade minimum price.
     * @return the space trade min price
     */
    public int getSpaceTradeMinPrice() {
        return spaceTradeMinPrice;
    }

    /**
     * Get the space trade maximum price.
     * @return the space trade max price
     */
    public int getSpaceTradeMaxPrice() {
        return spaceTradeMaxPrice;
    }
    
    /**
     * Get the current price of the ware.
     * @return the current price
     */
    public int getCurrentPrice() {
        return currentPrice;
    }
    
    /**
     * Set the current price of the ware.
     * @param pcurrentPrice the current price
     */
    public void setCurrentPrice(int pcurrentPrice) {
        this.currentPrice = pcurrentPrice;
    }
    
    /**
     * Get the current quantity of the ware.
     * @return the current quantity
     */
    public int getCurrentQuantity() {
        return currentQuantity;
    }
    
    /**
     * Set the current quantity of the ware.
     * @param pcurrentQuantity the current quantity
     */
    public void setCurrentQuantity(int pcurrentQuantity) {
        this.currentQuantity = pcurrentQuantity;
    }
    
    /**
     * Get the current condition of the ware.
     * @return the current condition
     */
    public String getCurrentCondition() {
        return currentCondition;
    }
    
    /**
     * Set the current condition of the ware.
     * @param pcurrentCondition the current condition
     */
    public void setCurrentCondition(String pcurrentCondition) {
        this.currentCondition = pcurrentCondition;
    }
    
    /**
     * Get the list of the default wares.
     * @return the list of the default wares
     */
    public static List<Ware> defaultWares() {
        ArrayList<Ware> wares = new ArrayList<>(Good.values().length);
        for (Good good: Good.values()) {
            Ware ware = new Ware(good);
            wares.add(ware);
        }
        return wares;
    }
    
    @Override
    public String toString() {
        return "<Ware: " + name + ", Cost: " + currentPrice + ", Avl: "
                + currentQuantity + ">";
    }
}
