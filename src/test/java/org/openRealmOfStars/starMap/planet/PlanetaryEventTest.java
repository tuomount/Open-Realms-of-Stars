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
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("", event.getExplanation());
      }
      if (event == PlanetaryEvent.LUSH_VEGETATION) {
        assertEquals(1, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Lush vegetation +1 Food", event.getExplanation());
      }
      if (event == PlanetaryEvent.PARADISE) {
        assertEquals(2, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(1, event.getExtraHappiness());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Paradise +2 Food, +1 happiness", event.getExplanation());
      }
      if (event == PlanetaryEvent.METAL_RICH_SURFACE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(1, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals(null, event.getBuilding());
        assertEquals(false, event.oneTimeOnly());
        assertEquals("Metal rich surface +1 Metal", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_LAB) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals("Ancient lab", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient lab building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_FACTORY) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals("Ancient factory", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient factory building", event.getExplanation());
      }
      if (event == PlanetaryEvent.ANCIENT_TEMPLE) {
        assertEquals(0, event.getExtraFoodProduction());
        assertEquals(0, event.getExtraMetalProduction());
        assertEquals(0, event.getExtraHappiness());
        assertEquals("Ancient temple", event.getBuilding().getName());
        assertEquals(true, event.oneTimeOnly());
        assertEquals("Ancient temple building", event.getExplanation());
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
    DiceGenerator.initializeGenerators(5L, 1234);
    PlanetaryEvent event = PlanetaryEvent.getRandomEvent(PlanetTypes.CARBONWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
    DiceGenerator.initializeGenerators(6L, 1235);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.CARBONWORLD1, 100);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, event);
    DiceGenerator.initializeGenerators(8L, 1237);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.ICEWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(5L, 1234);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.ICEWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_FACTORY, event);
    DiceGenerator.initializeGenerators(5L, 1234);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD3, 100);
    assertEquals(PlanetaryEvent.ANCIENT_FACTORY, event);
    DiceGenerator.initializeGenerators(6L, 1236);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD3, 100);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, event);
    DiceGenerator.initializeGenerators(8L, 1238);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD2, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(9L, 1239);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_TEMPLE, event);
    DiceGenerator.initializeGenerators(12L, 1242);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD1, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
    DiceGenerator.initializeGenerators(16L, 1246);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.WATERWORLD4, 100);
    assertEquals(PlanetaryEvent.PARADISE, event);
    DiceGenerator.initializeGenerators(16L, 1246);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.SILICONWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(16L, 1246);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD1, 100);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, event);
    DiceGenerator.initializeGenerators(12L, 1242);
    event = PlanetaryEvent.getRandomEvent(PlanetTypes.IRONWORLD2, 100);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, event);
  }

}
