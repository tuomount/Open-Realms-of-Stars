package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.espionage.Espionage;
import org.openRealmOfStars.player.espionage.EspionageBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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
 * Tests for StarMap
 *
 */
public class StarMapTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapCreate() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);
    Mockito.when(config.getScoringVictoryTurns()).thenReturn(400);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);

    StarMap map = new StarMap(config, players);
    assertEquals(400, map.getScoreVictoryTurn());
    assertEquals(50, map.getMaxX());
    assertEquals(50, map.getMaxY());
    assertEquals(true, map.isValidCoordinate(25, 25));
    assertEquals(false, map.isValidCoordinate(-1, 25));
    assertEquals(false, map.isValidCoordinate(25, -1));
    assertEquals(false, map.isValidCoordinate(512, 25));
    assertEquals(false, map.isValidCoordinate(252, 512));
    map.setScoreVictoryTurn(0);
    assertEquals(200, map.getScoreVictoryTurn());
    map.setScoreVictoryTurn(1999);
    assertEquals(1000, map.getScoreVictoryTurn());
    map.setScoreVictoryTurn(600);
    assertEquals(600, map.getScoreVictoryTurn());
    assertEquals(GameLengthState.START_GAME, map.getGameLengthState());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapCreateWithPlayers() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);

    StarMap map = new StarMap(config, players);
    assertEquals(4,map.getNewsCorpData().getCredit().getMaxPlayers());
    assertEquals(4,map.getNewsCorpData().getMilitary().getMaxPlayers());
    assertEquals(4,map.getNewsCorpData().getCultural().getMaxPlayers());
    assertEquals(4,map.getNewsCorpData().getPlanets().getMaxPlayers());
    assertEquals(4,map.getNewsCorpData().getPopulation().getMaxPlayers());
    assertEquals(4,map.getNewsCorpData().getResearch().getMaxPlayers());
    assertEquals(50, map.getMaxX());
    assertEquals(50, map.getMaxY());
    assertEquals(true, map.isValidCoordinate(25, 25));
    assertEquals(false, map.isValidCoordinate(-1, 25));
    assertEquals(false, map.isValidCoordinate(25, -1));
    assertEquals(false, map.isValidCoordinate(512, 25));
    assertEquals(false, map.isValidCoordinate(252, 512));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapCreateWithPlayers2() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(75);
    Mockito.when(config.getSizeY()).thenReturn(75);
    Mockito.when(config.getMaxPlayers()).thenReturn(2);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_RANDOM);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);


    StarMap map = new StarMap(config, players);
    assertEquals(75, map.getMaxX());
    assertEquals(75, map.getMaxY());
    assertEquals(true, map.isValidCoordinate(25, 25));
    assertEquals(false, map.isValidCoordinate(-1, 25));
    assertEquals(false, map.isValidCoordinate(25, -1));
    assertEquals(false, map.isValidCoordinate(512, 25));
    assertEquals(false, map.isValidCoordinate(252, 512));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapBrowsingThePlayerPlanets() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(75);
    Mockito.when(config.getSizeY()).thenReturn(75);
    Mockito.when(config.getMaxPlayers()).thenReturn(2);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_RANDOM);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_WAR))
        .thenReturn(false);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);

    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Sporks");
    msgList = Mockito.mock(MessageList.class);
    Mockito.when(info2.getMsgList()).thenReturn(msgList);
    stats = new ShipStat[0];
    Mockito.when(info2.getShipStatList()).thenReturn(stats);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);

    Planet planet1 = Mockito.mock(Planet.class);
    Mockito.when(planet1.getX()).thenReturn(5);
    Mockito.when(planet1.getY()).thenReturn(7);
    Mockito.when(planet1.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getX()).thenReturn(15);
    Mockito.when(planet2.getY()).thenReturn(17);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getX()).thenReturn(25);
    Mockito.when(planet3.getY()).thenReturn(27);
    Mockito.when(planet3.getPlanetPlayerInfo()).thenReturn(info2);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getX()).thenReturn(35);
    Mockito.when(planet4.getY()).thenReturn(37);
    Mockito.when(planet4.getPlanetPlayerInfo()).thenReturn(info2);
    Planet planetUnhabitated = Mockito.mock(Planet.class);
    Mockito.when(planetUnhabitated.getX()).thenReturn(45);
    Mockito.when(planetUnhabitated.getY()).thenReturn(47);
    Mockito.when(planetUnhabitated.getPlanetPlayerInfo()).thenReturn(null);
    ArrayList<Planet> list = new ArrayList<>();
    list.add(planetUnhabitated);
    list.add(planet1);
    list.add(planet3);
    list.add(planetUnhabitated);
    list.add(planetUnhabitated);
    list.add(planet4);
    list.add(planetUnhabitated);
    list.add(planet2);
    list.add(planetUnhabitated);
    list.add(planetUnhabitated);
    StarMap map = new StarMap(config, players);
    map.setPlanetList(list);
    Planet test = map.getNextPlanetForPlayer(info, planet1, true);
    assertEquals(planet2, test);
    test = map.getNextPlanetForPlayer(info, planet3, true);
    assertEquals(planet2, test);
    test = map.getNextPlanetForPlayer(info, planetUnhabitated, true);
    assertEquals(planet1, test);
    test = map.getNextPlanetForPlayer(info, planet2, true);
    assertEquals(planet1, test);
    test = map.getNextPlanetForPlayer(info2, planet3, true);
    assertEquals(planet4, test);
    test = map.getNextPlanetForPlayer(info2, planet1, true);
    assertEquals(planet3, test);
    test = map.getNextPlanetForPlayer(info2, planetUnhabitated, true);
    assertEquals(planet3, test);
    test = map.getNextPlanetForPlayer(info2, planet4, true);
    assertEquals(planet3, test);
    assertEquals(false, map.isWarBetween(info, info2));
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_WAR))
    .thenReturn(true);
    assertEquals(true, map.isWarBetween(info, info2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapClosestHomeWorld() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(75);
    Mockito.when(config.getSizeY()).thenReturn(75);
    Mockito.when(config.getMaxPlayers()).thenReturn(2);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_RANDOM);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_WAR))
        .thenReturn(false);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);

    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Sporks");
    msgList = Mockito.mock(MessageList.class);
    Mockito.when(info2.getMsgList()).thenReturn(msgList);
    stats = new ShipStat[0];
    Mockito.when(info2.getShipStatList()).thenReturn(stats);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);

    Planet planet1 = Mockito.mock(Planet.class);
    Mockito.when(planet1.getX()).thenReturn(5);
    Mockito.when(planet1.getY()).thenReturn(7);
    Mockito.when(planet1.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getX()).thenReturn(15);
    Mockito.when(planet2.getY()).thenReturn(17);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getX()).thenReturn(25);
    Mockito.when(planet3.getY()).thenReturn(27);
    Mockito.when(planet3.getPlanetPlayerInfo()).thenReturn(info2);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getX()).thenReturn(35);
    Mockito.when(planet4.getY()).thenReturn(37);
    Mockito.when(planet4.getPlanetPlayerInfo()).thenReturn(info2);
    Planet planetUnhabitated = Mockito.mock(Planet.class);
    Mockito.when(planetUnhabitated.getX()).thenReturn(45);
    Mockito.when(planetUnhabitated.getY()).thenReturn(47);
    Mockito.when(planetUnhabitated.getPlanetPlayerInfo()).thenReturn(null);
    ArrayList<Planet> list = new ArrayList<>();
    list.add(planetUnhabitated);
    list.add(planet1);
    list.add(planet3);
    list.add(planetUnhabitated);
    list.add(planetUnhabitated);
    list.add(planet4);
    list.add(planetUnhabitated);
    list.add(planet2);
    list.add(planetUnhabitated);
    list.add(planetUnhabitated);
    StarMap map = new StarMap(config, players);
    map.setPlanetList(list);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.calculateDistance(Mockito.any(Coordinate.class))).thenReturn(3.2);
    assertEquals(planet1, map.getClosestHomePort(info, coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarMapCreateWithMilitaryCompare() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);

    StarMap map = new StarMap(config, players);
    assertEquals(0,map.getNewsCorpData().getMilitary().getLatest(0));
    assertEquals(0,map.getNewsCorpData().getMilitary().getLatest(1));
    assertEquals(0,map.getNewsCorpData().getMilitary().getLatest(2));
    assertEquals(0,map.getNewsCorpData().getMilitary().getLatest(3));
    map.getNewsCorpData().getMilitary().addStat(0, 15);
    map.getNewsCorpData().getMilitary().addStat(1, 25);
    map.getNewsCorpData().getMilitary().addStat(2, 5);
    map.getNewsCorpData().getMilitary().addStat(3, 6);
    map.getNewsCorpData().getPlanets().addStat(0, 1);
    map.getNewsCorpData().getPlanets().addStat(1, 1);
    map.getNewsCorpData().getPlanets().addStat(2, 2);
    map.getNewsCorpData().getPlanets().addStat(3, 1);
    assertEquals(false, map.isPlayerStrongerThan(0, 0));
    assertEquals(false, map.isPlayerStrongerThan(0, 1));
    assertEquals(true, map.isPlayerStrongerThan(1, 2));
    assertEquals(true, map.isPlayerStrongerThan(2, 3));
    assertEquals(false, map.isPlayerStrongerThan(3, 0));
    assertEquals(false, map.isPlayerStrongerThan(3, 1));
  }
  
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testWarDeclarationReputation() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "statsNews.save");
    PlayerInfo attacker = starMap.getPlayerByIndex(0);
    PlayerInfo info1 = starMap.getPlayerByIndex(1);
    PlayerInfo info4 = starMap.getPlayerByIndex(4);
    assertEquals(false, info1.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.WAR_DECLARTION));
    assertEquals(false, info4.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.WAR_DECLARTION));
    StarMapUtilities.addWarDeclatingRepuation(starMap, attacker);
    assertEquals(true, info1.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.WAR_DECLARTION));
    assertEquals(true, info4.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.WAR_DECLARTION));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEscapePosition() {
    Coordinate defender = new Coordinate(8,8);
    Coordinate attack = new Coordinate(7,7);
    Coordinate pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(9, pos.getX());
    assertEquals(9, pos.getY());
    attack = new Coordinate(8,7);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(8, pos.getX());
    assertEquals(9, pos.getY());
    attack = new Coordinate(9,7);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(7, pos.getX());
    assertEquals(9, pos.getY());
    attack = new Coordinate(7,8);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(9, pos.getX());
    assertEquals(8, pos.getY());
    attack = new Coordinate(9,8);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(7, pos.getX());
    assertEquals(8, pos.getY());
    attack = new Coordinate(7,9);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(9, pos.getX());
    assertEquals(7, pos.getY());
    attack = new Coordinate(8,9);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(8, pos.getX());
    assertEquals(7, pos.getY());
    attack = new Coordinate(9,9);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(7, pos.getX());
    assertEquals(7, pos.getY());
    attack = new Coordinate(19,19);
    pos = StarMapUtilities.getEscapeCoordinates(defender, attack);
    assertEquals(7, pos.getX());
    assertEquals(7, pos.getY());
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testNukedReputation() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "statsNews.save");
    PlayerInfo attacker = starMap.getPlayerByIndex(0);
    PlayerInfo info1 = starMap.getPlayerByIndex(1);
    PlayerInfo info4 = starMap.getPlayerByIndex(4);
    assertEquals(false, info1.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.NUKED));
    assertEquals(false, info4.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.NUKED));
    StarMapUtilities.addNukeRepuation(starMap, attacker);
    assertEquals(true, info1.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.NUKED));
    assertEquals(true, info4.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.NUKED));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCulture() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);
    StarMap map = new StarMap(config, players);
    map.resetCulture();
    map.calculateCulture(5, 5, 4, 0);
    assertEquals(0, map.getSectorCulture(5, 5).getHighestCulture());
    map.calculateCulture(10, 5, 9, 1);
    assertEquals(1, map.getSectorCulture(10, 5).getHighestCulture());
    map.calculateCulture(15, 5, 19, 2);
    assertEquals(2, map.getSectorCulture(15, 5).getHighestCulture());
    map.calculateCulture(20, 5, 39, 3);
    assertEquals(3, map.getSectorCulture(20, 5).getHighestCulture());
    map.calculateCulture(25, 5, 79, 0);
    assertEquals(0, map.getSectorCulture(25, 5).getHighestCulture());
    map.calculateCulture(30, 5, 159, 1);
    assertEquals(1, map.getSectorCulture(30, 5).getHighestCulture());
    map.calculateCulture(35, 5, 319, 2);
    assertEquals(2, map.getSectorCulture(35, 5).getHighestCulture());
    map.calculateCulture(40, 5, 639, 3);
    assertEquals(3, map.getSectorCulture(40, 5).getHighestCulture());
    map.calculateCulture(5, 10, 1279, 0);
    assertEquals(0, map.getSectorCulture(5, 10).getHighestCulture());
    map.calculateCulture(10, 10, 1281, 1);
    assertEquals(1, map.getSectorCulture(10, 10).getHighestCulture());
    map.calculateCulture(32, 7, 159, 1);
    map.calculateCulture(33, 8, 159, 1);
    map.calculateCulture(25, 15, 80, 2);
    map.calculateCulture(29, 15, 60, 0);
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < 50; y++) {
      for (int x = 0; x < 50; x++) {
        int value = map.getSectorCulture(x, y).getHighestCulture();
        if (value != -1) {
          sb.append(value);
        } else {
          sb.append(".");
        }
      }
      sb.append("\n");
    }
    String expected = "..................................222.33333.......\n" + 
        ".........................0...111.22223333333......\n" + 
        "....................3...000.11112222233333333.....\n" + 
        ".....0...111...2...333.00001111112223333333333....\n" + 
        "....000.11111.222.33330000011111122333333333333...\n" + 
        "..000001111111122333300000111111113333333333333...\n" + 
        "..000000111111122.33330000011111111333333333333...\n" + 
        "00000001111111111..333.00001111111223333333333....\n" + 
        "00000001111111111...3...000.11111122233333333.....\n" + 
        "000000111111111111.......0...111111223333333......\n" + 
        "000001111111111111............1111122.33333.......\n" + 
        "000000111111111111......222..0.11111...333........\n" + 
        "00000001111111111......22222000.111...............\n" + 
        "00000001111111111.....2222220000..................\n" + 
        "..0000001111111......222222200000.................\n" + 
        "..0000011111111......2222222200000................\n" + 
        "....000.11111........222222200000.................\n" + 
        ".....0...111..........2222220000..................\n" + 
        ".......................22222000...................\n" + 
        "........................222..0....................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n" + 
        "..................................................\n";
    assertEquals(expected, sb.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMiltiaryEstimation() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    Espionage espionage = new Espionage(4);
    espionage.getByIndex(2).setEspionageLevel1Estimate(40);
    espionage.getByIndex(2).setEspionageLevel3Estimate(-30);
    espionage.getByIndex(2).setEspionageLevel5Estimate(20);
    espionage.getByIndex(2).setEspionageLevel7Estimate(-10);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(1);
    Mockito.when(fleetList.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getFakeMilitarySize()).thenReturn(100);
    Mockito.when(info.getEspionage()).thenReturn(espionage);

    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Centaurs");
    Mockito.when(info2.getMsgList()).thenReturn(msgList);
    Mockito.when(info2.getShipStatList()).thenReturn(stats);
    FleetList fleetList2 = Mockito.mock(FleetList.class);
    Mockito.when(fleetList2.getNumberOfFleets()).thenReturn(2);
    Mockito.when(fleetList2.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info2.getFleets()).thenReturn(fleetList2);
    Mockito.when(info2.getFakeMilitarySize()).thenReturn(150);
    Mockito.when(info2.getEspionage()).thenReturn(espionage);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info2);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info2);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);
    StarMap map = new StarMap(config, players);
    map.getNewsCorpData().calculateMilitary(players, false);
    int diff = map.getMilitaryDifference(0, 2);
    assertEquals(-48, diff);
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Test");
    diff = map.getMilitaryDifference(0, 2);
    assertEquals(-44, diff);
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    diff = map.getMilitaryDifference(0, 2);
    assertEquals(-17, diff);
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    diff = map.getMilitaryDifference(0, 2);
    assertEquals(-33, diff);
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    diff = map.getMilitaryDifference(0, 2);
    assertEquals(-20, diff);
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    diff = map.getMilitaryDifference(0, 2);
    assertEquals(-24, diff);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMilitaryEstimation2() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    Espionage espionage = new Espionage(4);
    espionage.getByIndex(2).setEspionageLevel1Estimate(40);
    espionage.getByIndex(2).setEspionageLevel3Estimate(-30);
    espionage.getByIndex(2).setEspionageLevel5Estimate(20);
    espionage.getByIndex(2).setEspionageLevel7Estimate(-10);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(1);
    Mockito.when(fleetList.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getFakeMilitarySize()).thenReturn(100);
    Mockito.when(info.getEspionage()).thenReturn(espionage);

    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Centaurs");
    Mockito.when(info2.getMsgList()).thenReturn(msgList);
    Mockito.when(info2.getShipStatList()).thenReturn(stats);
    FleetList fleetList2 = Mockito.mock(FleetList.class);
    Mockito.when(fleetList2.getNumberOfFleets()).thenReturn(2);
    Mockito.when(fleetList2.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info2.getFleets()).thenReturn(fleetList2);
    Mockito.when(info2.getFakeMilitarySize()).thenReturn(150);
    Mockito.when(info2.getEspionage()).thenReturn(espionage);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info2);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info2);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);
    StarMap map = new StarMap(config, players);
    map.getNewsCorpData().calculateMilitary(players, false);
    assertEquals(72, map.getMilitaryEstimation(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Test");
    assertEquals(67, map.getMilitaryEstimation(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(34, map.getMilitaryEstimation(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(57, map.getMilitaryEstimation(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(44, map.getMilitaryEstimation(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(48, map.getMilitaryEstimation(0, 2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDefensivePactEstimation() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    Mockito.when(config.getSizeX()).thenReturn(50);
    Mockito.when(config.getSizeY()).thenReturn(50);
    Mockito.when(config.getMaxPlayers()).thenReturn(4);
    Mockito.when(config.getStartingPosition()).thenReturn(
        GalaxyConfig.START_POSITION_BORDER);

    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Human");
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    ShipStat[] stats = new ShipStat[0];
    Mockito.when(info.getShipStatList()).thenReturn(stats);
    Espionage espionage = new Espionage(4);
    espionage.getByIndex(2).setEspionageLevel1Estimate(40);
    espionage.getByIndex(2).setEspionageLevel3Estimate(-30);
    espionage.getByIndex(2).setEspionageLevel5Estimate(20);
    espionage.getByIndex(2).setEspionageLevel7Estimate(-10);
    espionage.getByIndex(3).setEspionageLevel1Estimate(35);
    espionage.getByIndex(3).setEspionageLevel3Estimate(25);
    espionage.getByIndex(3).setEspionageLevel5Estimate(-15);
    espionage.getByIndex(3).setEspionageLevel7Estimate(-8);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.isDefensivePact(3)).thenReturn(true);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(1);
    Mockito.when(fleetList.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getFakeMilitarySize()).thenReturn(100);
    Mockito.when(info.getEspionage()).thenReturn(espionage);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);

    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Centaurs");
    Mockito.when(info2.getMsgList()).thenReturn(msgList);
    Mockito.when(info2.getShipStatList()).thenReturn(stats);
    FleetList fleetList2 = Mockito.mock(FleetList.class);
    Mockito.when(fleetList2.getNumberOfFleets()).thenReturn(2);
    Mockito.when(fleetList2.getByIndex(Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(info2.getFleets()).thenReturn(fleetList2);
    Mockito.when(info2.getFakeMilitarySize()).thenReturn(150);
    Mockito.when(info2.getEspionage()).thenReturn(espionage);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info2);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info2);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);
    StarMap map = new StarMap(config, players);
    map.getNewsCorpData().calculateMilitary(players, false);
    assertEquals(144, map.getMilitaryEstimationForDefensivePact(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Test");
    espionage.getByIndex(3).addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Test");
    assertEquals(131, map.getMilitaryEstimationForDefensivePact(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    espionage.getByIndex(3).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(94, map.getMilitaryEstimationForDefensivePact(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    espionage.getByIndex(3).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(98, map.getMilitaryEstimationForDefensivePact(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    espionage.getByIndex(3).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(89, map.getMilitaryEstimationForDefensivePact(0, 2));
    espionage.getByIndex(2).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    espionage.getByIndex(3).addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(96, map.getMilitaryEstimationForDefensivePact(0, 2));
  }

}
