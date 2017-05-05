package org.openRealmOfStars.starMap.planet.construction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icon16x16;

/**
 *
 * Open Realm of Stars game project Copyright (C) 2016 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see http://www.gnu.org/licenses/
 *
 *
 * Test for Construction
 *
 */
public class ConstructionTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testConstruction() {
    Icon16x16 icon = Mockito.mock(Icon16x16.class);
    Mockito.when(icon.getName()).thenReturn("icon1");
    Icon16x16 icon2 = Mockito.mock(Icon16x16.class);
    Mockito.when(icon2.getName()).thenReturn("icon2");
    Construction construction = new Construction("Test Thingy",icon);
    construction.setDescription("Here is description!");
    assertEquals("Here is description!",construction.getDescription());
    assertEquals("Test Thingy",construction.getName());
    // Test default cost
    assertEquals(1,construction.getProdCost());
    assertEquals(1,construction.getMetalCost());
    construction.setName("Thingy 2");
    construction.setDescription("Thingy description");
    construction.setIcon(icon2);
    construction.setMetalCost(2);
    construction.setProdCost(15);
    assertEquals("Thingy description",construction.getDescription());
    assertEquals("Thingy 2",construction.getName());
    assertEquals(15,construction.getProdCost());
    assertEquals(2,construction.getMetalCost());
    assertEquals("icon2", construction.getIcon().getName());
  }

}
