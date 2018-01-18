package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018 Tuomo Untinen
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
* Test for PlanetaryEent
*
*/
public class PlanetaryEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllValids() {
    for (int i = 0; i < PlanetaryEvent.values().length; i++) {
      PlanetaryEvent event = PlanetaryEvent.getByIndex(i);
      assertEquals(i, event.getIndex());
      if (event == PlanetaryEvent.NONE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.LUSH_VEGETATION) {
        assertEquals(1, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.PARADISE) {
        assertEquals(2, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.METAL_RICH_SURFACE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(1, event.getExtraMetalProduction());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.ANCIENT_LAB) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals("Ancient lab", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.ANCIENT_FACTORY) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals("Ancient factory", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
      }
      if (event == PlanetaryEvent.ANCIENT_TEMPLE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals("Ancient temple", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
      }
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllInvalid() {
    PlanetaryEvent event = PlanetaryEvent.getByIndex(255);
    assertEquals(255, event.getIndex());
  }

}
