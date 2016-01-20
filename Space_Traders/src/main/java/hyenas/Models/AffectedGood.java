package hyenas.Models;

/**
 * Represents a good affected by either the Planet's Type, or a PlanetEvent.
 * @author Alex
 */
public class AffectedGood {
    /**
     * The good that is affected.
     */
    private Ware.Good good;
    /**
     * Whether or not the price is increased.
     */
    private boolean increasedPrice;
    /**
     * Text when a good is abundant.
     */
    public static final String ABUNDANT_TEXT = "Abundant";
    /**
     * Text when a good is scarce.
     */
    public static final String SCARCE_TEXT = "Scarce";

    /**
     * Initializes AffectedGood instance.
     *
     * @param pgood the Good type
     * @param pincreasedPrice whether the affected good price increases
     */
    public AffectedGood(Ware.Good pgood, boolean pincreasedPrice) {
        this.good = pgood;
        this.increasedPrice = pincreasedPrice;
    }

    /**
     * Getter for the good.
     *
     * @return the Good type
     */
    public Ware.Good getGood() {
        return good;
    }

    /**
     * Getter for whether the price is increased.
     *
     * @return true if the price is increased; false otherwise
     */
    public boolean isIncreasedPrice() {
        return increasedPrice;
    }
}
