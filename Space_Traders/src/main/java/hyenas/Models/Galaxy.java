package hyenas.Models;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A Galaxy that contains solar systems.
 * @author Alex
 */
public class Galaxy {
    /**
     * The galaxy solar systems.
     */
    private Map<String, SolarSystem> solarSystems;
    /**
     * The galaxy distances between solar systems.
     */
    private Map<SolarSystem, List<ABPair<SolarSystem, Double>>> distances;
    /**
     * Whether the galaxy location is set.
     */
    private boolean locationSet;
    /**
     * The common galaxy instance. For use with singleton.
     */
    private static volatile Galaxy instance;
 
    /**
     * The companies in the galaxy.
     */
    //private Map<String, Company> companies;
    private List<Company> companies;
    
    /**
     * Initializes an instance of Galaxy, sets initial values.
     */
    private Galaxy() {
        distances = new HashMap<>();
        locationSet = false;
        setupSolarSystems();
        setupCompanies();
    }

    /**
     * Getter for Galaxy singleton.
     * @return Galaxy the common galaxy instance
     */
    public static Galaxy getInstance() {
        if (instance == null) {
            instance = new Galaxy();
        }
        return instance;
    }

    /**
     * Sets up the galaxy's solar systems.
     */
    private void setupSolarSystems() {
        final String[] systemNames = new String[] {
            "Earth616",
            "Destiny",
            "Acamar",
            "Adahn", // The alternate personality for The Nameless One in "Planescape: Torment"
            "Aldea",
            "Andevian",
            "Antedi",
            "Balosnee",
            "Baratas",
            "Brax", // One of the heroes in Master of Magic
            "Bretel", // This is a Dutch device for keeping your pants up.
            "Calondia",
            "Campor",
            "Capelle", // The city I lived in while programming this game
            "Carzon",
            "Castor", // A Greek demi-god
            "Cestus",
            "Cheron",
            "Courteney", // After Courteney Cox…
            "Daled",
            "Damast",
            "Davlos",
            "Deneb",
            "Deneva",
            "Devidia",
            "Draylon",
            "Drema",
            "Endor",
            "Esmee", // One of the witches in Pratchett's Discworld
            "Exo",
            "Ferris", // Iron
            "Festen", // A great Scandinavian movie
            "Fourmi", // An ant, in French
            "Frolix", // A solar system in one of Philip K. Dick's novels
            "Gemulon",
            "Guinifer", // One way of writing the name of king Arthur's wife
            "Hades", // The underworld
            "Hamlet", // From Shakespeare
            "Helena", // Of Troy
            "Hulst", // A Dutch plant
            "Iodine", // An element
            "Iralius",
            "Janus", // A seldom encountered Dutch boy's name
            "Japori",
            "Jarada",
            "Jason", // A Greek hero
            "Kaylon",
            "Khefka",
            "Kira", // My dog's name
            "Klaatu", // From a classic SF movie
            "Klaestron",
            "Korma", // An Indian sauce
            "Kravat", // Interesting spelling of the French word for "tie"
            "Krios",
            "Laertes", // A king in a Greek tragedy
            "Largo",
            "Lave", // The starting system in Elite
            "Ligon",
            "Lowry", // The name of the "hero" in Terry Gilliam's "Brazil"
            "Magrat", // The second of the witches in Pratchett's Discworld
            "Malcoria",
            "Melina",
            "Mentar", // The Psilon home system in Master of Orion
            "Merik",
            "Mintaka",
            "Montor", // A city in Ultima III and Ultima VII part 2
            "Mordan",
            "Myrthe", // The name of my daughter
            "Nelvana",
            "Nix", // An interesting spelling of a word meaning "nothing" in Dutch
            "Nyle", // An interesting spelling of the great river
            "Odet",
            "Og", // The last of the witches in Pratchett's Discworld
            "Omega", // The end of it all
            "Omphalos", // Greek for navel
            "Orias",
            "Othello", // From Shakespeare
            "Parade", // This word means the same in Dutch and in English
            "Penthara",
            "Picard", // The enigmatic captain from ST:TNG
            "Pollux", // Brother of Castor
            "Quator",
            "Rakhar",
            "Ran", // A film by Akira Kurosawa
            "Regulas",
            "Relva",
            "Rhymus",
            "Rochani",
            "Rubicum", // The river Ceasar crossed to get into Rome
            "Rutia",
            "Sarpeidon",
            "Sefalla",
            "Seltrice",
            "Sigma",
            "Sol", // That's our own solar system
            "Somari",
            "Stakoron",
            "Styris",
            "Talani",
            "Tamus",
            "Tantalos", // A king from a Greek tragedy
            "Tanuga",
            "Tarchannen",
            "Terosa",
            "Thera", // A seldom encountered Dutch girl's name
            "Titan", // The largest moon of Jupiter
            "Torin", // A hero from Master of Magic
            "Triacus",
            "Turkana",
            "Tyrus",
            "Umberlee", // A god from AD&D, which has a prominent role in Baldur's Gate
            "Utopia", // The ultimate goal
            "Vadera",
            "Vagra",
            "Vandor",
            "Ventax",
            "Xenon",
            "Xerxes", // A Greek hero
            "Yew", // A city which is in almost all of the Ultima games
            "Yojimbo", // A film by Akira Kurosawa
            "Zalkon",
            "Zuul"
        };

        solarSystems = new HashMap<>();

        for (String systemName : systemNames) {
            SolarSystem solarSystem = new SolarSystem(systemName);
            solarSystems.put(systemName, solarSystem);
        }
    }    
    
    /**
     * Get the solar systems in the galaxy.
     * @return Array of solar systems in the galaxy
     */
    public Map<String, SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    /**
     * Get the solar system for a system name.
     * @param solarSystemName the name of the solar ssytem
     * @return the system corresponding to the system name
     */
    public SolarSystem getSolarSystemForName(String solarSystemName) {
        return solarSystems.get(solarSystemName);
    }

    /**
     * Get the adjacency list of the solar systems.
     * @return adjacency list of the solar systems
     */
    public Map<SolarSystem, List<ABPair<SolarSystem, Double>>> getDistances() {
        return distances;
    }

    /**
     * Get whether the galaxy's location is set.
     * @return whether the galaxy's location is set
     */
    public boolean isLocationsSet() {
        return locationSet;
    }

    /**
     * Set whether the galaxy's location is set.
     * @param plocationSet whether the galaxy's location is set
     */
    public void setLocationsSet(boolean plocationSet) {
        this.locationSet = plocationSet;
    }
    
    /**
     * Sets up the galaxy's companies.
     */
    private void setupCompanies() {
        final String[] companyNames = new String[] {
            "Supergreen",
            "Sailgreen",
            "Labzone",
            "Stimholding",
            "Icegreen",
            "Jobholding",
            "Zotholding",
            "retechi",
            "Newfan",
            "Vaiacorporation",
            "Phystechno",
            "Kincone",
            "Viazone",
            "Lanesailice",
            "Damway",
            "Treeapjob",
            "treszone",
            "Joyin",
            "Subtouch",
            "Highphase",
            "Newtech"
        };

        companies = new ArrayList<>();

        for (String companyName : companyNames) {
            Company company = new Company(companyName);
            companies.add(company);
        }
    }

     /**
     * Gets the galaxy's companies.
     * @return a list containing the companies
     */
    public List<Company> getCompanies() {
        return this.companies;
    }
}
