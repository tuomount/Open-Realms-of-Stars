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
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetaryEvent;
import org.openRealmOfStars.utilities.repository.GameRepository;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.DEADLY_VIRUS_OUTBREAK,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleDeadlyVirusOutbreak(event, starMap);
    assertNotEquals("", event.getText());
    assertNotEquals(null, event.getPlanet());
    assertEquals(1, event.getPlanet().getTotalPopulation());
    info = starMap.getPlayerByIndex(2);
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
    PlayerInfo info = starMap.getPlayerByIndex(1);
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(GoodRandomType.SOLAR_ACTIVITY_DIMISHED,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleSolarActivityDecreased(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSolarActivityIncreased() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
    PlayerInfo info = starMap.getPlayerByIndex(1);
    RandomEvent event = new RandomEvent(BadRandomType.SOLAR_ACTIVITY_INCREASE,
        info);
    assertEquals("", event.getText());
    RandomEventUtility.handleSolarActivityIncreased(event, starMap);
    assertNotEquals(null, event.getSun());
    assertNotEquals("", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDesertedShip() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
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
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
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
