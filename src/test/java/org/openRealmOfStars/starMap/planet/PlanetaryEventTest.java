package org.openRealmOfStars.starMap.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2023 Tuomo Untinen
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
 */

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
* Test for PlanetaryEent
*
*/
public class PlanetaryEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllValids() {
    for (var event: PlanetaryEvent.values()) {
      if (event == PlanetaryEvent.NONE) {
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_LAB) {
        assertEquals("Ancient lab", event.getName());
        assertEquals("Ancient lab", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient lab building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_FACTORY) {
        assertEquals("Ancient factory", event.getName());
        assertEquals("Ancient factory", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient factory building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_TEMPLE) {
        assertEquals("Ancient temple", event.getName());
        assertEquals("Ancient temple", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient temple building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_PALACE) {
        assertEquals("Ancient palace", event.getName());
        assertEquals("Ancient palace", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient palace building", event.getExplanation());
      }
      if (event == PlanetaryEvent.BLACK_MONOLITH) {
        assertEquals("Black monolith", event.getName());
        assertEquals("Black monolith", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Black monolith building", event.getExplanation());
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
