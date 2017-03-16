package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
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
            + ": 1\nCulture: 5",tmp);
    }

}
