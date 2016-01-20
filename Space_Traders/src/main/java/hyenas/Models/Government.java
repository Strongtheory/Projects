package hyenas.Models;

/**
 * Government for planet.
 * @author Alex
 */
public class Government {
    /**
     * The government type.
     */
    private GovernmentType type;
    /**
     * The government tax rate.
     */
    private double taxRate;
    
    /**
     * A GvoernmentType, used to distinguish between the types of governments.
     */
    public enum GovernmentType {
        /**
         * Hold elections.
         */
        DEMOCRACY,
        /**
         * Kings and such.
         */
        MONARCHY,
        /**
         * One dude in charge.
         */
        DICTATORSHIP,
        /**
         * Common ownership.
         */
        COMMUNISM,
        /**
         * Government-owned utilities.
         */
        SOCIALISM,
        /**
         * Representatives.
         */
        REPUBLIC,
        /**
         * Rich ruling class.
         */
        ARISTOCRACY
    }
    
    public Government(GovernmentType type) {
        switch (type) {
            case DEMOCRACY:
                taxRate = .15;
                break;
            case MONARCHY:
                taxRate = .3;
                break;
            case DICTATORSHIP:
                taxRate = .6;
                break;
            case COMMUNISM:
                taxRate = .7;
                break;
            case SOCIALISM:
                taxRate = .6;
                break;
            case REPUBLIC:
                taxRate = .15;
                break;
            case ARISTOCRACY:
                taxRate = .4;
                break;
        }
        this.type = type;
    }
    
    /**
     * Get the tax rate.
     * @return the tax rate
     */
    public double getTaxRate() {
        return taxRate;
    }
    
    /**
     * Get the user-facing government string
     * @return the government string
     */
    public String getGovernmentString() {
        switch (this.type) {
            case DEMOCRACY:
                return "Democracy";
            case MONARCHY:
                return "Monarchy";
            case DICTATORSHIP:
                return "Dictatorship";
            case COMMUNISM:
                return "Communism";
            case SOCIALISM:
                return "Socialism";
            case REPUBLIC:
                return "Republic";
            case ARISTOCRACY:
                return "Aristocracy";
        }
        return "N/A";
    }
}
