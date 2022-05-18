package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.utilities.DiceGenerator;

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
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("", event.getExplanation());
      }
      if (event == PlanetaryEvent.LUSH_VEGETATION) {
        assertEquals(1, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Lush vegetation", event.getName());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Lush vegetation +1 Food", event.getExplanation());
      }
      if (event == PlanetaryEvent.PARADISE) {
        assertEquals(2, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(1, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Paradise", event.getName());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Paradise +2 Food, +1 happiness", event.getExplanation());
      }
      if (event == PlanetaryEvent.METAL_RICH_SURFACE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(1, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Metal rich surface", event.getName());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Metal rich surface +1 Metal", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_LAB) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Ancient lab", event.getName());
        assertEquals("Ancient lab", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient lab building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_FACTORY) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Ancient factory", event.getName());
        assertEquals("Ancient factory", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient factory building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_TEMPLE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Ancient temple", event.getName());
        assertEquals("Ancient temple", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient temple building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_PALACE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Ancient palace", event.getName());
        assertEquals("Ancient palace", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient palace building", event.getExplanation());
      }
      if (event == PlanetaryEvent.BLACK_MONOLITH) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Black monolith", event.getName());
        assertEquals("Black monolith", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Black monolith building", event.getExplanation());
      }
      if (event == PlanetaryEvent.MOLTEN_LAVA) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(1, event.getExtraMetalProduction());
        assertEquals(-1, event.getExtraHappiness());
        assertEquals(1, event.getExtraProduction());
        assertEquals("Molten lava", event.getName());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Molten lava +1 metal, +1 production,"
            + " -1 happiness", event.getExplanation());
      }
      if (event == PlanetaryEvent.ARID) {
        assertEquals(-1, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(0, event.getExtraProduction());
        assertEquals("Arid climate", event.getName());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Arid climate -1 Food", event.getExplanation());
      }

    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllInvalid() {
    PlanetaryEvent event = PlanetaryEvent.getByIndex(255);
    assertEquals(255, event.getIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGeneration() {
    DiceGenerator.initializeGenerators(0, 55, 55);
    PlanetaryEvent event = PlanetaryEvent.getRandomEvent(PlanetTypes.CARBONWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
    DiceGenerator.initializeGenerators(6L, 1235);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.CARBONWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(8L, 1237);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.ICEWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(0, 50, 50);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.ICEWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_FACTORY, event);
    DiceGenerator.initializeGenerators(5L, 1234);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD3, 100);
    assertEquals(PlanetaryEvent.ANCIENT_PALACE, event);
    DiceGenerator.initializeGenerators(6L, 1236);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD3, 100);
    assertEquals(PlanetaryEvent.ANCIENT_PALACE, event);
    DiceGenerator.initializeGenerators(8L, 1238);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_PALACE, event);
    DiceGenerator.initializeGenerators(9L, 1239);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
    DiceGenerator.initializeGenerators(12L, 1242);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD1, 100);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, event);
    DiceGenerator.initializeGenerators(16L, 1246);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD4, 100);
    assertEquals(PlanetaryEvent.PARADISE, event);
    DiceGenerator.initializeGenerators(16L, 1246);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.SILICONWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(0, 15, 15);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(0, 30, 30);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
    DiceGenerator.initializeGenerators(0, 97, 97);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.SILICONWORLD1, 100);
    assertEquals(PlanetaryEvent.BLACK_MONOLITH, event);
    DiceGenerator.initializeGenerators(0, 90, 90);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.SILICONWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_PALACE, event);
    DiceGenerator.initializeGenerators(0, 75, 75);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD3, 100);
    assertEquals(PlanetaryEvent.MOLTEN_LAVA, event);
    DiceGenerator.initializeGenerators(55, 55, 55);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD6, 100);
    assertEquals(PlanetaryEvent.ARID, event);
    DiceGenerator.initializeGenerators(55, 55, 55);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.DESERTWORLD1, 100);
    assertEquals(PlanetaryEvent.ARID, event);
    DiceGenerator.initializeGenerators(75, 75, 75);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.DESERTWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_TEMPLE, event);
  }

}
