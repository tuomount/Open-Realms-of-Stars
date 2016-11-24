package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;

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
 * Test for SquareInfo class
 * 
 */
public class SquareInfoTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateSquareInfoPlanet() {
    SquareInfo square = new SquareInfo(SquareInfo.TYPE_PLANET, 1);
    assertEquals(SquareInfo.TYPE_PLANET, square.getType());
    assertEquals(1,square.getValue());
    assertEquals("Blocked!",false,square.isBlocked());
    assertEquals("Visibility blocked!",false,square.isVisibilityBlocked());
    square.setValue(65535);
    assertEquals(SquareInfo.MAX_VALUE,square.getValue());
    square.setValue(-2);
    assertEquals(0,square.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateSquareInfoEmpty() {
    SquareInfo square = new SquareInfo(SquareInfo.TYPE_EMPTY, 1);
    assertEquals(SquareInfo.TYPE_EMPTY, square.getType());
    assertEquals(1,square.getValue());
    assertEquals("Blocked!",false,square.isBlocked());
    assertEquals("Visibility blocked!",false,square.isVisibilityBlocked());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateSquareInfoGasGiant() {
    SquareInfo square = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, 1);
    assertEquals(SquareInfo.TYPE_GAS_PLANET, square.getType());
    assertEquals(1,square.getValue());
    assertEquals("Blocked!",true,square.isBlocked());
    assertEquals("Visibility blocked!",true,square.isVisibilityBlocked());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateSquareInfoSUN() {
    SquareInfo square = new SquareInfo(SquareInfo.TYPE_SUN, 1);
    assertEquals(SquareInfo.TYPE_SUN, square.getType());
    assertEquals(1,square.getValue());
    assertEquals("Blocked!",true,square.isBlocked());
    assertEquals("Visibility blocked!",true,square.isVisibilityBlocked());
  }

}
