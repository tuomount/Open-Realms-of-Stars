package org.openRealmOfStars.starMap.randomEvent;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.vote.Votes;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2019, 2020 Tuomo Untinen
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
* JUnits for Random Event utility.
*
*/
public class RandomEventUtilityTest {

  private StarMap generateMapWithPlayer(final SpaceRace race) {
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

    PlayerInfo player2 = new PlayerInfo(SpaceRace.GREYANS, 2, 1);
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
    Planet planet = Mockito.mock(Planet.class);
    Sun sun = Mockito.mock(Sun.class);
    Mockito.when(sun.getCenterCoordinate()).thenReturn(new Coordinate(3, 3));
    Mockito.when(map.locateSolarSystem(0, 0)).thenReturn(sun);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(player2);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(0, 0));
    Mockito.when(planet.getRadiationLevel()).thenReturn(2);
    Mockito.when(planet.getTotalPopulation()).thenReturn(1);
    Mockito.when(planet.getOrderNumber()).thenReturn(1);
    Building[] buildingList = new Building[2];
    Building build = Mockito.mock(Building.class);
    Mockito.when(build.getName()).thenReturn("Test building");
    buildingList[0] = build;
    buildingList[1] = build;
    Mockito.when(planet.getBuildingList()).thenReturn(buildingList);
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
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.getTechList().setTechResearchPoints(TechType.Combat, 20);
    info.getTechList().setTechResearchPoints(TechType.Defense, 20);
    info.getTechList().setTechResearchPoints(TechType.Hulls, 20);
    info.getTechList().setTechResearchPoints(TechType.Propulsion, 20);
    info.getTechList().setTechResearchPoints(TechType.Improvements, 20);
    info.getTechList().setTechResearchPoints(TechType.Electrics, 20);
    RandomEvent event = new RandomEvent(BadRandomType.MASSIVE_DATA_LOST, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMassiveDataLost(event);
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
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    RandomEvent event = new RandomEvent(GoodRandomType.TECHNICAL_BREAKTHROUGH,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleTechnicalBreakThrough(event);
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
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.METEOR_HIT, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMeteorHit(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMissedMeteoroid() {
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(GoodRandomType.MISSED_METEOROID, info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMissedMeteoroid(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testVirusOutbreak() {
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.DEADLY_VIRUS_OUTBREAK,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleDeadlyVirusOutbreak(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
    assertEquals(1, event.getPlanet().getTotalPopulation());
    info = starMap.getPlayerByIndex(1);
    event = new RandomEvent(BadRandomType.DEADLY_VIRUS_OUTBREAK,
        info);
    RandomEventUtility.handleDeadlyVirusOutbreak(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCorruptionScandal() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    RandomEvent event = new RandomEvent(BadRandomType.CORRUPTION_SCANDAL,
        info);
    assertEquals("", event.getText());
    info.setTotalCredits(32);
    RandomEventUtility.handleCorruptionScandal(event);
    assertEquals(16, info.getTotalCredits());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMysteriousSignal() {
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(0);
    RandomEvent event = new RandomEvent(GoodRandomType.MYSTERIOUS_SIGNAL,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleMysteriousSignal(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSolarActivityDimished() {
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(GoodRandomType.SOLAR_ACTIVITY_DIMISHED,
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
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.SOLAR_ACTIVITY_INCREASE,
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
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(GoodRandomType.DESERTED_SHIP,
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
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.CATASTROPHIC_ACCIDENT,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleCatastrophicAccident(event, starMap);
    assertNotEquals(null, event.getPlanet());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAggresiveWildLife() {
    StarMap starMap = generateMapWithPlayer(SpaceRace.HUMAN);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.AGGRESSIVE_WILD_LIFE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleAggressiveWildLife(event, starMap);
    assertNotEquals(null, event.getPlanet());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGoodClimateChange() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    planet.setPlanetaryEvent(PlanetaryEvent.LUSH_VEGETATION);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    RandomEvent event = new RandomEvent(GoodRandomType.CLIMATE_CHANGE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleGoodClimateChange(event, starMap);
    assertNotEquals("", event.getText());
    assertEquals(planet, event.getPlanet());
    assertEquals(PlanetaryEvent.PARADISE, event.getPlanet().getPlanetaryEvent());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBadClimateChange() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    planet.setPlanetaryEvent(PlanetaryEvent.LUSH_VEGETATION);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    RandomEvent event = new RandomEvent(BadRandomType.CLIMATE_CHANGE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleBadClimateChange(event, starMap);
    assertNotEquals("", event.getText());
    assertEquals(planet, event.getPlanet());
    assertEquals(PlanetaryEvent.NONE, event.getPlanet().getPlanetaryEvent());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLostTreasure() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.setTotalCredits(9);
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    RandomEvent event = new RandomEvent(GoodRandomType.LOST_TREASURE_FOUND,
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
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    PlayerInfo board = new PlayerInfo(SpaceRace.SPACE_PIRATE);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(40);
    Mockito.when(fleet.getName()).thenReturn("Test fleet");
    Coordinate coord = new Coordinate(5,6);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    info.getFleets().add(fleet);
    RandomEvent event = new RandomEvent(BadRandomType.MUTINY,
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
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    PlayerInfo board = new PlayerInfo(SpaceRace.SPACE_PIRATE);
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setPlanetOwner(0, info);
    StarMap starMap = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    Mockito.when(starMap.getPlanetList()).thenReturn(planets);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getBoardPlayer()).thenReturn(board);
    Mockito.when(starMap.getPlayerList()).thenReturn(playerList);
    RandomEvent event = new RandomEvent(BadRandomType.RAIDERS,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleRaiders(event, starMap);
    assertNotEquals("", event.getText());
    assertEquals(planet, event.getPlanet());
  }

}
