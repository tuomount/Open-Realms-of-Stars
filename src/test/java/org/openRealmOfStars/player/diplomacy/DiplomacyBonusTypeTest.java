package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017,2018,2020 Tuomo Untinen
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
 * Tests for Diplomacy Bonus Type Test
 */
public class DiplomacyBonusTypeTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIndexes() {
    for (int i = 0;i < 16; i++) {
      assertEquals(i,DiplomacyBonusType.getTypeByIndex(i).getIndex());
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOutOfBound() {
     DiplomacyBonusType.getTypeByIndex(255).getIndex();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCasusBelliScore() {
    assertEquals(0, DiplomacyBonusType.IN_WAR.getCasusBelliScore());
    assertEquals(5, DiplomacyBonusType.WAR_DECLARTION.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.IN_TRADE_ALLIANCE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.IN_ALLIANCE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.DIPLOMAT_CAPTURED.getCasusBelliScore());
    assertEquals(3, DiplomacyBonusType.BORDER_CROSSED.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.GIVEN_VALUABLE_FREE.getCasusBelliScore());
    assertEquals(7, DiplomacyBonusType.MADE_DEMAND.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.DIPLOMATIC_TRADE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.SAME_RACE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.LONG_PEACE.getCasusBelliScore());
    assertEquals(5, DiplomacyBonusType.INSULT.getCasusBelliScore());
    assertEquals(4, DiplomacyBonusType.NUKED.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.NOTHING_TO_TRADE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.IN_DEFENSIVE_PACT.getCasusBelliScore());
    assertEquals(5, DiplomacyBonusType.ESPIONAGE_BORDER_CROSS.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.SPY_TRADE.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.DIPLOMACY_BONUS.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.TRADE_FLEET.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.BOARD_PLAYER.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.EMBARGO.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.LIKED_EMBARGO.getCasusBelliScore());
    assertEquals(2, DiplomacyBonusType.DISLIKED_EMBARGO.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.REALM_LOST.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.OLYMPICS.getCasusBelliScore());
    assertEquals(1, DiplomacyBonusType.DNS_OLYMPICS.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.OLYMPICS_EMBARGO.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.PROMISED_VOTE_YES.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.PROMISED_VOTE_NO.getCasusBelliScore());
    assertEquals(0, DiplomacyBonusType.PROMISE_KEPT.getCasusBelliScore());
    assertEquals(4, DiplomacyBonusType.PROMISE_BROKEN.getCasusBelliScore());
    assertEquals(8, DiplomacyBonusType.WAR_DECLARATION_AGAINST_US.getCasusBelliScore());
  }

}
