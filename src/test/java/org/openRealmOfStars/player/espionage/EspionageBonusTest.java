package org.openRealmOfStars.player.espionage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
*
* Espionage Bonus Test
*
*/
public class EspionageBonusTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    EspionageBonus bonus = new EspionageBonus(EspionageBonusType.TRADE, 2);
    assertEquals(EspionageBonusType.TRADE, bonus.getType());
    assertEquals(2, bonus.getValue());
    bonus = new EspionageBonus(EspionageBonusType.SPY_FLEET, 3);
    assertEquals(EspionageBonusType.SPY_FLEET, bonus.getType());
    assertEquals(3, bonus.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescription() {
    EspionageBonus bonus = new EspionageBonus(EspionageBonusType.TRADE, 2);
    bonus.setDescription("Test");
    assertEquals(EspionageBonusType.TRADE, bonus.getType());
    assertEquals(2, bonus.getValue());
    assertEquals("Test", bonus.getDescription());
    bonus = new EspionageBonus(EspionageBonusType.SPY_FLEET, 3);
    bonus.setDescription("Test");
    assertEquals(EspionageBonusType.SPY_FLEET, bonus.getType());
    assertEquals(3, bonus.getValue());
    assertEquals("Test", bonus.getDescription());
    bonus.setDescription("");
    assertEquals("", bonus.getDescription());
  }

}
