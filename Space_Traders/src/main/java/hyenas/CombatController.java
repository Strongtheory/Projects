package hyenas;

import hyenas.Models.Gadget;
import hyenas.Models.Player;
import hyenas.Models.Shield;
import hyenas.Models.Ship;
import hyenas.Models.Weapon;
import hyenas.UI.AlertPane;
import hyenas.UI.ShipInfoPane;
import hyenas.UI.ShipInfoPane.ShipInfoPaneType;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Controller for combat.
 * @author Brian Surber
 */


public class CombatController implements Initializable {

    /**
     * label displaying the player's current health.
     */
    private Label playerHealth;

    /**
     * label displaying the player's current shields
     */
    private Label playerShields;

    /**
     * label displaying the enemy's current health
     */
    private Label enemyHealth;

    /**
     * label displaying the enemy's current shields
     */
    private Label enemyShields;

    /**
     * button to click to pick a weapon and attack
     */
    private Button fight;

    /**
     * button to click to attempt to flee the fight
     */
    @FXML
    private Button flee;

    /**
     * button to click to use a gadget in the fight
     */
    private Button gadget;

    private Ship playerShip;

    private Ship enemyShip;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private VBox centerPane;
    
    @FXML
    private Label titleLabel;
    
    private ShipInfoPane playerInfoPane;
    private ShipInfoPane enemyInfoPane;

    private boolean stealthed;

    private int targeters;

    private int timesStealthed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font titleFont = Font.loadFont(HyenasLoader.class.getResource("/hyenas/fonts/BlenderPro-Book.otf").toExternalForm(), 40);
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: rgba(0,231,255, .9); -fx-effect: dropshadow( gaussian, rgba(0,0,0,1), 0,0,2,2);");
        
        Random rand = new Random();
        int randomInt = rand.nextInt(Ship.getDefaultShips().size()-1);
        //enemy ship type is completely random, but never fight a flea
        enemyShip = new Ship(Ship.getDefaultShips().
                get(randomInt+1).getShipType());
        for (int i = 0; i < enemyShip.getWeaponSlots(); i++)    {
            int randomWep = rand.nextInt(3);
            if(randomWep == 0)
                enemyShip.getWeapons().add(new Weapon(Weapon.WeaponType.PULSE));
            else if(randomWep == 1)
                enemyShip.getWeapons().add(new Weapon(Weapon.WeaponType.BEAM));
            else 
                enemyShip.getWeapons().add(new Weapon(Weapon.WeaponType.MISSILE));
        }
        for(int i=0; i < enemyShip.getShieldSlots(); i++)   {
            int randomShield = rand.nextInt(6);
            if(randomShield == 0)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.CIVILIAN_ENERGY));
            else if(randomShield == 1)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.CIVILIAN_REFLECTIVE));
            else if(randomShield == 2)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.MILITARY_ENERGY));
            else if(randomShield == 3)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.MILITARY_REFLECTIVE));
            else if(randomShield == 4)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.MILITIA_ENERGY));
            else if(randomShield == 5)
                enemyShip.getShields().add(new Shield(Shield.ShieldType.MILITIA_REFLECTIVE));
        }
        
        HBox hbox = new HBox();
        
        playerInfoPane = new ShipInfoPane(ShipInfoPaneType.COMBAT);
        Player player = Player.getInstance();
        playerInfoPane.updateInfo(player, player.getShip());
        fight = playerInfoPane.getBuyButton();
        gadget = playerInfoPane.getSellButton();
        fight.setText("Fight");
        gadget.setText("Gadget");
        fight.addEventHandler(ActionEvent.ACTION, event -> onFight(event));
        gadget.addEventHandler(ActionEvent.ACTION, event -> onGadget(event));
        fight.setDisable(false);
        gadget.setDisable(false);
                
        enemyInfoPane = new ShipInfoPane(ShipInfoPaneType.COMBAT);
        enemyInfoPane.updateInfo(null, enemyShip);
        
        playerHealth = playerInfoPane.getShipHealthLabel();
        playerShields = playerInfoPane.getShipShieldStrengthLabel();
        enemyHealth = enemyInfoPane.getShipHealthLabel();
        enemyShields = enemyInfoPane.getShipShieldStrengthLabel();
        
        VBox youBox = new VBox();
        Label youLabel = new Label("You:");
        BorderPane youPane = new BorderPane();
        youPane.setCenter(youLabel);
        youBox.getChildren().addAll(youPane, playerInfoPane);
        
        VBox enemyBox = new VBox();
        Label enemyLabel = new Label("Enemy:");
        BorderPane enemyPane = new BorderPane();
        enemyPane.setCenter(enemyLabel);
        enemyBox.getChildren().addAll(enemyPane, enemyInfoPane);
        
        Pane spacingPane = new Pane();
        spacingPane.setPrefWidth(100);
        hbox.getChildren().addAll(youBox, spacingPane, enemyBox);
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(300);
        borderPane.setCenter(hbox);
        Pane leftPane = new Pane();
        Pane rightPane = new Pane();
        leftPane.setPrefWidth(350);
        rightPane.setPrefWidth(350);
        playerInfoPane.setPrefHeight(350);
        enemyInfoPane.setPrefHeight(350);
        borderPane.setLeft(leftPane);
        borderPane.setRight(rightPane);
        centerPane.getChildren().add(borderPane);
        
        enemyInfoPane.getBuyButton().setText("Fight");
        enemyInfoPane.getSellButton().setText("Gadget");
        
        playerShip = Player.getInstance().getShip();
        stealthed = false;
        timesStealthed = 0;
        targeters = 0;
        for (Gadget g : playerShip.getGadgets())   {
            if(g.getType() == Gadget.GadgetType.TARGETING_SYSTEM)
                targeters++;
        }
    }

    public void onGadget(ActionEvent e) {
        boolean found = false;
        for (Gadget gadget : playerShip.getGadgets())   {
            if(gadget.getType() == Gadget.GadgetType.CLOAKING_DEVICE && timesStealthed < 3)   {
                found = true;
                break;
            }
        }
        if(found)  {
            AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.TWOBUTTONS,
                "Gadgets", "Pick a gadget.");
            alertPane.getActionButton().setText("Cloaking Device");
            EventHandler<ActionEvent> stealthAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
                stealthed = true;
            };
            EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
            };
            alertPane.getActionButton().setOnAction(stealthAction);
            alertPane.getCloseButton().setOnAction(closeAction);
            anchorPane.getChildren().add(alertPane);
        }
        else    {
            AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Invalid", "You don't have any useable gadgets.");
            EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
            };
            alertPane.getCloseButton().setOnAction(closeAction);
            anchorPane.getChildren().add(alertPane);
        }
    }

    /**
     * ActionListener for "flee" button in main combat screen
     * @param e Action Trigger
     */
    public void onFlee(ActionEvent e)   {
        int fleeChance = (enemyShip.getWeaponSlots()
                + enemyShip.getShieldSlots())*10
                + Player.getInstance().getPilotSkill()*5;
        Random rand = new Random();
        if(rand.nextInt(100) < fleeChance)  {
            AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Success", "You've successfully fled the " + enemyShip.getShipType() + ".");
            EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
                successfullyFled();
            };
            alertPane.getCloseButton().setOnAction(closeAction);
            anchorPane.getChildren().add(alertPane);
        }
        else    {
            AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Failure", "You've failed to flee the " + enemyShip.getShipType() + ".");
            EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
                anchorPane.getChildren().remove(alertPane);
                if(!stealthed)
                    attackPlayer();
            };
            alertPane.getCloseButton().setOnAction(closeAction);
            anchorPane.getChildren().add(alertPane);
        }
    }

    private void successfullyFled()  {
        HyenasLoader.getInstance().goToMapScreen();
    }

    /**
     * ActionListener for "fight" button in main combat screen
     * @param e Action Trigger
     */
    public void onFight(ActionEvent e)  {
        AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.FIGHTBUTTONS,
                "Attack", "How would you like to attack?");
        EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
            anchorPane.getChildren().remove(alertPane);
        };
        EventHandler<ActionEvent> pulseAction = (ActionEvent e2) -> {
            boolean damageDealt = pulseAttack();
            if(damageDealt) {
                anchorPane.getChildren().remove(alertPane);
                if(!stealthed && enemyShip.getHealth() > 0) {
                    attackPlayer();
                }
                else if(enemyShip.getHealth() <= 0) {
                    enemyHealth.setText("0/0");
                }
            }
            anchorPane.getChildren().remove(alertPane);
        };
        EventHandler<ActionEvent> beamAction = (ActionEvent e2) -> {
            boolean damageDealt = beamAttack();
            if(damageDealt) {
                anchorPane.getChildren().remove(alertPane);
                if(!stealthed && enemyShip.getHealth() > 0) {
                    attackPlayer();
                }
                else if(enemyShip.getHealth() <= 0) {
                    enemyHealth.setText("0/0");
                }
            }
            anchorPane.getChildren().remove(alertPane);
        };
        EventHandler<ActionEvent> missileAction = (ActionEvent e2) -> {
            boolean damageDealt = missileAttack();
            if(damageDealt) {
                anchorPane.getChildren().remove(alertPane);
                if(!stealthed && enemyShip.getHealth() > 0) {
                    attackPlayer();
                }
                if(enemyShip.getHealth() <= 0) {
                    enemyHealth.setText("0/0");
                }
            }
            anchorPane.getChildren().remove(alertPane);
        };
        
        Button pulseButton = alertPane.getPulseButton();
        Button beamButton = alertPane.getBeamButton();
        Button missileButton = alertPane.getMissileButton();
        Button closeButton = alertPane.getCloseButton();
        
        pulseButton.setOnAction(pulseAction);
        beamButton.setOnAction(beamAction);
        missileButton.setOnAction(missileAction);
        closeButton.setOnAction(closeAction);
        
        Ship ship = Player.getInstance().getShip();
        pulseButton.setDisable(!ship.hasWeaponType(Weapon.WeaponType.PULSE));
        beamButton.setDisable(!ship.hasWeaponType(Weapon.WeaponType.BEAM));
        missileButton.setDisable(!ship.hasWeaponType(Weapon.WeaponType.MISSILE));
        
        anchorPane.getChildren().add(alertPane);
    }
    
    /**
     * Enemy calculates potential damage of the 3 weapon groups and picks the
     * most effective. The player is then attacked by the selected weapon group.
     */
    private void attackPlayer()  {
        double damagePulse = 0;
        double damageBeam = 0;
        double damageMissile = 0;
        if(playerShip.getShieldStrength() > 0) {
            for (Weapon weapon : enemyShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.BEAM) {
                    damageBeam = damageBeam + weapon.getShieldDamage();
                }
                else if(weapon.getType() == Weapon.WeaponType.PULSE)    {
                    damagePulse = damagePulse + weapon.getShieldDamage();
                }
                else if(weapon.getType() == Weapon.WeaponType.MISSILE)  {
                    damageMissile = damageMissile + weapon.getShieldDamage();
                }
            }
        }
        else    {
            for (Weapon weapon : enemyShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.BEAM) {
                    damageBeam = damageBeam + weapon.getShipDamage();
                }
                else if(weapon.getType() == Weapon.WeaponType.PULSE)    {
                    damagePulse = damagePulse + weapon.getShipDamage();
                }
                else if(weapon.getType() == Weapon.WeaponType.MISSILE)  {
                    damageMissile = damageMissile + weapon.getShipDamage();
                }
            }
        }
        double damage;
        double dealtDamage;
        String weaponGroup;
        if(damagePulse >= damageBeam && damagePulse >= damageMissile) {
            damage = damagePulse;
            dealtDamage = damagePulse;
            weaponGroup = "Pulse weapons";
        }
        else if(damageBeam >= damagePulse && damageBeam >= damageMissile) {
            damage = damageBeam;
            dealtDamage = damageBeam;
            weaponGroup = "Beam weapons";
        }
        else    {
            damage = damageMissile;
            dealtDamage = damageMissile;
            weaponGroup = "Missile weapons";
        }
        if (playerShip.getShieldStrength() < damage)  {
            damage = damage - playerShip.getShieldStrength();
            playerShip.setShieldStrength(0);
        }
        else    {
            playerShip.setShieldStrength(playerShip.getShieldStrength() - damage);
            damage = 0;
        }
        playerShip.setHealth(playerShip.getHealth() - damage);
        playerHealth.setText(""+playerShip.getHealth());
        playerShields.setText(""+playerShip.getShieldStrength());
        AlertPane alertPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Damage Received", "You've taken " + dealtDamage
                + " from their " + weaponGroup + ".");
        EventHandler<ActionEvent> closeAction = (ActionEvent e2) -> {
            anchorPane.getChildren().remove(alertPane);
            if(playerShip.getHealth() <= 0) {
                playerHealth.setText("0/0");
                AlertPane losePane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                    "Defeat", "You've been destroyed by the " + enemyShip.getShipType() + ".");
                EventHandler<ActionEvent> loseAction = (ActionEvent e3) -> {
                    anchorPane.getChildren().remove(alertPane);
                    Player.getInstance().death();
                    HyenasLoader.getInstance().goToMapScreen();
                };
                losePane.getCloseButton().setOnAction(loseAction);
                anchorPane.getChildren().add(losePane);
            }
        };
        alertPane.getCloseButton().setOnAction(closeAction);
        anchorPane.getChildren().add(alertPane);
    }

    /**
     * calculate and deal damage from the user's missile weaponry
     * @return whether or not there were missile weapons on the player ship
     */
    private boolean missileAttack()  {
        double damage = 0;
        if (enemyShip.getShieldStrength() > 0)    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.MISSILE)  {
                    damage += weapon.getShieldDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        else    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.MISSILE)  {
                    damage += weapon.getShipDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        damage += (targeters + Player.getInstance().getFighterSkill()) * 10;
        if (enemyShip.getShieldStrength() < damage)  {
            damage = damage - enemyShip.getShieldStrength();
            enemyShip.setShieldStrength(0);
        }
        else    {
            enemyShip.setShieldStrength(enemyShip.getShieldStrength() - damage);
            damage = 0;
        }
        enemyShip.setHealth(enemyShip.getHealth() - damage);
        enemyHealth.setText(""+enemyShip.getHealth());
        enemyShields.setText(""+enemyShip.getShieldStrength());
        if(enemyShip.getHealth() <= 0) {
            AlertPane winPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Victory", "You've destroyed the " + enemyShip.getShipType()
                + " and get a bounty of "
                + (enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*10 + ".");
            EventHandler<ActionEvent> winAction = (ActionEvent e3) -> {
                anchorPane.getChildren().remove(winPane);
                Player.getInstance().setCredits(Player.getInstance().getCredits()
                        +(enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*10);
                HyenasLoader.getInstance().goToMapScreen();
            };
            winPane.getCloseButton().setOnAction(winAction);
            anchorPane.getChildren().add(winPane);
        }
        return true;
    }

    /**
     * calculate and deal damage from the user's beam weaponry
     * @return whether or not there were beam weapons on the player ship
     */
    private boolean beamAttack()   {
        double damage = 0;
        if (enemyShip.getShieldStrength() > 0)    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.BEAM)  {
                    damage += weapon.getShieldDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        else    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.BEAM)  {
                    damage += weapon.getShipDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        damage += (targeters + Player.getInstance().getFighterSkill()) * 10;
        if (enemyShip.getShieldStrength() < damage)  {
            damage = damage - enemyShip.getShieldStrength();
            enemyShip.setShieldStrength(0);
        }
        else    {
            enemyShip.setShieldStrength(enemyShip.getShieldStrength() - damage);
            damage = 0;
        }
        enemyShip.setHealth(enemyShip.getHealth() - damage);
        enemyHealth.setText(""+enemyShip.getHealth());
        enemyShields.setText(""+enemyShip.getShieldStrength());
        if(enemyShip.getHealth() <= 0) {
            AlertPane winPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Victory", "You've destroyed the " + enemyShip.getShipType()
                + " and get a bounty of "
                + (enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*10 + ".");
            EventHandler<ActionEvent> winAction = (ActionEvent e3) -> {
                anchorPane.getChildren().remove(winPane);
                Player.getInstance().setCredits(Player.getInstance().getCredits()
                        +(enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*10);
                HyenasLoader.getInstance().goToMapScreen();
            };
            winPane.getCloseButton().setOnAction(winAction);
            anchorPane.getChildren().add(winPane);
        }
        return true;
    }

    /**
     * calculate and deal damage from the user's pulse weaponry
     * calculation includes gadgets and fighter skill
     * @return whether or not there were pulse weapons on the player ship
     */
    private boolean pulseAttack()   {
        double damage = 0;
        if (enemyShip.getShieldStrength() > 0)    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.PULSE)  {
                    damage += weapon.getShieldDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        else    {
            for (Weapon weapon : playerShip.getWeapons())    {
                if (weapon.getType() == Weapon.WeaponType.PULSE)  {
                    damage += weapon.getShipDamage();
                }
            }
            if(damage == 0) {
                return false;
            }
        }
        damage += (targeters + Player.getInstance().getFighterSkill()) * 10;
        if (enemyShip.getShieldStrength() < damage)  {
            damage = damage - enemyShip.getShieldStrength();
            enemyShip.setShieldStrength(0);
        }
        else    {
            enemyShip.setShieldStrength(enemyShip.getShieldStrength() - damage);
            damage = 0;
        }
        enemyShip.setHealth(enemyShip.getHealth() - damage);
        enemyHealth.setText(""+enemyShip.getHealth());
        enemyShields.setText(""+enemyShip.getShieldStrength());
        if(enemyShip.getHealth() <= 0) {
            AlertPane winPane = new AlertPane(AlertPane.AlertPaneType.ONEBUTTON,
                "Victory", "You've destroyed the " + enemyShip.getShipType()
                + " and get a bounty of "
                + (enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*100 + ".");
            EventHandler<ActionEvent> winAction = (ActionEvent e3) -> {
                anchorPane.getChildren().remove(winPane);
                Player.getInstance().setCredits(Player.getInstance().getCredits()
                        +(enemyShip.getWeaponSlots()+enemyShip.getShieldSlots())*10);
                HyenasLoader.getInstance().goToMapScreen();
            };
            winPane.getCloseButton().setOnAction(winAction);
            anchorPane.getChildren().add(winPane);
        }
        return true;
    }
}
