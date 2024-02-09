package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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

/**
*
* Intelligence Bonus Test
*
*/
public class IntelligenceBonusTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    IntelligenceBonus bonus = new IntelligenceBonus(IntelligenceBonusType.TRADE, 2);
    assertEquals(IntelligenceBonusType.TRADE, bonus.getType());
    assertEquals(2, bonus.getValue());
    bonus = new IntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 3);
    assertEquals("SPY_FLEET -  : 3", bonus.toString());
    assertEquals(IntelligenceBonusType.SPY_FLEET, bonus.getType());
    assertEquals(3, bonus.getValue());
    bonus = new IntelligenceBonus(IntelligenceBonusType.OWN_REALM, 10);
    assertEquals(IntelligenceBonusType.OWN_REALM, bonus.getType());
    assertEquals(10, bonus.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescription() {
    IntelligenceBonus bonus = new IntelligenceBonus(IntelligenceBonusType.TRADE, 2);
    bonus.setDescription("Test");
    assertEquals(IntelligenceBonusType.TRADE, bonus.getType());
    assertEquals(2, bonus.getValue());
    assertEquals("Test", bonus.getDescription());
    bonus = new IntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 3);
    bonus.setDescription("Test");
    assertEquals(IntelligenceBonusType.SPY_FLEET, bonus.getType());
    assertEquals(3, bonus.getValue());
    assertEquals("Test", bonus.getDescription());
    bonus.setDescription("");
    assertEquals("", bonus.getDescription());
  }

}
