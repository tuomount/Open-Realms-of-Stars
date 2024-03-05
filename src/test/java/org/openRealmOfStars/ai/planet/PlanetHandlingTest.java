package org.openRealmOfStars.ai.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionList;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.espionage.Intelligence;
import org.openRealmOfStars.player.espionage.IntelligenceList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.scenario.StartingScenarioFactory;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapGenerator;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.enums.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;

import junit.framework.TestCase;

/**
 * Test for PlanetHandling class
 */

public class PlanetHandlingTest extends TestCase {

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
   * Create defense turret with mockito
   * @return defense turret building
   */
  private static Building createBarracks() {
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(50);
    Mockito.when(building.getDefenseDamage()).thenReturn(0);
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
    Mockito.when(building.getMaintenanceCost()).thenReturn(0.33);
    Mockito.when(building.getMetalCost()).thenReturn(7);
    Mockito.when(building.getProdCost()).thenReturn(14);
    Mockito.when(building.getHappiness()).thenReturn(1);
    Mockito.when(building.getFleetCapacityBonus()).thenReturn(1);
    Mockito.when(building.getName()).thenReturn("Barracks");
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
   * Create tax center with mockito
   * @return tax center building
   */
  private static Building createTaxCenter() {
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
    Mockito.when(building.getMetalCost()).thenReturn(5);
    Mockito.when(building.getProdCost()).thenReturn(15);
    Mockito.when(building.getName()).thenReturn("Tax center");
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
    Mockito.when(planet.getTotalPopulation()).thenReturn(2);
    Mockito.when(planet.getPopulationLimit()).thenReturn(12);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne(
        "HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(39,score);

    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.EXPANSIONIST, false);
    assertEquals(49,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("CENTAURS"));
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(49,score);

    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("MECHIONS"));
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(-1,score);

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasicScoring() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 0);
    info.setEmpireName("Human Kingdom");
    info.setGovernment(GovernmentType.KINGDOM);
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 1);
    info2.setEmpireName("Terran Federation");
    info2.setGovernment(GovernmentType.FEDERATION);

    GalaxyConfig config = new GalaxyConfig();
    config.setPlayerName(0, info.getEmpireName());
    config.setPlayerName(1, info2.getEmpireName());
    config.setMaxPlayers(2);
    PlayerList playerList = new PlayerList();
    playerList.addPlayer(info);
    playerList.addPlayer(info2);
    playerList.setCurrentPlayer(0);
    playerList.calculateInitialDiplomacyBonuses();
    StarMapGenerator generator = new StarMapGenerator();
    StarMap map = generator.generateStarMap(config, playerList);
    Planet planet  =map.getPlanetList().get(0);
    for (int i = 0; i < map.getPlanetList().size(); i++) {
      Planet iterator = map.getPlanetList().get(i);
      if (iterator.getPlanetPlayerInfo() == info) {
        planet = iterator;
      }
    }

    Construction[] constructions = planet.getProductionList();
    int[] scores = DefaultScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(39, scores[2]);
    assertEquals(99, scores[3]);
    assertEquals(99, scores[4]);
    assertEquals(-1, scores[6]);
    planet.addBuilding(createBasicMine());
    constructions = planet.getProductionList();
    scores = DefaultScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(39, scores[2]);
    assertEquals(79, scores[3]);
    assertEquals(99, scores[4]);
    assertEquals(-1, scores[6]);
    planet.addBuilding(createBasicFactory());
    constructions = planet.getProductionList();
    scores = DefaultScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(39, scores[2]);
    assertEquals(99, scores[3]);
    assertEquals(99, scores[4]);
    assertEquals(-1, scores[6]);
    planet.addBuilding(createBasicMine());
    constructions = planet.getProductionList();
    scores = DefaultScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(39, scores[2]);
    assertEquals(79, scores[3]);
    assertEquals(99, scores[4]);
    assertEquals(-1, scores[6]);
    planet.addBuilding(createBasicFactory());
    constructions = planet.getProductionList();
    scores = DefaultScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(39, scores[2]);
    assertEquals(99, scores[3]);
    assertEquals(59, scores[4]);
    assertEquals(-1, scores[6]);
/*    for (int i = 0; i < constructions.length; i++) {
      System.out.println(constructions[i].getName() + " - " + scores[i]);
    }*/

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasicHighScoring() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 0);
    info.setEmpireName("Human Kingdom");
    info.setGovernment(GovernmentType.KINGDOM);
    info.setStartingScenario(StartingScenarioFactory.create("EARTH"));
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 1);
    info2.setEmpireName("Terran Federation");
    info2.setGovernment(GovernmentType.FEDERATION);

    GalaxyConfig config = new GalaxyConfig();
    config.setPlayerName(0, info.getEmpireName());
    config.setPlayerName(1, info2.getEmpireName());
    config.setStartingScenario(0, StartingScenarioFactory.create("EARTH"));
    config.setStartingScenario(1, StartingScenarioFactory.create(
        "TEMPERATE_HUMID_SIZE12"));
    config.setMaxPlayers(2);
    PlayerList playerList = new PlayerList();
    playerList.addPlayer(info);
    playerList.addPlayer(info2);
    playerList.setCurrentPlayer(0);
    playerList.calculateInitialDiplomacyBonuses();
    StarMapGenerator generator = new StarMapGenerator();
    StarMap map = generator.generateStarMap(config, playerList);
   // Let's remove possibility to have scientific ruler
    info.getRuler().setJob(Job.DEAD);
    info.setRuler(null);
    Planet planet  =map.getPlanetList().get(0);
    for (int i = 0; i < map.getPlanetList().size(); i++) {
      Planet iterator = map.getPlanetList().get(i);
      if (iterator.getPlanetPlayerInfo() == info) {
        planet = iterator;
      }
    }
    planet.setPlanetaryEvent(PlanetaryEvent.NONE);

    Construction[] constructions = planet.getProductionList();
    int[] scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
/*    for (int i = 0; i < constructions.length; i++) {
      System.out.println(constructions[i].getName() + " - " + scores[i]);
    }*/
    assertEquals(7, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(119, scores[3]);
    assertEquals(139, scores[4]);
    assertEquals(-1, scores[6]);
    planet.addBuilding(createBasicFactory());
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab",
        1));
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
/*    for (int i = 0; i < constructions.length; i++) {
      System.out.println(constructions[i].getName() + " - " + scores[i]);
    }*/
    assertEquals(8, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(134, scores[3]);
    assertEquals(19, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(-1, scores[7]);
    planet.addBuilding(createBasicMine());
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
/*    for (int i = 0; i < constructions.length; i++) {
      System.out.println(constructions[i].getName() + " - " + scores[i]);
    }*/
    assertEquals(8, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(119, scores[3]);
    assertEquals(119, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(-1, scores[7]);
    planet.addBuilding(createBasicMine());
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(8, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(89, scores[3]);
    assertEquals(119, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(-1, scores[7]);
    planet.addBuilding(createBasicLab());
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(8, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(89, scores[3]);
    assertEquals(119, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(-1, scores[7]);
    planet.addBuilding(createBasicFactory());
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(8, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(109, scores[3]);
    assertEquals(69, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(-1, scores[7]);
    planet.addBuilding(createBasicMine());
    info.getTechList().addTech(TechFactory.createImprovementTech("Tax center",
        1));
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
    assertEquals(9, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(39, scores[3]);
    assertEquals(69, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(79, scores[6]);
    assertEquals(-1, scores[8]);
    planet.addBuilding(createTaxCenter());
    info.getTechList().addTech(TechFactory.createImprovementTech(
        "Advanced factory", 2));
    constructions = planet.getProductionList();
    scores = ChallengingScoring.scoreConstructions(constructions,
        planet, info, map, Attitude.LOGICAL, false);
/*    System.out.println(planet.getBuildingList().length + "/"
        + planet.getGroundSize());
    for (int i = 0; i < constructions.length; i++) {
      System.out.println(constructions[i].getName() + " - " + scores[i]);
    }*/
    assertEquals(9, scores.length);
    assertEquals(-1, scores[0]);
    assertEquals(-1, scores[1]);
    assertEquals(49, scores[2]);
    assertEquals(39, scores[3]);
    assertEquals(69, scores[4]);
    assertEquals(59, scores[5]);
    assertEquals(113, scores[6]);
    assertEquals(-1, scores[8]);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(39,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBarracksScoring() {
    Building building = createBarracks();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(76,score);
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, true);
    assertEquals(126,score);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(5);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(5);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(59,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicFactoryBuildingScoring2() {
    Building building = createBasicFactory();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(5);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(1);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(-1,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicFactoryBuildingScoring3() {
    Building building = createBasicFactory();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(1);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(1);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(99,score);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(59,score);
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.SCIENTIFIC, false);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(5);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(5);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(39,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicMineBuildingScoring2() {
    Building building = createBasicMine();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(1);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(5);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(-1,score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicMineBuildingScoring3() {
    Building building = createBasicMine();

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(building.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)).thenReturn(2);
    Mockito.when(planet.getTotalProduction(Planet.PRODUCTION_METAL)).thenReturn(2);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(79, score);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(39,score);
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.DIPLOMATIC, false);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    int score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.LOGICAL, false);
    assertEquals(79,score);
    score = DefaultScoring.scoreBuilding(building, planet, info,
        Attitude.MERCHANTICAL, false);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    Building building = PlanetHandling.getWorstBuilding(planet, info,
        Attitude.LOGICAL, null, false);
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
    Mockito.when(planet.getTotalPopulation()).thenReturn(2);
    Mockito.when(planet.getPopulationLimit()).thenReturn(12);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.howManyBuildings(farm.getName())).thenReturn(0);
    Mockito.when(planet.exceedRadiation()).thenReturn(false);
    Building[] list = {farm, factory, lab, mine, factory, mine, factory, mine};
    Mockito.when(planet.getBuildingList()).thenReturn(list);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);

    Building building = PlanetHandling.getWorstBuilding(planet, info,
        Attitude.LOGICAL, advancedFactory, false);
    assertEquals(factory, building);
  }


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipScoring() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.NORMAL);
    Mockito.when(ship.getHull()).thenReturn(hull);
    int score = DefaultScoring.scoreShip(ship, GameLengthState.START_GAME,
        planet);
    assertEquals(49, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.EARLY_GAME, planet);
    assertEquals(49, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.MIDDLE_GAME, planet);
    assertEquals(65, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.LATE_GAME, planet);
    assertEquals(81, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.END_GAME, planet);
    assertEquals(97, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalScoring() {
    Ship ship = Mockito.mock(Ship.class);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.ORBITAL);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Mockito.when(hull.getName()).thenReturn("Medium orbital");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    int score = DefaultScoring.scoreShip(ship, GameLengthState.START_GAME,
        planet);
    assertEquals(109, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.EARLY_GAME, planet);
    assertEquals(109, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.MIDDLE_GAME, planet);
    assertEquals(125, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.LATE_GAME, planet);
    assertEquals(141, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.END_GAME, planet);
    assertEquals(157, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalScoring2() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Medium orbital");
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.ORBITAL);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Mockito.when(hull.getName()).thenReturn("Medium orbital");
    ShipHull hull2 = Mockito.mock(ShipHull.class);
    Mockito.when(hull2.getHullType()).thenReturn(ShipHullType.ORBITAL);
    Mockito.when(hull2.getSize()).thenReturn(ShipSize.LARGE);
    Mockito.when(hull2.getName()).thenReturn("Large orbital");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Ship ship2 = Mockito.mock(Ship.class);
    Mockito.when(ship2.getName()).thenReturn("Large orbital");
    Mockito.when(ship2.getTotalMilitaryPower()).thenReturn(32);
    Mockito.when(ship2.getHull()).thenReturn(hull2);
    Mockito.when(ship2.getMetalCost()).thenReturn(14);
    Mockito.when(ship2.getProdCost()).thenReturn(22);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Mockito.when(planet.getOrbital()).thenReturn(ship2);
    int score = DefaultScoring.scoreShip(ship, GameLengthState.START_GAME,
        planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.EARLY_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.MIDDLE_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.LATE_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.END_GAME, planet);
    assertEquals(0, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalScoring3() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Medium orbital");
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.ORBITAL);
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Mockito.when(hull.getName()).thenReturn("Medium orbital");
    ShipHull hull2 = Mockito.mock(ShipHull.class);
    Mockito.when(hull2.getHullType()).thenReturn(ShipHullType.ORBITAL);
    Mockito.when(hull2.getSize()).thenReturn(ShipSize.MEDIUM);
    Mockito.when(hull2.getName()).thenReturn("Medium orbital");
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(16);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getMetalCost()).thenReturn(14);
    Mockito.when(ship.getProdCost()).thenReturn(22);
    Planet planet = Mockito.mock(Planet.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Ship ship2 = Mockito.mock(Ship.class);
    Mockito.when(ship2.getName()).thenReturn("Medium orbital Mk2");
    Mockito.when(ship2.getTotalMilitaryPower()).thenReturn(32);
    Mockito.when(ship2.getHull()).thenReturn(hull2);
    Mockito.when(ship2.getMetalCost()).thenReturn(14);
    Mockito.when(ship2.getProdCost()).thenReturn(22);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Mockito.when(planet.getOrbital()).thenReturn(ship2);
    int score = DefaultScoring.scoreShip(ship, GameLengthState.START_GAME,
        planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.EARLY_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.MIDDLE_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.LATE_GAME, planet);
    assertEquals(0, score);
    score = DefaultScoring.scoreShip(ship, GameLengthState.END_GAME, planet);
    assertEquals(0, score);
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

    int score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
        Attitude.MERCHANTICAL);
    assertEquals(40, score);
    score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
        Attitude.DIPLOMATIC);
    assertEquals(35, score);
    score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
        Attitude.PEACEFUL);
    assertEquals(30, score);
    score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
        Attitude.SCIENTIFIC);
    assertEquals(25, score);
    score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
        Attitude.AGGRESSIVE);
    assertEquals(10, score);
    Mockito.when(missionList.getMission(MissionType.TRADE_FLEET,
        MissionPhase.PLANNING)).thenReturn(null);
    score = DefaultScoring.scoreTradeShip(20, ship, planet, info, map,
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
    Intelligence intelligence = Mockito.mock(Intelligence.class);
    IntelligenceList intelligenceList = Mockito.mock(IntelligenceList.class);
    Mockito.when(intelligenceList.getTotalBonus()).thenReturn(2);
    Mockito.when(intelligence.getByIndex(Mockito.anyInt())).thenReturn(intelligenceList);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    Mockito.when(diplomacyList.getNumberOfMeetings()).thenReturn(1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(intelligence);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(9);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(8);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    int score = DefaultScoring.scoreSpyShip(20, ship, info, map,
        Attitude.BACKSTABBING);
    assertEquals(40, score);
    score = DefaultScoring.scoreSpyShip(20, ship, info, map,
        Attitude.AGGRESSIVE);
    assertEquals(35, score);
    score = DefaultScoring.scoreSpyShip(20, ship, info, map,
        Attitude.EXPANSIONIST);
    assertEquals(30, score);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpyShipMissionCreation() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Intelligence espionage = Mockito.mock(Intelligence.class);
    Mockito.when(diplomacy.getLeastLikingWithLowEspionage(espionage)).thenReturn(5);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
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
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planet.getRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Coordinate planetCoord = Mockito.mock(Coordinate.class);
    Mockito.when(planet.getCoordinate()).thenReturn(planetCoord);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(planet.evaluatePlanetValue(info, planetCoord, 1))
        .thenReturn(25);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    MissionList missionList = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.COLONIZE);
    Mockito.when(mission.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    missionList.add(mission);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.LATE_GAME);
    Mockito.when(map.getPlanetByCoordinate(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(planet);
    int score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST, planet);
    assertEquals(63, score);
    score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.MERCHANTICAL, planet);
    assertEquals(43, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.START_GAME);
    score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST, planet);
    assertEquals(93, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.EARLY_GAME);
    score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST, planet);
    assertEquals(83, score);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.MIDDLE_GAME);
    score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.EXPANSIONIST, planet);
    assertEquals(73, score);
    missionList.remove(mission);
    score = DefaultScoring.scoreColonyShip(20, ship, info, map,
        Attitude.AGGRESSIVE, planet);
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
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Mockito.when(info.getTotalCredits()).thenReturn(500);
    Mockito.when(info.getMsgList()).thenReturn(list);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.DEMOCRACY);
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Planet planet = new Planet(new Coordinate(6, 6), "Test planet", 1, false);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
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
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("MECHIONS"));
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
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling2Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setAiDifficulty(AiDifficulty.WEAK);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setAiDifficulty(AiDifficulty.NORMAL);
    planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setAiDifficulty(AiDifficulty.CHALLENGING);
    planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info, 6);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setGovernment(GovernmentType.HEGEMONY);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setGovernment(GovernmentType.HORDE);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling6Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setGovernment(GovernmentType.ENTERPRISE);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 6);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling7Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    info.setGovernment(GovernmentType.AI);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 7);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
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
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 8);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(8, planet.getTotalPopulation());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMechionHandling8Population2() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.FOOD_FARMERS, 5);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(8, planet.getTotalPopulation());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling1Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling2Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling3PopulationLateGame() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info, 0,
        GameLengthState.LATE_GAME);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling4Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling4PopulationClan() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.NEST);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationClan() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.MARINE);
    planet.setTemperatureType(TemperatureType.TROPICAL);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationClanLab() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.CLAN);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling5PopulationHegemony() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    info.setGovernment(GovernmentType.HEGEMONY);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 5);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6PopulationAllMiners() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 6);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 0);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population4Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population3Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 3);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHomarianHandling6Population2Miners() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HOMARIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(2, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling1Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 1);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling2Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 2);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling3Population() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 3);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling3PopulationWithFarm() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 3);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling3PopulationWithFarmAndLab() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 3);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling3PopulationWithFactoryMineLab() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 3);
    planet.addBuilding(BuildingFactory.createByName("Basic factory"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAlteirianHandling4PopulationWithFarmAndLab() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("ALTEIRIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.colonizeWithOrbital();
    planet.setWorkers(Planet.METAL_MINERS, 4);
    planet.addBuilding(BuildingFactory.createByName("Advanced farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling2PopulationWithLab() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("CHIRALOIDS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 2);
    planet.addBuilding(BuildingFactory.createByName("Basic lab"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(0, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testChiraloidHandling4PopulationHighRad() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("CHIRALOIDS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.LOW_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 4);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(4, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLithorianHandling3PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("LITHORIANS"));
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLithorianHandling9PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("LITHORIANS"));
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.BARREN);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 9);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(9, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(4, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScauriansHandling2PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("SCAURIANS"));
    info.setGovernment(GovernmentType.EMPIRE);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.addBuilding(BuildingFactory.createByName("Space port"));
    planet.addBuilding(BuildingFactory.createByName("Tax center"));
    planet.addBuilding(BuildingFactory.createByName("Advanced laboratory"));
    planet.addBuilding(BuildingFactory.createByName("Advanced laboratory"));
    planet.addBuilding(BuildingFactory.createByName("Planetary defense turret Mk1"));
    planet.addBuilding(BuildingFactory.createByName("Barracks"));
    planet.addBuilding(BuildingFactory.createByName("Market center"));
    planet.addBuilding(BuildingFactory.createByName("Trade center"));
    planet.addBuilding(BuildingFactory.createByName("Advanced factory"));
    planet.addBuilding(BuildingFactory.createByName("Planetary defense turret Mk1"));
    planet.addBuilding(BuildingFactory.createByName("Manufacturing center"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(8, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCentaursHandling3PopulationKingdomWithFarm() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("CENTAURS"));
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 3);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getTotalPopulation());
    assertEquals(1, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(0, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling10PopulationKingdom() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MOTHOIDS"));
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(-1, planet.calculateHappiness());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling10PopulationKingdomFull() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MOTHOIDS"));
    info.setGovernment(GovernmentType.KINGDOM);
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(10, planet.getTotalPopulation());
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(4, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(1, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(-1, planet.calculateHappiness());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationAllFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 6);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationAllFarmersLateGame() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.setPlanetOwner(1, info);
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.addBuilding(BuildingFactory.createByName("Basic farm"));
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 6);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0,
        GameLengthState.LATE_GAME);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(1, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(7, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling6PopulationAllFarmers2() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(0, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    assertEquals(9, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGreyansHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(10, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTeuthidaesHandling9PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("TEUTHIDAES"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
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
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 9);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(4, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(13, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling9PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
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
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 9);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(2, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(2, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(12, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling7PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
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
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 7);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(12, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumansHandling8PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
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
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(1, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(12, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTeuthidaesHandling10PopulationFullFarmers() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("TEUTHIDAES"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(10);
    planet.generateGravityBasedOnSize();
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
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.FOOD_FARMERS, 10);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(4, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(4, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(2, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(5, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(13, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling6PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MOTHOIDS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(2, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(6, planet.getTotalPopulation());
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(9, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMothoidsHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("MOTHOIDS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScaurianHandling7PopulationFullFarms() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("SCAURIANS"));
    Planet planet = new Planet(new Coordinate(6, 7), "Planet Test", 1, false);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
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
    PlanetHandling.handlePlanetPopulation(planet, info, 0);
    assertEquals(3, planet.getWorkers(Planet.PRODUCTION_WORKERS));
    assertEquals(0, planet.getWorkers(Planet.METAL_MINERS));
    assertEquals(0, planet.getWorkers(Planet.FOOD_FARMERS));
    assertEquals(3, planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    assertEquals(1, planet.getWorkers(Planet.CULTURE_ARTIST));
    assertEquals(7, planet.getTotalPopulation());
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    assertEquals(11, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_METAL));
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

}
