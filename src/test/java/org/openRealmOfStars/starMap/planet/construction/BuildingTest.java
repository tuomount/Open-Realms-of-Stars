package org.openRealmOfStars.starMap.planet.construction;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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
* Test for Building
*
*/
public class BuildingTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientLab() {
    Building building = BuildingFactory.createByName("Ancient lab");
    assertEquals("Ancient lab", building.getName());
    assertEquals(1, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientFactory() {
    Building building = BuildingFactory.createByName("Ancient factory");
    assertEquals("Ancient factory", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(1, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSuperAiCenter() {
    Building building = BuildingFactory.createByName("Super AI center");
    assertEquals("Super AI center", building.getName());
    assertEquals(4, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(1, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(0, building.getHappiness());
    assertEquals(1.00, building.getMaintenanceCost(), 0.05);
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testResearchMatix() {
    Building building = BuildingFactory.createByName("Research matrix");
    assertEquals("Research matrix", building.getName());
    assertEquals(3, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(2, building.getHappiness());
    assertEquals(0.25, building.getMaintenanceCost(), 0.05);
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientTemple() {
    Building building = BuildingFactory.createByName("Ancient temple");
    assertEquals("Ancient temple", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(1, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientPalace() {
    Building building = BuildingFactory.createByName("Ancient palace");
    assertEquals("Ancient palace", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(1, building.getCultBonus());
    assertEquals(1, building.getHappiness());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBlackMonolith() {
    Building building = BuildingFactory.createByName("Black monolith");
    assertEquals("Black monolith", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(2, building.getCultBonus());
    assertEquals(-1, building.getHappiness());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(false, building.isBroadcaster());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBarracks() {
    Building building = BuildingFactory.createByName("Barracks");
    assertEquals("Barracks", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(50, building.getBattleBonus());
    assertEquals(false, building.isBroadcaster());
    assertEquals(1, building.getHappiness());
    assertEquals(1, building.getFleetCapacityBonus());
    assertEquals("Barracks - one per planet\n" +
        "Population fights better against\n" +
        "invaders. Recruited leaders start with\n50 experience.\n" +
        "Cost: Prod.:20 Metal:20 Mainte.: 0.33\n" +
        "Happiness: +1 Fleet capacity: +1 Battle: +50%", building.getFullDescription());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBroadcastingNetwork() {
    Building building = BuildingFactory.createByName("Broadcasting network");
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(3, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
    assertEquals(0, building.getBattleBonus());
    assertEquals(1, building.getHappiness());
    assertEquals(0, building.getFleetCapacityBonus());
    assertEquals(true, building.isBroadcaster());
    assertEquals("Broadcasting network - one per planet\n" +
        "Broadcasting network for creating\n" +
        "culture and happiness.\n" +
        "Cost: Prod.:60 Metal:40 Mainte.: 0.25\n" +
        "Cult.: +3 Happiness: +1 Broadcasting", building.getFullDescription());
  }

}
