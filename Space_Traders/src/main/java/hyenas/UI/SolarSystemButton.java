package hyenas.UI;

import hyenas.Models.Galaxy;
import hyenas.Models.SolarSystem;
import java.awt.Point;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * For displaying a solar system button.
 * @author Alex
 */
public class SolarSystemButton extends Button {
    /**
     * The factor the solar system's size is increased by.
     */
    public static final double SYSTEM_UI_SIZE_FACTOR = 10.0;
    /**
     * The solar system button's CSS style class.
     */
    private static final String STYLE_CLASS = "planet";

    /**
     * Sets up the button for the given system in the map UI.
     * @param solarSystem the solar system
     */
    public void setupForMapUI(SolarSystem solarSystem) {
        setUpSystemImage(solarSystem, 1);
        if (!Galaxy.getInstance().isLocationsSet()) {
            Point coordinates = getCoordinates();
            solarSystem.setX((int) coordinates.getX());
            solarSystem.setY((int) coordinates.getY());
        }
        setLayoutX(solarSystem.getX());
        setLayoutY(solarSystem.getY());
        setId(solarSystem.getSystemName());
        setMnemonicParsing(false);
        getStyleClass().add(STYLE_CLASS);
    }

    /**
     * Sets up the button for the given system in the solar system UI.
     * @param solarSystem the solar system
     */
    public void setupForSystemUI(SolarSystem solarSystem) {
        setUpSystemImage(solarSystem, SYSTEM_UI_SIZE_FACTOR);
        setMnemonicParsing(false);
        getStyleClass().add(STYLE_CLASS);
    }
    
    /**
     * Sets up the button image for the given system and size factor.
     * @param solarSystem the solar system
     * @param sizeFactor the size factor
     */
    public void setUpSystemImage(SolarSystem solarSystem, double sizeFactor) {
        Image image = new Image("hyenas/images/Planet.png");
        ImageView planetImageView = new ImageView(image);
        planetImageView.setFitWidth(solarSystem.getSize() * sizeFactor);
        planetImageView.setPreserveRatio(true);
        setPrefWidth(solarSystem.getSize() * sizeFactor);
        setPrefHeight(solarSystem.getSize() * sizeFactor);
        setGraphic(planetImageView);

        String styleString = String.format("-fx-effect: innershadow(gaussian,"
                + "%s, 15, 0, 0, 0)", solarSystem.getColorString());
        setStyle(styleString);
    }

    /**
     * Generate random coordinates for the button.
     * @return the coordinates of the system button
     */
    private Point getCoordinates() {
        Random rand = new Random();
        // Adds padding, so no button is too close to edge
        int x = 20 + rand.nextInt(UIHelper.GALAXY_SIZE - 50);
        int y = 20 + rand.nextInt(UIHelper.GALAXY_SIZE - 50);

        Point coordinates = new Point(x, y);
        return coordinates;
    }
}
