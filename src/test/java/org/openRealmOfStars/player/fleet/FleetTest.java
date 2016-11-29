package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Route;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for Ship hull class
 */
public class FleetTest {

  private Ship createShipOne() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getFtlSpeed()).thenReturn(2);
    Mockito.when(ship.getScannerDetectionLvl()).thenReturn(40);
    Mockito.when(ship.getScannerLvl()).thenReturn(2);
    Mockito.when(ship.getSpeed()).thenReturn(1);
    Mockito.when(ship.getFreeCargoColonists()).thenReturn(0);
    Mockito.when(ship.getFreeCargoMetal()).thenReturn(0);
    Mockito.when(ship.getHullPoints()).thenReturn(4);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(4);
    return ship;
  }

  private Ship createShipTwo() {
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
  }

}
