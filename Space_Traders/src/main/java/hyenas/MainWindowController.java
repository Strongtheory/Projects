package hyenas;

import hyenas.UI.UIHelper;
import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Main window controller. This class handles the display and contains the logic
 * for the title screen.
 *
 * @author saikrishna
 */
public class MainWindowController implements Initializable {
    /**
     * The main window controller title label.
     */
    @FXML
    private Label titleLabel;
    /**
     * The main window controller new game button label.
     */
    @FXML
    private Button newGameButton;
    /**
     * The main window controller continue button label.
     */
    @FXML
    private Button continueButton;
    /**
     * The main window controller close button label.
     */
    @FXML
    private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font titleFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/GALACTICVANGUARDIANNCV.ttf").toExternalForm(), 70);
        Font buttonFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/BlenderPro-Book.otf").toExternalForm(), 40);

        titleLabel.setFont(titleFont);
        titleLabel.setCache(true);

        Dimension screenSize = UIHelper.getScreenSize();
        double buttonPadding = screenSize.getWidth() / 2 - 200;
        double titleLabelPadding = screenSize.getWidth() / 2 - 400;

        AnchorPane.setTopAnchor(titleLabel, 150.0);
        AnchorPane.setLeftAnchor(titleLabel, titleLabelPadding);
        AnchorPane.setRightAnchor(titleLabel, titleLabelPadding);

        newGameButton.setFont(buttonFont);
        AnchorPane.setTopAnchor(newGameButton, 250.0);
        AnchorPane.setLeftAnchor(newGameButton, buttonPadding);
        AnchorPane.setRightAnchor(newGameButton, buttonPadding);

        continueButton.setFont(buttonFont);
        AnchorPane.setTopAnchor(continueButton, 350.0);
        AnchorPane.setLeftAnchor(continueButton, buttonPadding);
        AnchorPane.setRightAnchor(continueButton, buttonPadding);

        closeButton.setFont(buttonFont);
        AnchorPane.setTopAnchor(closeButton, 450.0);
        AnchorPane.setLeftAnchor(closeButton, buttonPadding);
        AnchorPane.setRightAnchor(closeButton, buttonPadding);
    }

    /**
     * Display the new game screen.
     * @param e unused
     */
    public void onNewGameClicked(ActionEvent e)  {
        HyenasLoader.getInstance().goToAllocationScreen();
    }

    /**
     * Load a previous game.
     * @param e unused
     */
    public void continueGame(ActionEvent e) {
        HyenasLoader.getInstance().continueGame();
    }

    /**
     * Display the settings screen.
     * @param e unused
     */
    public void onSettingsClicked(ActionEvent e) {
        HyenasLoader.getInstance().goToSettingsScreen();
    }

    /**
     * Exit the game.
     * @param e unused
     */
    public void closeGame(ActionEvent e) {
        System.exit(0);
    }

}
