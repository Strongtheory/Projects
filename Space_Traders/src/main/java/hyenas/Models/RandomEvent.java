package hyenas.Models;

import java.util.Random;

/**
 * Represents a Random Event.
 * @author Alex
 */
public class RandomEvent {
    /**
     * The random event type.
     */
    private RandomEventType eventType;
    /**
     * The random event name.
     */
    private String name;
    /**
     * The random event description, which described what has happened.
     */
    private String description;
    /**
     * The random event question, which prompts user for an action.
     */
    private String question;
    /**
     * The random event action button text.
     */
    private String actionButtonText;
    /**
     * The random event cancel button text.
     */
    private String cancelButtonText;
    
    /**
     * The random event action result text.
     */
    private String actionResultText;
    /**
     * The random event cancel result text.
     */
    private String cancelResultText;
    
    /**
     * A RandomEventType, used to distinguish between random events.
     */
    public enum RandomEventType {
        /**
         * The police random event.
         */
        POLICE,
        /**
         * The trader random event.
         */
        TRADER,
        /**
         * The pirate random event.
         */
        PIRATE,
    }

    /**
     * Initialized RandomEvent based on the RandomEventType.
     * @param peventType the type of random event
     */
    public RandomEvent(RandomEventType peventType) {
        this.eventType = peventType;
        setUp();
    }
    
    /**
     * Sets up the default random event values based on the RandomEvent's
     * eventType.
     */
    private void setUp() {
        switch (eventType) {
            case POLICE:
                name = "Police";
                description = "You've been stopped by police!";
                question = "Pay fine or attempt to run away?";
                actionButtonText = "Pay Fine";
                cancelButtonText = "Run Away";
                break;
            case TRADER:
                name = "Trader";
                description = "You've encountered a trader!";
                question = "Trade or ignore?";
                actionButtonText = "Trade";
                cancelButtonText = "Ignore";
                break;
            case PIRATE:
                name = "Pirate";
                description = "You've been attacked by pirates!";
                question = "Fight or attempt to run away?";
                actionButtonText = "Fight!";
                cancelButtonText = "Run Away!";
                break;
            default:
                break;
        }
    }
    
    /**
     * Get the random event's name.
     * @return name the name of the random event
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the random event's description.
     * @return description the description of the random event
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Get the random event's question.
     * @return question the question of the random event
     */
    public String getQuestion() {
        return question;
    }
    
    /**
     * Get the random event's actionButtonText.
     * @return actionButtonText the actionButtonText of the random event
     */
    public String getActionButtonText() {
        return actionButtonText;
    }
    
    /**
     * Get the random event's cancel button text.
     * @return cancelButtonText the cancel button text of the random event
     */
    public String getCancelButtonText() {
        return cancelButtonText;
    }
    
    /**
     * Get the random event's action result text.
     * @return actionResultText the action result text of the random event
     */
    public String getActionResultText() {
        return actionResultText;
    }
    
    /**
     * Get the random event's cancel result text.
     * @return cancelResultText the cancel result text of the random event
     */
    public String getCancelResultText() {
        return cancelResultText;
    }
    
    /**
     * Performs the standard action for the given random event type. Informs
     * whether the player's action succeeded or failed.
     * 
     * @return true if the action was successful, false otherwise
     */
    public boolean performAction() {
        Player player = Player.getInstance();
        switch (eventType) {
            case POLICE:
                player.setCredits(player.getCredits() - 50);
                actionResultText = "You pay the fine and are deducted 50 credits.";
                return true;
            case TRADER:
                player.setCredits(player.getCredits() + 100);
                actionResultText = "You trade some unused parts and gain 100 credits.";
                return true;
            case PIRATE:
                Random rand = new Random();
                boolean success = rand.nextBoolean();
                Ship ship = player.getShip();
                if (success) {
                    actionResultText = "You successfully fend off the pirates. Your ship is slightly damaged, but you continue to your destination.";
                    double newhealth = ship.getHealth() * .9;
                    ship.setHealth(newhealth);
                } else {
                    actionResultText = "You unsuccessfully fend of the pirates and they steal some credits. Your ship has been severely damaged, so you head back to where you came from.";
                    double newhealth = ship.getHealth() * .5;
                    ship.setHealth(newhealth);
                    player.setCredits(player.getCredits() - 50);
                }
                return success;
        }
        
        return true;
    }
    
    /**
     * Performs the standard cancel for the given random event type. Informs
     * whether the player's action succeeded or failed.
     * 
     * @return true if the action was successful, false otherwise
     */
    public boolean performCancel() {
        Player player = Player.getInstance();
        switch (eventType) {
            case POLICE:
                Random rand = new Random();
                boolean success = rand.nextBoolean();
                if (success) {
                    cancelResultText = "You successfully outmaneuver the police and continue to the planet.";
                } else {
                    cancelResultText = "You fail to escape from the police, who fines you double. You return to where you came from having lost some fuel.";
                    player.setCredits(player.getCredits() - 100);
                }
                return success;
            case TRADER:
                return true;
            case PIRATE:
                Random random = new Random();
                boolean successful = random.nextBoolean();
                Ship ship = player.getShip();
                if (successful) {
                    cancelResultText = "You successfully outmaneuver the pirates and continue to the planet.";
                } else {
                    cancelResultText = "You fail to outmaneuver the pirates and with your ship half damaged, you return to where you came from.";
                    double newhealth = ship.getHealth() * .5;
                    ship.setHealth(newhealth);
                }
                
                return successful;
        }
        
        return true;
    }
}
