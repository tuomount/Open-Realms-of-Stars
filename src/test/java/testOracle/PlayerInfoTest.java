package testOracle;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.mockito.Mockito;

public class PlayerInfoTest {
	
	private static final byte UNCHARTED = 0;
	  /**
	   * Fog of war, no fleets are drawn
	   */
	private static final byte FOG_OF_WAR = 1;
	  /**
	   * Every thing are drawn
	   */
	private static final byte VISIBLE = 2;
	/*
	 * input : SpaceRace.HUMAN
	 * output : PlayerInfo's techList ={
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random shield or armor in Defense Type level 1,
	 * 			Hulltech level 1 Colony,
	 * 			Hulltech level 1 Scout Mk1,
	 * 			PropulsionTech level 1 Ion drive Mk1,
	 * 			PropulsionTech level 1 Fission source Mk1
	 * 			},
	 * 			PlayerInfo's ShipStatList = {
	 * 			scout have random weapon, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
	 * 			colony have Colony Hull, Ion drive Mk1, Fission source Mk1
	 * 			}  
	 */
	@Test
	public void testPlayerInfoHuman() {
		PlayerInfo human = new PlayerInfo(SpaceRace.HUMAN);
		TechList techList = human.getTechList();
		Tech[] tech = techList.getList();
		ShipStat[] statList = human.getShipStatList();
		
		assertEquals(TechType.Combat,tech[0].getType());
		assertEquals(1,tech[0].getLevel());
		assertEquals(TechType.Defense,tech[1].getType());
		assertEquals(1,tech[1].getLevel());
		assertEquals("Colony",tech[2].getName());
		assertEquals(1,tech[2].getLevel());
		assertEquals("Scout Mk1",tech[3].getName());
		assertEquals(1,tech[3].getLevel());
		assertEquals("Ion drive Mk1",tech[4].getName());
		assertEquals(1,tech[4].getLevel());
		assertEquals("Fission source Mk1",tech[5].getName());
		assertEquals(1,tech[5].getLevel());
		
		ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
		assertEquals(expectedStat.toString(),statList[0].toString());
		expectedStat = new ShipStat(ShipGenerator.createColony(human,false));
		assertEquals(expectedStat.toString(),statList[1].toString());
	}
	
	/*
	 * input : SpaceRace.MECHIONS
	 * output : PlayerInfo's techList ={
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random shield or armor in Defense Type level 1,
	 * 			Hulltech level 1 Colony,
	 * 			Hulltech level 1 Scout Mk1,
	 * 			PropulsionTech level 1 Ion drive Mk1,
	 * 			PropulsionTech level 1 Fission source Mk1
	 * 			},
	 * 			PlayerInfo's ShipStatList = {
	 * 			scout have random weapon, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
	 * 			colony have Colony Hull, Ion drive Mk1, Fission source Mk1
	 * 			}  
	 */
	@Test
	public void testPlayerInfoMechions() {
		SpaceRace race = SpaceRace.MECHIONS;
		PlayerInfo human = new PlayerInfo(race);
		TechList techList = human.getTechList();
		Tech[] tech = techList.getList();
		ShipStat[] statList = human.getShipStatList();
		ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
		
		assertEquals(TechType.Combat,tech[0].getType());
		assertEquals(1,tech[0].getLevel());
		assertEquals(TechType.Defense,tech[1].getType());
		assertEquals(1,tech[1].getLevel());
		assertEquals("Colony",tech[2].getName());
		assertEquals(1,tech[2].getLevel());
		assertEquals("Scout Mk1",tech[3].getName());
		assertEquals(1,tech[3].getLevel());
		assertEquals("Ion drive Mk1",tech[4].getName());
		assertEquals(1,tech[4].getLevel());
		assertEquals("Fission source Mk1",tech[5].getName());
		assertEquals(1,tech[5].getLevel());
		assertEquals(expectedStat.toString(),statList[0].toString());
		expectedStat = new ShipStat(ShipGenerator.createColony(human,false));
		assertEquals(expectedStat.toString(),statList[1].toString());
	}
	
	
	/*
	 * input : SpaceRace.CENTAURS
	 * output : PlayerInfo's techList ={
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random shield or armor in Defense Type level 1,
	 * 			Hulltech level 1 Colony,
	 * 			Hulltech level 1 Scout Mk1,
	 * 			PropulsionTech level 1 Ion drive Mk1,
	 * 			PropulsionTech level 1 Fission source Mk1
	 * 			},
	 * 			PlayerInfo's ShipStatList = {
	 * 			scout have random weapon, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
	 * 			colony have Colony Hull, Ion drive Mk1, Fission source Mk1
	 * 			}  
	 */
	@Test
	public void testPlayerInfoCentaurs() {
		SpaceRace race = SpaceRace.CENTAURS;
		PlayerInfo human = new PlayerInfo(race);
		TechList techList = human.getTechList();
		Tech[] tech = techList.getList();
		ShipStat[] statList = human.getShipStatList();
		ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
		
		assertEquals(TechType.Combat,tech[0].getType());
		assertEquals(1,tech[0].getLevel());
		assertEquals(TechType.Defense,tech[1].getType());
		assertEquals(1,tech[1].getLevel());
		assertEquals("Colony",tech[2].getName());
		assertEquals(1,tech[2].getLevel());
		assertEquals("Scout Mk1",tech[3].getName());
		assertEquals(1,tech[3].getLevel());
		assertEquals("Ion drive Mk1",tech[4].getName());
		assertEquals(1,tech[4].getLevel());
		assertEquals("Fission source Mk1",tech[5].getName());
		assertEquals(1,tech[5].getLevel());
		assertEquals(expectedStat.toString(),statList[0].toString());
		expectedStat = new ShipStat(ShipGenerator.createColony(human,false));
		assertEquals(expectedStat.toString(),statList[1].toString());
	}
	
	/*
	 * input : SpaceRace.SPORKS
	 * output : PlayerInfo's techList ={
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random shield or armor in Defense Type level 1,
	 * 			Hulltech level 1 Colony,
	 * 			Hulltech level 1 Scout Mk1,
	 * 			PropulsionTech level 1 Ion drive Mk1,
	 * 			PropulsionTech level 1 Fission source Mk1
	 * 			},
	 * 			PlayerInfo's ShipStatList = {
	 * 			scout have random weapon in two weapons, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
	 * 			colony have Colony Hull, Ion drive Mk1, Fission source Mk1
	 * 			}  
	 */
	@Test
	public void testPlayerInfoSporks() {
		SpaceRace race = SpaceRace.SPORKS;
		PlayerInfo human = new PlayerInfo(race);
		TechList techList = human.getTechList();
		Tech[] tech = techList.getList();
		ShipStat[] statList = human.getShipStatList();
		
		assertEquals(TechType.Combat,tech[0].getType());
		assertEquals(1,tech[0].getLevel());
		assertEquals(TechType.Combat,tech[1].getType());
		assertEquals(1,tech[1].getLevel());
		assertEquals(TechType.Defense,tech[2].getType());
		assertEquals(1,tech[2].getLevel());
		assertEquals("Colony",tech[3].getName());
		assertEquals(1,tech[3].getLevel());
		assertEquals("Scout Mk1",tech[4].getName());
		assertEquals(1,tech[4].getLevel());
		assertEquals("Ion drive Mk1",tech[5].getName());
		assertEquals(1,tech[5].getLevel());
		assertEquals("Fission source Mk1",tech[6].getName());
		assertEquals(1,tech[6].getLevel());
		
		ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
		assertEquals(expectedStat.toString(),statList[0].toString());
		expectedStat = new ShipStat(ShipGenerator.createColony(human,false));
		assertEquals(expectedStat.toString(),statList[1].toString());
	}
	
	
	/*
	 * input : SpaceRace.GREYANS
	 * output : PlayerInfo's techList ={
	 * 			one random weapon in Combat TechType level 1,
	 * 			one random shield or armor in Defense Type level 1,
	 * 			Hulltech level 1 Colony,
	 * 			Hulltech level 1 Scout Mk1,
	 * 			PropulsionTech level 1 Ion drive Mk1,
	 * 			PropulsionTech level 1 Fission source Mk1,
	 * 			one random engine or powersources in propulsion TechType level 1,
	 * 			one random scanners or jammer or cloaking devices in Electronics TechType level 1
	 * 			},
	 * 			PlayerInfo's ShipStatList = {
	 * 			scout have random weapon, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
	 * 			colony have Colony Hull, Ion drive Mk1, Fission source Mk1
	 * 			}  
	 */
	@Test
	public void testPlayerInfoGreyans() {
		SpaceRace race = SpaceRace.GREYANS;
		PlayerInfo human = new PlayerInfo(race);
		TechList techList = human.getTechList();
		Tech[] tech = techList.getList();
		ShipStat[] statList = human.getShipStatList();
		
		assertEquals(TechType.Combat,tech[0].getType());
		assertEquals(1,tech[0].getLevel());
		assertEquals(TechType.Defense,tech[1].getType());
		assertEquals(1,tech[1].getLevel());
		assertEquals("Colony",tech[2].getName());
		assertEquals(1,tech[2].getLevel());
		assertEquals("Scout Mk1",tech[3].getName());
		assertEquals(1,tech[3].getLevel());
		assertEquals("Ion drive Mk1",tech[4].getName());
		assertEquals(1,tech[4].getLevel());
		assertEquals("Fission source Mk1",tech[5].getName());
		assertEquals(1,tech[5].getLevel());
		assertEquals(TechType.Propulsion,tech[6].getType());
		assertEquals(1,tech[6].getLevel());
		assertEquals(TechType.Electrics,tech[7].getType());
		assertEquals(1,tech[7].getLevel());
		
		ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
		assertEquals(expectedStat.toString(),statList[0].toString());
		expectedStat = new ShipStat(ShipGenerator.createColony(human,false));
		assertEquals(expectedStat.toString(),statList[1].toString());
	}

	@Test
	public void testgetUnchartedValueSystem(){
		Sun sun = Mockito.mock(Sun.class);
		Fleet fleet = Mockito.mock(Fleet.class);
		Mockito.when(sun.getCenterX()).thenReturn(3);
		Mockito.when(sun.getCenterY()).thenReturn(3);
		PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
		player.initMapData(256, 256);
		player.setSectorVisibility(7, 7, (byte)2);
		int expect=0;
		for (int x = -StarMap.SOLAR_SYSTEM_WIDTH - 2; x < StarMap.SOLAR_SYSTEM_WIDTH + 3; x++) {
		      for (int y = -StarMap.SOLAR_SYSTEM_WIDTH - 2; y < StarMap.SOLAR_SYSTEM_WIDTH + 3; y++) {
		    	  expect++;
		      }
		}
		expect = (expect-1)*100/expect;
		int result = player.getUnchartedValueSystem(sun, fleet);
		assertEquals(expect,result);
	}
	
	@Test
	public void testgetUnchartedSector(){
		Sun sun = Mockito.mock(Sun.class);
		Fleet fleet = Mockito.mock(Fleet.class);
		Mockito.when(sun.getCenterX()).thenReturn(4);
		Mockito.when(sun.getCenterY()).thenReturn(4);
		Mockito.when(fleet.getCoordinate()).thenReturn(new Coordinate(2,2));
		PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
		player.initMapData(65, 65);
		player.getUnchartedSector(sun, fleet);
	}
	
	
	@Test
	public void testgetUnchartedSector1(){
		Sun sun = Mockito.mock(Sun.class);
		Fleet fleet = Mockito.mock(Fleet.class);
		Mockito.when(sun.getCenterX()).thenReturn(2);
		Mockito.when(sun.getCenterY()).thenReturn(2);
		Mockito.when(fleet.getCoordinate()).thenReturn(new Coordinate(2,2));
		PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
		player.initMapData(5,5);
		for(int i=0;i <5; i++){
			for(int j=0; j<5;j++){
				player.setSectorVisibility(i, j, VISIBLE);
			}	
		}
		player.getUnchartedSector(sun, fleet);
	}
}
