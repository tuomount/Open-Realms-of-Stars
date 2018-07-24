package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Test for Fleet class
 */
public class FleetTest {

  /**
   * Create first ship
   * @return First ship
   */
  private static Ship createShipOne() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.SMALL);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(40);
    Mockito.when(ship.getScannerLvl()).thenReturn(2);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(0);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(0);
    Mockito.when(ship.getHullPoints()).thenReturn(4);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(4);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Scout");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(15);
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.getCulture()).thenReturn(0);
    return ship;
  }

  /**
   * Create first ship
   * @return First ship
   */
  private static Ship createEspionageShip() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.SMALL);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(40);
    Mockito.when(ship.getScannerLvl()).thenReturn(2);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(0);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(0);
    Mockito.when(ship.getHullPoints()).thenReturn(4);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(4);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Scout");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(15);
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.getEspionageBonus()).thenReturn(1);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.getCulture()).thenReturn(0);
    return ship;
  }

  /**
   * Create second ship, colony in this case
   * @return Colony ship
   */
  private static Ship createShipTwo() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(1);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(4);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(20);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(true);
    Mockito.when(ship.isColonyShip()).thenReturn(true);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Colony");
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.getCulture()).thenReturn(1);
    return ship;
  }

  /**
   * Create trooper ship
   * @return Trooper ship
   */
  private static Ship createTrooper() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(1);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(4);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(20);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(false);
    Mockito.when(ship.isColonyShip()).thenReturn(false);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Trooper");
    Mockito.when(ship.isTrooperModule()).thenReturn(true);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.getCulture()).thenReturn(0);
    return ship;
  }

  /**
   * Create trooper ship
   * @return Trooper ship
   */
  private static Ship createBomber() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(1);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(4);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(20);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(false);
    Mockito.when(ship.isColonyShip()).thenReturn(false);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Bomber");
    Mockito.when(ship.isTrooperModule()).thenReturn(true);
    Mockito.when(ship.hasBombs()).thenReturn(true);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.getCulture()).thenReturn(0);
    return ship;
  }

  /**
   * Create starbase
   * @return Starbase
   */
  private static Ship createStarbase() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(0);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(0);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(0);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(false);
    Mockito.when(ship.isColonyShip()).thenReturn(false);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Starbase");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(5);
    Mockito.when(ship.getCulture()).thenReturn(0);
    Mockito.when(ship.getTotalCreditBonus()).thenReturn(1);
    Mockito.when(ship.getTotalResearchBonus()).thenReturn(2);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.getTotalCultureBonus()).thenReturn(3);
    Mockito.when(ship.isStarBase()).thenReturn(true);
    Mockito.when(ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)).thenReturn(true);
    return ship;
  }

  /**
   * Create Trade ship
   * @return Trade ship
   */
  private static Ship createTradeShip() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(1);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(4);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(20);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(false);
    Mockito.when(ship.isColonyShip()).thenReturn(false);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Trader");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getCulture()).thenReturn(0);
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.isTradeShip()).thenReturn(true);
    return ship;
  }

  /**
   * Create Carrier ship
   * @return Carrier ship
   */
  private static Ship createCarrierShip() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.LARGE);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(5);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(0);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(0);
    Mockito.when(ship.getHullPoints()).thenReturn(12);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(12);
    Mockito.when(ship.isColonyModule()).thenReturn(false);
    Mockito.when(ship.isColonyShip()).thenReturn(false);
    Mockito.when(ship.getColonist()).thenReturn(0);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Carrier");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getCulture()).thenReturn(0);
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.isTradeShip()).thenReturn(true);
    Mockito.when(ship.getFighterBaySize()).thenReturn(2);
    return ship;
  }

  /**
   * Create privateer ship
   * @return Privateer ship
   */
  private static Ship createPrivateerShipOne() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.LARGE);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(40);
    Mockito.when(ship.getScannerLvl()).thenReturn(2);
    Mockito.when(ship.getCloakingValue()).thenReturn(40);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(2);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(4);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(4);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(true);
    Mockito.when(ship.getName()).thenReturn("Privateer");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(ship.isTrooperModule()).thenReturn(false);
    Mockito.when(ship.getEspionageBonus()).thenReturn(0);
    Mockito.when(ship.hasBombs()).thenReturn(false);
    Mockito.when(ship.getCulture()).thenReturn(0);
    return ship;
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithOneShip() {
    Route route = Mockito.mock(Route.class);
    AStarSearch asearch = Mockito.mock(AStarSearch.class);
    Ship ship = createShipOne();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(2, fleet.getCoordinate().getX());
    assertEquals(3, fleet.getCoordinate().getY());
    assertEquals("Fleet #0",fleet.getName());
    fleet.setName("Test-Fleet");
    assertEquals("Test-Fleet",fleet.getName());
    assertEquals(ship,fleet.getFirstShip());
    assertEquals(2, fleet.getFleetFtlSpeed());
    assertEquals(40, fleet.getFleetCloakDetection());
    assertEquals(2, fleet.getFleetScannerLvl());
    assertEquals(1, fleet.getFleetSpeed());
    assertEquals(1, fleet.getNumberOfShip());
    assertEquals(0, fleet.getFreeSpaceForColonist());
    assertEquals(0, fleet.getFreeSpaceForMetal());
    assertEquals(true, fleet.allFixed());
    assertEquals(0, fleet.getFleetCloackingValue());
    fleet.setMovesLeft(2);
    assertEquals(2, fleet.getMovesLeft());
    fleet.decMovesLeft();
    assertEquals(1, fleet.getMovesLeft());
    fleet.setRoute(route);
    assertEquals(route, fleet.getRoute());
    fleet.setaStarSearch(asearch);
    assertEquals(asearch,fleet.getaStarSearch());
    assertEquals(false, fleet.isPrivateerFleet());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Terran alliance");
    assertEquals(0, fleet.getCulturalValue());
    assertEquals("Test-Fleet\nTerran alliance\nSpeed: 1 FTL: 2\nMoves:1\nScout - 15"
        + "\n\nEnroute", fleet.getInfoAsText(info));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTradeShip() {
    Route route = Mockito.mock(Route.class);
    AStarSearch asearch = Mockito.mock(AStarSearch.class);
    Ship ship = createTradeShip();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(2, fleet.getCoordinate().getX());
    assertEquals(3, fleet.getCoordinate().getY());
    assertEquals("Fleet #0",fleet.getName());
    fleet.setName("Trader");
    assertEquals("Trader",fleet.getName());
    assertEquals(ship,fleet.getFirstShip());
    assertEquals(1, fleet.getFleetFtlSpeed());
    assertEquals(0, fleet.getFleetCloakDetection());
    assertEquals(1, fleet.getFleetScannerLvl());
    assertEquals(1, fleet.getFleetSpeed());
    assertEquals(1, fleet.getNumberOfShip());
    assertEquals(4, fleet.getFreeSpaceForColonist());
    assertEquals(20, fleet.getFreeSpaceForMetal());
    assertEquals(true, fleet.allFixed());
    assertEquals(0, fleet.getFleetCloackingValue());
    fleet.setMovesLeft(2);
    assertEquals(2, fleet.getMovesLeft());
    fleet.decMovesLeft();
    assertEquals(1, fleet.getMovesLeft());
    fleet.setRoute(route);
    assertEquals(route, fleet.getRoute());
    fleet.setaStarSearch(asearch);
    assertEquals(asearch,fleet.getaStarSearch());
    assertEquals(false, fleet.isPrivateerFleet());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Terran alliance");
    assertEquals(0, fleet.getCulturalValue());
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(ship.doTrade(planet, info)).thenReturn(2);
    assertEquals(2, fleet.doTrade(planet, info));
    assertEquals(true, fleet.isTradeFleet());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTradeShip2() {
    Ship ship = createTradeShip();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(2, fleet.getCoordinate().getX());
    assertEquals(3, fleet.getCoordinate().getY());
    fleet.setName("Trader #1");
    assertEquals("Trader #1",fleet.getName());
    Coordinate tradeCoordinate = new Coordinate(15,16);
    Mockito.when(ship.calculateTradeCredits((Coordinate) Mockito.any(),
        (Coordinate) Mockito.any())).thenReturn(4);
    assertEquals(4, fleet.calculateTrade(tradeCoordinate));
    fleet.addShip(ship);
    assertEquals(8, fleet.calculateTrade(tradeCoordinate));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTwoShip() {
    Ship ship = createShipOne();
    Fleet fleet = new Fleet(ship, 2, 3);
    Ship colony = createShipTwo();
    fleet.addShip(colony);
    assertEquals(ship,fleet.getFirstShip());
    assertEquals(null, fleet.getColonyShip());
    assertEquals(colony, fleet.getColonyShip(false));
    Mockito.when(colony.getColonist()).thenReturn(2);
    assertEquals(colony, fleet.getColonyShip());
    assertEquals(ship,fleet.getShipByIndex(0));
    assertEquals(colony,fleet.getShipByIndex(1));
    assertEquals(1, fleet.getFleetFtlSpeed());
    assertEquals(40, fleet.getFleetCloakDetection());
    assertEquals(2, fleet.getFleetScannerLvl());
    assertEquals(1, fleet.getFleetSpeed());
    assertEquals(2, fleet.getNumberOfShip());
    assertEquals(4, fleet.getFreeSpaceForColonist());
    assertEquals(20, fleet.getFreeSpaceForMetal());
    assertEquals(0, fleet.getFleetCloackingValue());
    assertEquals(true, fleet.allFixed());
    assertEquals(false, fleet.isPrivateerFleet());
    assertEquals(1, fleet.getCulturalValue());
    assertEquals("Fleet #0\nSpeed: 1 FTL: 1\nMoves:0\nScout - 15\nColony - 0"
        + "\n", fleet.getInfoAsText(null));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithThreeShip() {
    Ship ship = createShipOne();
    Fleet fleet = new Fleet(ship, 2, 3);
    Ship espionageShip = createEspionageShip();
    fleet.addShip(espionageShip);
    fleet.addShip(espionageShip);
    assertEquals(2, fleet.getEspionageBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTwoPrivateer() {
    Ship privateer = createPrivateerShipOne();
    Fleet fleet = new Fleet(privateer, 2, 3);
    fleet.addShip(privateer);
    assertEquals(true, fleet.isPrivateerFleet());
    assertEquals(40, fleet.getFleetCloackingValue());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Terran alliance");
    assertEquals(0, fleet.getCulturalValue());
    assertEquals("Fleet #0\nPrivateer fleet\nSpeed: 1 FTL: 2\nMoves:0"
        + "\nPrivateer - 20\nPrivateer - 20\n", fleet.getInfoAsText(info));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithThreeShips() {
    Ship ship = createShipOne();
    Ship privateer = createPrivateerShipOne();
    Ship colony = createShipTwo();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(15,fleet.getMilitaryValue());
    fleet.addShip(privateer);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(35,fleet.getMilitaryValue());
    fleet.addShip(colony);
    assertEquals(1, fleet.getCulturalValue());
    assertEquals(35,fleet.getMilitaryValue());
    assertEquals(22, fleet.getFleetCloackingValue());
    assertEquals(false, fleet.isTradeFleet());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithFighterBay() {
    Ship ship = createShipOne();
    Ship carrier = createCarrierShip();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(2, fleet.getFleetFtlSpeed());
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(15,fleet.getMilitaryValue());
    fleet.addShip(carrier);
    assertEquals(5, fleet.getFleetFtlSpeed());
    fleet.addShip(ship);
    assertEquals(5, fleet.getFleetFtlSpeed());
    fleet.addShip(ship);
    assertEquals(2, fleet.getFleetFtlSpeed());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithAttackMission() {
    Ship ship = createShipOne();
    Ship trooper = createTrooper();
    Ship bomber = createBomber();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(15,fleet.getMilitaryValue());
    fleet.addShip(trooper);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(15,fleet.getMilitaryValue());
    fleet.addShip(bomber);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(15,fleet.getMilitaryValue());
    assertEquals(0, fleet.getFleetCloackingValue());
    assertEquals(false, fleet.isTradeFleet());
    assertEquals(ship, fleet.getAssaultShip());
    assertEquals(trooper, fleet.getTrooperShip());
    assertEquals(bomber, fleet.getBomberShip());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithDeployedStarbase() {
    Ship ship = createStarbase();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(0, fleet.getCulturalValue());
    assertEquals(5,fleet.getMilitaryValue());
    assertEquals(false, fleet.isTradeFleet());
    assertEquals(true, fleet.isStarBaseDeployed());
    assertEquals(1, fleet.getTotalCreditsBonus());
    assertEquals(2, fleet.getTotalReseachBonus());
    assertEquals(3, fleet.getTotalCultureBonus());
  }

}
