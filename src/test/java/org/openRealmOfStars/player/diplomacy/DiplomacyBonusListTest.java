package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
 * Tests for Diplomacy Bonus List
 */
public class DiplomacyBonusListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    assertEquals(1, list.getPlayerIndex());
    assertEquals(0, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAdd() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.IN_WAR, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(-30, list.getDiplomacyBonus());
    result = list.addBonus(DiplomacyBonusType.IN_WAR, SpaceRace.HUMAN);
    assertEquals(false, result);
    assertEquals(-30, list.getDiplomacyBonus());
    result = list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(-25, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHandleTurn() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE, SpaceRace.HUMAN);
    assertEquals(true, result);
    for (int i = 0; i < 80; i++) {
      assertEquals(5, list.getDiplomacyBonus());
      list.handleForTurn();
    }
    assertEquals(0, list.getDiplomacyBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeaceAndWar() {
    DiplomacyBonusList list = new DiplomacyBonusList(1);
    boolean result = list.addBonus(DiplomacyBonusType.LONG_PEACE, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(5, list.getDiplomacyBonus());
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_TRADE_ALLIANCE, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_ALLIANCE, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.IN_WAR, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(false,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_ALLIANCE));
    assertEquals(true,list.isBonusType(DiplomacyBonusType.IN_WAR));
    result = list.addBonus(DiplomacyBonusType.LONG_PEACE, SpaceRace.HUMAN);
    assertEquals(true, result);
    assertEquals(true,list.isBonusType(DiplomacyBonusType.LONG_PEACE));
    assertEquals(false,list.isBonusType(DiplomacyBonusType.IN_WAR));
  }

}
