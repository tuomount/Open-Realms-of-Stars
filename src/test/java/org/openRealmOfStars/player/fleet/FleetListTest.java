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
 * Test for FleetList class
 */
public class FleetListTest {

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
    return ship;
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetList() {
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
    FleetList fleets = new FleetList();
    assertEquals(0,fleets.getNumberOfFleets());
    assertEquals(null,fleets.getCurrent());
    fleets.add(fleet1);
    assertEquals(1,fleets.getNumberOfFleets());
    assertEquals(fleet1,fleets.getCurrent());
    assertEquals(fleet1,fleets.getNext());
    assertEquals(fleet1,fleets.getPrev());
    fleets.add(fleet2);
    assertEquals(2,fleets.getNumberOfFleets());
    assertEquals(fleet1,fleets.getCurrent());
    assertEquals(fleet2,fleets.getNext());
    assertEquals(fleet1,fleets.getPrev());
    assertEquals(fleet2,fleets.getByIndex(1));
    fleets.add(fleet3);
    assertEquals(3,fleets.getNumberOfFleets());
    assertEquals(fleet1,fleets.getCurrent());
    assertEquals(fleet2,fleets.getNext());
    assertEquals(fleet3,fleets.getNext());
    assertEquals(fleet1,fleets.getNext());
    assertEquals(fleet3,fleets.getPrev());
    assertEquals(fleet2,fleets.getPrev());
    fleets.remove(0);
    assertEquals(2,fleets.getNumberOfFleets());
    assertEquals(fleet3,fleets.getPrev());
    assertEquals(fleet2,fleets.getNext());
    assertEquals(fleet2,fleets.getByName("Fleet #2"));
    assertEquals(2,fleets.howManyFleetWithStartingNames("Fleet #"));
  }


}
