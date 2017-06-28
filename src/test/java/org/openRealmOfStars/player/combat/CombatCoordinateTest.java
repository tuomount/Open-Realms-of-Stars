package org.openRealmOfStars.player.combat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017  Tuomo Untinen
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
public class CombatCoordinateTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCombatCoordinate() {
    CombatCoordinate coordinate = new CombatCoordinate(5, 7);
    assertEquals(5, coordinate.getX());
    assertEquals(7, coordinate.getY());
    assertEquals("X: 5 Y: 7", coordinate.toString());
    assertEquals(0.0, coordinate.calculateDistance(coordinate), 0.1);
    CombatCoordinate coordinate2 = new CombatCoordinate(3, 5);
    assertEquals(2.82, coordinate.calculateDistance(coordinate2), 0.1);
    assertEquals(true, coordinate.sameAs(coordinate));
    assertEquals(false, coordinate.sameAs(coordinate2));
    coordinate2.setX(5);
    coordinate2.setY(7);
    assertEquals(true, coordinate.sameAs(coordinate2));
  }

}
