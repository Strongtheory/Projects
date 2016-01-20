package hyenas.UI;

import hyenas.Models.SolarSystem;
import hyenas.UI.AlertPane.AlertPaneMessageLabel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Displays info about solar system.
 * @author Alex
 */
public class SolarSystemInfoPane extends AnchorPane {
    /**
     * The solar system info pane's system name label.
     */
    private Label systemNameLabel;
    /**
     * The solar system info pane's planet number label.
     */
    private Label numPlanetsLabel;
    /**
     * The solar system info pane's travel button.
     */
    private Button travelButton;
    /**
     * The solar system info pane size.
     */
    private static final int INFO_PANE_SIZE = 200;

    /**
     * Initializer for solar system Pane.
     */
    public SolarSystemInfoPane() {
        getStyleClass().add("alertPane");
        setPrefSize(INFO_PANE_SIZE, INFO_PANE_SIZE);

        systemNameLabel = new AlertPaneMessageLabel("System:");
        AnchorPane.setTopAnchor(systemNameLabel, 10.0);
        AnchorPane.setRightAnchor(systemNameLabel, 10.0);
        AnchorPane.setLeftAnchor(systemNameLabel, 10.0);

        numPlanetsLabel = new AlertPaneMessageLabel("Planets:");
        AnchorPane.setTopAnchor(numPlanetsLabel, 40.0);
        AnchorPane.setRightAnchor(numPlanetsLabel, 10.0);
        AnchorPane.setLeftAnchor(numPlanetsLabel, 10.0);

        travelButton = new StandardButton("Travel");
        AnchorPane.setBottomAnchor(travelButton, 10.0);
        AnchorPane.setRightAnchor(travelButton, 10.0);
        AnchorPane.setLeftAnchor(travelButton, 10.0);

        getChildren().addAll(travelButton, systemNameLabel, numPlanetsLabel);
    }
    
    /**
     * Sets up info pane for a given solar system.
     * @param solarSystem the solar system
     */
    public void setupForSolarSystem(SolarSystem solarSystem) {
        systemNameLabel.setText("System: " + solarSystem.getSystemName());
        numPlanetsLabel.setText("Planets: " + solarSystem.getPlanets().size());
        setInfoPaneCoordinates(solarSystem);
    }
    
    /**
     * Sets proper layout coordinates for a given solar system.
     * @param solarSystem the solar system
     */
    private void setInfoPaneCoordinates(SolarSystem solarSystem) {
        // Ensures entire pane stays in view region
        int x = solarSystem.getX() + 40;
        int y = solarSystem.getY() - (INFO_PANE_SIZE / 2);
        if (x > UIHelper.GALAXY_SIZE - INFO_PANE_SIZE) {
            x = (int) UIHelper.GALAXY_SIZE - INFO_PANE_SIZE;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > UIHelper.GALAXY_SIZE - INFO_PANE_SIZE) {
            y = UIHelper.GALAXY_SIZE - INFO_PANE_SIZE;
        }
        if (x < solarSystem.getX()) {
            x = solarSystem.getX() - (INFO_PANE_SIZE + 20);
        }
        setLayoutX(x);
        setLayoutY(y);
    }
    
    /**
     * Get the travel button.
     * @return the travel button
     */
    public Button getTravelButton() {
        return travelButton;
    }
}
