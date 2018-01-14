package org.openRealmOfStars.AI.PlanetHandling;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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

  /**
   * Create culture building with mockito
   * @return basic culture building
   */
  private static Building createBasicCulture() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.CULTURE);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(1);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Culture building");
    return building;
  }

  /**
   * Create credit building with mockito
   * @return basic credit building
   */
  private static Building createBasicCredit() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.CREDIT);
    Mockito.when(building.getFactBonus()).thenReturn(0);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(1);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getName()).thenReturn("Credit building");
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

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(39,score);

    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.EXPANSIONIST);
    assertEquals(49,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(49,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
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

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(39,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(54,score);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.MILITARISTIC);
    assertEquals(69,score);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.AGGRESSIVE);
    assertEquals(64,score);


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

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
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

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(59,score);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.SCIENTIFIC);
    assertEquals(74,score);
    
    Mockito.when(info.getRace()).thenReturn(SpaceRace.GREYANS);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
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

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(40,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicCultureBuildingScoring() {
    Building building = createBasicCulture();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(39,score);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.DIPLOMATIC);
    assertEquals(54,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicCreditBuildingScoring() {
    Building building = createBasicCredit();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);

    int score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(79,score);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.MERCHANTICAL);
    assertEquals(94,score);
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

    Building building = PlanetHandling.getWorstBuilding(planet, info,
        Attitude.LOGICAL);
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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.isTradeShip()).thenReturn(true);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(missionList.getMission(MissionType.TRADE_FLEET,
        MissionPhase.PLANNING)).thenReturn(mission);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    StarMap map = Mockito.mock(StarMap.class);
    
    int score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.MERCHANTICAL);
    assertEquals(40, score);
    score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.DIPLOMATIC);
    assertEquals(35, score);
    score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.PEACEFUL);
    assertEquals(30, score);
    score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.SCIENTIFIC);
    assertEquals(25, score);
    score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.AGGRESSIVE);
    assertEquals(10, score);
    Mockito.when(missionList.getMission(MissionType.TRADE_FLEET,
        MissionPhase.PLANNING)).thenReturn(null);
    score = PlanetHandling.scoreTradeShip(20, ship, planet, info, map,
        Attitude.MERCHANTICAL);
    assertEquals(-1, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testColonyShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.isTradeShip()).thenReturn(false);
    Mockito.when(ship.isColonyModule()).thenReturn(true);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getGroundSize()).thenReturn(12);
    Mockito.when(planet.getRadiationLevel()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(missionList.getMission(MissionType.COLONIZE,
        MissionPhase.PLANNING)).thenReturn(mission);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlanetByCoordinate(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(planet);
    int score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(57, score);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.MERCHANTICAL);
    assertEquals(37, score);
    Mockito.when(missionList.getMission(MissionType.COLONIZE,
        MissionPhase.PLANNING)).thenReturn(null);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.AGGRESSIVE);
    assertEquals(-1, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreditRushing() {
    Building building = createBasicFarm();
    MessageList list = Mockito.mock(MessageList.class);
    ArrayList<Message> fullList = new ArrayList<>();
    Mockito.when(list.getFullList()).thenReturn(fullList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getTotalCredits()).thenReturn(500);
    Mockito.when(info.getMsgList()).thenReturn(list);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Planet planet = new Planet(new Coordinate(6, 6), "Test planet", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setUnderConstruction(building);
    boolean rushed = false;
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info);
    int loop = 10000;
    while (loop > 0) {
      loop--;
      PlanetHandling.handlePlanet(map, planet, 1);
      int buildingTime = planet.getProductionTime(building);
      if (buildingTime == 1) {
        rushed = true;
        loop = 0;
      }
    }
    assertEquals(true, rushed);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPopulationRushing() {
    Building building = createBasicFarm();
    MessageList list = Mockito.mock(MessageList.class);
    ArrayList<Message> fullList = new ArrayList<>();
    Mockito.when(list.getFullList()).thenReturn(fullList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    Mockito.when(info.getTotalCredits()).thenReturn(500);
    Mockito.when(info.getMsgList()).thenReturn(list);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Planet planet = new Planet(new Coordinate(6, 6), "Test planet", 1, false);
    planet.setGroundSize(8);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, planet.getGroundSize());
    planet.setPlanetOwner(1, info);
    planet.setUnderConstruction(building);
    boolean rushed = false;
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info);
    int loop = 10000;
    while (loop > 0) {
      loop--;
      PlanetHandling.handlePlanet(map, planet, 1);
      int buildingTime = planet.getProductionTime(building);
      if (buildingTime == 1) {
        rushed = true;
        loop = 0;
      }
    }
    assertEquals(true, rushed);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling1Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling2Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling6Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 6);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling7Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 7);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling8Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 8);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling1Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling2Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(3, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

}
