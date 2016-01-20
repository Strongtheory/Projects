package hyenas.Models;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Completed tests: testGetShipType(), testSetShipType(),
 *     testShipAgainstDefaults(), testGetName(), testGetWeaponSlots(),
 *     testAddCargo(), testRemoveCargo()
 * @author Brian Surber
 */
public class ShipTest {
    
    public ShipTest() {
    }

    /**
     * Test of getShipType method, of class Ship.
     */
    @Test
    public void testGetShipType() {
        Ship ship = new Ship(Ship.ShipType.BUMBLEBEE);
        assertEquals(ship.getShipType(), Ship.ShipType.BUMBLEBEE);
        ship = new Ship(Ship.ShipType.FIREFLY);
        assertEquals(ship.getShipType(), Ship.ShipType.FIREFLY);
        ship = new Ship(Ship.ShipType.FLEA);
        assertEquals(ship.getShipType(), Ship.ShipType.FLEA);
        ship = new Ship(Ship.ShipType.GNAT);
        assertEquals(ship.getShipType(), Ship.ShipType.GNAT);
        ship = new Ship(Ship.ShipType.MOSQUITO);
        assertEquals(ship.getShipType(), Ship.ShipType.MOSQUITO);
    }

    /**
     * assertEquals ship's variables to those of a default ship of the same type
     * @param type
     * @param ship 
     */
    private void testShipAgainstDefaults(Ship.ShipType type, Ship ship)   {
        Ship def = new Ship(type);
        assertEquals(def.getName(), ship.getName());
        assertEquals(def.getMaxFuel(), ship.getMaxFuel(), 0);
        assertEquals(def.getMaxHealth(), ship.getMaxHealth(), 0);
        assertEquals(def.getMinTechLevel(), ship.getMinTechLevel());
        assertEquals(def.getPrice(), ship.getPrice());
        assertEquals(def.getWeaponSlots(), ship.getWeaponSlots());
        assertEquals(def.getShieldSlots(), ship.getShieldSlots());
        assertEquals(def.getGadgetSlots(), ship.getGadgetSlots());
        assertEquals(def.getCrewSlots(), ship.getCrewSlots());
        assertEquals(def.getCargoSlots(), ship.getCargoSlots());
    }

    /**
     * Test of getName method, of class Ship.
     */
    @Test
    public void testGetName() {
        Ship ship = new Ship(Ship.ShipType.BUMBLEBEE);
        assertEquals(ship.getName(), "Bumblebee");
        ship = new Ship(Ship.ShipType.FIREFLY);
        assertEquals(ship.getName(), "Firefly");
        ship = new Ship(Ship.ShipType.FLEA);
        assertEquals(ship.getName(), "Flea");
        ship = new Ship(Ship.ShipType.GNAT);
        assertEquals(ship.getName(), "Gnat");
        ship = new Ship(Ship.ShipType.MOSQUITO);
        assertEquals(ship.getName(), "Mosquito");
    }

    /**
     * Test of getWeaponSlots method, of class Ship.
     */
    @Test
    public void testGetWeaponSlots() {
        Ship ship = new Ship(Ship.ShipType.BUMBLEBEE);
        assertEquals(ship.getWeaponSlots(), 3);
        ship = new Ship(Ship.ShipType.FIREFLY);
        assertEquals(ship.getWeaponSlots(), 1);
        ship = new Ship(Ship.ShipType.FLEA);
        assertEquals(ship.getWeaponSlots(), 0);
        ship = new Ship(Ship.ShipType.GNAT);
        assertEquals(ship.getWeaponSlots(), 1);
        ship = new Ship(Ship.ShipType.MOSQUITO);
        assertEquals(ship.getWeaponSlots(), 2);
    }

    /**
     * Test of getShieldSlots method, of class Ship.
     */
    @Test
    public void testGetShieldSlots() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getGadgetSlots method, of class Ship.
     */
    @Test
    public void testGetGadgetSlots() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getCrewSlots method, of class Ship.
     */
    @Test
    public void testGetCrewSlots() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getCargoSlots method, of class Ship.
     */
    @Test
    public void testGetCargoSlots() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getFreeCargo method, of class Ship.
     */
    @Test
    public void testGetFreeCargo() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getCargo method, of class Ship.
     */
    @Test
    public void testGetCargo() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getWeapons method, of class Ship.
     */
    @Test
    public void testGetWeapons() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getShields method, of class Ship.
     */
    @Test
    public void testGetShields() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getGadgets method, of class Ship.
     */
    @Test
    public void testGetGadgets() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getCrew method, of class Ship.
     */
    @Test
    public void testGetCrew() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getWares method, of class Ship.
     */
    @Test
    public void testGetWares() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Compares 2 lists of Wares by comparing the Good variable of the contents
     * @param list1, 1st list to compare
     * @param list2, 2nd list to compare
     * @return boolean, if the 2 lists have the same types of wares
     */
    private boolean compareWareLists(List<Ware> list1, List<Ware> list2)    {
        if (list1.size() != list2.size())    {
            return false;
        }
        for (int i = 0; i < list1.size(); i++)  {
            if (list1.get(i).getGood() != list2.get(i).getGood())    {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Test of addCargo method, of class Ship.
     */
    @Test
    public void testAddCargo() {
        List<Ware> expectedCargo = new ArrayList<>();
        Ship ship = new Ship(Ship.ShipType.FLEA);
        //Empty list
        assertTrue(ship.addCargo(new Ware(Ware.Good.WATER)));
        expectedCargo.add(new Ware(Ware.Good.WATER));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water
        assertTrue(ship.addCargo(new Ware(Ware.Good.WATER)));
        expectedCargo.add(new Ware(Ware.Good.WATER));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water
        assertTrue(ship.addCargo(new Ware(Ware.Good.WATER)));
        expectedCargo.add(new Ware(Ware.Good.WATER));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water, Water
        assertTrue(ship.addCargo(new Ware(Ware.Good.WATER)));
        expectedCargo.add(new Ware(Ware.Good.WATER));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water, Water, Water
        assertTrue(ship.addCargo(new Ware(Ware.Good.WATER)));
        expectedCargo.add(new Ware(Ware.Good.WATER));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water, Water, Water, Water
        assertFalse(ship.addCargo(new Ware(Ware.Good.WATER)));
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water, Water, Water, Water
    }

    /**
     * Test of removeCargo method, of class Ship.
     */
    @Test
    public void testRemoveCargo() {
        List<Ware> expectedCargo = new ArrayList<>();
        Ship ship = new Ship(Ship.ShipType.FLEA);
        //fill ship cargo with water
        for (int i = 0; i < ship.getCargoSlots(); i++)   {
            expectedCargo.add(new Ware(Ware.Good.WATER));
            ship.addCargo(new Ware(Ware.Good.WATER));
        }
        assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        //Water, Water, Water, Water, Water
        //remove each Water and assert after each
        for (int i = 0; i < ship.getCargoSlots(); i++)   {
            expectedCargo.remove(0);
            ship.removeCargo(new Ware(Ware.Good.WATER));
            assertTrue(compareWareLists(ship.getCargo(), expectedCargo));
        }
        assertTrue(ship.getCargo().isEmpty());
    }

    /**
     * Test of getFuel method, of class Ship.
     */
    @Test
    public void testGetFuel() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of setFuel method, of class Ship.
     */
    @Test
    public void testSetFuel() {
        //TODO
    }

    /**
     * Test of getMaxFuel method, of class Ship.
     */
    @Test
    public void testGetMaxFuel() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getHealth method, of class Ship.
     */
    @Test
    public void testGetHealth() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of setHealth method, of class Ship.
     */
    @Test
    public void testSetHealth() {
        //TODO
    }

    /**
     * Test of getMaxHealth method, of class Ship.
     */
    @Test
    public void testGetMaxHealth() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getPrice method, of class Ship.
     */
    @Test
    public void testGetPrice() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getMinTechLevel method, of class Ship.
     */
    @Test
    public void testGetMinTechLevel() {
        //TODO as with testGetWeaponSlots()
    }

    /**
     * Test of getDefaultShips method, of class Ship.
     */
    @Test
    public void testGetDefaultShips() {
        //TODO
    }
}
