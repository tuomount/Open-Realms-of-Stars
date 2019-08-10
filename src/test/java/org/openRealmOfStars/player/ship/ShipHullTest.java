package org.openRealmOfStars.player.ship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2019 Tuomo Untinen
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
public class ShipHullTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipHull() {
    ShipHull hull = new ShipHull(0, "Test", 4, 1, ShipHullType.NORMAL,
        ShipSize.SMALL, 8, 10, SpaceRace.GREYANS);
    assertEquals(0.1, hull.getFleetCapacity(), 0.01);
    hull.setFleetCapacity(0.2);
    assertEquals(0.2, hull.getFleetCapacity(), 0.01);
    assertEquals(8, hull.getCost());
    assertEquals(10, hull.getMetalCost());
    assertEquals(0, hull.getIndex());
    assertEquals("Test", hull.getName());
    assertEquals(SpaceRace.GREYANS, hull.getRace());
    assertEquals(1, hull.getSlotHull());
    assertEquals(ShipSize.SMALL, hull.getSize());
    assertEquals(4, hull.getMaxSlot());
    String tmp = hull.toString();
    assertTrue("ToString does not contain ship hull name",
        tmp.contains("Test"));
    assertTrue("ToString does not contain ship hull free slots",
        tmp.contains("Slots:4"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipHullCentaurs() {
    ShipHull hull = new ShipHull(0, "Test", 4, 1, ShipHullType.NORMAL,
        ShipSize.SMALL, 8, 10, SpaceRace.CENTAURS);
    assertEquals(12, hull.getCost());
    assertEquals(20, hull.getMetalCost());
    assertEquals(0, hull.getIndex());
    assertEquals("Test", hull.getName());
    assertEquals(SpaceRace.CENTAURS, hull.getRace());
    assertEquals(2, hull.getSlotHull());
    assertEquals(ShipSize.SMALL, hull.getSize());
    assertEquals(4, hull.getMaxSlot());
  }


}
