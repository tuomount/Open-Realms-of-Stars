package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017 Tuomo Untinen
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

    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);

    StarMap map = new StarMap(config, players);
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

}
