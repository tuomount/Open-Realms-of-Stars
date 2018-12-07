package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.Coordinate;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
    assertEquals(true,fleets.isUniqueName("No such", null));
    assertEquals(false,fleets.isUniqueName("fleet #2", null));
    assertEquals(true,fleets.isUniqueName("fleet #2", fleet2));
    fleets.remove(0);
    assertEquals(2,fleets.getNumberOfFleets());
    assertEquals(fleet3,fleets.getPrev());
    assertEquals(fleet2,fleets.getNext());
    assertEquals(fleet2,fleets.getByName("Fleet #2"));
    assertEquals(2,fleets.howManyFleetWithStartingNames("Fleet #"));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFleetByCoordinate() {
    Coordinate coord1 = new Coordinate(3, 5);
    Coordinate coord2 = new Coordinate(6, 7);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
    Mockito.when(fleet1.getCoordinate()).thenReturn(coord1);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
    Mockito.when(fleet2.getCoordinate()).thenReturn(coord1);
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
    Mockito.when(fleet3.getCoordinate()).thenReturn(coord2);
    FleetList fleets = new FleetList();
    fleets.add(fleet1);
    fleets.add(fleet2);
    fleets.add(fleet3);
    assertEquals(fleet3,fleets.getFleetByCoordinate(coord2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetOnList() {
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
    FleetList fleets = new FleetList();
    assertEquals(false, fleets.isFleetOnList(fleet1));
    assertEquals(false, fleets.isFleetOnList(fleet2));
    assertEquals(false, fleets.isFleetOnList(fleet3));
    fleets.add(fleet1);
    assertEquals(true, fleets.isFleetOnList(fleet1));
    assertEquals(false, fleets.isFleetOnList(fleet2));
    assertEquals(false, fleets.isFleetOnList(fleet3));
    fleets.add(fleet2);
    assertEquals(true, fleets.isFleetOnList(fleet1));
    assertEquals(true, fleets.isFleetOnList(fleet2));
    assertEquals(false, fleets.isFleetOnList(fleet3));
    fleets.add(fleet3);
    assertEquals(true, fleets.isFleetOnList(fleet1));
    assertEquals(true, fleets.isFleetOnList(fleet2));
    assertEquals(true, fleets.isFleetOnList(fleet3));
    fleets.remove(1);
    assertEquals(true, fleets.isFleetOnList(fleet1));
    assertEquals(false, fleets.isFleetOnList(fleet2));
    assertEquals(true, fleets.isFleetOnList(fleet3));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetRemove() {
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
    FleetList fleets = new FleetList();
    fleets.add(fleet1);
    fleets.add(fleet2);
    fleets.add(fleet3);
    assertEquals(3, fleets.getNumberOfFleets());
    fleets.removeFleet(fleet2);
    assertEquals(2, fleets.getNumberOfFleets());
    assertEquals(fleet1, fleets.getByIndex(0));
    assertEquals(fleet3, fleets.getByIndex(1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetListUniqueNameGeneration() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Test hull");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getFirstShip()).thenReturn(ship);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
    FleetList fleets = new FleetList();
    assertEquals("Fleet #1", fleets.generateUniqueName());
    fleets.add(fleet1);
    fleets.add(fleet2);
    fleets.add(fleet3);
    assertEquals("Fleet #4", fleets.generateUniqueName());
    fleets.add(fleet1);
    fleets.add(fleet2);
    assertEquals("Fleet #6", fleets.generateUniqueName());
  }


}
