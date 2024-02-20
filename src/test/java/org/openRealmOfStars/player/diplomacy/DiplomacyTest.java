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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.state.DiplomacyView;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.espionage.Intelligence;
import org.openRealmOfStars.player.espionage.IntelligenceList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.StarMap;

/**
 * Tests for Diplomacy
 */
public class DiplomacyTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(2));
    assertNotEquals(null, diplomacy.getDiplomacyList(3));
    assertEquals(null, diplomacy.getDiplomacyList(1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicWithBoard() {
    Diplomacy diplomacy = new Diplomacy(4, 1, 3);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(2));
    assertNotEquals(null, diplomacy.getDiplomacyList(3));
    assertEquals(null, diplomacy.getDiplomacyList(1));
    assertEquals(false,
        diplomacy.getDiplomacyList(0).isBonusType(DiplomacyBonusType.BOARD_PLAYER));
    assertEquals(false,
        diplomacy.getDiplomacyList(2).isBonusType(DiplomacyBonusType.BOARD_PLAYER));
    assertEquals(true,
        diplomacy.getDiplomacyList(3).isBonusType(DiplomacyBonusType.BOARD_PLAYER));
    assertEquals(0, diplomacy.getNumberOfWar());
    diplomacy.getDiplomacyList(2).addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(1, diplomacy.getNumberOfWar());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    Diplomacy diplomacy = new Diplomacy(4);
    assertEquals(4, diplomacy.getDiplomacySize());
    assertEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(null, diplomacy.getDiplomacyList(1));
    assertEquals(null, diplomacy.getDiplomacyList(2));
    assertEquals(null, diplomacy.getDiplomacyList(3));
    DiplomacyBonusList list = Mockito.mock(DiplomacyBonusList.class);
    diplomacy.setList(list, 0);
    assertEquals(list, diplomacy.getDiplomacyList(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsMultipleBorderCrossing() {
    Diplomacy diplomacy = new Diplomacy(4);
    assertEquals(4, diplomacy.getDiplomacySize());
    assertEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(null, diplomacy.getDiplomacyList(1));
    assertEquals(null, diplomacy.getDiplomacyList(2));
    assertEquals(null, diplomacy.getDiplomacyList(3));
    DiplomacyBonusList list = Mockito.mock(DiplomacyBonusList.class);
    DiplomacyBonus bonusBorderCross = Mockito.mock(DiplomacyBonus.class);
    Mockito.when(bonusBorderCross.getType()).thenReturn(DiplomacyBonusType.BORDER_CROSSED);
    DiplomacyBonus bonus = Mockito.mock(DiplomacyBonus.class);
    Mockito.when(bonus.getType()).thenReturn(DiplomacyBonusType.DIPLOMATIC_TRADE);
    Mockito.when(list.getListSize()).thenReturn(5);
    Mockito.when(list.get(0)).thenReturn(bonus);
    Mockito.when(list.get(1)).thenReturn(bonusBorderCross);
    Mockito.when(list.get(2)).thenReturn(bonusBorderCross);
    Mockito.when(list.get(3)).thenReturn(bonus);
    Mockito.when(list.get(4)).thenReturn(bonusBorderCross);
    diplomacy.setList(list, 0);
    assertEquals(true, diplomacy.isMultipleBorderCrossing(0));
    Mockito.when(list.get(1)).thenReturn(bonus);
    Mockito.when(list.get(2)).thenReturn(bonus);
    Mockito.when(list.get(4)).thenReturn(bonus);
    assertEquals(false, diplomacy.isMultipleBorderCrossing(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInWar() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals("", diplomacy.getDiplomaticRelation(0));
    assertEquals(0, diplomacy.getNumberOfWar());
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("SPORKS"));
    assertEquals(1, diplomacy.getNumberOfWar());
    assertEquals("War", diplomacy.getDiplomaticRelation(0));
    assertEquals(true, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(-30, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.HATE, diplomacy.getLiking(0));
    assertEquals("Hate", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DESTROYED, diplomacy.getLikingAsColor(0));
    assertEquals(false, diplomacy.isWar(256));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRaceFactory.createOne("SPORKS"));
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertEquals("Peace", diplomacy.getDiplomaticRelation(0));
    assertEquals("Neutral", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_HALF, diplomacy.getLikingAsColor(0));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.WAR_DECLARTION,
        SpaceRaceFactory.createOne("SPORKS"));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
        SpaceRaceFactory.createOne("SPORKS"));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("SPORKS"));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.INSULT,
        SpaceRaceFactory.createOne("SPORKS"));
    assertEquals(Diplomacy.DISLIKE, diplomacy.getLiking(0));
    assertEquals("Dislike", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_MUCH, diplomacy.getLikingAsColor(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsLost() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertEquals(false, diplomacy.isLost(0));
    assertEquals(0, diplomacy.getNumberOfWar());
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("SPORKS"));
    assertEquals(1, diplomacy.getNumberOfWar());
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.REALM_LOST,
        SpaceRaceFactory.createOne("SPORKS"));
    assertEquals(0, diplomacy.getNumberOfWar());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInTradeAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_TRADE_ALLIANCE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals("Trade alliance", diplomacy.getDiplomaticRelation(0));
    assertEquals(true, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(12, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.LIKE, diplomacy.getLiking(0));
    assertEquals("Like", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_LITTLE, diplomacy.getLikingAsColor(0));
    assertEquals(false, diplomacy.isTradeAlliance(256));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInTradeEmbargo() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isTradeEmbargo(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.EMBARGO,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals("Trade embargo", diplomacy.getDiplomaticRelation(0));
    assertEquals(true, diplomacy.isTradeEmbargo(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(-5, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertEquals("Neutral", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_HALF, diplomacy.getLikingAsColor(0));
    assertEquals(false, diplomacy.isTradeEmbargo(256));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.hasAlliance());
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_ALLIANCE,
        SpaceRaceFactory.createOne("CENTAURS"));
    assertEquals("Alliance", diplomacy.getDiplomaticRelation(0));
    assertEquals(25, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.FRIENDS, diplomacy.getLiking(0));
    assertEquals("Friends", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_GREEN_TEXT, diplomacy.getLikingAsColor(0));
    assertEquals(true, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(256));
    assertEquals(true, diplomacy.hasAlliance());
    assertEquals(0, diplomacy.getAllianceIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInDefensivePact() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_DEFENSIVE_PACT,
        SpaceRaceFactory.createOne("CENTAURS"));
    assertEquals("Defensive pact", diplomacy.getDiplomaticRelation(0));
    assertEquals(25, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.FRIENDS, diplomacy.getLiking(0));
    assertEquals("Friends", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_GREEN_TEXT, diplomacy.getLikingAsColor(0));
    assertEquals(true, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(256));
    assertEquals(false, diplomacy.isDefensivePact(256));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHasDefensivePact() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isDefensivePact(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.hasDefensivePact());
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_DEFENSIVE_PACT,
        SpaceRaceFactory.createOne("CENTAURS"));
    assertEquals(true, diplomacy.hasDefensivePact());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInDefensivePactActivation() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_DEFENSIVE_PACT,
        SpaceRaceFactory.createOne("CENTAURS"));
    StarMap starMap = Mockito.mock(StarMap.class);
    Diplomacy diplomacyCentaur = new Diplomacy(4, 0);
    Diplomacy diplomacyAttacker = new Diplomacy(4, 2);
    Diplomacy diplomacyByStander = new Diplomacy(4, 3);
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo byStander = Mockito.mock(PlayerInfo.class);
    Mockito.when(byStander.getDiplomacy()).thenReturn(diplomacyByStander);
    PlayerInfo centaur = Mockito.mock(PlayerInfo.class);
    Mockito.when(centaur.getEmpireName()).thenReturn("Centaur empire");
    Mockito.when(centaur.getDiplomacy()).thenReturn(diplomacyCentaur);
    PlayerInfo attacker = Mockito.mock(PlayerInfo.class);
    Mockito.when(attacker.getDiplomacy()).thenReturn(diplomacyAttacker);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getIndex(byStander)).thenReturn(3);
    Mockito.when(playerList.getIndex(attacker)).thenReturn(2);
    Mockito.when(playerList.getIndex(centaur)).thenReturn(0);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(3);
    Mockito.when(starMap.getPlayerList()).thenReturn(playerList);
    Mockito.when(starMap.getPlayerByIndex(0)).thenReturn(centaur);
    Mockito.when(starMap.getPlayerByIndex(1)).thenReturn(defender);
    Mockito.when(starMap.getPlayerByIndex(2)).thenReturn(attacker);
    Mockito.when(starMap.getPlayerByIndex(3)).thenReturn(byStander);
    String[] members = diplomacy.activateDefensivePact(starMap, attacker);
    assertEquals(1, members.length);
    assertEquals("Centaur empire", members[0]);
    diplomacy = new Diplomacy(4, 1);
    Mockito.when(defender.getDiplomacy()).thenReturn(diplomacy);
    members = diplomacy.activateDefensivePact(starMap, attacker);
    assertNull(members);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpyTrade() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.SPY_TRADE,
        SpaceRaceFactory.createOne("CENTAURS"));
    assertEquals(20, diplomacy.getSpyTradeLasting(0));
    assertEquals(true, diplomacy.isSpyTrade(0));
    assertEquals(0, diplomacy.getSpyTradeLasting(1));
    assertEquals(false, diplomacy.isSpyTrade(1));
    diplomacy.updateDiplomacyLastingForTurn();
    assertEquals(19, diplomacy.getSpyTradeLasting(0));
    assertEquals(true, diplomacy.isSpyTrade(0));
    assertEquals(0, diplomacy.getSpyTradeLasting(1));
    assertEquals(false, diplomacy.isSpyTrade(1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLeastLiking() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    DiplomacyBonusList list0 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list0.getDiplomacyBonus()).thenReturn(25);
    DiplomacyBonusList list2 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list2.getDiplomacyBonus()).thenReturn(-11);
    DiplomacyBonusList list3 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list3.getDiplomacyBonus()).thenReturn(10);
    diplomacy.setList(list0, 0);
    diplomacy.setList(list2, 2);
    diplomacy.setList(list3, 3);
    assertEquals(2, diplomacy.getLeastLiking());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLeastLikingWithEspionage() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    DiplomacyBonusList list0 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list0.getDiplomacyBonus()).thenReturn(25);
    DiplomacyBonusList list2 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list2.getDiplomacyBonus()).thenReturn(-11);
    DiplomacyBonusList list3 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list3.getDiplomacyBonus()).thenReturn(11);
    diplomacy.setList(list0, 0);
    diplomacy.setList(list2, 2);
    diplomacy.setList(list3, 3);
    Intelligence espionage = Mockito.mock(Intelligence.class);
    IntelligenceList eList0 = Mockito.mock(IntelligenceList.class);
    Mockito.when(eList0.getTotalBonus()).thenReturn(5);
    Mockito.when(espionage.getByIndex(0)).thenReturn(eList0);
    IntelligenceList eList2 = Mockito.mock(IntelligenceList.class);
    Mockito.when(eList2.getTotalBonus()).thenReturn(2);
    Mockito.when(espionage.getByIndex(2)).thenReturn(eList2);
    IntelligenceList eList3 = Mockito.mock(IntelligenceList.class);
    Mockito.when(eList3.getTotalBonus()).thenReturn(0);
    Mockito.when(espionage.getByIndex(3)).thenReturn(eList3);
    assertEquals(2, diplomacy.getLeastLikingWithLowEspionage(espionage));
    Mockito.when(list3.getDiplomacyBonus()).thenReturn(-13);
    assertEquals(3, diplomacy.getLeastLikingWithLowEspionage(espionage));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLeastLikingAllAboveCharts() {
    Diplomacy diplomacy = new Diplomacy(4, 0);
    DiplomacyBonusList list1 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list1.getDiplomacyBonus()).thenReturn(10000);
    DiplomacyBonusList list2 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list2.getDiplomacyBonus()).thenReturn(10000);
    DiplomacyBonusList list3 = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(list3.getDiplomacyBonus()).thenReturn(10000);
    diplomacy.setList(list1, 1);
    diplomacy.setList(list2, 2);
    diplomacy.setList(list3, 3);
    assertEquals(1, diplomacy.getLeastLiking());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBorderCrossing() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    int index = 0;
    Mockito.when(diplomacy.isTradeAlliance(index)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(index)).thenReturn(false);
    Mockito.when(diplomacy.isAlliance(index)).thenReturn(false);
    Mockito.when(diplomacy.isWar(index)).thenReturn(false);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Fleet fleet = Mockito.mock(Fleet.class);
    int type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_BORDER_CROSS, type);
    Mockito.when(diplomacy.isTradeAlliance(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_REGULAR, type);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(5);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_BORDER_CROSS, type);
    Mockito.when(diplomacy.isDefensivePact(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_REGULAR, type);
    Mockito.when(diplomacy.isAlliance(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_REGULAR, type);
    Mockito.when(fleet.getFleetCloackingValue()).thenReturn(0);
    Mockito.when(fleet.getEspionageBonus()).thenReturn(1);
    Mockito.when(info.getSectorCloakDetection(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(50);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_ESPIONAGE, type);
    Mockito.when(diplomacy.isWar(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_REGULAR, type);
    Mockito.when(diplomacy.isWar(index)).thenReturn(false);
    Mockito.when(fleet.isPrivateerFleet()).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.AI_REGULAR, type);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBorderCrossingHuman() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.isHuman()).thenReturn(true);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    int index = 2;
    Mockito.when(diplomacy.isTradeAlliance(index)).thenReturn(false);
    Mockito.when(diplomacy.isDefensivePact(index)).thenReturn(false);
    Mockito.when(diplomacy.isAlliance(index)).thenReturn(false);
    Mockito.when(diplomacy.isWar(index)).thenReturn(false);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Fleet fleet = Mockito.mock(Fleet.class);
    int type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_BORDER_CROSS, type);
    Mockito.when(diplomacy.isTradeAlliance(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_REGULAR, type);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(5);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_BORDER_CROSS, type);
    Mockito.when(diplomacy.isDefensivePact(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_REGULAR, type);
    Mockito.when(diplomacy.isAlliance(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_REGULAR, type);
    Mockito.when(fleet.getFleetCloackingValue()).thenReturn(0);
    Mockito.when(fleet.getEspionageBonus()).thenReturn(1);
    Mockito.when(info.getSectorCloakDetection(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(50);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_ESPIONAGE, type);
    Mockito.when(diplomacy.isWar(index)).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_REGULAR, type);
    Mockito.when(diplomacy.isWar(index)).thenReturn(false);
    Mockito.when(fleet.isPrivateerFleet()).thenReturn(true);
    type = Diplomacy.getBorderCrossingType(info, fleet, index);
    assertEquals(DiplomacyView.HUMAN_REGULAR, type);
  }

}
