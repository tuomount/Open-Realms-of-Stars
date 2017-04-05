package org.openRealmOfStars.AI.PlanetHandling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;

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
 * Test for PlanetHandling class
 */

public class PlanetHandlingTest {

  /**
   * Create basic farm with mockito
   * @return basic farm building
   */
  private static Building createBasicFarm() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.FARM);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(1);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Basic farm");
    return building;
  }

  /**
   * Create basic factory with mockito
   * @return basic factory building
   */
  private static Building createBasicFactory() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.FACTORY);
    Mockito.when(building.getFactBonus()).thenReturn(1);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Basic factory");
    return building;
  }

  /**
   * Create defense turret with mockito
   * @return defense turret building
   */
  private static Building createDefenseTurret() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(3);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.MILITARY);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.5);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Defense turret");
    return building;
  }

  /**
   * Create basic lab with mockito
   * @return basic lab building
   */
  private static Building createBasicLab() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.RESEARCH);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(1);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Basic lab");
    return building;
  }

  /**
   * Create basic mine with mockito
   * @return basic mine building
   */
  private static Building createBasicMine() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.MINE);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(1);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Basic mine");
    return building;
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicFarmBuildingScoring() {
    Building building = createBasicFarm();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(39,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(49,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(-1,score);

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDefenseTurretScoring() {
    Building building = createDefenseTurret();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(39,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(54,score);


  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicFactoryBuildingScoring() {
    Building building = createBasicFactory();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(59,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicLabBuildingScoring() {
    Building building = createBasicLab();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(59,score);
    
    Mockito.when(info.getRace()).thenReturn(SpaceRace.GREYANS);
    score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(74,score);

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicMineBuildingScoring() {
    Building building = createBasicMine();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info);
    assertEquals(40,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWorstScoring() {
    Building farm = createBasicFarm();
    Building factory = createBasicFactory();
    Building lab = createBasicLab();
    Building mine = createBasicMine();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(farm.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);
    Building[] list = {farm, factory, lab, mine};
    Mockito.when(planet.getBuildingList()).thenReturn(list);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    Building building = PlanetHandling.getWorstBuilding(planet, info);
    assertEquals(farm,building);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    
    int score = PlanetHandling.scoreShip(ship);
    assertEquals(49, score);
  }

}
