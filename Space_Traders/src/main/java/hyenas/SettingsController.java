package hyenas;

import hyenas.Models.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class for Settings.
 *
 * @author saikrishna
 */
public class SettingsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Cancels settings changes, returns to previous screen.
     * @param e unused
     */
    public void onCancelClicked(ActionEvent e) {
        if (!Player.getInstance().getState()) {
            HyenasLoader.getInstance().goToHomeScreen();
        } else {
            // TODO: Go back to previous instance of state.
            HyenasLoader.getInstance().goToMapScreen();
        }
    }
    
    /**
     * Saves settings changes, returns to previous screen.
     * @param e unused
     */
    public void onSaveClicked(ActionEvent e) {
        if (!Player.getInstance().getState()) {
            HyenasLoader.getInstance().goToHomeScreen();
        } else {
            // TODO: Go back to previous instance of state.
            // TODO: Save any changes made to the settings.
            HyenasLoader.getInstance().goToMapScreen();
        }
    }

}
