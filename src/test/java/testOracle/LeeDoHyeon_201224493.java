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

public class LeeDoHyeon_201224493 {
	
	final String SHIP_DESIGN_NAME = "shipDesignNo1";
	final String SHIP_DESIGN_INFO = "DesignInfoNo1";

	final int SHIP_HULL_SLOT = 5; 
	final int SHIP_DESIGN_COST = 100; 
	final int SHIP_DESIGN_METALCOST = 50; 
	final int SHIP_DESIGN_TOTALSHIELD = 20; 
	final int SHIP_DESIGN_TOTALARMOR = 10; 
	final int SHIP_IMAGE_INDEX = 0; 
	final int SHIP_COMPONENT_INDEX = 0; 

	ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
	ShipHull shiphull = Mockito.mock(ShipHull.class);
	ShipComponent weapon = Mockito.mock(ShipComponent.class);
	ShipComponent engine = Mockito.mock(ShipComponent.class);
	ShipComponent energy = Mockito.mock(ShipComponent.class);
	ShipComponent armor = Mockito.mock(ShipComponent.class);
	ShipComponent shield = Mockito.mock(ShipComponent.class);
    ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield};
    
	@Before
	public void setUp() throws Exception {
		Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
	}

	/**
	* Purpose: Make sure the constructor works correctly about the inherited arguments in Construction.class
	* Input: ship = new Ship(final ShipDesign design) 
	* Expected: 
	* 			design.getName() == ship.getName()
	*			design.getProdCost() == ship.getCost()
	*			design.getMetalCost() == ship.getMetalCost()
	*/		
	@Test
	public void TestConstructorInheritedArgu(){
	    Ship ship = new Ship(shipdesign);
	    
	    assertEquals(SHIP_DESIGN_NAME, ship.getName());
	    assertEquals(SHIP_DESIGN_COST, ship.getProdCost());
	    assertEquals(SHIP_DESIGN_METALCOST, ship.getMetalCost());
   //   assertEquals(SHIP_DESIGN_INFO, ship.getDescription());
	}
	
	/**
	* Purpose: Make sure the constructor works correctly about the InnerClass named ShipHull in ShipDesign.class
	* Input: ship = new Ship(final ShipDesign design) 
	* Expected: 
	* 			design.getHull() == ship.getHull()
	*			design.getHull().getImage() == ship.getHull().getImage()
	*		    design.getHull().getSlotHull() == ship.getHullPointForComponent(0)
	*/
	@Test
	public void TestConstructorShipHullInDesignArgu(){
	    Ship ship = new Ship(shipdesign);
	    
	    assertEquals(shiphull, ship.getHull());
	    assertEquals(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX), ship.getHull().getImage());
	    assertEquals(SHIP_HULL_SLOT, ship.getHullPointForComponent(SHIP_COMPONENT_INDEX));
	}
	
	/**
	* Purpose: Make sure the constructor works correctly about the ShipDesign argument class
	* Input: ship = new Ship(final ShipDesign design) 
	* Expected: 
	* 			design.getTotalShield() == ship.getShield()
	*			design.getTotalArmor() == ship.getArmor()
	*/
	@Test
	public void TestConstructorDesignArgu(){
	    Ship ship = new Ship(shipdesign);

	    assertEquals(SHIP_DESIGN_TOTALSHIELD, ship.getShield());
	    assertEquals(SHIP_DESIGN_TOTALARMOR, ship.getArmor());
	}
	
	/**
	* Purpose: Make sure the constructor works correctly about variables to add in Constructor
	* Input: ship = new Ship(final ShipDesign design) 
	* Expected: 
	* 			ship.getColonist() == 0
	*			ship.getMetal() == 0
	*			ship.getExperience() == 0
	*   		ship.getCulture() == 0
	*/
	@Test
	public void TestConstructorVariables(){
	    Ship ship = new Ship(shipdesign);
	    assertEquals(0, ship.getColonist());
	    assertEquals(0, ship.getMetal());
	    assertEquals(0, ship.getExperience());
	    assertEquals(0, ship.getCulture());	
	}
	
	/**
	* Purpose: Confirm invalidIndex in getHullPointForComponent Method
	* Input: index < 0, index >= hullPoints.length
	* hullPoints.length = 5
	* Expected: 
	* 			return = 0
	*/
	@Test
	public void TestGetHullPointForComponentWithInvalidIndex(){
		Ship ship = new Ship(shipdesign);
		
		assertEquals(0, ship.getHullPointForComponent(-2));
		assertEquals(0, ship.getHullPointForComponent(-1));
		assertEquals(0, ship.getHullPointForComponent(5));
		assertEquals(0, ship.getHullPointForComponent(6));
	}
	
	/**
	* Purpose: Confirm validIndex in getHullPointForComponent Method
	* Input: index = 0, 2, 4
	* hullPoints.length = 5
	* Expected: 
	* 			return = hullPoints[index]
	* 			hullPoints[index] = 5
	*/
	@Test
	public void TestGetHullPointForComponentWithValidIndex(){
		Ship ship = new Ship(shipdesign);
		
		assertEquals(5, ship.getHullPointForComponent(0));
		assertEquals(5, ship.getHullPointForComponent(2));
		assertEquals(5, ship.getHullPointForComponent(4));
	}
}
