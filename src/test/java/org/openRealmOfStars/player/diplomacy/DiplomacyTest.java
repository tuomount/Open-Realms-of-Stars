package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
  public void testInWar() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRace.SPORKS);
    assertEquals(true, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(-30, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.HATE, diplomacy.getLiking(0));
    assertEquals(false, diplomacy.isWar(256));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRace.SPORKS);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.WAR_DECLARTION,
        SpaceRace.SPORKS);
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.DIPLOMAT_CAPTURED,
        SpaceRace.SPORKS);
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.DIPLOMAT_CAPTURED,
        SpaceRace.SPORKS);
    assertEquals(Diplomacy.DISLIKE, diplomacy.getLiking(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInTradeAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_TRADE_ALLIANCE, SpaceRace.HUMAN);
    assertEquals(true, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(12, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.LIKE, diplomacy.getLiking(0));
    assertEquals(false, diplomacy.isTradeAlliance(256));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_ALLIANCE, SpaceRace.CENTAURS);
    assertEquals(25, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.FRIENDS, diplomacy.getLiking(0));
    assertEquals(true, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(256));
  }

}
