package hyenas.UI;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Use for displaying hover text over a UI element.
 * @author Alex
 */
public class HoverPane extends AnchorPane {
    /**
     * The hover pane's label.
     */
    private Label label;
    /**
     * The hover pane's margin padding.
     */
    private static final double PADDING = 3.0;
    /**
     * The hover pane's width.
     */
    private static final double WIDTH = 110.0;
    /**
     * The hover pane's height.
     */
    private static final double HEIGHT = 30.0;
    
    /**
     * Initializes a HoverPane.
     */
    public HoverPane() {
        getStyleClass().add("alertPane");
        setPrefSize(WIDTH, HEIGHT);
        
        label = new Label();
        label.getStyleClass().add("hoverPaneLabel");
        AnchorPane.setTopAnchor(label, PADDING);
        AnchorPane.setBottomAnchor(label, PADDING);
        AnchorPane.setRightAnchor(label, PADDING);
        AnchorPane.setLeftAnchor(label, PADDING);
        getChildren().add(label);
    }
    
    /**
     * Sets the text of the hover pane.
     * @param text the text to set
     */
    public void setLabelText(String text) {
        label.setText(text);
    }
}
