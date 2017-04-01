package testOracle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipImages;

public class ShipTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestConstructorWithDesignParam(){
		final String SHIP_DESIGN_NAME = "shipDesignNo1";
		final String SHIP_DESIGN_INFO = "DesignInfoNo1";

		final int SHIP_HULL_SLOT = 5; 
		final int SHIP_DESIGN_COST = 100; 
		final int SHIP_DESIGN_METALCOST = 50; 
		final int SHIP_DESIGN_TOTALSHIELD = 20; 
		final int SHIP_DESIGN_TOTALARMOR = 10; 
		
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
		Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		ShipHull shiphull = Mockito.mock(ShipHull.class);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		ShipComponent weapon = Mockito.mock(ShipComponent.class);
		ShipComponent engine = Mockito.mock(ShipComponent.class);
		ShipComponent energy = Mockito.mock(ShipComponent.class);
		ShipComponent armor = Mockito.mock(ShipComponent.class);
		ShipComponent shield = Mockito.mock(ShipComponent.class);
	    ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(0));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
	    
	    Ship ship = new Ship(shipdesign);
	    
	    assertEquals(SHIP_DESIGN_NAME, ship.getName());
	    assertEquals(SHIP_DESIGN_COST, ship.getProdCost());
	    assertEquals(SHIP_DESIGN_METALCOST, ship.getMetalCost());
	    assertEquals(shiphull, ship.getHull());
	    assertEquals(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(0), ship.getHull().getImage());
	    assertEquals(SHIP_HULL_SLOT, ship.getHullPointForComponent(0));
	    assertEquals(SHIP_DESIGN_TOTALSHIELD, ship.getShield());
	    assertEquals(SHIP_DESIGN_TOTALARMOR, ship.getArmor());
	
	    assertEquals(0, ship.getColonist());
	    assertEquals(0, ship.getMetal());
	    assertEquals(0, ship.getExperience());
	    assertEquals(0, ship.getCulture());

	   // assertEquals(SHIP_DESIGN_INFO, ship.getDescription());
	}
	
//	@Test
//	public void TestGetDescription(){
//		final String SHIP_NAME = "ShipNo1";
//		Ship ship = Mockito.mock(Ship.class, Mockito.RETURNS_DEEP_STUBS);
//		ShipHull shiphull = Mockito.mock(ShipHull.class,Mockito.RETURNS_DEEP_STUBS);
//		Mockito.when(ship.getName()).thenReturn(SHIP_NAME);
//		Mockito.when(shiphull.getHullType().toString()).thenReturn("FREIGHTER");
//
//	}
}
