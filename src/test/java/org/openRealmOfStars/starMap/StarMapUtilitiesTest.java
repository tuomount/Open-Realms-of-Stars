package org.openRealmOfStars.starMap;
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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;

/**
*
* Tests for StarMapUtilites calls
*
*/
public class StarMapUtilitiesTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 0);
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"), 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, null,2565);
    assertEquals(5, info.getTotalCredits());
    assertEquals(5, info2.getTotalCredits());
    StarMapUtilities.doTradeWithShips(null, fleet, planet, info, null, 2533);
    assertEquals(15, info.getTotalCredits());
    assertEquals(5, info2.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade2() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("SCAURIANS"), 2, 0);
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"), 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, null,
        2453);
    assertEquals(7, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
    StarMapUtilities.doTradeWithShips(null, fleet, planet, info, null, 2554);
    assertEquals(22, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade3() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("SCAURIANS"), 2, 0);
    info.setEmpireName("Traders of Universum");
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"), 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Mockito.when(planet.getName()).thenReturn("Market");
    Mockito.when(planet.getImageInstructions()).thenReturn(ImageInstruction.PLANET_VOLCANICWORLD1);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    NewsCorpData newsData = new NewsCorpData(2);
    assertEquals(0, newsData.getUpcomingNews().length);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, newsData,
        2534);
    assertEquals(7, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
    assertEquals(1, newsData.getUpcomingNews().length);
    assertEquals(true, newsData.getUpcomingNews()[0].getNewsText().contains(
        "Traders of Universum"));
    assertEquals(true, newsData.getUpcomingNews()[0].getNewsText().contains(
        "Market"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureScoreLimit() {
    int limit = StarMapUtilities.calculateCultureScoreLimit(50, 50, 200, 0);
    assertEquals(600, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(75, 75, 200, 1);
    assertEquals(700, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(128, 128, 300, 1);
    assertEquals(1500, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(160, 160, 300, 1);
    assertEquals(1650, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(200, 200, 400, 1);
    assertEquals(2800, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(256, 256, 400, 1);
    assertEquals(3000, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(256, 256, 400, 2);
    assertEquals(4500, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(160, 160, 300, 3);
    assertEquals(3300, limit);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTowerLimit() {
    int limit = StarMapUtilities.calculateRequireTowerLimit(50, 50);
    assertEquals(1, limit);
    limit = StarMapUtilities.calculateRequireTowerLimit(75, 75);
    assertEquals(2, limit);
    limit = StarMapUtilities.calculateRequireTowerLimit(128, 128);
    assertEquals(3, limit);
    limit = StarMapUtilities.calculateRequireTowerLimit(160, 160);
    assertEquals(4, limit);
    limit = StarMapUtilities.calculateRequireTowerLimit(200, 200);
    assertEquals(5, limit);
    limit = StarMapUtilities.calculateRequireTowerLimit(256, 256);
    assertEquals(6, limit);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEmbargo() {
    PlayerInfo embargoImposer = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 4, 0);
    embargoImposer.setEmpireName("Empire of Imposer");
    PlayerInfo embargoImposed = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 4, 1);
    embargoImposed.setEmpireName("Empire of Imposed");
    PlayerInfo embargoAgree = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 4, 2);
    embargoAgree.setEmpireName("Empire of Agree");
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 4, 3);
    info.setEmpireName("Empire of Info");
    PlayerList playerList = new PlayerList();
    playerList.addPlayer(embargoImposer);
    playerList.addPlayer(embargoImposed);
    playerList.addPlayer(embargoAgree);
    playerList.addPlayer(info);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    StarMapUtilities.addEmbargoReputation(map, embargoImposer, embargoAgree, embargoImposed);
    assertEquals(true, embargoImposer.getDiplomacy().getDiplomacyList(1)
        .isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, embargoAgree.getDiplomacy().getDiplomacyList(1)
        .isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, embargoImposed.getDiplomacy().getDiplomacyList(0)
        .isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, embargoImposed.getDiplomacy().getDiplomacyList(2)
        .isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(false, embargoImposed.getDiplomacy().getDiplomacyList(3)
        .isBonusType(DiplomacyBonusType.EMBARGO));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmbargoWithLike() {
    PlayerInfo embargoImposer = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoImposer.getEmpireName()).thenReturn("Imposer");
    PlayerInfo embargoImposed = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoImposed.getEmpireName()).thenReturn("Imposed");
    PlayerInfo embargoAgree = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoAgree.getEmpireName()).thenReturn("Agree");
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Info");
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyImposer = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyImposed = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyAgree = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(embargoAgree.getDiplomacy()).thenReturn(diplomacyAgree);
    Mockito.when(embargoImposed.getDiplomacy()).thenReturn(diplomacyImposed);
    Mockito.when(embargoImposer.getDiplomacy()).thenReturn(diplomacyImposer);
    Mockito.when(diplomacy.getLiking(1)).thenReturn(2);
    PlayerList playerList = new PlayerList();
    playerList.addPlayer(embargoAgree);
    playerList.addPlayer(embargoImposed);
    playerList.addPlayer(embargoImposer);
    playerList.addPlayer(info);
    DiplomacyBonusList bonusListImposer = new DiplomacyBonusList(2);
    DiplomacyBonusList bonusListAgree = new DiplomacyBonusList(0);
    Mockito.when(diplomacy.getDiplomacyList(0)).thenReturn(bonusListAgree);
    Mockito.when(diplomacy.getDiplomacyList(2)).thenReturn(bonusListImposer);
    Mockito.when(diplomacyAgree.getDiplomacyList(1)).thenReturn(new DiplomacyBonusList(1));
    Mockito.when(diplomacyImposed.getDiplomacyList(2)).thenReturn(new DiplomacyBonusList(2));
    Mockito.when(diplomacyImposed.getDiplomacyList(0)).thenReturn(new DiplomacyBonusList(0));
    Mockito.when(diplomacyImposer.getDiplomacyList(1)).thenReturn(new DiplomacyBonusList(1));
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    StarMapUtilities.addEmbargoReputation(map, embargoImposer, embargoAgree, embargoImposed);
    assertEquals(true, bonusListAgree.isBonusType(DiplomacyBonusType.DISLIKED_EMBARGO));
    assertEquals(true, bonusListImposer.isBonusType(DiplomacyBonusType.DISLIKED_EMBARGO));
    assertEquals(true, diplomacyAgree.getDiplomacyList(1).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposer.getDiplomacyList(1).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposed.getDiplomacyList(2).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposed.getDiplomacyList(0).isBonusType(DiplomacyBonusType.EMBARGO));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmbargoWithDisLike() {
    PlayerInfo embargoImposer = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoImposer.getEmpireName()).thenReturn("Imposer");
    PlayerInfo embargoImposed = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoImposed.getEmpireName()).thenReturn("Imposed");
    PlayerInfo embargoAgree = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoAgree.getEmpireName()).thenReturn("Agree");
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Info");
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyImposer = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyImposed = Mockito.mock(Diplomacy.class);
    Diplomacy diplomacyAgree = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(embargoAgree.getDiplomacy()).thenReturn(diplomacyAgree);
    Mockito.when(embargoImposed.getDiplomacy()).thenReturn(diplomacyImposed);
    Mockito.when(embargoImposer.getDiplomacy()).thenReturn(diplomacyImposer);
    Mockito.when(diplomacy.getLiking(Mockito.anyInt())).thenReturn(-2);
    PlayerList playerList = new PlayerList();
    playerList.addPlayer(embargoAgree);
    playerList.addPlayer(embargoImposed);
    playerList.addPlayer(embargoImposer);
    playerList.addPlayer(info);
    DiplomacyBonusList bonusListImposer = new DiplomacyBonusList(2);
    DiplomacyBonusList bonusListAgree = new DiplomacyBonusList(0);
    Mockito.when(diplomacy.getDiplomacyList(0)).thenReturn(bonusListAgree);
    Mockito.when(diplomacy.getDiplomacyList(2)).thenReturn(bonusListImposer);
    Mockito.when(diplomacyAgree.getDiplomacyList(1)).thenReturn(new DiplomacyBonusList(1));
    Mockito.when(diplomacyImposed.getDiplomacyList(2)).thenReturn(new DiplomacyBonusList(2));
    Mockito.when(diplomacyImposed.getDiplomacyList(0)).thenReturn(new DiplomacyBonusList(0));
    Mockito.when(diplomacyImposer.getDiplomacyList(1)).thenReturn(new DiplomacyBonusList(1));
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    StarMapUtilities.addEmbargoReputation(map, embargoImposer, embargoAgree, embargoImposed);
    assertEquals(true, bonusListAgree.isBonusType(DiplomacyBonusType.LIKED_EMBARGO));
    assertEquals(true, bonusListImposer.isBonusType(DiplomacyBonusType.LIKED_EMBARGO));
    assertEquals(true, diplomacyAgree.getDiplomacyList(1).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposer.getDiplomacyList(1).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposed.getDiplomacyList(2).isBonusType(DiplomacyBonusType.EMBARGO));
    assertEquals(true, diplomacyImposed.getDiplomacyList(0).isBonusType(DiplomacyBonusType.EMBARGO));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAttitudeVotingSupport() {
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.AGGRESSIVE, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.AGGRESSIVE, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(-20, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.AGGRESSIVE, VotingType.GALACTIC_PEACE));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.AGGRESSIVE, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.AGGRESSIVE, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.BACKSTABBING, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.BACKSTABBING, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.BACKSTABBING, VotingType.GALACTIC_PEACE));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.BACKSTABBING, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.BACKSTABBING, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.DIPLOMATIC, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.DIPLOMATIC, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.DIPLOMATIC, VotingType.GALACTIC_PEACE));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.DIPLOMATIC, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.DIPLOMATIC, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.EXPANSIONIST, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.EXPANSIONIST, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.EXPANSIONIST, VotingType.GALACTIC_PEACE));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.EXPANSIONIST, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.EXPANSIONIST, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.LOGICAL, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.LOGICAL, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.LOGICAL, VotingType.GALACTIC_PEACE));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.LOGICAL, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.LOGICAL, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MERCHANTICAL, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(20, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MERCHANTICAL, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MERCHANTICAL, VotingType.GALACTIC_PEACE));
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MERCHANTICAL, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MERCHANTICAL, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MILITARISTIC, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MILITARISTIC, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MILITARISTIC, VotingType.GALACTIC_PEACE));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MILITARISTIC, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.MILITARISTIC, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(20, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.PEACEFUL, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.PEACEFUL, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(20, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.PEACEFUL, VotingType.GALACTIC_PEACE));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.PEACEFUL, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(-10, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.PEACEFUL, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.BAN_NUCLEAR_WEAPONS));
    assertEquals(-5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.BAN_PRIVATEER_SHIPS));
    assertEquals(5, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.GALACTIC_PEACE));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.TAXATION_OF_RICHEST_REALM));
    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.SECOND_CANDIDATE_MILITARY));

    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.RULER_OF_GALAXY));

    assertEquals(0, StarMapUtilities.getVotingSupportAccordingAttitude(
        Attitude.SCIENTIFIC, VotingType.FIRST_CANDIDATE));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportGalacticPeace() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.MILITARISTIC);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Vote vote = new Vote(VotingType.GALACTIC_PEACE, 8, 20);
    StarMap map = Mockito.mock(StarMap.class);
    assertEquals(5, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.getNumberOfWar()).thenReturn(1);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentFactory
        .createOne("DEMOCRACY"));
    assertEquals(10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(info.getTotalWarFatigue()).thenReturn(-2);
    assertEquals(15, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportTaxationOfRichest() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.MILITARISTIC);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Vote vote = new Vote(VotingType.TAXATION_OF_RICHEST_REALM, 8, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(8);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(1);
    assertEquals(-25, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(2);
    assertEquals(-17, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(3);
    assertEquals(-10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(4);
    assertEquals(-2, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(5);
    assertEquals(17, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(6);
    assertEquals(20, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(7);
    assertEquals(22, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(8);
    assertEquals(25, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportTaxationOfRichest2() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.DIPLOMATIC);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Vote vote = new Vote(VotingType.TAXATION_OF_RICHEST_REALM, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(1);
    assertEquals(-20, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(2);
    assertEquals(-10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(3);
    assertEquals(0, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(4);
    assertEquals(23, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(5);
    assertEquals(26, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(6);
    assertEquals(30, StarMapUtilities.getVotingSupport(info, vote, map));


  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportTaxationOfRichest3() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.DIPLOMATIC);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.LIKE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.DISLIKE);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Vote vote = new Vote(VotingType.TAXATION_OF_RICHEST_REALM, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(1);
    Mockito.when(map.getWealthyIndex(true)).thenReturn(0);
    Mockito.when(map.getWealthyIndex(false)).thenReturn(5);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(true);
    assertEquals(-50, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(2);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(true);
    assertEquals(-30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(3);
    assertEquals(5, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(4);
    assertEquals(-2, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(false);
    Mockito.when(diplomacy.isWar(0)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(5);
    assertEquals(36, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isWar(0)).thenReturn(false);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(6);
    assertEquals(20, StarMapUtilities.getVotingSupport(info, vote, map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportTaxationOfRichest4() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.FRIENDS);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.HATE);
    Vote vote = new Vote(VotingType.TAXATION_OF_RICHEST_REALM, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(1);
    Mockito.when(map.getWealthyIndex(true)).thenReturn(0);
    Mockito.when(map.getWealthyIndex(false)).thenReturn(5);
    Mockito.when(diplomacy.isAlliance(5)).thenReturn(true);
    assertEquals(-30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getWealthyIndex(info)).thenReturn(2);
    Mockito.when(diplomacy.isAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(true);
    assertEquals(-30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(3);
    assertEquals(-45, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(4);
    assertEquals(8, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(false);
    Mockito.when(diplomacy.isWar(5)).thenReturn(true);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(5);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.HATE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.LIKE);
    assertEquals(11, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isWar(5)).thenReturn(false);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.DISLIKE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.FRIENDS);
    Mockito.when(map.getWealthyIndex(info)).thenReturn(6);
    assertEquals(35, StarMapUtilities.getVotingSupport(info, vote, map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportSecondCandidateMilitary() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.LIKE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.FRIENDS);
    Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(true);
    assertEquals(25, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(1);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(true);
    assertEquals(30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    assertEquals(45, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(1);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    assertEquals(30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(1);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(true);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(false);
    assertEquals(-15, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(true);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(false);
    Mockito.when(diplomacy.isWar(0)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.HATE);
    assertEquals(-60, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportSecondCandidateMilitary2() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.DISLIKE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.DISLIKE);
    Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(true);
    assertEquals(-10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(1);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(1)).thenReturn(true);
    assertEquals(45, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    assertEquals(20, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(5);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(0);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    assertEquals(-20, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(5);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(0);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(false);
    Mockito.when(diplomacy.isAlliance(5)).thenReturn(true);
    assertEquals(10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(true);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(false);
    Mockito.when(diplomacy.isWar(0)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.HATE);
    assertEquals(-75, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportSecondCandidateMilitary3() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.FRIENDS);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.HATE);
    Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY, 6, 20);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(true);
    assertEquals(30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(map.getMilitaryHighest()).thenReturn(0);
    Mockito.when(map.getSecondCandidateForTower()).thenReturn(5);
    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(true);
    assertEquals(10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isWar(5)).thenReturn(true);
    assertEquals(40, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isWar(5)).thenReturn(false);
    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(true);
    assertEquals(30, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isWar(5)).thenReturn(false);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.LIKE);
    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(false);
    assertEquals(5, StarMapUtilities.getVotingSupport(info, vote, map));

  }


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportRulerOfGalaxySelf() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.FRIENDS);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.HATE);
    Vote vote = new Vote(VotingType.RULER_OF_GALAXY, 6, 20);
    vote.setOrganizerIndex(1);
    vote.setSecondCandidateIndex(5);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    assertEquals(90, StarMapUtilities.getVotingSupport(info, vote, map));

    vote.setOrganizerIndex(0);
    vote.setSecondCandidateIndex(1);
    assertEquals(-70, StarMapUtilities.getVotingSupport(info, vote, map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportRulerOfGalaxy() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.FRIENDS);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.HATE);
    Vote vote = new Vote(VotingType.RULER_OF_GALAXY, 6, 20);
    vote.setOrganizerIndex(0);
    vote.setSecondCandidateIndex(5);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(diplomacy.isAlliance(0)).thenReturn(true);
    assertEquals(60, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(true);
    assertEquals(40, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isDefensivePact(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.LIKE);
    assertEquals(25, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(0)).thenReturn(false);
    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.DISLIKE);
    assertEquals(-5, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeEmbargo(0)).thenReturn(false);
    Mockito.when(diplomacy.isWar(0)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.HATE);
    assertEquals(-30, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSupportRulerOfGalaxy2() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(info.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(diplomacy.getLiking(0)).thenReturn(Diplomacy.DISLIKE);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.FRIENDS);
    Vote vote = new Vote(VotingType.RULER_OF_GALAXY, 6, 20);
    vote.setOrganizerIndex(0);
    vote.setSecondCandidateIndex(5);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getIndex(info)).thenReturn(1);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(6);
    Mockito.when(diplomacy.isAlliance(5)).thenReturn(true);
    assertEquals(-55, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(true);
    assertEquals(-35, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isDefensivePact(5)).thenReturn(false);
    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.LIKE);
    assertEquals(-20, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeAlliance(5)).thenReturn(false);
    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.DISLIKE);
    assertEquals(10, StarMapUtilities.getVotingSupport(info, vote, map));

    Mockito.when(diplomacy.isTradeEmbargo(5)).thenReturn(false);
    Mockito.when(diplomacy.isWar(5)).thenReturn(true);
    Mockito.when(diplomacy.getLiking(5)).thenReturn(Diplomacy.HATE);
    assertEquals(35, StarMapUtilities.getVotingSupport(info, vote, map));

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDirection() {
    int degree = StarMapUtilities.getDirection(3, 3, 6, 6);
    assertEquals(315, degree);
    degree = StarMapUtilities.getDirection(3, 6, 6, 3);
    assertEquals(45, degree);
    degree = StarMapUtilities.getDirection(6, 6, 3, 3);
    assertEquals(135, degree);
    degree = StarMapUtilities.getDirection(6, 3, 3, 6);
    assertEquals(225, degree);
    degree = StarMapUtilities.getDirection(80, 40, 15, 20);
    assertEquals(107, degree);
    degree = StarMapUtilities.getDirection(40, 40, 40, 20);
    assertEquals(90, degree);
    degree = StarMapUtilities.getDirection(40, 20, 40, 40);
    assertEquals(270, degree);
    degree = StarMapUtilities.getDirection(80, 20, 40, 20);
    assertEquals(180, degree);
    degree = StarMapUtilities.getDirection(10, 20, 40, 20);
    assertEquals(0, degree);
    degree = StarMapUtilities.getDirection(20, 20, 20, 20);
    assertEquals(270, degree);
  }

}
