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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.race.SpaceRaceFactory;

/**
 * Tests for Diplomacy Bonus
 */
public class DiplomacyBonusTest {


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    DiplomacyBonus bonus = new DiplomacyBonus(
        DiplomacyBonusType.getTypeByIndex(0),
        SpaceRaceFactory.createOne("HUMANS"));
    bonus.setBonusLasting(9);
    bonus.setBonusValue(5);
    assertEquals(9, bonus.getBonusLasting());
    assertEquals(5, bonus.getBonusValue());
    bonus.setBonusLasting(256);
    assertEquals(255, bonus.getBonusLasting());
    bonus.setBonusLasting(-1);
    assertEquals(0, bonus.getBonusLasting());
    assertEquals(0, bonus.getBonusValue());
    bonus.setBonusLasting(10);
    bonus.setBonusValue(12);
    assertEquals(10, bonus.getBonusLasting());
    assertEquals(12, bonus.getBonusValue());
    bonus.setBonusLasting(0);
    assertEquals(0, bonus.getBonusLasting());
    assertEquals(0, bonus.getBonusValue());
  }
}
