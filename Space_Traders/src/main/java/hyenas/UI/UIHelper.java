package hyenas.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

/**
 * For UI helper methods.
 * @author Alex
 */
public class UIHelper {
    /**
     * The size of the galaxy in pixels.
     */
    public static final int GALAXY_SIZE = 2000;
    /**
     * The width of a system in pixels.
     */
    public static final double SYSTEM_WIDTH = 1160.0;
    /**
     * The height of a system in pixels.
     */
    public static final double SYSTEM_HEIGHT = 680.0;

    /**
     * Returns the monitor's screen size.
     * @return the screen size
     */
    public static final Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    /**
     * Generates a random color string for use with CSS.
     * @return generated random color string
     */
    public static String randomColorString() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return String.format("rgb(%d, %d, %d, 1)", r, g, b);
    }
}
