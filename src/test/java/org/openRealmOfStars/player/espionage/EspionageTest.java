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
* Espionage Test
*
*/
public class EspionageTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Espionage espionage = new Espionage(4);
    assertEquals(4, espionage.getSize());
    assertNotNull(espionage.getByIndex(0));
    assertEquals(null, espionage.getByIndex(-1));
    assertNotNull(espionage.getByIndex(1));
    assertNotNull(espionage.getByIndex(2));
    assertNotNull(espionage.getByIndex(3));
    assertEquals(null, espionage.getByIndex(4));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testClear() {
    Espionage espionage = new Espionage(4);
    assertEquals(4, espionage.getSize());
    assertEquals(false, espionage.isSpyTradePossible());
    EspionageList list = espionage.getByIndex(1);
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test 1");
    assertEquals(true, espionage.isSpyTradePossible());
    assertEquals(2, list.getTotalBonus());
    espionage.clearAllEspionageBonuses();
    list = espionage.getByIndex(1);
    assertEquals(0, list.getTotalBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCost() {
    assertEquals(0, Espionage.calculateEspionageCost(100));
    assertEquals(0, Espionage.calculateEspionageCost(80));
    assertEquals(1, Espionage.calculateEspionageCost(79));
    assertEquals(1, Espionage.calculateEspionageCost(70));
    assertEquals(2, Espionage.calculateEspionageCost(60));
    assertEquals(3, Espionage.calculateEspionageCost(50));
    assertEquals(0, Espionage.calculateEspionageCost(110));
    assertEquals(0, Espionage.calculateEspionageCost(120));
    assertEquals(1, Espionage.calculateEspionageCost(130));
    assertEquals(2, Espionage.calculateEspionageCost(140));
    assertEquals(4, Espionage.calculateEspionageCost(160));
    assertEquals(8, Espionage.calculateEspionageCost(200));
  }

}
