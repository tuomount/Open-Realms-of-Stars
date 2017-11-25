package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.construction.Building;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  Tuomo Untinen
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
 * Test for planet
 *
 */
public class PlanetTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetCoordinateShouldChangeableWithSideEffect() {
      Coordinate planetCoordinate = new Coordinate(10, 15);
      Planet planet = new Planet(planetCoordinate, "Earth", 1, false);

      planetCoordinate.setX(5);
      planetCoordinate.setY(10);

      assertNotEquals(planet.getCoordinate(), planetCoordinate);

      Coordinate getPlanetCoordinate = planet.getCoordinate();
      getPlanetCoordinate.setX(5);
      getPlanetCoordinate.setY(10);

      assertNotEquals(planet.getCoordinate(), getPlanetCoordinate);
  }
  
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetPopulationGrowthAndBuilding() {
    Coordinate planetCoordinate = new Coordinate(10, 15);
    Planet planet = new Planet(planetCoordinate, "Earth", 1, false);
    planet.setRadiationLevel(1);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.setEmpireName("Alliance of Humans");
    planet.setPlanetOwner(0, info);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    Building factory = BuildingFactory.createByName("Basic factory");
    planet.setUnderConstruction(factory);

    assertEquals("The population of the planet should be one.", 1, planet.getTotalPopulation());
    assertEquals("The production time should be 15 turns.", "15 turns", planet.getProductionTime(factory));
    for (int i=0;i<5;i++) {
      // 5 turns to grow one population
      planet.updateOneTurn();
    }
    assertEquals("The population of the planet should be two.", 2, planet.getTotalPopulation());
    for (int i=0;i<10;i++) {
      // Total of 15 turns for basic factory
      planet.updateOneTurn();
    }
    assertEquals("Planet should have only one building.", 1, planet.getBuildingList().length);
    assertEquals("Planet should have a Basic factory.", "Basic factory", planet.getBuildingList()[0].getName());
    assertEquals(false,planet.isFullOfPopulation());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetTextGenerator() {
      Coordinate planetCoordinate = new Coordinate(10, 15);
      Planet planet = new Planet(planetCoordinate, "Earth", 1, false);
      planet.setGroundSize(10);
      planet.setAmountMetalInGround(6543);
      planet.setRadiationLevel(1);
      planet.setName("Earth");

      String tmp = planet.generateInfoText(false);
      assertEquals("Earth\nRadiation: 1\nSize: below average\n",tmp);
      tmp = planet.generateInfoText(true);
      assertEquals("Earth\nRadiation: 1\nSize: below average\nMetal: 6543\n",tmp);
      planet.setHomeWorldIndex(1);
      tmp = planet.generateInfoText(false);
      assertEquals("Earth\nRadiation: 1\nSize: below average\nHome world of\nMechions\n",tmp);
      tmp = planet.generateInfoText(true);
      assertEquals("Earth\nRadiation: 1\nSize: below average\nMetal: 6543\nHome world of\nMechions\n",tmp);
      PlayerInfo info = new PlayerInfo(SpaceRaceUtility.getRaceByName("Mechion"));
      info.setEmpireName("Mechion Great Empire");
      planet.setPlanetOwner(0, info);
      planet.setWorkers(0, 1);
      planet.setCulture(5);
      tmp = planet.generateInfoText(true);
      assertEquals("Earth\nRadiation: 1\nSize: below average\nMetal: 6543\n"
          + "Home world of\nMechions\nMechion Great Empire\nPopulation"
          + ": 1\nCulture: 5\n",tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetKillWorker() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    planet.setWorkers(Planet.CULTURE_ARTIST, 1);
    assertEquals(5, planet.getTotalPopulation());
    planet.killOneWorker();
    assertEquals(4, planet.getTotalPopulation());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRemoveBuilding() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Radiation well");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    assertEquals(1, planet.getNumberOfBuildings());
    planet.setRadiationLevel(5);
    planet.removeBuilding(building);
    assertEquals(0, planet.getNumberOfBuildings());
    assertEquals(6, planet.getRadiationLevel());
    planet.setGroundSize(7);
    Mockito.when(building.getName()).thenReturn("Test building");
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    assertEquals(7, planet.getNumberOfBuildings());
    planet.setCulture(40);
    planet.destroyOneBuilding();
    assertEquals(6, planet.getNumberOfBuildings());
    assertEquals(30, planet.getCulture());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreditProduction() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    planet.setPlanetOwner(0, info);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Tax center");
    Mockito.when(building.getProdCost()).thenReturn(10);
    Mockito.when(building.getCredBonus()).thenReturn(1);
    planet.addBuilding(building);
    Building building2 = Mockito.mock(Building.class);
    Mockito.when(building2.getName()).thenReturn("Radiation well");
    Mockito.when(building2.getProdCost()).thenReturn(10);
    Mockito.when(building2.getCredBonus()).thenReturn(0);
    planet.addBuilding(building2);
    assertEquals(2, planet.getNumberOfBuildings());
    planet.setRadiationLevel(5);
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
  }

}
