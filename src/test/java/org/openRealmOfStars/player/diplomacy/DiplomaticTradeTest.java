package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationList;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Diplomatic Trade tests
 *
 */
public class DiplomaticTradeTest {

  private StarMap generateMap(final int maxPlayer) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(maxPlayer);

    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    return map;
  }

  private StarMap generateMapWithPlayer(final SpaceRace race) {
    return generateMapWithPlayer(race, 0);
  }

  private StarMap generateMapWithPlayer(final SpaceRace race,
      final int powerDifference) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    PlayerInfo player1 = new PlayerInfo(race);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    
    PlayerInfo player2 = new PlayerInfo(SpaceRace.GREYANS);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    NewsCorpData newsData = Mockito.mock(NewsCorpData.class);
    Mockito.when(newsData.getMilitaryDifference(0, 1)).thenReturn(
        powerDifference);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsData);
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
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    TechList tech1 = new TechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    TechList tech2 = new TechList();
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
    PlayerInfo player1 = new PlayerInfo(SpaceRace.HUMAN);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    
    PlayerInfo player2 = new PlayerInfo(SpaceRace.GREYANS);
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
    PlayerInfo player1 = new PlayerInfo(SpaceRace.HUMAN, 0, 2);
    TechList tech1 = player1.getTechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    
    PlayerInfo player2 = new PlayerInfo(SpaceRace.GREYANS, 1, 2);
    TechList tech2 = player2.getTechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
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
    assertEquals(true, trade.isOfferGoodForBoth());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFleetListGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    TechList tech1 = new TechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    TechList tech2 = new TechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    FleetList fleetList = new FleetList();
    Ship scout = Mockito.mock(Ship.class);
    Fleet fleet = new Fleet(scout, 5, 5);
    fleetList.add(fleet);
    Mockito.when(player1.getFleets()).thenReturn(fleetList);
    fleetList = new FleetList();
    fleet = new Fleet(scout, 6, 5);
    fleetList.add(fleet);
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
    assertEquals(scout, fleets[0].getFirstShip());
    fleets = trade.getTradeableFleetListForSecond();
    assertEquals(1, fleets.length);
    assertEquals(scout, fleets[0].getFirstShip());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetListGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    TechList tech1 = new TechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("Scout Mk1", TechType.Hulls, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    TechList tech2 = new TechList();
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
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    planets.add(planet);
    planets.add(planet);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
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
    StarMap map = generateMapWithPlayer(SpaceRace.HUMAN);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.generateMapTrade(map.getPlayerByIndex(1), false);
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.MAP, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateMapTrade(map.getPlayerByIndex(1), true);
    assertEquals(NegotiationType.MAP, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.CREDIT, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEqualTrade() {
    StarMap map = generateMapWithPlayer(SpaceRace.HUMAN);
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
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechTrade() {
    StarMap map = generateMapWithPlayer(SpaceRace.HUMAN);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateTechTrade(map.getPlayerByIndex(1), false);
    assertEquals(NegotiationType.TECH, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.TECH, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    trade.generateTechTrade(map.getPlayerByIndex(1), true);
    assertEquals(NegotiationType.TECH, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.CREDIT, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGivingOutValuable() {
    GameRepository repository = new GameRepository();
    StarMap map = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    NegotiationList offerList1 = new NegotiationList();
    offerList1.add(new NegotiationOffer(NegotiationType.CREDIT, new Integer(30)));
    NegotiationList offerList2 = new NegotiationList();
    trade.setFirstOffer(offerList1);
    trade.setSecondOffer(offerList2);
    trade.doTrades();
    int bonus = map.getPlayerByIndex(0).getDiplomacy().getDiplomacyList(1)
      .getDiplomacyBonus();
    assertEquals(3, bonus);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetAndFleetNotGivingThemAway() {
    GameRepository repository = new GameRepository();
    StarMap map = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
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
  public void testFirstTrade() {
    StarMap map = generateMapWithPlayer(SpaceRace.SPORKS);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.MOTHOIDS);
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
    map = generateMapWithPlayer(SpaceRace.MECHIONS);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateFirstOffer();
    assertEquals(NegotiationType.PEACE, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.PEACE, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.TEUTHIDAES);
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
    StarMap map = generateMapWithPlayer(SpaceRace.MECHIONS,600);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateLogicalAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.MECHIONS,0);
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
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH)) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testExpansionistOffer() {
    StarMap map = generateMapWithPlayer(SpaceRace.MECHIONS,300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateLogicalAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.MECHIONS,0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.LOGICAL);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateLogicalAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    NegotiationType type2 = trade.getSecondOffer().getByIndex(0)
        .getNegotiationType();
    if ((type2 == NegotiationType.CREDIT || type2 == NegotiationType.MAP
        || type2 == NegotiationType.TECH) 
        && (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH)) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAggressiveOffer() {
    StarMap map = generateMapWithPlayer(SpaceRace.SPORKS,200);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateAggressiveAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.SPORKS,0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateAggressiveAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH
        || type1 == NegotiationType.CREDIT) {
      return;
    }
    assertFalse(true);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackstabbingOffer() {
    StarMap map = generateMapWithPlayer(SpaceRace.SPORKS,300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateBackstabbingAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.SPORKS,0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateBackstabbingAttitudeOffer();
    NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
        .getNegotiationType();
    if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH) {
    } else {
      assertFalse(true);
    }
    map.getPlayerList().getPlayerInfoByIndex(0).getDiplomacy()
    .getDiplomacyList(1).addBonus(DiplomacyBonusType.IN_ALLIANCE,
            SpaceRace.SPORKS);
    if (type1 == NegotiationType.MAP
        || type1 == NegotiationType.TECH
        || type1 == NegotiationType.CREDIT
        || type1 == NegotiationType.WAR) {
    } else {
      assertFalse(true);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDiplomaticOffer() {
    StarMap map = generateMapWithPlayer(SpaceRace.SPORKS,300);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateDiplomaticAttitudeOffer();
    assertEquals(NegotiationType.WAR, trade.getFirstOffer().getByIndex(0)
        .getNegotiationType());
    assertEquals(NegotiationType.WAR, trade.getSecondOffer().getByIndex(0)
        .getNegotiationType());
    map = generateMapWithPlayer(SpaceRace.SPORKS,0);
    map.getPlayerList().getPlayerInfoByIndex(0).setAttitude(Attitude.AGGRESSIVE);
    trade = new DiplomaticTrade(map, 0, 1);
    trade.getTradeableTechListForFirst();
    trade.getTradeableTechListForSecond();
    trade.generateBackstabbingAttitudeOffer();
    if (trade.getFirstOffer().getByIndex(0) != null) {
      NegotiationType type1 = trade.getFirstOffer().getByIndex(0)
          .getNegotiationType();
      if (type1 == NegotiationType.MAP || type1 == NegotiationType.TECH) {
        return;
      }
      assertFalse(true);
    } 
    NegotiationType type2 = trade.getFirstOffer().getByIndex(1)
        .getNegotiationType();
    assertEquals(NegotiationType.CREDIT, type2);
  }

}
