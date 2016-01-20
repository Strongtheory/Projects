package hyenas.UI;

import javafx.scene.control.Button;

/**
 * The standard button used throughout the ui.
 * @author Alex
 */
public class StandardButton extends Button {
    /**
     * The preferred width for the regular button type.
     */
    public static final double REGULAR_PREF_WIDTH = 143.0;
    /**
     * The preferred height for the regular button type.
     */
    public static final double REGULAR_PREF_HEIGHT = 37.0;
    
    /**
     * The preferred width for the medium button type.
     */
    public static final double MEDIUM_PREF_WIDTH = 120.0;
    /**
     * The preferred height for the medium button type.
     */
    public static final double MEDIUM_PREF_HEIGHT = 31.0;
    
    /**
     * The preferred width for the small button type.
     */
    public static final double SMALL_PREF_WIDTH = 104.0;
    /**
     * The preferred height for the small button type.
     */
    public static final double SMALL_PREF_HEIGHT = 27.0;
    
    /**
     * StandardButtonType, for distinguishing between the types of buttons.
     */
    public enum StandardButtonType {
        /**
         * A regular sized button.
         */
        REGULAR,
        /**
         * A small sized button.
         */
        SMALL,
        /**
         * A medium sized button.
         */
        MEDIUM
    }
    
    /**
     * Default initializer for standard button.
     */
    public StandardButton() {
        this("");
    }
    
    /**
     * Initializes a standard button with text.
     * @param text the text to set
     */
    public StandardButton(String text) {
        this(text, StandardButtonType.REGULAR);
    }
    
    /**
     * Initializes a standard button with text and given type.
     * @param text the text to set
     * @param type the type of the button
     */
    public StandardButton(String text, StandardButtonType type) {
        super(text);
        getStyleClass().add("standard-button");
        switch (type) {
            case SMALL:
                setPrefWidth(SMALL_PREF_WIDTH);
                setPrefHeight(SMALL_PREF_HEIGHT);
                break;
            case MEDIUM:
                setPrefWidth(MEDIUM_PREF_WIDTH);
                setPrefHeight(MEDIUM_PREF_HEIGHT);
                break;
            case REGULAR:
            default:
                setPrefWidth(REGULAR_PREF_WIDTH);
                setPrefHeight(REGULAR_PREF_HEIGHT);
                break;
        }
        
    }
}
