package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
* Intelligence Test
*
*/
public class IntelligenceTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Intelligence espionage = new Intelligence(4);
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
    Intelligence espionage = new Intelligence(4);
    assertEquals(4, espionage.getSize());
    assertEquals(false, espionage.isSpyTradePossible());
    IntelligenceList list = espionage.getByIndex(1);
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Test 1");
    assertEquals(true, espionage.isSpyTradePossible());
    assertEquals(2, list.getTotalBonus());
    espionage.clearAllIntelligenceBonuses();
    list = espionage.getByIndex(1);
    assertEquals(0, list.getTotalBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCost() {
    assertEquals(0, Intelligence.calculateIntelligenceCost(100));
    assertEquals(0, Intelligence.calculateIntelligenceCost(80));
    assertEquals(1, Intelligence.calculateIntelligenceCost(79));
    assertEquals(1, Intelligence.calculateIntelligenceCost(70));
    assertEquals(2, Intelligence.calculateIntelligenceCost(60));
    assertEquals(3, Intelligence.calculateIntelligenceCost(50));
    assertEquals(0, Intelligence.calculateIntelligenceCost(110));
    assertEquals(0, Intelligence.calculateIntelligenceCost(120));
    assertEquals(1, Intelligence.calculateIntelligenceCost(130));
    assertEquals(2, Intelligence.calculateIntelligenceCost(140));
    assertEquals(4, Intelligence.calculateIntelligenceCost(160));
    assertEquals(8, Intelligence.calculateIntelligenceCost(200));
  }

}
