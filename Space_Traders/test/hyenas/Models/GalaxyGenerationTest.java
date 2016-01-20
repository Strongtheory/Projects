package hyenas.Models;

import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saikrishna
 */


public class GalaxyGenerationTest {
    
    private Galaxy galaxy;
    
    @Before
    public void setUp() {
        galaxy = Galaxy.getInstance();
    }
    
    @Test
    public void testSolarSystemGenerated() {
        Map<String, SolarSystem> solarSystems = galaxy.getSolarSystems();
        if (solarSystems.size() < 100) {
            fail("Not enough solar systems generated");
        }
        solarSystems.entrySet().parallelStream().forEach((solarSystem) -> {
            assertEquals(solarSystem.getKey(), solarSystem.getValue().getSystemName());
        });
    }
    
    @Test
    public void testPlanetsGenerated() {
        Map<String, SolarSystem> solarSystems = galaxy.getSolarSystems();
        for (SolarSystem solarSystem : solarSystems.values()) {
            if (solarSystem.getPlanets().size() < 1 || solarSystem.getPlanets().size() > 5) {
                fail("Incorrect number of planets generated");
            }
            for (Planet planet : solarSystem.getPlanets()) {
                assertFalse(planet.getPlanetName().equals(solarSystem.getSystemName()));
            }
        }
    }
    
    @Test
    public void testPlanetsDifferentNames() {
        Map<String, SolarSystem> solarSystems = galaxy.getSolarSystems();
        for (SolarSystem solarSystem : solarSystems.values()) {
            for (int i = 0; i < solarSystem.getPlanets().size(); i++) {
                Planet planet1 = solarSystem.getPlanets().get(i);
                for (int j = i + 1; j < solarSystem.getPlanets().size(); j++) {
                    Planet planet2 = solarSystem.getPlanets().get(j);
                    assertFalse(planet1.getPlanetName().equals(planet2.getPlanetName()));
                }
            }
        }
    }
    
}
