package hyenas.Models;

import java.util.Random;

/**
 * A Mercenary that can be hired and added to a ship.
 * @author Alex
 */
public class Mercenary {
    /**
     * The mercenary name.
     */
    private String name;
    /**
     * The mercenary pilot skill.
     */
    private int pilotSkill;
    /**
     * The mercenary fighter skill.
     */
    private int fighterSkill;
    /**
     * The mercenary trader skill.
     */
    private int traderSkill;
    /**
     * The mercenary engineer skill.
     */
    private int engineerSkill;
    /**
     * The mercenary investor skill.
     */
    private int investorSkill;
    /**
     * The mercenary price.
     */
    private int price;
    /**
     * The maximum total skill points.
     */
    private static final int SKILL_MAX = 30;
    
    /**
     * Constructor for mercenary, randomly assigns mercenary's skills and name
     * then generates cost accordingly.
     */
    public Mercenary()  {
        Random rand = new Random();
        final String[] names = new String[] {
            "Hoban Washburne", "Inara Serra", "Malcolm Reynolds", "Jayne Cobb",
            "Zoe Washburne", "Kaylee Frye", "Simon Tam", "River Tam",
            "Derrial Book"
        };
        name = names[rand.nextInt(names.length)];
        pilotSkill = rand.nextInt(SKILL_MAX);
        fighterSkill = rand.nextInt(SKILL_MAX);
        traderSkill = rand.nextInt(SKILL_MAX);
        engineerSkill = rand.nextInt(SKILL_MAX);
        investorSkill = rand.nextInt(SKILL_MAX);
        price = mercenaryCost();
    }
    
    /**
     * Constructor for mercenary, takes in generated mercenary's skills and name.
     * @param pname new mercenary's name
     * @param pilot new mercenary's pilot skill
     * @param fighter new mercenary's fighter skill
     * @param trader new mercenary's trader skill
     * @param engineer new mercenary's engineer skill
     * @param investor new mercenary's investor skill
     */
    public Mercenary(String pname, int pilot, int fighter, int trader, int engineer, int investor)    {
        this.name = pname;
        pilotSkill = pilot;
        fighterSkill = fighter;
        traderSkill = trader;
        engineerSkill = engineer;
        investorSkill = investor;
        price = mercenaryCost();
    }
    
    /**
     * Calculates the mercenary's cost.
     * @return cost to hire the mercenary
     */
    private int mercenaryCost()  {
        int totalSkill = pilotSkill + fighterSkill + traderSkill + engineerSkill
                + investorSkill;
        return totalSkill * 50;
    }
    
    /**
     * Get the mercenary's pilot skill.
     * @return the mercenary's pilot skill
     */
    public int getPilotSkill()  {
        return pilotSkill;
    }
    
    /**
     * Get the mercenary's fighter skill.
     * @return the mercenary's fighter skill
     */
    public int getFighterSkill()  {
        return fighterSkill;
    }
    
    /**
     * Get the mercenary's trader skill.
     * @return the mercenary's trader skill
     */
    public int getTraderSkill()  {
        return traderSkill;
    }
    
    /**
     * Get the mercenary's engineer skill.
     * @return the mercenary's engineer skill
     */
    public int getEngineerSkill()  {
        return engineerSkill;
    }
    
    /**
     * Get the mercenary's investor skill.
     * @return the mercenary's investor skill
     */
    public int getInvestorSkill()  {
        return investorSkill;
    }
    
    /**
     * Get the mercenary's price.
     * @return the mercenary's price
     */
    public int getPrice()   {
        return price;
    }
    
    /**
     * Get the mercenary's name.
     * @return the mercenary's name
     */
    public String getName() {
        return name;
    }
}
