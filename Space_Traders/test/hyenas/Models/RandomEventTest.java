package hyenas.Models;

import hyenas.Models.RandomEvent.RandomEventType;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for Random Event.
 * @author Alex
 */
public class RandomEventTest {

    /**
     * Test of performCancel method, of class RandomEvent.
     */
    @Test
    public void testPerformCancel() {
        System.out.println("Test Perform Cancel");
        
        Player player = Player.getInstance();
        int startingCredits = player.getCredits();
        Ship ship = player.getShip();
        double startingShipHealth = ship.getHealth();
        
        RandomEvent policeEvent = new RandomEvent(RandomEventType.POLICE);
        RandomEvent traderEvent = new RandomEvent(RandomEventType.TRADER);
        RandomEvent pirateEvent = new RandomEvent(RandomEventType.PIRATE);
        
        // Cancel result text should not be set until after event is performed
        assertEquals(policeEvent.getCancelResultText(), null);
        assertEquals(traderEvent.getCancelResultText(), null);
        assertEquals(pirateEvent.getCancelResultText(), null);
        
        boolean policeResult = policeEvent.performCancel();
        boolean traderResult = traderEvent.performCancel();
        boolean pirateResult = pirateEvent.performCancel();
        
        String policeResultText = policeEvent.getCancelResultText();
        String traderResultText = traderEvent.getCancelResultText();
        String pirateResultText = pirateEvent.getCancelResultText();
        
        // Police result has random probability of being successful
        if (policeResult) {
            String expectedResultText = "You successfully outmaneuver the police and continue to the planet.";
            assertEquals(expectedResultText, policeResultText);
        } else {
            String expectedResultText = "You fail to escape from the police, who fines you double. You return to where you came from having lost some fuel.";
            assertEquals(expectedResultText, policeResultText);
            // Starting credits should have been reduced by 100
            assertEquals(player.getCredits(), startingCredits - 100);
        }
        
        // Trader cancel result should always be true
        assertEquals(traderResult, true);
        // Trader cancel result text should still be null
        assertEquals(traderResultText, null);
        
        // Pirate result has random probability of being successful
        if (pirateResult) {
            String expectedResultText = "You successfully outmaneuver the pirates and continue to the planet.";
            assertEquals(expectedResultText, pirateResultText);
        } else {
            String expectedResultText = "You fail to outmaneuver the pirates and with your ship half damaged, you return to where you came from.";
            assertEquals(expectedResultText, pirateResultText);
            
            // Make sure ship's health was reduced (use delta of 2)
            assertEquals(ship.getHealth(), startingShipHealth * .5, 2);
        }
    }
    
}
