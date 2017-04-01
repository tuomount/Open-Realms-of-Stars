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
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
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
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
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
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
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
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
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
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
		Ship ship = new Ship(shipdesign);
	    assertEquals(0, ship.getColonist());
	    assertEquals(0, ship.getMetal());
	    assertEquals(0, ship.getExperience());
	    assertEquals(0, ship.getCulture());	
	}
	
	/**
	* Purpose: Confirm invalidIndex in getHullPointForComponent Method
	* Input: index < 0, index >= hullPoints.length
	* Expected: 
	* 			return = 0
	*/
	@Test
	public void TestGetHullPointForComponentWithInvalidIndex(){
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
		Ship ship = new Ship(shipdesign);
		
		assertEquals(0, ship.getHullPointForComponent(-2));
		assertEquals(0, ship.getHullPointForComponent(-1));
		assertEquals(0, ship.getHullPointForComponent(6));
		assertEquals(0, ship.getHullPointForComponent(7));
	}
	
	/**
	* Purpose: Confirm RegenerateShield
	* Input: shield = 0  
	*        shield generator Defense Value = 1
	*        getTotalShield() = 9
	* Expected: 
	* 			if(shield < getTotalShield()) shield = shield + 1
	* 			if(shield < getTotalShield()) shield = shield + 1(Shield generator Mk1)
	* 			else shield = getTotalShield()
	*/
	@Test
	public void TestRegenerateShield(){
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor,shield, shieldgenerator};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
		Ship ship = new Ship(shipdesign);

		ship.setShield(0);
		assertEquals(0,ship.getShield());
		ship.regenerateShield();
		assertEquals(2,ship.getShield());
		ship.regenerateShield();
		assertEquals(4,ship.getShield());
		ship.regenerateShield();
		assertEquals(6,ship.getShield());
		ship.regenerateShield();
		assertEquals(8,ship.getShield());
		ship.regenerateShield();
		assertEquals(9,ship.getShield());
		ship.regenerateShield();
		assertEquals(9,ship.getShield());

	}

	
	/**
	* Purpose: Confirm RegenerateShield Without ShieldComponent()
	* Input: shield = 0
	* 
	* Expected: 
	* 		shield = 0
	*/
	@Test
	public void TestRegenerateShieldWithoutShieldComponent(){
		ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
    	ShipHull shiphull = Mockito.mock(ShipHull.class);

    	ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");
    	
    	Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
		Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
		Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
		Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
		Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
		Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
		Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
		Mockito.when(shipdesign.getHull().getImage()).thenReturn(ShipImages.getByRace(SpaceRace.HUMAN).getShipImage(SHIP_IMAGE_INDEX));
		Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
			
        ShipComponent[] shipcomponents = new ShipComponent[]{weapon,engine,energy,armor};
		Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
		
		Ship ship = new Ship(shipdesign);
		ship.regenerateShield();
		assertEquals(0,ship.getShield());	
	}
}
