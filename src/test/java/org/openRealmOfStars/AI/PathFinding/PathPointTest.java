package org.openRealmOfStars.AI.PathFinding;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
 * Test for PathPoint class
 */

public class PathPointTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPathPoint() {
    PathPoint point = new PathPoint(2, 3, 1.5);
    assertEquals(2,point.getX());
    assertEquals(3,point.getY());
    assertEquals(1.5,point.getDistance(),0.1);
    PathPoint point2 = new PathPoint(2,3,1.5);
    assertEquals(point,point2);
    point.setX(4);
    point.setY(5);
    point.setDistance(0.5);
    assertEquals(4,point.getX());
    assertEquals(5,point.getY());
    assertEquals(0.5,point.getDistance(),0.1);
    assertNotEquals(point,point2);
    assertEquals("X: 4 Y: 5 Dist: 0.5",point.toString());
    assertEquals(38929,point.hashCode());
  }

}
