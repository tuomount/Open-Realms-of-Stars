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
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.espionage.Espionage;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.GameLengthState;
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
   * Create advanced factory with mockito
   * @return advanced factory building
   */
  private static Building createAdvancedFactory() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(0);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
    Mockito.when(building.getScanRange()).thenReturn(0);
    Mockito.when(building.getScanCloakingDetection()).thenReturn(0);
    Mockito.when(building.getType()).thenReturn(BuildingType.FACTORY);
    Mockito.when(building.getFactBonus()).thenReturn(2);
    Mockito.when(building.getMineBonus()).thenReturn(0);
    Mockito.when(building.getFarmBonus()).thenReturn(0);
    Mockito.when(building.getReseBonus()).thenReturn(0);
    Mockito.when(building.getCultBonus()).thenReturn(0);
    Mockito.when(building.getCredBonus()).thenReturn(0);
    Mockito.when(building.getRecycleBonus()).thenReturn(0);
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.0);
    Mockito.when(building.getMetalCost()).thenReturn(14);
    Mockito.when(building.getProdCost()).thenReturn(20);
    Mockito.when(building.getName()).thenReturn("Advanced factory");
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
    Mockito.when(building.getHappiness()).thenReturn(1);
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
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HEGEMONY);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(69,score);
    Mockito.when(planet.calculateHappiness()).thenReturn(80);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(64,score);
    Mockito.when(planet.calculateHappiness()).thenReturn(-80);
    score = PlanetHandling.scoreBuilding(building, planet, info,
        Attitude.LOGICAL);
    assertEquals(94,score);
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
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

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
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    Building building = PlanetHandling.getWorstBuilding(planet, info,
        Attitude.LOGICAL, null);
    assertEquals(farm, building);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWorstScoring2() {
    Building farm = createBasicFarm();
    Building factory = createBasicFactory();
    Building lab = createBasicLab();
    Building mine = createBasicMine();
    Building advancedFactory = createAdvancedFactory();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(farm.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);
    Building[] list = {farm, factory, lab, mine};
    Mockito.when(planet.getBuildingList()).thenReturn(list);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    Building building = PlanetHandling.getWorstBuilding(planet, info,
        Attitude.LOGICAL, advancedFactory);
    assertEquals(factory, building);
  }

  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    
    int score = PlanetHandling.scoreShip(ship, GameLengthState.START_GAME);
    assertEquals(49, score);
    score = PlanetHandling.scoreShip(ship, GameLengthState.EARLY_GAME);
    assertEquals(49, score);
    score = PlanetHandling.scoreShip(ship, GameLengthState.MIDDLE_GAME);
    assertEquals(65, score);
    score = PlanetHandling.scoreShip(ship, GameLengthState.LATE_GAME);
    assertEquals(81, score);
    score = PlanetHandling.scoreShip(ship, GameLengthState.END_GAME);
    assertEquals(97, score);
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
  public void testSpyShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(ship.getEspionageBonus()).thenReturn(1);
    Mockito.when(ship.isSpyShip()).thenReturn(true);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Espionage espionage = Mockito.mock(Espionage.class);
    EspionageList espionageList = Mockito.mock(EspionageList.class);
    Mockito.when(espionageList.getTotalBonus()).thenReturn(2);
    Mockito.when(espionage.getByIndex(Mockito.anyInt())).thenReturn(espionageList);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    Mockito.when(diplomacyList.getNumberOfMeetings()).thenReturn(1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEspionage()).thenReturn(espionage);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(9);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(8);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    int score = PlanetHandling.scoreSpyShip(20, ship, info, map,
        Attitude.BACKSTABBING);
    assertEquals(40, score);
    score = PlanetHandling.scoreSpyShip(20, ship, info, map,
        Attitude.AGGRESSIVE);
    assertEquals(35, score);
    score = PlanetHandling.scoreSpyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(30, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpyShipMissionCreation() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Espionage espionage = Mockito.mock(Espionage.class);
    Mockito.when(diplomacy.getLeastLikingWithLowEspionage(espionage)).thenReturn(5);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getEspionage()).thenReturn(espionage);
    StarMap map = Mockito.mock(StarMap.class);
    Planet target = Mockito.mock(Planet.class);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(target.getCoordinate()).thenReturn(coord);
    Mockito.when(target.getName()).thenReturn("Target planet I");
    Planet target2 = Mockito.mock(Planet.class);
    Mockito.when(target2.getCoordinate()).thenReturn(coord);
    Mockito.when(target2.getName()).thenReturn("Target planet II");
    Planet[] planets = {target};
    Planet[] planets2 = {target2};
    Planet[] emptyPlanets = new Planet[0];
    Mockito.when(map.getPlanetListSeenByOther(5, info)).thenReturn(planets);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(9);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(8);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(map.getPlanetListSeenByOther(0, info)).thenReturn(planets2);
    Mission mission = PlanetHandling.createSpyShipMission(info, map);
    assertEquals("Target planet I", mission.getTargetPlanet());
    assertEquals(MissionType.SPY_MISSION, mission.getType());
    Mockito.when(map.getPlanetListSeenByOther(5, info)).thenReturn(emptyPlanets);
    mission = PlanetHandling.createSpyShipMission(info, map);
    assertEquals("Target planet II", mission.getTargetPlanet());
    assertEquals(MissionType.SPY_MISSION, mission.getType());
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
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(missionList.getMission(MissionType.COLONIZE,
        MissionPhase.PLANNING)).thenReturn(mission);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.LATE_GAME);
    Mockito.when(map.getPlanetByCoordinate(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(planet);
    int score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(57, score);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.MERCHANTICAL);
    assertEquals(37, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.START_GAME);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(87, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.EARLY_GAME);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(77, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.MIDDLE_GAME);
    score = PlanetHandling.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(67, score);
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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
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
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    info.setGovernment(GovernmentType.HEGEMONY);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    info.setGovernment(GovernmentType.MECHANICAL_HORDE);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling6Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    info.setGovernment(GovernmentType.ENTERPRISE);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 6);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling7Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    info.setGovernment(GovernmentType.AI);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 7);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(-1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
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
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling4PopulationClan() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationClan() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationClanLab() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.addBuilding(BuildingFactory.createByName("Basic Lab"));
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationHegemony() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    info.setGovernment(GovernmentType.HEGEMONY);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
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

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6PopulationAllMiners() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 6);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 0);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(4, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population4Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(3, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population3Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(3, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population2Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(3, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling1Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
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
  public void testChiraloidHandling2PopulationWithoutLab() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling2PopulationWithLab() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    planet.addBuilding(BuildingFactory.createByName("Basic Lab"));
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getTotalPopulation());
    int workers = planet.getWorkers(Planet.PRODUCTION_WORKERS);
    int miners = planet.getWorkers(Planet.METAL_MINERS);
    int artists = planet.getWorkers(Planet.CULTURE_ARTIST);
    if (workers < 1 || workers > 2) {
      assertEquals(true, false);
    }
    if (miners < 0 || miners > 1) {
      assertEquals(true, false);
    }
    if (artists < 0 || artists > 1) {
      assertEquals(true, false);
    }
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(1);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(4, planet.getTotalPopulation());
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling4PopulationHighRad() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(4);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(4, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling4PopulationHighRadWithLab() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(4);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    planet.addBuilding(BuildingFactory.createByName("Basic Lab"));
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(4, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(1);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(5, planet.getTotalPopulation());
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(3, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling5PopulationHighRadWithLab() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(4);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    planet.addBuilding(BuildingFactory.createByName("Basic Lab"));
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(5, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(3, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling5PopulationHighRad() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setRadiationLevel(4);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(5, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling5PopulationHegemony() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.setGovernment(GovernmentType.HEGEMONY);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(5, planet.getTotalPopulation());
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling10Population() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setGroundSize(12);
    planet.setRadiationLevel(1);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 10);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(8, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling10PopulationMediumRad() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setGroundSize(12);
    planet.setRadiationLevel(5);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 10);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling10PopulationMediumRadWithLabFactory() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setGroundSize(12);
    planet.setRadiationLevel(5);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 10);
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    planet.addBuilding(BuildingFactory.createByName("Basic factory"));
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(3, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling10PopulationMediumRadWithLabMine() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CHIRALOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setGroundSize(12);
    planet.setRadiationLevel(5);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 10);
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    planet.setWorkers(Planet.FOOD_FARMERS, 4);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(6, planet.getTotalPopulation());
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(5, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCentaursHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CENTAURS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScauriansHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.SCAURIANS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling10PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setGroundSize(12);
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic factory"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 10);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(6, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(-2, planet.calculateHappiness());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling10PopulationKingdomFull() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS);
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setGroundSize(10);
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic factory"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 10);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(5, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(-1, planet.calculateHappiness());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationAllFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 6);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationAllFarmers2() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 6);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 7);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(9, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTeuthidaesHandling9PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.TEUTHIDAES);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setGroundSize(10);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 9);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(4, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(12, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling9PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setGroundSize(10);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 9);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling7PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setGroundSize(10);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 7);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling8PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setGroundSize(10);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTeuthidaesHandling10PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRace.TEUTHIDAES);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setGroundSize(10);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 10);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(12, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling6PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 6);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalPopulation());
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(8, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 7);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScaurianHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRace.SCAURIANS);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 7);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

}
