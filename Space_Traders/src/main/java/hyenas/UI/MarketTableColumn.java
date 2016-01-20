package hyenas.UI;

import javafx.scene.control.TableColumn;

/**
 * Table column for marketplace.
 * @author Alex
 */
public class MarketTableColumn extends TableColumn {
    /**
     * Initializer for Market Table Column.
     * @param text the column text
     */
    public MarketTableColumn(String text) {
        super(text);
        setResizable(false);
        setPrefWidth(100);
        setSortable(false);
    }
}
