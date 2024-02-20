package org.openRealmOfStars.starMap.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2021 Tuomo Untinen
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

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.starMap.vote.Votes;


/**
* JUnits for Random Event utility.
*
*/
public class RandomEventUtilityTest {

  private static StarMap generateMapWithPlayer(final SpaceRace race) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    PlayerInfo player1 = new PlayerInfo(race,2,0);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    player1.setTotalCredits(15);
    player1.initMapData(5, 5);

    PlayerInfo player2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"), 2, 1);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    player2.setTotalCredits(10);
    player2.initMapData(5, 5);
    player2.setSectorVisibility(0, 0, PlayerInfo.VISIBLE);

    NewsCorpData newsData = Mockito.mock(NewsCorpData.class);
    Mockito.when(newsData.getMilitaryDifference(0, 1)).thenReturn(50);
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> planetList = new ArrayList<>();
    Planet planet = new Planet(new Coordinate(0, 0), "Planet I", 1, false);
    Sun sun = Mockito.mock(Sun.class);
    Mockito.when(map.locateSolarSystem(0, 0)).thenReturn(sun);
    planet.setRadiationLevel(RadiationType.LOW_RADIATION);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setWaterLevel(WaterLevelType.HUMID);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.generateWorldType();
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    planet.setPlanetOwner(1, player2);
    Leader leader1 = LeaderUtility.createLeader(player1, planet,
        LeaderUtility.LEVEL_START_RULER);
    player1.setRuler(leader1);
    player1.getLeaderPool().add(leader1);
    Leader leader2 = LeaderUtility.createLeader(player2, planet,
        LeaderUtility.LEVEL_START_RULER);
    player2.setRuler(leader2);
    player2.getLeaderPool().add(leader2);
    planet.addBuilding(BuildingFactory.createByName("Basic factory"));
    planet.addBuilding(BuildingFactory.createByName("Basic mine"));
    planetList.add(planet);
    Mockito.when(map.getPlanetList()).thenReturn(planetList);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsData);
    Mockito.when(map.getMilitaryDifference(0, 1)).thenReturn(50);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);
    Mockito.when(map.getGameLengthState()).thenReturn(GameLengthState.MIDDLE_GAME);
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    return map;
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMassiveDataLost() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    info.getTechList().setTechResearchPoints(TechType.Combat, 20);
    info.getTechList().setTechResearchPoints(TechType.Defense, 20);
    info.getTechList().setTechResearchPoints(TechType.Hulls, 20);
    info.getTechList().setTechResearchPoints(TechType.Propulsion, 20);
    info.getTechList().setTechResearchPoints(TechType.Improvements, 20);
    info.getTechList().setTechResearchPoints(TechType.Electrics, 20);
    RandomEvent event = new RandomEvent(RandomEventType.MASSIVE_DATA_LOST, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMassiveDataLost(event, starMap);
    boolean found = false;
    for (TechType type : TechType.values()) {
      if (info.getTechList().getTechResearchPoints(type) == 0) {
        found = true;
      }
    }
    assertEquals(true, found);
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTechBreakThrough() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    RandomEvent event = new RandomEvent(RandomEventType.TECHNICAL_BREAKTHROUGH,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleTechnicalBreakThrough(event, starMap);
    boolean found = false;
    for (TechType type : TechType.values()) {
      if (info.getTechList().getTechResearchPoints(type) > 10) {
        found = true;
      }
    }
    assertEquals(true, found);
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMeteorHit() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.METEOR_HIT, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMeteorHit(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMissedMeteoroid() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.MISSED_METEOROID, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMissedMeteoroid(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testVirusOutbreak() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.DEADLY_VIRUS_OUTBREAK,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleDeadlyVirusOutbreak(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
    assertEquals(true, event.isNewsWorthy());
    assertEquals(1, event.getPlanet().getTotalPopulation());
    info = starMap.getPlayerByIndex(1);
    event = new RandomEvent(RandomEventType.DEADLY_VIRUS_OUTBREAK,
        info);
    RandomEventUtility.handleDeadlyVirusOutbreak(event, starMap);
    assertEquals("", event.getText());
    assertEquals(null, event.getPlanet());
    assertEquals(false, event.isNewsWorthy());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCorruptionScandal() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    RandomEvent event = new RandomEvent(RandomEventType.CORRUPTION_SCANDAL,
        info);
    assertEquals("", event.getText());
    info.setTotalCredits(32);
    RandomEventUtility.handleCorruptionScandal(event, starMap);
    assertEquals(16, info.getTotalCredits());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMysteriousSignal() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(0);
    RandomEvent event = new RandomEvent(RandomEventType.MYSTERIOUS_SIGNAL,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMysteriousSignal(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSolarActivityDimished() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.SOLAR_ACTIVITY_DIMISHED,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleSolarActivityDecreased(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
    assertEquals("image(solar no flares)", event.getImageInstructions());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSolarActivityIncreased() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.SOLAR_ACTIVITY_INCREASE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleSolarActivityIncreased(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
    assertEquals("image(solar flares)", event.getImageInstructions());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDesertedShip() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.DESERTED_SHIP,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleDesertedShip(event, starMap);
    assertNotEquals(null, event.getPlanet());
    assertNotEquals(null, event.getFleet());
    assertEquals("Alien vessel", event.getFleet().getFirstShip().getName());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCatastrohicAccident() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.CATASTROPHIC_ACCIDENT,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleCatastrophicAccident(event, starMap);
    assertNotEquals(null, event.getPlanet());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRulerStress() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.RULER_STRESS,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleRulerStress(event, starMap);
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLeaderLevel() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.LEADER_LEVEL,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleLeaderLevel(event);
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAggresiveWildLife() {
    StarMap starMap = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(RandomEventType.AGGRESSIVE_WILD_LIFE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleAggressiveWildLife(event, starMap);
    assertNotEquals(null, event.getPlanet());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLostTreasure() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    info.setTotalCredits(9);
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    RandomEvent event = new RandomEvent(RandomEventType.LOST_TREASURE_FOUND,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleLostTreasure(event, starMap);
    assertNotEquals("", event.getText());
    assertEquals(planet, event.getPlanet());
    assertEquals(true, info.getTotalCredits() > 10);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMutiny() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    PlayerInfo board = new PlayerInfo(SpaceRaceFactory.createOne("SPACEPIRATES"));
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(40);
    Mockito.when(fleet.getName()).thenReturn("Test fleet");
    Coordinate coord = new Coordinate(5,6);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    info.getFleets().add(fleet);
    RandomEvent event = new RandomEvent(RandomEventType.MUTINY,
        info);
    assertEquals("", event.getText());
    assertEquals(1, info.getFleets().getNumberOfFleets());
    assertEquals(0, board.getFleets().getNumberOfFleets());
    RandomEventUtility.handleMutiny(event, board);
    assertNotEquals("", event.getText());
    assertEquals(fleet, event.getFleet());
    assertEquals(0, info.getFleets().getNumberOfFleets());
    assertEquals(1, board.getFleets().getNumberOfFleets());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRaiders() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"));
    PlayerInfo board = new PlayerInfo(SpaceRaceFactory.createOne("SPACEPIRATES"));
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getSpacePiratePlayer()).thenReturn(board);
    Mockito.when(starMap.getPlayerList()).thenReturn(playerList);
    RandomEvent event = new RandomEvent(RandomEventType.RAIDERS,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleRaiders(event, starMap);
    assertNotEquals("", event.getText());
    assertEquals(planet, event.getPlanet());
  }

}
