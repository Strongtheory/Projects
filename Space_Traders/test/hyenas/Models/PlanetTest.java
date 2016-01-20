package hyenas.Models;

import hyenas.Models.Ware.Good;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
/**
 * Test for Planet
 * 
 * @author JR Mailig
 */


public class PlanetTest {
    
    /** 
     * Tests to see if the correct AffectedGood is created based on the planet
     * type.
     */
    @Test
    public void testAffectedGoodForPlanetType() {
           
        Planet planets[] = new Planet[Planet.PlanetType.values().length];
        
        for (int i = 0; i < Planet.PlanetType.values().length; i++) {
            planets[i] = new Planet("Planet", true, 1, i);
        }
        
        assertNull(planets[0].affectedGoodForPlanetType());
        
        assertEquals(planets[1].affectedGoodForPlanetType().getGood(),
                     Good.ORE);
        assertEquals(planets[1].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[2].affectedGoodForPlanetType().getGood(),
                     Good.ORE);
        assertEquals(planets[2].affectedGoodForPlanetType().isIncreasedPrice(),
                     true);
        
        assertEquals(planets[3].affectedGoodForPlanetType().getGood(),
                     Good.WATER);
        assertEquals(planets[3].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[4].affectedGoodForPlanetType().getGood(),
                     Good.WATER);
        assertEquals(planets[4].affectedGoodForPlanetType().isIncreasedPrice(),
                     true);
        
        assertEquals(planets[5].affectedGoodForPlanetType().getGood(),
                     Good.FOOD);
        assertEquals(planets[5].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[6].affectedGoodForPlanetType().getGood(),
                     Good.FOOD);
        assertEquals(planets[6].affectedGoodForPlanetType().isIncreasedPrice(),
                     true);
        
        assertEquals(planets[7].affectedGoodForPlanetType().getGood(),
                     Good.FURS);
        assertEquals(planets[7].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[8].affectedGoodForPlanetType().getGood(),
                     Good.FURS);
        assertEquals(planets[8].affectedGoodForPlanetType().isIncreasedPrice(),
                     true);
        
        assertEquals(planets[9].affectedGoodForPlanetType().getGood(),
                     Good.NARCOTICS);
        assertEquals(planets[9].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[10].affectedGoodForPlanetType().getGood(),
                     Good.MEDICINE);
        assertEquals(planets[10].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[11].affectedGoodForPlanetType().getGood(),
                     Good.GAMES);
        assertEquals(planets[11].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
        
        assertEquals(planets[12].affectedGoodForPlanetType().getGood(),
                     Good.FIREARMS);
        assertEquals(planets[12].affectedGoodForPlanetType().isIncreasedPrice(),
                     false);
    }
}
