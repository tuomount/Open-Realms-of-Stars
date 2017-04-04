package testOracle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openRealmOfStars.player.combat.*;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;

import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CombatTest {
	public static Fleet attackerFleet;
	public static Fleet dependerFleet;
	public static FleetList attackerFleetList;
	public static FleetList dependerFleetList;
	public static PlayerInfo attackerInfo;
	public static PlayerInfo dependerInfo;
	public static Ship attackerShip;
	public static Ship dependerShip;
	public static Ship interruptShip;
	public static ShipStat attackerShipStat;
	public static ShipStat dependerShipStat;
	public static ShipStat interruptShipStat;
	public static Route attackerFleetRoute;
	public static Route dependerFleetRoute;
	public static Ship[] attackerShips;
	public static Ship[] dependerShips;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		attackerFleet = mock(Fleet.class);
		dependerFleet = mock(Fleet.class);
		attackerFleetList = mock(FleetList.class);
		dependerFleetList = mock(FleetList.class);
		attackerInfo = mock(PlayerInfo.class);
		dependerInfo = mock(PlayerInfo.class);
		attackerShip = mock(Ship.class);
		dependerShip = mock(Ship.class);
		interruptShip = mock(Ship.class);
		attackerShipStat = mock(ShipStat.class);
		dependerShipStat = mock(ShipStat.class);
		interruptShipStat = mock(ShipStat.class);
		attackerFleetRoute = mock(Route.class);
		dependerFleetRoute = mock(Route.class);
		doNothing().when(attackerShipStat).setNumberOfCombats(anyInt());
		doNothing().when(dependerShipStat).setNumberOfCombats(anyInt());
		doNothing().when(interruptShipStat).setNumberOfCombats(anyInt());
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		when(attackerFleet.getRoute()).thenReturn(attackerFleetRoute);
		when(dependerFleet.getRoute()).thenReturn(dependerFleetRoute);
		when(attackerShipStat.getNumberOfCombats()).thenReturn(1);
		when(dependerShipStat.getNumberOfCombats()).thenReturn(1);
		when(interruptShipStat.getNumberOfCombats()).thenReturn(1);
		when(attackerShip.getName()).thenReturn("attacker");
		when(dependerShip.getName()).thenReturn("depender");
		when(dependerShip.getName()).thenReturn("depender");
		when(interruptShip.getName()).thenReturn("interrupt");
		when(attackerInfo.getShipStatByName(eq("attacker"))).thenReturn(attackerShipStat);
		when(dependerInfo.getShipStatByName(eq("depender"))).thenReturn(dependerShipStat);
		when(attackerInfo.getFleets()).thenReturn(attackerFleetList);
		when(dependerInfo.getFleets()).thenReturn(dependerFleetList);
		
		
		when(attackerFleetRoute.isDefending()).thenReturn(false);
		when(dependerFleetRoute.isDefending()).thenReturn(true);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	/**
	 * 
	 * Purpose: When Fleet class is attacking or depending, ensure that
	 * 			the accuracyBonus of the CombatShip is set correctly
	 * Input: Combat() construct Combat class.
	 * Expected: 
	 * 			attackerCombatShip.getBonusAccuracy() == 0
	 * 			dependerCombatShip.getBonusAccuracy() == 5
	 */
	public void testCombatConstructor() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		assertNotNull(combatTestItem);
		assertEquals(0,combatTestItem.getCurrentShip().getBonusAccuracy());
		combatTestItem.nextShip();
		assertEquals(5,combatTestItem.getCurrentShip().getBonusAccuracy());
		combatTestItem.nextShip();
		combatTestItem.nextShip();
		
		
	}
	
	
	@Test
	/**
	 * Purpose: Test isWeapon method when selected shipComponent is not weapon. 
	 * Input: isClearShot, shooterComponent is not weapon
	 * Expected: shooterComponent.getWeaponRange() is never called
	 * 			 targetCombatShip.getX() and getY() is never called
	 * 			
	 */
	public void testComponentIsNotWeapon() {
		CombatShip shooterCombatShip = mock(CombatShip.class);
		CombatShip targetCombatShip = mock(CombatShip.class);
		ShipComponent shooterComponent = mock(ShipComponent.class);
		Ship shooterShip = mock(Ship.class);

		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		
		when(shooterCombatShip.getShip()).thenReturn(shooterShip);
		when(shooterShip.getComponent(anyInt())).thenReturn(shooterComponent);
		when(shooterComponent.isWeapon()).thenReturn(false);
		
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		combatTestItem.isClearShot(shooterCombatShip, targetCombatShip);
		verify(shooterComponent, never()).getWeaponRange();
		verify(targetCombatShip, never()).getX();
		verify(targetCombatShip, never()).getY();
	}
	
	@Test
	/**
	 * Purpose: Test isWeapon method when selected shipComponent is weapon. 
	 * Input: isClearShot, shooterComponent is weapon
	 * Expected: shooterComponent.getWeaponRange() is called
	 * 			 targetCombatShip.getX() and getY() is called
	 * 			
	 */
	public void testComponentIsWeapon() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		
		CombatShip shooterCombatShip = mock(CombatShip.class);
		CombatShip targetCombatShip = mock(CombatShip.class);
		ShipComponent shooterComponent = mock(ShipComponent.class);
		Ship shooterShip = mock(Ship.class);
		
		when(shooterCombatShip.getShip()).thenReturn(shooterShip);
		when(shooterShip.getComponent(anyInt())).thenReturn(shooterComponent);
		when(shooterComponent.isWeapon()).thenReturn(true);
		
		
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		combatTestItem.isClearShot(shooterCombatShip, targetCombatShip);
		verify(shooterComponent, times(1)).getWeaponRange();
		verify(targetCombatShip, times(1)).getX();
		verify(targetCombatShip, times(1)).getY();
	}
	
	@Test
	/**
	 * Purpose: test isClearShot when weaponRange longer than distance between the ships
	 * Input: isClearShot , shooter located (4,2) and target located (4,4)
	 * Expected: isClearShot == true
	 * 			
	 */
	public void testVaildShot() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		
		CombatShip shooterCombatShip = mock(CombatShip.class);
		CombatShip targetCombatShip = mock(CombatShip.class);
		ShipComponent shooterComponent = mock(ShipComponent.class);
		Ship shooterShip = mock(Ship.class);
		
		when(shooterCombatShip.getShip()).thenReturn(shooterShip);
		when(shooterShip.getComponent(anyInt())).thenReturn(shooterComponent);
		when(shooterComponent.isWeapon()).thenReturn(true);
		when(shooterCombatShip.getX()).thenReturn(4);
		when(shooterCombatShip.getY()).thenReturn(2);
		when(targetCombatShip.getX()).thenReturn(4);
		when(targetCombatShip.getY()).thenReturn(4);
		when(shooterComponent.getWeaponRange()).thenReturn(3);
		
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		assertTrue(combatTestItem.isClearShot(shooterCombatShip, targetCombatShip));
	}
	
	@Test
	/**
	 * Purpose: test isClearShot when weaponRange shorter than distance between the ships
	 * Input: isClearShot , shooter located (4,2) and target located (4,4)
	 * Expected: isClearShot == false
	 */
	public void testWeaponRangeShorterThanDistance() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
	
		CombatShip shooterCombatShip = mock(CombatShip.class);
		CombatShip targetCombatShip = mock(CombatShip.class);
		ShipComponent shooterComponent = mock(ShipComponent.class);
		Ship shooterShip = mock(Ship.class);
		
		when(shooterCombatShip.getShip()).thenReturn(shooterShip);
		when(shooterShip.getComponent(anyInt())).thenReturn(shooterComponent);
		when(shooterComponent.isWeapon()).thenReturn(true);
		when(shooterCombatShip.getX()).thenReturn(4);
		when(shooterCombatShip.getY()).thenReturn(2);
		when(targetCombatShip.getX()).thenReturn(4);
		when(targetCombatShip.getY()).thenReturn(4);
		when(shooterComponent.getWeaponRange()).thenReturn(1);
		
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		assertFalse(combatTestItem.isClearShot(shooterCombatShip, targetCombatShip));
	}
	
	@Test
	/**
	 * Purpose: test isBlocked
	 * Input: isBlocked(4,2) when CombatShip is set (4,2)
	 * Expected: isClearShot == false
	 */
	public void testIsBlocked() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		ShipComponent shooterComponent = mock(ShipComponent.class);
		CombatShip currentCombatShip;
		when(attackerShip.getComponent(anyInt())).thenReturn(shooterComponent);
		when(shooterComponent.isWeapon()).thenReturn(true);
		when(shooterComponent.getWeaponRange()).thenReturn(5);
		
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);

		currentCombatShip = combatTestItem.getCurrentShip();
		currentCombatShip.setX(4);
		currentCombatShip.setY(2);
		
		assertTrue(combatTestItem.isBlocked(4, 2));
	}
	
	@Test
	/**
	 * Purpose: When attackerShip call getMostPowerfulShip,
	 * 			to see if the results are the same as expected
	 * Input: getMostPowerfulShip(attackerInfo). dependerShipPower:4, interruptShipPower:2
	 * Expected: mostPowerfulShip == dependerShip
	 */
	public void testGetMostPowerfulShip() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		when(dependerShip.getTotalMilitaryPower()).thenReturn(4);
		when(interruptShip.getTotalMilitaryPower()).thenReturn(2);
		
		CombatShip mostPowerfulShip = combatTestItem.getMostPowerfulShip(attackerInfo);
		
		assertEquals(mostPowerfulShip, combatTestItem.getShipFromCoordinate(4,1));
		assertNotEquals(mostPowerfulShip, combatTestItem.getShipFromCoordinate(4,7));
		assertNotEquals(mostPowerfulShip, combatTestItem.getShipFromCoordinate(3,1));
	}
	
	
	@Test
	/**
	 * Purpose: When coordinate is bigger than maximum or lower than minimum
	 * 			, to see isValidPos return false value
	 * Input: isValidPos(x,y). (x,y) combinations are 5
	 * Expected: 4 combinations[(10,3),(3,10),(-1,8),(8,-1)] are false
	 * 			 1 combination(3,3) is true
	 */
	public void testIsValidPos() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		assertFalse(combatTestItem.isValidPos(10, 3));
		assertFalse(combatTestItem.isValidPos(3, 10));
		assertFalse(combatTestItem.isValidPos(-1, 8));
		assertFalse(combatTestItem.isValidPos(8, -1));
		assertTrue(combatTestItem.isValidPos(3, 3));
	}


	@Test
	/**
	 * Purpose: When call getShipFromCoordinate(x, y),
	 * 			to see if the actual CombatShip location and the result are same  
	 * Input: getShipFromCoordinate(5,5) , currentCombatShip is set (5,5)
	 * Expected: currentCombatShip == getShipFromCoordinate()
	 */
	public void testGetShipFromCoordinateIsVaild() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		CombatShip currentCombatShip = combatTestItem.getCurrentShip();
		currentCombatShip.setX(5);
		currentCombatShip.setY(5);
		CombatShip testCombatShip = combatTestItem.getShipFromCoordinate(5,5);
		assertEquals(currentCombatShip, testCombatShip);
	}
	
	@Test
	/**
	 * Purpose: When call getShipFromCoordinate(x, y),
	 * 			to see if the actual CombatShip location and the result are same  
	 * Input: getShipFromCoordinate(x,y). invalid inputs are 3 combination [(4,6), (6,4), (6,6)]
	 * 		, currentCambatShip is set (4,5)
	 * Expected: currentCombatShip != (all combination)getShipFromCoordinate()
	 */
	public void testGetShipFromCoordinateIsInvaild() {
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		
		CombatShip currentCombatShip = combatTestItem.getCurrentShip();
		currentCombatShip.setX(4);
		currentCombatShip.setY(5);
		CombatShip testCombatShip = combatTestItem.getShipFromCoordinate(6,6);
		assertNotEquals(currentCombatShip, testCombatShip);
		testCombatShip = combatTestItem.getShipFromCoordinate(4,6);
		assertNotEquals(currentCombatShip, testCombatShip);
		testCombatShip = combatTestItem.getShipFromCoordinate(6,4);
		assertNotEquals(currentCombatShip, testCombatShip);
		
		
	}

	@Test
	/**
	 * Purpose: When there is only one player, to see isCombatOver is true
	 * Input: isCombatOver(). Ship is only one.
	 * Expected: isCombatOver() == true
	 */
	public void testOnePlayerCombatOver(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		when(attackerShip.getTotalMilitaryPower()).thenReturn(5);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		assertTrue(combatTestItem.isCombatOver());
	}
	
	@Test
	/**
	 * Purpose: When there is no combatShip(e.g.if ship has no weapon), to see isCombatOver is true
	 * Input: isCombatOver(). Ships are two. Both Ships have no weapon.
	 * Expected: isCombatOver() == true
	 */
	public void testNoMilitaryCombatOver(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		when(attackerShip.getTotalMilitaryPower()).thenReturn(0);
		when(dependerShip.getTotalMilitaryPower()).thenReturn(0);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		assertTrue(combatTestItem.isCombatOver());
	}

	@Test
	/**
	 * Purpose: When there are two player, to see isCombatOver is false
	 * Input: isCombatOver(). Ships are two.
	 * Expected: isCombatOver() == false
	 */
	public void testIsNotCombatOver(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		when(attackerShip.getTotalMilitaryPower()).thenReturn(1);
		when(dependerShip.getTotalMilitaryPower()).thenReturn(1);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		assertFalse(combatTestItem.isCombatOver());
	}
	
	@Test
	/**
	 * Purpose: When there are two player, to see isCombatOver is false
	 * Input: isCombatOver(). Ships are two.
	 * Expected: isCombatOver() == false
	 */
	public void testGetClosestEnemyShip(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip,interruptShip};
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		CombatShip attackerCombatShip = combatTestItem.getCurrentShip();
		combatTestItem.nextShip();
		CombatShip dependerCombatShip = combatTestItem.getCurrentShip();
		combatTestItem.nextShip();
		CombatShip interruptCombatShip = combatTestItem.getCurrentShip();
		attackerCombatShip.setX(3);
		attackerCombatShip.setY(3);
		dependerCombatShip.setX(4);
		dependerCombatShip.setY(4);
		interruptCombatShip.setX(5);
		interruptCombatShip.setY(5);
		assertEquals(dependerCombatShip,combatTestItem.getClosestEnemyShip(attackerInfo, attackerCombatShip));
	}
	
	@Test
	/**
	 * Purpose: When there are only attackerShip, to make sure that handleEndCombat works well
	 * Input: handleEndCombat(). there is only attackerShip.
	 * Expected: Verify that the methods that make up handleEndCombat() are working.
	 * 			attackerFleet.setPos is called once.
	 * 			dependerFleetList.getIndexByName is called once.
	 * 			dependerFleetList.remove is called once.
	 * 
	 */
	public void testAttackerWinEndCombat(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		Coordinate dependerFleetCoordinate = mock(Coordinate.class);
		when(dependerFleet.getCoordinate()).thenReturn(dependerFleetCoordinate);
		doNothing().when(attackerFleet).setPos(dependerFleetCoordinate);
		when(dependerFleetList.getIndexByName(anyString())).thenReturn(1);
		when(attackerFleetList.getIndexByName(anyString())).thenReturn(1);
		combatTestItem.isCombatOver();
		combatTestItem.handleEndCombat();
		verify(attackerFleet,times(1)).setPos(dependerFleetCoordinate);;
		verify(dependerFleetList,times(1)).getIndexByName(anyString());
		verify(dependerFleetList,times(1)).remove(anyInt());
	}
	@Test
	/**
	 * Purpose: When there are only dependerShip, to make sure that handleEndCombat works well
	 * Input: handleEndCombat(). there is only dependerShip.
	 * Expected: Verify that the methods that make up handleEndCombat() are working.
	 * 			attackerFleet.setPos is never called.
	 * 			attackerFleetList.getIndexByName is called once.
	 * 			attackerFleetList.remove is called once.
	 */
	public void testDependerWinEndCombat(){
		attackerShips = new Ship[]{};
		dependerShips = new Ship[]{dependerShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		Coordinate dependerFleetCoordinate = mock(Coordinate.class);
		when(dependerFleet.getCoordinate()).thenReturn(dependerFleetCoordinate);
		doNothing().when(attackerFleet).setPos(dependerFleetCoordinate);
		when(dependerFleetList.getIndexByName(anyString())).thenReturn(1);
		when(attackerFleetList.getIndexByName(anyString())).thenReturn(1);
		combatTestItem.isCombatOver();
		combatTestItem.handleEndCombat();
		verify(attackerFleet,never()).setPos(dependerFleetCoordinate);;
		verify(attackerFleetList,times(1)).getIndexByName(anyString());
		verify(attackerFleetList,times(1)).remove(anyInt());
	}
	@Test
	/**
	 * Purpose: When there are two ships, to make sure that handleEndCombat works well
	 * Input: handleEndCombat(). there are two Ships.
	 * Expected: Verify that the methods that make up handleEndCombat() are working.
	 * 			attackerFleet.setPos is never called.
	 * 			attackerFleetList.getIndexByName is never called.
	 * 			attackerFleetList.remove is never called.
	 * 			dependerFleetList.getIndexByName is never called.
	 * 			dependerFleetList.remove is never called.
	 */
	public void testNoWinerEndCombat(){
		attackerShips = new Ship[]{attackerShip};
		dependerShips = new Ship[]{dependerShip};
		when(attackerFleet.getShips()).thenReturn(attackerShips);
		when(dependerFleet.getShips()).thenReturn(dependerShips);
		Combat combatTestItem = new Combat(attackerFleet, dependerFleet, attackerInfo, dependerInfo);
		Coordinate dependerFleetCoordinate = mock(Coordinate.class);
		when(dependerFleet.getCoordinate()).thenReturn(dependerFleetCoordinate);
		doNothing().when(attackerFleet).setPos(dependerFleetCoordinate);
		when(dependerFleetList.getIndexByName(anyString())).thenReturn(1);
		when(attackerFleetList.getIndexByName(anyString())).thenReturn(1);
		combatTestItem.isCombatOver();
		combatTestItem.handleEndCombat();
		
		verify(attackerFleet,never()).setPos(dependerFleetCoordinate);;
		verify(dependerFleetList,never()).getIndexByName(anyString());
		verify(dependerFleetList,never()).remove(anyInt());
		verify(attackerFleetList,never()).getIndexByName(anyString());
		verify(attackerFleetList,never()).remove(anyInt());
	}
}
