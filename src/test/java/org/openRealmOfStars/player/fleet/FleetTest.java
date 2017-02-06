package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Route;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  Tuomo Untinen
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
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
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
    return ship;
  }

  /**
   * Create second ship, colony in this case
   * @return Colony ship
   */
  private static Ship createShipTwo() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(1);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(0);
    Mockito.when(ship.getScannerLvl()).thenReturn(0);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(4);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(20);
    Mockito.when(ship.getHullPoints()).thenReturn(6);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(6);
    Mockito.when(ship.isColonyModule()).thenReturn(true);
    Mockito.when(ship.isColonyShip()).thenReturn(true);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(false);
    Mockito.when(ship.getName()).thenReturn("Colony");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    return ship;
  }

  /**
   * Create privateer ship
   * @return Privateer ship
   */
  private static Ship createPrivateerShipOne() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(40);
    Mockito.when(ship.getScannerLvl()).thenReturn(2);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(2);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(4);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(4);
    Mockito.when(ship.isPrivateeringShip()).thenReturn(true);
    Mockito.when(ship.getName()).thenReturn("Privateer");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
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
    assertEquals("Fleet #-1",fleet.getName());
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
    assertEquals("Test-Fleet\nTerran alliance\nSpeed: 1 FTL: 2\nMoves:1\nScout"
        + "\n\nEnroute", fleet.getInfoAsText(info));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTwoShip() {
    Ship ship = createShipOne();
    Fleet fleet = new Fleet(ship, 2, 3);
    Ship colony = createShipTwo();
    fleet.addShip(colony);
    assertEquals(ship,fleet.getFirstShip());
    assertEquals(ship,fleet.getShipByIndex(0));
    assertEquals(colony,fleet.getShipByIndex(1));
    assertEquals(1, fleet.getFleetFtlSpeed());
    assertEquals(40, fleet.getFleetCloakDetection());
    assertEquals(2, fleet.getFleetScannerLvl());
    assertEquals(1, fleet.getFleetSpeed());
    assertEquals(2, fleet.getNumberOfShip());
    assertEquals(4, fleet.getFreeSpaceForColonist());
    assertEquals(20, fleet.getFreeSpaceForMetal());
    assertEquals(true, fleet.allFixed());
    assertEquals(false, fleet.isPrivateerFleet());
    assertEquals("Fleet #-1\nSpeed: 1 FTL: 1\nMoves:0\nScout\nColony"
        + "\n", fleet.getInfoAsText(null));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithTwoPrivateer() {
    Ship privateer = createPrivateerShipOne();
    Fleet fleet = new Fleet(privateer, 2, 3);
    fleet.addShip(privateer);
    assertEquals(true, fleet.isPrivateerFleet());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Terran alliance");
    assertEquals("Fleet #-1\nPrivateer fleet\nSpeed: 1 FTL: 2\nMoves:0"
        + "\nPrivateer\nPrivateer\n", fleet.getInfoAsText(info));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetWithThreeShips() {
    Ship ship = createShipOne();
    Ship privateer = createPrivateerShipOne();
    Ship colony = createShipTwo();
    Fleet fleet = new Fleet(ship, 2, 3);
    assertEquals(15,fleet.getMilitaryValue());
    fleet.addShip(privateer);
    assertEquals(35,fleet.getMilitaryValue());
    fleet.addShip(colony);
    assertEquals(35,fleet.getMilitaryValue());
  }

}
