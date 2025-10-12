package org.openRealmOfStars.player.diplomacy;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2024 Tuomo Untinen
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
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationList;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.espionage.Intelligence;
import org.openRealmOfStars.player.espionage.IntelligenceList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.starMap.vote.VotingType;

/**
 * Diplomatic Trade tests
 *
 */
public class DiplomaticTradeTest {

  private static StarMap generateMap(final int maxPlayer) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(maxPlayer);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(maxPlayer);

    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    return map;
  }

  private static StarMap generateMapPirates(final int maxPlayer) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(maxPlayer);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(maxPlayer);

    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);

    PlayerInfo player1 = new PlayerInfo(SpaceRaceFactory.createOne(
        "HOMARIANS"),maxPlayer,0);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    player1.setTotalCredits(15);
    player1.initMapData(5, 5);

    PlayerInfo player2 = new PlayerInfo(SpaceRaceFactory.createOne(
        SpaceRaceFactory.SPACE_PIRATE), maxPlayer,
        maxPlayer);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    player2.setTotalCredits(20);
    player2.initMapData(5, 5);
    player2.setSectorVisibility(0, 0, PlayerInfo.VISIBLE);
    player2.setSectorVisibility(1, 0, PlayerInfo.VISIBLE);
    player2.setSectorVisibility(2, 0, PlayerInfo.VISIBLE);
    player2.setSectorVisibility(0, 1, PlayerInfo.VISIBLE);
    player2.setSectorVisibility(0, 2, PlayerInfo.VISIBLE);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(maxPlayer))
        .thenReturn(player2);
    Mockito.when(players.getSpacePiratePlayer()).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(maxPlayer)).thenReturn(player2);
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);

    return map;
  }

  private static StarMap generateMapWithPlayer(final SpaceRace race) {
    return generateMapWithPlayer(race, 0);
  }

  private static StarMap generateMapWithPlayer(final SpaceRace race,
      final int powerDifference) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    PlayerInfo player1 = new PlayerInfo(race,2,0);
    player1.setEmpireName("Terran");
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    player1.setTotalCredits(15);
    player1.initMapData(5, 5);

    PlayerInfo player2 = new PlayerInfo(SpaceRaceFactory.createOne(
        "GREYANS"), 2, 1);
    player2.setEmpireName("Martian");
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
    Mockito.when(newsData.getMilitaryDifference(0, 1)).thenReturn(
        powerDifference);
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> planetList = new ArrayList<>();
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(player2);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(0, 0));
    planetList.add(planet);
    Mockito.when(map.getPlanetList()).thenReturn(planetList);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsData);
    Mockito.when(map.getMilitaryDifference(0, 1)).thenReturn(powerDifference);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);
    Mockito.when(map.getPlanetByName(Mockito.anyString())).thenReturn(planet);
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    return map;
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(0,trade.getFirstIndex());
    assertEquals(1,trade.getSecondIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 5, 1);
    assertEquals(0,trade.getFirstIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSecondOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 3, -1);
    assertEquals(0,trade.getFirstIndex());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTechListGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    TechList tech1 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    TechList tech2 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(null, trade.getFirstOffer());
    assertEquals(null, trade.getSecondOffer());
    Tech[] techs = trade.getTradeableTechListForFirst();
    assertEquals(2, techs.length);
    assertEquals("DefTech2", techs[0].getName());
    assertEquals("EleTech1", techs[1].getName());
    techs = trade.getTradeableTechListForSecond();
    assertEquals(1, techs.length);
    assertEquals("MilTech3", techs[0].getName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFirstTradeGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(3);
    PlayerInfo player1 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"),
        2, 0);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));

    PlayerInfo player2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"),
        2, 1);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    NewsCorpData newsCorp = Mockito.mock(NewsCorpData.class);
    Mockito.when(newsCorp.getMilitaryDifference(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(0);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsCorp);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(null, trade.getFirstOffer());
    assertEquals(null, trade.getSecondOffer());
    trade.generateOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(true, trade.isOfferGoodForBoth());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCustomTradeGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    PlayerInfo player1 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"),
        0, 2);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));

    PlayerInfo player2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"),
        1, 2);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    NewsCorpData newsCorp = Mockito.mock(NewsCorpData.class);
    Mockito.when(newsCorp.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt())).thenReturn(-100);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsCorp);
    Mockito.when(map.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt())).thenReturn(-100);
    Mockito.when(map.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.anyBoolean())).thenReturn(100);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList offer = new NegotiationList();
    offer.add(new NegotiationOffer(NegotiationType.CREDIT, 80));
    trade.setFirstOffer(offer);
    offer = new NegotiationList();
    // Just empty list
    trade.setSecondOffer(offer);
    assertEquals(false, trade.isOfferGoodForBoth());

    trade = new DiplomaticTrade(map, 0, 1);
    player2.getDiplomacy().getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_ALLIANCE, player2.getRace());
    offer = new NegotiationList();
    offer.add(new NegotiationOffer(NegotiationType.CREDIT, 15));
    trade.setFirstOffer(offer);
    offer = new NegotiationList();
    // Just empty list
    trade.setSecondOffer(offer);
    // Second player has greatly bigger army so not agreeing
    assertEquals(false, trade.isOfferGoodForBoth());

    Mockito.when(newsCorp.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt())).thenReturn(50);
    Mockito.when(map.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt())).thenReturn(-50);
    Mockito.when(map.getMilitaryDifference(Mockito.anyInt(), Mockito.anyInt(),
        Mockito.anyBoolean())).thenReturn(-50);
    trade = new DiplomaticTrade(map, 0, 1);
    offer = new NegotiationList();
    offer.add(new NegotiationOffer(NegotiationType.CREDIT, 15));
    trade.setFirstOffer(offer);
    offer = new NegotiationList();
    // Just empty list
    trade.setSecondOffer(offer);
    // First player has bigger army so now agreeing
    assertEquals(true, trade.isOfferGoodForBoth());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetListGeneration() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(5);
    Mockito.when(coord.getY()).thenReturn(5);
    Coordinate coord2 = Mockito.mock(Coordinate.class);
    Mockito.when(coord2.getX()).thenReturn(6);
    Mockito.when(coord2.getY()).thenReturn(5);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(3);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    Intelligence espionage = Mockito.mock(Intelligence.class);
    IntelligenceList espionageList = Mockito.mock(IntelligenceList.class);
    Mockito.when(espionage.getByIndex(Mockito.anyInt())).thenReturn(espionageList);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player1.getIntelligence()).thenReturn(espionage);
    Mockito.when(player1.getSectorVisibility(Mockito.isA(Coordinate.class)))
        .thenReturn((byte) 2);
    TechList tech1 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player2.getIntelligence()).thenReturn(espionage);
    TechList tech2 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    Mockito.when(player2.getSectorVisibility(Mockito.isA(Coordinate.class)))
        .thenReturn((byte) 2);
    FleetList fleetList = new FleetList();
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    fleetList.add(fleet);
    Mockito.when(player1.getFleets()).thenReturn(fleetList);
    Mockito.when(player2.getFleets()).thenReturn(fleetList);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    Fleet[] fleets = trade.getTradeableFleetListForFirst();
    assertEquals(1, fleets.length);
    assertEquals(fleet, fleets[0]);
    fleets = trade.getTradeableFleetListForSecond();
    assertEquals(1, fleets.length);
    assertEquals(fleet, fleets[0]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetListGeneration() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player1.getSectorVisibility(coord)).thenReturn((byte) 1);
    TechList tech1 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player2.getSectorVisibility(coord)).thenReturn((byte) 1);
    TechList tech2 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    planets.add(planet);
    planets.add(planet);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCoordinate()).thenReturn(coord);
    planets.add(planet2);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    Planet[] list = trade.getTradeablePlanetListForFirst();
    assertEquals(2, list.length);
    assertEquals(planet, list[0]);
    assertEquals(planet, list[1]);
    list = trade.getTradeablePlanetListForSecond();
    assertEquals(1, list.length);
    assertEquals(planet2, list[0]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMapTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.generateMapTrade(DiplomaticTrade.TRADE, DiplomaticTrade.MAP_FULLMAP);
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.MAP, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateMapTrade(DiplomaticTrade.BUY, DiplomaticTrade.MAP_FULLMAP);
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.CREDIT, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUnEvenTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList firstList = new NegotiationList();
    firstList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createElectronicsTech("Scanner Mk1", 1)));
    NegotiationList secondList = new NegotiationList();
    secondList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createCombatTech("Laser Mk2", 2)));
    secondList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createDefenseTech("Shield Mk2", 2)));
    trade.setFirstOffer(firstList);
    trade.setSecondOffer(secondList);
    assertEquals(-6, trade.getOfferDifferenceForBoth());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUnEvenTradeFleet() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList firstList = new NegotiationList();
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(16);
    firstList.add(new NegotiationOffer(NegotiationType.FLEET,
        fleet));
    NegotiationList secondList = new NegotiationList();
    secondList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createCombatTech("Laser Mk2", 2)));
    secondList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createDefenseTech("Shield Mk2", 2)));
    trade.setFirstOffer(firstList);
    trade.setSecondOffer(secondList);
    assertEquals(10000, trade.getOfferDifferenceForBoth());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUnEvenTradeFleet2() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList firstList = new NegotiationList();
    firstList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createCombatTech("Laser Mk2", 2)));
    firstList.add(new NegotiationOffer(NegotiationType.TECH,
        TechFactory.createDefenseTech("Shield Mk2", 2)));
    NegotiationList secondList = new NegotiationList();
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(32);
    secondList.add(new NegotiationOffer(NegotiationType.FLEET,
        fleet));
    trade.setFirstOffer(firstList);
    trade.setSecondOffer(secondList);
    assertEquals(-8, trade.getOfferDifferenceForBoth());
  }

/*
  // XXX: Mechanic subject of change
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMapTradeWithVote() {
    StarMap map = generateMapWithPlayer(SpaceRace.HUMAN);
    Votes votes = Mockito.mock(Votes.class);
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_NUCLEAR_WEAPONS);
    Mockito.when(votes.getNextImportantVote()).thenReturn(vote);
    Mockito.when(map.getVotes()).thenReturn(votes);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.generateMapTrade(DiplomaticTrade.BUY, true);
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PROMISE_VOTE_YES, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }
*/
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMapPlanetTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.generateMapTrade(DiplomaticTrade.TRADE, 
        DiplomaticTrade.MAP_ONLYPLANETS);
    assertEquals(NegotiationType.MAP_PLANETS, 
        trade.getFirstOffer().getByIndex(0).getNegotiationType());
    assertEquals(NegotiationType.MAP_PLANETS, 
        trade.getSecondOffer().getByIndex(0).getNegotiationType());
    trade.generateMapTrade(DiplomaticTrade.BUY,
        DiplomaticTrade.MAP_ONLYPLANETS);
    assertEquals(NegotiationType.MAP_PLANETS,
        trade.getFirstOffer().getByIndex(0).getNegotiationType());
    assertEquals(NegotiationType.CREDIT, 
        trade.getSecondOffer().getByIndex(0).getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEqualTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.generateEqualTrade(NegotiationType.ALLIANCE);
    assertEquals(NegotiationType.ALLIANCE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.ALLIANCE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateEqualTrade(NegotiationType.PEACE);
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
    assertEquals(NegotiationType.TRADE_ALLIANCE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.TRADE_ALLIANCE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateEqualTrade(NegotiationType.WAR);
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
    assertEquals(NegotiationType.DEFENSIVE_PACT, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.DEFENSIVE_PACT, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRecallFleet() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    Fleet fleet = Mockito.mock(Fleet.class);
    trade.generateRecallFleetOffer(fleet);
    assertEquals(0, trade.getFirstOffer().getSize());
    assertEquals(NegotiationType.RECALL_FLEET, trade.getSecondOffer()
        .getByIndex(0).getNegotiationType());
    assertEquals(fleet, trade.getSecondOffer()
        .getByIndex(0).getFleet());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarChance() {
    int[] results = {95, 85, 75, 65, 55};
    int j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.AGGRESSIVE, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 85;
    results[1] = 75;
    results[2] = 65;
    results[3] = 55;
    results[4] = 45;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.BACKSTABBING, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 65;
    results[1] = 55;
    results[2] = 45;
    results[3] = 35;
    results[4] = 25;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.DIPLOMATIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 75;
    results[1] = 65;
    results[2] = 55;
    results[3] = 45;
    results[4] = 35;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.EXPANSIONIST, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 70;
    results[1] = 60;
    results[2] = 50;
    results[3] = 40;
    results[4] = 30;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.LOGICAL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.SCIENTIFIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 60;
    results[1] = 50;
    results[2] = 40;
    results[3] = 30;
    results[4] = 20;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.MERCHANTICAL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 85;
    results[1] = 75;
    results[2] = 65;
    results[3] = 55;
    results[4] = 45;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.MILITARISTIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 45;
    results[1] = 35;
    results[2] = 25;
    results[3] = 15;
    results[4] = 5;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.PEACEFUL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 30;
    results[1] = 20;
    results[2] = 10;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.AGGRESSIVE, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 25;
    results[1] = 15;
    results[2] = 5;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.BACKSTABBING, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 15;
    results[1] = 5;
    results[2] = 0;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.DIPLOMATIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 23;
    results[1] = 13;
    results[2] = 3;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.EXPANSIONIST, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 20;
    results[1] = 10;
    results[2] = 0;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.LOGICAL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.SCIENTIFIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 17;
    results[1] = 7;
    results[2] = 0;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.MERCHANTICAL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 25;
    results[1] = 15;
    results[2] = 5;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.MILITARISTIC, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 10;
    results[1] = 0;
    results[2] = 0;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.PEACEFUL, i, false);
      assertEquals(results[j], warChance);
      j++;
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarChanceCasusBelli() {
    int[] results = {100, 100, 95, 85, 75};
    int j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.AGGRESSIVE, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 100;
    results[1] = 95;
    results[2] = 85;
    results[3] = 75;
    results[4] = 65;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.BACKSTABBING, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 75;
    results[1] = 65;
    results[2] = 55;
    results[3] = 45;
    results[4] = 35;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.DIPLOMATIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 87;
    results[1] = 77;
    results[2] = 67;
    results[3] = 57;
    results[4] = 47;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.EXPANSIONIST, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 80;
    results[1] = 70;
    results[2] = 60;
    results[3] = 50;
    results[4] = 40;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.LOGICAL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.SCIENTIFIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 70;
    results[1] = 60;
    results[2] = 50;
    results[3] = 40;
    results[4] = 30;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.MERCHANTICAL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 100;
    results[1] = 95;
    results[2] = 85;
    results[3] = 75;
    results[4] = 65;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.MILITARISTIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 55;
    results[1] = 45;
    results[2] = 35;
    results[3] = 25;
    results[4] = 15;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.DEMAND,
          Attitude.PEACEFUL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 45;
    results[1] = 35;
    results[2] = 25;
    results[3] = 15;
    results[4] = 5;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.AGGRESSIVE, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 40;
    results[1] = 30;
    results[2] = 20;
    results[3] = 10;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.BACKSTABBING, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 25;
    results[1] = 15;
    results[2] = 5;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.DIPLOMATIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 35;
    results[1] = 25;
    results[2] = 15;
    results[3] = 5;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.EXPANSIONIST, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 30;
    results[1] = 20;
    results[2] = 10;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.LOGICAL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.SCIENTIFIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 27;
    results[1] = 17;
    results[2] = 7;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.MERCHANTICAL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 40;
    results[1] = 30;
    results[2] = 20;
    results[3] = 10;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.MILITARISTIC, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
    results = new int[5];
    results[0] = 20;
    results[1] = 10;
    results[2] = 0;
    results[3] = 0;
    results[4] = 0;
    j = 0;
    for (int i = -2; i < 3; i++) {
      int warChance = DiplomaticTrade.getWarChanceForDecline(SpeechType.TRADE,
          Attitude.PEACEFUL, i, true);
      assertEquals(results[j], warChance);
      j++;
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));

    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateTechTrade(DiplomaticTrade.TRADE);
    assertEquals(NegotiationType.TECH, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.TECH, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateTechTrade(DiplomaticTrade.BUY);
    assertEquals(NegotiationType.TECH, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.CREDIT, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechTradeWithVote() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne(
        "CHIRALOIDS"));
    Votes votes = Mockito.mock(Votes.class);
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_NUCLEAR_WEAPONS);
    Mockito.when(votes.getNextImportantVote()).thenReturn(vote);
    Mockito.when(map.getVotes()).thenReturn(votes);

    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateTechTrade(DiplomaticTrade.BUY);
    assertEquals(NegotiationType.TECH, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PROMISE_VOTE_NO, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNullTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    int value = trade.getOfferDifferenceForBoth();
    assertEquals(0, value);
    assertNotEquals(null, trade.getFirstOffer());
    assertNotEquals(null, trade.getSecondOffer());
  }


  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGivingOutValuable() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planet.getGovernor()).thenReturn(null);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList offerList1 = new NegotiationList();
    offerList1.add(new NegotiationOffer(NegotiationType.CREDIT,
        Integer.valueOf(30)));
    offerList1.add(new NegotiationOffer(NegotiationType.PLANET, planet));
    NegotiationList offerList2 = new NegotiationList();
    trade.setFirstOffer(offerList1);
    trade.setSecondOffer(offerList2);
    trade.doTrades();
    assertEquals(true, trade.getMajorDeals().contains("Terran"));
    assertEquals(true, trade.getMajorDeals().contains("30 credits from"));
    assertEquals(true, trade.getMajorDeals().contains("Martian."));
    int bonus = map.getPlayerByIndex(0).getDiplomacy().getDiplomacyList(1)
      .getDiplomacyBonus();
    assertEquals(2, bonus);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testActualMapTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HUMANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList offerList1 = new NegotiationList();
    offerList1.add(new NegotiationOffer(NegotiationType.MAP, null));
    NegotiationList offerList2 = new NegotiationList();
    offerList2.add(new NegotiationOffer(NegotiationType.MAP, null));
    trade.setFirstOffer(offerList1);
    trade.setSecondOffer(offerList2);
    trade.doTrades();
    int bonus = map.getPlayerByIndex(0).getDiplomacy().getDiplomacyList(1)
      .getDiplomacyBonus();
    assertEquals(4, bonus);
    bonus = map.getPlayerByIndex(1).getDiplomacy().getDiplomacyList(0)
        .getDiplomacyBonus();
    assertEquals(4, bonus);
    PlayerInfo buyer = map.getPlayerByIndex(0);
    PlayerInfo seller = map.getPlayerByIndex(1);
    seller.setSectorVisibility(0, 0, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(1, 0, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(2, 0, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(3, 0, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(4, 0, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(0, 1, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(1, 1, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(2, 1, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(3, 1, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(4, 1, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(0, 2, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(1, 2, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(2, 2, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(3, 2, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(4, 2, PlayerInfo.VISIBLE);
    seller.setSectorVisibility(0, 3, PlayerInfo.VISIBLE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.generateMapTrade(DiplomaticTrade.BUY, DiplomaticTrade.MAP_FULLMAP);
    int oldSellerCreds = seller.getTotalCredits();
    int oldBuyerCreds = buyer.getTotalCredits();
    trade.doTrades();
    assertEquals(oldSellerCreds + 15, seller.getTotalCredits());
    assertEquals(oldBuyerCreds - 15, buyer.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetAndFleetNotGivingThemAway() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Intelligence espionage = Mockito.mock(Intelligence.class);
    IntelligenceList espionageList = Mockito.mock(IntelligenceList.class);
    Mockito.when(espionage.getByIndex(Mockito.anyInt())).thenReturn(espionageList);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player1.getSectorVisibility(coord)).thenReturn((byte) 2);
    Mockito.when(player1.getFleets()).thenReturn(fleetList);
    Mockito.when(player1.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(player1.getIntelligence()).thenReturn(espionage);
    TechList tech1 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    Mockito.when(player1.getRace()).thenReturn(
        SpaceRaceFactory.createOne("HUMANS"));
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(player2.getSectorVisibility(coord)).thenReturn((byte) 2);
    Mockito.when(player2.getFleets()).thenReturn(fleetList);
    Mockito.when(player2.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(player2.getIntelligence()).thenReturn(espionage);
    TechList tech2 = new TechList(SpaceRaceFactory.createOne("HUMANS"));
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    Mockito.when(player2.getRace()).thenReturn(
        SpaceRaceFactory.createOne("HUMANS"));
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> planets = new ArrayList<>();
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    planets.add(planet);
    planets.add(planet);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    planets.add(planet2);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    Planet[] list = trade.getTradeablePlanetListForFirst();
    assertEquals(2, list.length);
    assertEquals(planet, list[0]);
    assertEquals(planet, list[1]);
    list = trade.getTradeablePlanetListForSecond();
    assertEquals(1, list.length);
    assertEquals(planet2, list[0]);
    NegotiationList offerList1 = new NegotiationList();
    offerList1.add(new NegotiationOffer(NegotiationType.PLANET, trade.getTradeablePlanetListForFirst()[0]));
    NegotiationList offerList2 = new NegotiationList();
    trade.setFirstOffer(offerList1);
    trade.setSecondOffer(offerList2);
    assertEquals(false, trade.isOfferGoodForBoth());
    offerList1 = new NegotiationList();
    offerList1.add(new NegotiationOffer(NegotiationType.FLEET, trade.getTradeableFleetListForFirst()[0]));
    trade.setFirstOffer(offerList1);
    assertEquals(false, trade.isOfferGoodForBoth());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGetBestPlanet() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"));
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne(
        "SPORKS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    ArrayList<Planet> planets = new ArrayList<>();
    Planet planetOne = Mockito.mock(Planet.class);
    Mockito.when(planetOne.getAmountMetalInGround()).thenReturn(3463);
    Mockito.when(planetOne.getGroundSize()).thenReturn(10);
    Mockito.when(planetOne.getTotalPopulation()).thenReturn(4);
    Mockito.when(planetOne.isHomeWorld()).thenReturn(false);
    Mockito.when(planetOne.getRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planetOne.getPlanetPlayerInfo()).thenReturn(info);
    Mockito.when(planetOne.isColonizeablePlanet(info)).thenReturn(true);
    planets.add(planetOne);
    Planet planetTwo = Mockito.mock(Planet.class);
    Mockito.when(planetTwo.getAmountMetalInGround()).thenReturn(3463);
    Mockito.when(planetTwo.getGroundSize()).thenReturn(10);
    Mockito.when(planetTwo.getTotalPopulation()).thenReturn(4);
    Mockito.when(planetTwo.isHomeWorld()).thenReturn(true);
    Mockito.when(planetTwo.getRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planetTwo.getPlanetPlayerInfo()).thenReturn(info);
    Mockito.when(planetTwo.isColonizeablePlanet(info)).thenReturn(true);
    planets.add(planetTwo);
    assertEquals(planetTwo, trade.getTradeablePlanet(info, planets));
    Mockito.when(planetTwo.getRadiationLevel()).thenReturn(
        RadiationType.VERY_HIGH_RAD);
    Mockito.when(planetTwo.isColonizeablePlanet(info)).thenReturn(false);
    assertEquals(planetOne, trade.getTradeablePlanet(info, planets));
    Mockito.when(planetTwo.getRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planetTwo.isColonizeablePlanet(info)).thenReturn(true);
    assertEquals(planetOne, trade.getTradeablePlanet(null, planets));
    Mockito.when(planetTwo.getRadiationLevel()).thenReturn(
        RadiationType.VERY_HIGH_RAD);
    Mockito.when(planetTwo.isColonizeablePlanet(info)).thenReturn(false);
    assertEquals(planetTwo, trade.getTradeablePlanet(null, planets));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGetBestFleet() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"));
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    ArrayList<Fleet> fleets = new ArrayList<>();
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getMilitaryValue()).thenReturn(10);
    Mockito.when(fleet1.calculateFleetObsoleteValue(info)).thenReturn(0);
    fleets.add(fleet1);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getMilitaryValue()).thenReturn(12);
    Mockito.when(fleet2.calculateFleetObsoleteValue(info)).thenReturn(1);
    fleets.add(fleet2);
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getMilitaryValue()).thenReturn(10);
    Mockito.when(fleet3.calculateFleetObsoleteValue(info)).thenReturn(2);
    fleets.add(fleet3);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(fleet3, trade.getTradeableFleet(info, fleets));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGetBestFleet2() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"));
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    ArrayList<Fleet> fleets = new ArrayList<>();
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getMilitaryValue()).thenReturn(10);
    Mockito.when(fleet1.calculateFleetObsoleteValue(info)).thenReturn(1);
    fleets.add(fleet1);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getMilitaryValue()).thenReturn(8);
    Mockito.when(fleet2.calculateFleetObsoleteValue(info)).thenReturn(1);
    fleets.add(fleet2);
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getMilitaryValue()).thenReturn(15);
    Mockito.when(fleet3.calculateFleetObsoleteValue(info)).thenReturn(1);
    fleets.add(fleet3);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(fleet2, trade.getTradeableFleet(info, fleets));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstTrade() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"));
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("MOTHOIDS"));
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.EXPANSIONIST);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(1)
        .getNegotiationType());
    assertEquals(NegotiationType.MAP, trade.getSecondOffer().getByIndex(1)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"));
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("TEUTHIDAES"));
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.MILITARISTIC);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLogicalOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne(
        "MECHIONS"), 600);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateLogicalAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"),0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateLogicalAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == type2
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.MAP_PLANETS)) {
      return;
    }
    if (type1 == NegotiationType.TECH && type2 == NegotiationType.CREDIT) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeacefulOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("HOMARIANS"),600);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.PEACEFUL);
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("HOMARIANS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generatePeacefulAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("HOMARIANS"),0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.PEACEFUL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generatePeacefulAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if ((type1 == type2 || type2 == NegotiationType.CREDIT)
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.MAP_PLANETS)) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testExpansionistOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"),
        300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateExpansionistAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"),
        0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateExpansionistAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if ((type2 == NegotiationType.CREDIT || type2 == NegotiationType.MAP
        || type2 == NegotiationType.TECH
        || type2 == NegotiationType.MAP_PLANETS)
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.MAP_PLANETS)) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScientificOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"),
        300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateScientificAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("MECHIONS"),
        0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateScientificAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if ((type2 == NegotiationType.CREDIT || type2 == NegotiationType.MAP
        || type2 == NegotiationType.MAP_PLANETS
        || type2 == NegotiationType.TECH)
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.MAP_PLANETS)) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateOffer() {
    StarMap map = generateMapPirates(2);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 2);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generatePirateOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if ((type2 == NegotiationType.CREDIT || type2 == NegotiationType.MAP
        || type2 == NegotiationType.TECH)
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH)) {
      return;
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAggressiveOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        200);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateAggressiveAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateAggressiveAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.CREDIT || type1 == NegotiationType.MAP_PLANETS) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMilitaristicOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        200);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateMilitaristicAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateMilitaristicAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.CREDIT || type1 == NegotiationType.MAP_PLANETS) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackstabbingOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateBackstabbingAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateBackstabbingAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.MAP_PLANETS) {
      // Success
    } else {
      assertFalse(true);
    }
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy()
    .getDiplomacyList(1).addBonus(DiplomacyBonusType.IN_ALLIANCE,
        SpaceRaceFactory.createOne("SPORKS"));
    if (type1 == NegotiationType.MAP
        || type1 == NegotiationType.MAP_PLANETS
        || type1 == NegotiationType.TECH
        || type1 == NegotiationType.CREDIT
        || type1 == NegotiationType.WAR) {
      // Success
    } else {
      assertFalse(true);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDiplomaticOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("SPORKS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("SPORKS"));
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy().getDiplomacyList(
        1).addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
            SpaceRaceFactory.createOne("SPORKS"));
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateDiplomaticAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateDiplomaticAttitudeOffer();
    if (trade.getFirstOffer().getByIndex(0) != null) {
      NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
          .getNegotiationType();
      if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
          || type1 == NegotiationType.MAP_PLANETS) {
        return;
      }
      assertFalse(true);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMerchanticalOffer() {
    StarMap map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"),
        300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateMerchanticalAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRaceFactory.createOne("SPORKS"), 0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateMerchanticalAttitudeOffer();
    if (trade.getFirstOffer().getByIndex(0) != null) {
      NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
          .getNegotiationType();
      if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
          || type1 == NegotiationType.CREDIT
          || type1 == NegotiationType.MAP_PLANETS) {
        return;
      }
      assertFalse(true);
    }
  }

}
