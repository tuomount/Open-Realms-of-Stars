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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;

/**
 * Tests for Diplomacy Bonus List
 */
public class DiplomacyBonusListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    assertEquals(1, list.getPlayerIndex());
    assertEquals(0, list.getDiplomacyBonus());
    assertEquals(0, list.getNumberOfMeetings());
    list.setNumberOfMeetings(list.getNumberOfMeetings()+1);
    assertEquals(1, list.getNumberOfMeetings());
    list.setNumberOfMeetings(list.getNumberOfMeetings()+1);
    assertEquals(2, list.getNumberOfMeetings());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAdd() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(-30, list.getDiplomacyBonus());
    DiplomacyBonus bonus = list.get(0);
    assertEquals(DiplomacyBonusType.IN_WAR, bonus.getType());
    result = list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(false, result);
    assertEquals(-30, list.getDiplomacyBonus());
    result = list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(-26, list.getDiplomacyBonus());
    bonus = list.get(1);
    assertEquals(DiplomacyBonusType.DIPLOMATIC_TRADE, bonus.getType());
    result = list.addBonus(DiplomacyBonusType.GIVEN_VALUABLE_FREE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    bonus = list.get(2);
    assertEquals(DiplomacyBonusType.GIVEN_VALUABLE_FREE, bonus.getType());
    result = list.addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    bonus = list.get(0);
    assertEquals(DiplomacyBonusType.DIPLOMATIC_TRADE, bonus.getType());
    bonus = list.get(1);
    assertEquals(DiplomacyBonusType.GIVEN_VALUABLE_FREE, bonus.getType());
    bonus = list.get(2);
    assertEquals(DiplomacyBonusType.LONG_PEACE, bonus.getType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTypes() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    list.addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.GIVEN_VALUABLE_FREE,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
        SpaceRaceFactory.createOne("HUMANS"));
    list.addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("HUMANS"));
    DiplomacyBonusType[] types = list.getTypes();
    assertEquals(4, types.length);
    int value = list.getTotalBonusForType(DiplomacyBonusType.BORDER_CROSSED);
    assertEquals(-9, value);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCasusBelliScore() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(3, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(3, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.NUKED,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(7, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(12, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.BORDER_CROSSED,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(15, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.DNS_OLYMPICS,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(16, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.MADE_DEMAND,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(23, list.getCasusBelliScore());
    list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(23, list.getCasusBelliScore());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCheckPromiseNoVoteNo() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.PROMISED_VOTE_NO,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(3, list.getDiplomacyBonus());
    list.checkPromise(VotingChoice.VOTED_NO,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(8, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCheckPromiseNoVoteYes() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.PROMISED_VOTE_NO,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(3, list.getDiplomacyBonus());
    list.checkPromise(VotingChoice.VOTED_YES,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(-8, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCheckPromiseYesVoteYes() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.PROMISED_VOTE_YES,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(3, list.getDiplomacyBonus());
    list.checkPromise(VotingChoice.VOTED_YES,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(8, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCheckPromiseYesVoteNo() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.PROMISED_VOTE_YES,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(3, list.getDiplomacyBonus());
    list.checkPromise(VotingChoice.VOTED_NO,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(-8, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAdd2() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    assertEquals(0, list.getListSize());
    boolean result = list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(1, list.getListSize());
    DiplomacyBonus bonus = Mockito.mock(DiplomacyBonus.class);
    list.addBonus(bonus);
    assertEquals(2,list.getListSize());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHandleTurn() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    for (int i = 0; i < 110; i++) {
      list.handleForTurn();
    }
    assertEquals(0, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeaceAndWar() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(5, list.getDiplomacyBonus());
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_TRADE_ALLIANCE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_ALLIANCE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_DEFENSIVE_PACT,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.TRADE_FLEET,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.TRADE_FLEET));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(false,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.TRADE_FLEET));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
  }

}
